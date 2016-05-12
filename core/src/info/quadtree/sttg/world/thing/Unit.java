package info.quadtree.sttg.world.thing;

import info.quadtree.sttg.world.WorldPosition;
import info.quadtree.sttg.world.WorldState;

public abstract class Unit extends Thing {
	public enum Skill {
		Combat, Crafting, Gathering, Science, Social
	}

	int actionCooldown;

	protected long age;

	protected long baseSeed;

	double energy;

	double food;
	double health;
	double maxHealth;
	long nextStatChangeAge;
	double temperature;

	double water;

	public Unit(WorldState worldState, WorldPosition location) {
		super(worldState, location);
	}

	public boolean canAct() {
		return actionCooldown <= 0;
	}

	@Override
	public void destroyed() {
		super.destroyed();

		// System.err.println(getName() + " has died at age " + getAgeYears());
	}

	protected int getAgeOfAdulthoodYears() {
		return 20;
	}

	public int getAgeYears() {
		return (int) (age / WorldState.TICKS_PER_YEAR);
	}

	protected int getAverageMaxAgeYears() {
		return 80;
	}

	public double getAverageMaxMaxHealth() {
		return 100;
	}

	@Override
	public boolean keep() {
		return super.keep() && health > 0;
	}

	public void moveTo(WorldPosition pos) {
		int dist = location.getManhattanDistanceTo(pos);

		if (dist == 1) {
			location = pos;
			actionCooldown = 20;
		}
	}

	@Override
	public void update(long ticks) {
		super.update(ticks);

		actionCooldown--;

		age += ticks;

		while (age >= nextStatChangeAge) {
			yearlyStatChange();
		}

		health = Math.min(health, maxHealth);
	}

	private void yearlyStatChange() {
		int ageYearsAtStatChange = (int) (nextStatChangeAge / WorldState.TICKS_PER_YEAR);

		if (ageYearsAtStatChange >= getAgeOfAdulthoodYears()) {
			maxHealth -= getAverageMaxMaxHealth() / (getAverageMaxAgeYears() - getAgeOfAdulthoodYears()) * (worldState.randomInt(20000, location, baseSeed ^ age) / 10000.0);
		} else {
			maxHealth += getAverageMaxMaxHealth() / getAgeOfAdulthoodYears() * (worldState.randomInt(20000, location, baseSeed ^ age) / 10000.0);
		}

		int change = worldState.randomInt((int) WorldState.TICKS_PER_YEAR, location, baseSeed ^ age);

		// System.err.println("C" + change);

		nextStatChangeAge += change;

	}
}
