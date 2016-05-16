package info.quadtree.sttg.world.thing;

import info.quadtree.sttg.world.WorldPosition;
import info.quadtree.sttg.world.WorldState;

public class Traveller extends Person {

	public Traveller(WorldState world, WorldPosition location) {
		super(world, location);
	}

	@Override
	protected char getDisplayCharacter() {
		return '@';
	}
}
