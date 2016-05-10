package info.quadtree.sttg.world;

import org.apache.commons.math3.random.MersenneTwister;

public class FixedDRNG implements DeterministicRNG {
	MersenneTwister rand = new MersenneTwister();

	/*
	 * (non-Javadoc)
	 *
	 * @see info.quadtree.sttg.world.DeterministicRNG#randomInt(int, int, int,
	 * long)
	 */
	@Override
	public int randomInt(int maxExclusive, int x, int y, long additionalSeedMaterial) {
		return randomInt(maxExclusive, (x + y * WorldState.WORLD_SIZE) ^ additionalSeedMaterial);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see info.quadtree.sttg.world.DeterministicRNG#randomInt(int, long)
	 */
	@Override
	public int randomInt(int maxExclusive, long additionalSeedMaterial) {
		synchronized (rand) {
			rand.setSeed(additionalSeedMaterial);
			int ret = rand.nextInt(maxExclusive);
			return ret;
		}
	}

	@Override
	public int randomInt(int maxExclusive, WorldPosition pos, long additionalSeedMaterial) {
		return randomInt(maxExclusive, pos.x, pos.y, additionalSeedMaterial);
	}

	@Override
	public long randomLong(long additionalSeedMaterial) {
		synchronized (rand) {
			rand.setSeed(additionalSeedMaterial);
			long ret = rand.nextLong();
			return ret;
		}
	}

	@Override
	public long randomLong(WorldPosition pos, long additionalSeedMaterial) {
		return randomLong((pos.x + pos.y * WorldState.WORLD_SIZE) ^ additionalSeedMaterial);
	}
}
