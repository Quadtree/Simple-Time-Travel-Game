package info.quadtree.sttg.world;

import com.badlogic.gdx.graphics.Color;

public enum TerrainType {
	FLOOR(true, '0', Color.GRAY), GRASS(true, '.', Color.GREEN), GRASS2(true, ',', Color.GREEN), ROCK(false, 'X', Color.GRAY), WALL(false, '*', Color.BROWN), WATER(false, '~', Color.BLUE);

	public final Color color;
	public final boolean passable;
	public final char visual;

	private TerrainType(boolean passable, char visual, Color color) {
		this.passable = passable;
		this.visual = visual;
		this.color = color;
	}

}
