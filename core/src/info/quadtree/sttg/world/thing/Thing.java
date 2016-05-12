package info.quadtree.sttg.world.thing;

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

	public abstract String getName();

	public boolean keep() {
		return true;
	}

	public void update(long ticks) {
	}
}
