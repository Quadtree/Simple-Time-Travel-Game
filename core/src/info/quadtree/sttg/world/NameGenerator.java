package info.quadtree.sttg.world;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		long k = 0;

		String name = "";

		while (true) {
			while (rng.randomInt(4, k++) != 0) {
				name += syllables.get(rng.randomInt(syllables.size(), k++));
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

			if (k > 1000000)
				throw new RuntimeException("Unable to generate name?");
		}

		return name.substring(0, 1).toLowerCase() + name.substring(1);
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
			String name = line.split("\\s")[0];

			for (int i = 0; i < name.length(); ++i) {

				if (vowels.contains(name.charAt(i))) {
					String syllable = name.substring(Math.max(i - 1, 0), Math.min(i + 2, name.length()));

					syllables.add(syllable);
				}
			}
		}
	}
}
