package info.quadtree.sttg.world.thing;

import com.badlogic.gdx.graphics.Color;

import info.quadtree.sttg.world.WorldPosition;
import info.quadtree.sttg.world.WorldState;

public abstract class Thing {
	protected WorldPosition location;
	protected WorldState worldState;

	public Thing(WorldState worldState, WorldPosition location) {
		this.worldState = worldState;
		this.location = location;
	}

	public void destroyed() {

	}

	protected char getDisplayCharacter() {
		return ' ';
	}

	protected Color getDisplayColor() {
		return Color.WHITE;
	}

	public WorldPosition getLocation() {
		return location;
	}

	public abstract String getName();

	public boolean keep() {
		return true;
	}

	public void render(char[][] buffer, Color[][] colorBuffer, WorldPosition cameraLocation) {
		WorldPosition screenPosition = location.realToCamera(cameraLocation);

		if (screenPosition.isOnScreen()) {
			buffer[screenPosition.x][screenPosition.y] = getDisplayCharacter();
			colorBuffer[screenPosition.x][screenPosition.y] = getDisplayColor();
		}
	}

	public void update(long ticks) {
	}
}
