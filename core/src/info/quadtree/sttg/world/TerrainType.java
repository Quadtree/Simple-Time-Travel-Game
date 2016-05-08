package info.quadtree.sttg.world;

public enum TerrainType {
	FLOOR(true), GRASS(true), GRASS2(true), ROCK(false), WALL(false), WATER(false);

	public final boolean passable;

	private TerrainType(boolean passable) {
		this.passable = passable;
	}
}
