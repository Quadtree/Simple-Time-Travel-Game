package info.quadtree.sttg.world.thing;

import info.quadtree.sttg.world.WorldPosition;
import info.quadtree.sttg.world.WorldState;

public abstract class Unit extends Thing {
	public enum Skill {
		Combat, Crafting, Gathering, Science, Social
	}

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
	public void update(long ticks) {
		super.update(ticks);

		age += ticks;

		while (age >= nextStatChangeAge) {
			yearlyStatChange();
		}
	}

	private void yearlyStatChange() {
		int ageYearsAtStatChange = (int) (nextStatChangeAge / WorldState.TICKS_PER_YEAR);

		if (ageYearsAtStatChange >= getAgeOfAdulthoodYears()) {
			maxHealth -= getAverageMaxMaxHealth() / (getAverageMaxAgeYears() - getAgeOfAdulthoodYears());
		} else {
			maxHealth += getAverageMaxMaxHealth() / getAgeOfAdulthoodYears();
		}

		int change = worldState.randomInt((int) WorldState.TICKS_PER_YEAR, location, baseSeed ^ age);

		// System.err.println("C" + change);

		nextStatChangeAge += change;

	}
}
