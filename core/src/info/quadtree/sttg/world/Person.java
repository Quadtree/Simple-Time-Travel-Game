package info.quadtree.sttg.world;

import java.util.HashMap;
import java.util.Map;

public class Person extends Unit {
	WorldPosition home;

	Map<String, Double> opinionOf;

	public Person() {
		opinionOf = new HashMap<String, Double>();
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
