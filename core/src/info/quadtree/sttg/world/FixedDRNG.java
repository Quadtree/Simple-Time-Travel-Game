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
}
