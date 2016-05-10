package info.quadtree.sttg.world;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.random.MersenneTwister;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class NameGenerator {
	private static NameGenerator instance;

	public static synchronized NameGenerator getInstance() {
		if (instance == null)
			instance = new NameGenerator();

		return instance;
	}

	private List<String> syllables = new ArrayList<>();

	public NameGenerator() {
		try {
			loadSyllables(Gdx.files.internal("data/male.txt"));
			loadSyllables(Gdx.files.internal("data/female.txt"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public String generateName(Gender gender, DeterministicRNG rng) {
		long seed = rng.randomLong(0);

		System.err.println("seed=" + seed);

		MersenneTwister rand = new MersenneTwister(seed);

		String name = "";

		while (true) {
			name = "";
			while (rand.nextInt(4) != 0) {
				name += syllables.get(rand.nextInt(syllables.size()));
			}

			if (name.length() > 8 || name.length() < 3)
				continue;

			Gender nameGender = null;

			if (name.endsWith("A") || name.endsWith("E") || name.endsWith("I"))
				nameGender = Gender.Female;
			else
				nameGender = Gender.Male;

			if (nameGender == gender)
				break;
		}

		return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
	}

	private void loadSyllables(FileHandle fh) throws IOException {
		BufferedReader r = new BufferedReader(fh.reader());

		String line;

		Set<Character> vowels = new HashSet<>();
		vowels.add('A');
		vowels.add('E');
		vowels.add('I');
		vowels.add('O');
		vowels.add('U');

		while ((line = r.readLine()) != null) {
			String name = line.split("\\s")[0].toUpperCase();

			for (int i = 0; i < name.length(); ++i) {

				if (vowels.contains(name.charAt(i))) {
					String syllable = name.substring(Math.max(i - 1, 0), Math.min(i + 2, name.length()));

					syllables.add(syllable);
				}
			}
		}
	}
}
