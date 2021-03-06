package info.quadtree.sttg.world.thing;

import java.util.HashMap;
import java.util.Map;

import info.quadtree.sttg.world.Gender;
import info.quadtree.sttg.world.NameGenerator;
import info.quadtree.sttg.world.WorldPosition;
import info.quadtree.sttg.world.WorldState;

public class Person extends Unit {

	String firstName;
	Gender gender;

	String lastName;

	Map<String, Double> opinionOf;

	public Person(WorldState world, WorldPosition location) {
		super(world, location);
		opinionOf = new HashMap<String, Double>();

		baseSeed = world.randomLong(location, 0);

		gender = world.randomInt(2, location, baseSeed) == 0 ? Gender.Female : Gender.Male;

		firstName = NameGenerator.getInstance().generateName(gender, world.randomLong(location, baseSeed), false);
		lastName = NameGenerator.getInstance().generateName(Gender.Male, world.randomLong(location, baseSeed) + 3289843, true);

		// System.out.println(firstName + " " + lastName + " created at " +
		// location);

		maxHealth = 0.001;
		health = 0.001;
	}

	@Override
	protected char getDisplayCharacter() {
		if (getAgeYears() < getAgeOfAdulthoodYears()) {
			return '^';
		} else if (getAgeYears() < getAverageMaxAgeYears() / 2) {
			return '#';
		} else {
			return '%';
		}
	}

	@Override
	public String getName() {
		return firstName + " " + lastName;
	}

	public double getOpinionOf(String other) {
		if (!opinionOf.containsKey(other))
			opinionOf.put(other, 0.0);

		return opinionOf.get(other);
	}

	public void modOpinionOf(String other, double val) {
		opinionOf.put(other, getOpinionOf(other) + val);
	}

	public void modOpinionOf(Unit other, double val) {
		opinionOf.put(other.getName(), val);
	}

	@Override
	public void update(long ticks) {
		super.update(ticks);
	}
}
