package info.quadtree.sttg.world;

public interface DeterministicRNG {

	int randomInt(int maxExclusive, int x, int y, long additionalSeedMaterial);

	int randomInt(int maxExclusive, long additionalSeedMaterial);

	long randomLong(long additionalSeedMaterial);
}