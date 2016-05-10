package info.quadtree.sttg.world;

import java.util.HashMap;
import java.util.Map;

public class Person extends Unit {
	long baseSeed;

	String firstName;
	Gender gender;

	String lastName;

	WorldPosition location;

	Map<String, Double> opinionOf;

	public Person(WorldState world, WorldPosition location) {
		opinionOf = new HashMap<String, Double>();

		baseSeed = world.randomLong(location, 0);

		gender = world.randomInt(2, location, baseSeed) == 0 ? Gender.Female : Gender.Male;

		firstName = NameGenerator.getInstance().generateName(gender, world.randomLong(location, baseSeed));
		lastName = NameGenerator.getInstance().generateName(Gender.Male, world.randomLong(location, baseSeed) + 3289843);

		System.out.println(firstName + " " + lastName + " created at " + location);
	}

	public double getOpinionOf(String other) {
		if (!opinionOf.containsKey(other))
			opinionOf.put(other, 0.0);

		return opinionOf.get(other);
	}

	public void modOpinionOf(String other, double val) {
		opinionOf.put(other, getOpinionOf(other) + val);
	}
}
