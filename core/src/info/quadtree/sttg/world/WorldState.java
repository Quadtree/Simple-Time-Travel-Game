package info.quadtree.sttg.world;

import java.util.ArrayList;
import java.util.List;

import info.quadtree.sttg.world.thing.Person;
import info.quadtree.sttg.world.thing.Thing;

/**
 * The current state of the world, in macro terms. Typically multiple world
 * states will be kept around, for rewinding
 *
 * @author quadtree
 *
 */
public class WorldState implements DeterministicRNG {
	public static final long TICKS_PER_DAY = 864000;
	public static final long TICKS_PER_YEAR = 315360000;

	public static final int WORLD_SIZE = 512;

	long baseSeed;

	/**
	 * This particular world's current tick. Ticks are 1/10 of a second long
	 *
	 * Years are 365 days long (there are no leap years). This means there are
	 * 315,360,000 ticks per year.
	 */
	long currentTick;

	DeterministicRNG rng;

	List<List<TerrainType>> terrain;

	List<Thing> things;

	public WorldState(long baseSeed) {
		this.baseSeed = baseSeed;

		rng = new FixedDRNG();

		terrain = new ArrayList<>();
		for (int x = 0; x < WORLD_SIZE; ++x) {
			terrain.add(new ArrayList<TerrainType>());
			for (int y = 0; y < WORLD_SIZE; ++y) {
				terrain.get(x).add(rng.randomInt(4, x, y, baseSeed + 3984893) == 0 ? TerrainType.GRASS : TerrainType.GRASS2);
			}
		}

		things = new ArrayList<>();

		for (int i = 0; i < 2; ++i) {

			List<Person> townPeople = new ArrayList<Person>();

			for (int j = 0; j < 50; ++j) {
				Person p = new Person(this, new WorldPosition(i * 400 + 100 + rng.randomInt(70, i * 100000 + j * 100) - 35, 256 + rng.randomInt(70, i * 100000 + j * 100) - 35));

				things.add(p);
				townPeople.add(p);
			}

			for (Person p : townPeople) {
				for (Person p2 : townPeople) {
					p.modOpinionOf(p2, 50.0);
				}
			}
		}
	}

	public void addThing(Thing thing) {
		this.things.add(thing);
	}

	public TerrainType getTerrainTypeAt(int x, int y) {
		if (x < 0 || y < 0 || x >= WORLD_SIZE || y >= WORLD_SIZE)
			return TerrainType.WATER;

		return terrain.get(x).get(y);
	}

	@Override
	public int randomInt(int maxExclusive, int x, int y, long additionalSeedMaterial) {
		return rng.randomInt(maxExclusive, x, y, additionalSeedMaterial ^ baseSeed ^ currentTick);
	}

	@Override
	public int randomInt(int maxExclusive, long additionalSeedMaterial) {
		return rng.randomInt(maxExclusive, additionalSeedMaterial ^ baseSeed ^ currentTick);
	}

	@Override
	public int randomInt(int maxExclusive, WorldPosition pos, long additionalSeedMaterial) {
		return rng.randomInt(maxExclusive, pos, additionalSeedMaterial ^ baseSeed ^ currentTick);
	}

	@Override
	public long randomLong(long additionalSeedMaterial) {
		return rng.randomLong(additionalSeedMaterial ^ baseSeed ^ currentTick);
	}

	@Override
	public long randomLong(WorldPosition pos, long additionalSeedMaterial) {
		return rng.randomLong(pos, additionalSeedMaterial ^ baseSeed ^ currentTick);
	}

	/**
	 * Moves this world state forwards to a particular tick
	 *
	 * @param ticks
	 */
	public void seek(long ticks) {
		if (ticks < currentTick)
			throw new UnsupportedOperationException("An individual state cannot reverse time");

		long steps = 0;

		while (currentTick <= ticks) {
			if (ticks - currentTick > WorldState.TICKS_PER_YEAR + WorldState.TICKS_PER_DAY) {
				update(WorldState.TICKS_PER_YEAR);
			} else if (ticks - currentTick > WorldState.TICKS_PER_DAY) {
				update(ticks - WorldState.TICKS_PER_DAY - currentTick);
			} else {
				update(1);
			}
			++steps;
		}

		System.err.println("Seek to " + ticks + " complete, " + steps + " steps");
	}

	protected void update(long ticks) {
		currentTick += ticks;
		// System.out.println("Now at tick " + currentTick);

		for (int i = 0; i < things.size(); ++i) {
			if (things.get(i).keep()) {
				things.get(i).update(ticks);
			} else {
				things.get(i).destroyed();
				things.remove(i--);
			}
		}
	}

}
