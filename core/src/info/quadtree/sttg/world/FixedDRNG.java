package info.quadtree.sttg.world;

public class FixedDRNG implements DeterministicRNG {
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
		int ret = (int) (xorshift64s(additionalSeedMaterial) % maxExclusive);
		return ret;
	}

	@Override
	public int randomInt(int maxExclusive, WorldPosition pos, long additionalSeedMaterial) {
		return randomInt(maxExclusive, pos.x, pos.y, additionalSeedMaterial);
	}

	@Override
	public long randomLong(long additionalSeedMaterial) {
		long ret = xorshift64s(additionalSeedMaterial);
		return ret;
	}

	@Override
	public long randomLong(WorldPosition pos, long additionalSeedMaterial) {
		return randomLong((pos.x + pos.y * WorldState.WORLD_SIZE) ^ additionalSeedMaterial);
	}

	public long xorshift64s(long seed) {
		seed ^= (seed << 13);
		seed ^= (seed >>> 7);
		seed ^= (seed << 17);

		return seed;
	}
}
