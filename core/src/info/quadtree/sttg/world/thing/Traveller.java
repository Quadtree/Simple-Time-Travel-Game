package info.quadtree.sttg.world.thing;

import com.badlogic.gdx.graphics.Color;

import info.quadtree.sttg.world.WorldPosition;
import info.quadtree.sttg.world.WorldState;

public class Traveller extends Person {

	public Traveller(WorldState world, WorldPosition location) {
		super(world, location);
	}

	@Override
	public void render(char[][] buffer, Color[][] colorBuffer, WorldPosition cameraLocation) {
		super.render(buffer, colorBuffer, cameraLocation);

		WorldPosition screenPosition = location.realToCamera(cameraLocation);

		if (screenPosition.isOnScreen()) {
			buffer[screenPosition.x][screenPosition.y] = '@';
			colorBuffer[screenPosition.x][screenPosition.y] = Color.WHITE;
		}
	}

}
