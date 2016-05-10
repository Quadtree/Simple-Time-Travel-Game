package info.quadtree.sttg.world;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.random.MersenneTwister;

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

	List<Unit> units;

	public WorldState(long baseSeed) {
		this.baseSeed = baseSeed;

		rng = new FixedDRNG();

		MersenneTwister localRng = new MersenneTwister(rng.randomLong(0));

		terrain = new ArrayList<>();
		for (int x = 0; x < WORLD_SIZE; ++x) {
			terrain.add(new ArrayList<TerrainType>());
			for (int y = 0; y < WORLD_SIZE; ++y) {
				terrain.get(x).add(localRng.nextInt(4) == 0 ? TerrainType.GRASS : TerrainType.GRASS2);
			}
		}

		units = new ArrayList<Unit>();

		for (int i = 0; i < 2; ++i) {
			for (int j = 0; j < 50; ++j) {
				Person p = new Person(this, new WorldPosition(i * 400 + 100 + rng.randomInt(70, i * 100000 + j * 100) - 35, 256 + rng.randomInt(70, i * 100000 + j * 100) - 35));

				units.add(p);
			}
		}
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

}
