package info.quadtree.sttg.world;

import info.quadtree.sttg.STTG;

public class WorldPosition {
	public final int x;
	public final int y;

	public WorldPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean isOnScreen() {
		return x >= 0 && y >= 0 && x < STTG.CAMERA_WIDTH && y < STTG.CAMERA_HEIGHT;
	}

	public WorldPosition realToCamera(WorldPosition cameraPosition) {
		return new WorldPosition(x + STTG.CAMERA_WIDTH / 2 - cameraPosition.x, y + STTG.CAMERA_HEIGHT / 2 - cameraPosition.y);
	}

	@Override
	public String toString() {
		return "WorldPosition [x=" + x + ", y=" + y + "]";
	}
}
