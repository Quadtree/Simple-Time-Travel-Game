package info.quadtree.sttg.world.thing;

public abstract class Thing {
	public void destroyed() {
	}

	public abstract String getName();

	public boolean keep() {
		return true;
	}

	public void update(long ticks) {
	}
}
