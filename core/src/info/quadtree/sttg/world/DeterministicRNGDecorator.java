package info.quadtree.sttg.world;

public class DeterministicRNGDecorator implements DeterministicRNG {

	long additionalSeedMaterial;

	DeterministicRNG rng;

	public DeterministicRNGDecorator(DeterministicRNG rng, int x, int y, long additionalSeedMaterial) {
		this.rng = rng;
		this.additionalSeedMaterial = x + y * WorldState.WORLD_SIZE ^ additionalSeedMaterial;
	}

	public DeterministicRNGDecorator(DeterministicRNG rng, long additionalSeedMaterial) {
		this.rng = rng;
		this.additionalSeedMaterial = additionalSeedMaterial;
	}

	public DeterministicRNGDecorator(DeterministicRNG rng, WorldPosition pos, long additionalSeedMaterial) {
		this.rng = rng;
		this.additionalSeedMaterial = pos.x + pos.y * WorldState.WORLD_SIZE ^ additionalSeedMaterial;
	}

	@Override
	public int randomInt(int maxExclusive, int x, int y, long additionalSeedMaterial) {
		return rng.randomInt(maxExclusive, x, y, additionalSeedMaterial ^ this.additionalSeedMaterial);
	}

	@Override
	public int randomInt(int maxExclusive, long additionalSeedMaterial) {
		return rng.randomInt(maxExclusive, additionalSeedMaterial ^ this.additionalSeedMaterial);
	}

	@Override
	public int randomInt(int maxExclusive, WorldPosition pos, long additionalSeedMaterial) {
		return rng.randomInt(maxExclusive, pos, this.additionalSeedMaterial ^ additionalSeedMaterial);
	}

	@Override
	public long randomLong(long additionalSeedMaterial) {
		return rng.randomLong(this.additionalSeedMaterial ^ additionalSeedMaterial);
	}

	@Override
	public long randomLong(WorldPosition pos, long additionalSeedMaterial) {
		return rng.randomLong(pos, this.additionalSeedMaterial ^ additionalSeedMaterial);
	}

}
