package info.quadtree.sttg.world;

public class WorldPosition {
	public final int x;
	public final int y;

	public WorldPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "WorldPosition [x=" + x + ", y=" + y + "]";
	}
}
