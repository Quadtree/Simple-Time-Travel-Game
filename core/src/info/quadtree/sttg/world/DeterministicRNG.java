package info.quadtree.sttg.world;

public interface DeterministicRNG {

	int randomInt(int maxExclusive, int x, int y, long additionalSeedMaterial);

	int randomInt(int maxExclusive, long additionalSeedMaterial);

	int randomInt(int maxExclusive, WorldPosition pos, long additionalSeedMaterial);

	long randomLong(long additionalSeedMaterial);

	long randomLong(WorldPosition pos, long additionalSeedMaterial);
}