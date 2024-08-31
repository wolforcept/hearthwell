package wolforce.hearthwell;

import wolforce.hearthwell.util.Util;

import java.util.*;

public class TokenNames {

	public static final int NUMBER_OF_TOKENS = 12;
	public static final int[] SIZE_OF_TOKEN_NAMES = { 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6 };

	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++)
			System.out.println(createNames((long) (Math.random() * 1000000)));
	}

	private static final String[] premadeWords = { "Abhor", "Abuse", "Ace", "Acid", "Aid", "Air", "Anyo", "Arco", "Awe",
			"Bank", "Bea", "Bery", "Bin", "Blood", "Click", "Cold", "Colt", "Cue", "Dans", "Dee", "Depth", "Dust",
			"Ease", "Emir", "End", "Ent", "Epht", "Error", "Exit", "Fael", "Fate", "Few", "Fiber", "Fire", "Fold",
			"Ghast", "Glass", "Go", "God", "Gold", "Hand", "Haste", "Hay", "Hero", "Hymn", "Ice", "Imake", "Iner",
			"Ira", "Ira", "Iron", "Jack", "Jem", "Jhad", "Jhast", "Joy", "Ka", "Kauer", "Keep", "Kesh", "King", "Ko",
			"Lag", "Lead", "Leaf", "Lift", "Light", "Locus", "Luck", "Magic", "Mass", "Mat", "Maze", "Meap", "Mil",
			"Moon", "Nonne", "North", "Oak", "Ocean", "Opus", "Ore", "Pace", "Pain", "Palus", "Peace", "Py", "Qhan",
			"Quiet", "Rag", "Rain", "Range", "Rock", "Rog", "Rose", "Rumus", "Scent", "Sea", "Self", "Set", "Shock",
			"Sky", "Snow", "Stars", "Tar", "Tech", "Text", "Thar", "Time", "Times", "Tink", "Trees", "Union", "Up",
			"Ur", "Uszi", "Vex", "Vi", "Vira", "Voice", "Vurze", "War", "Ward", "Wave", "Wing", "Wood", "Wound", "Xis",
			"Yar", "Yen", "Yield", "Yma", "Yn", "Zaun", "Zero", "Zod", "Tardaz", "Oghein", "Almeiz", "Thobru", "Mezzos",
			"Qwanda", "Wannyr", "Obtus","Methil","Erdzo", "Qwinciat" };
	@SuppressWarnings("rawtypes")
	private static final List[] wordsBySize = { //
			Arrays.stream(premadeWords).filter(x -> x.length() == 2).toList(),
			Arrays.stream(premadeWords).filter(x -> x.length() == 3).toList(),
			Arrays.stream(premadeWords).filter(x -> x.length() == 4).toList(),
			Arrays.stream(premadeWords).filter(x -> x.length() == 5).toList(),
			Arrays.stream(premadeWords).filter(x -> x.length() == 6).toList(),
			//
	};

	private static final String[] allVowels = { "a", "e", "i", "o", "u", "y" };
	private static final String[] commonVowels = { "a", "e", "o" };
	private static final String[] commonConsonants = { "r", "t", "n", "s", "l", "c" };
	private static final String[] allConsonants = { "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "q",
			"r", "s", "t", "v", "w", "x", "y", "z" };

	public static List<String> createNames(long seed) {

		Random rand = new Random(seed);

		List<String> list = new ArrayList<>(NUMBER_OF_TOKENS);
		for (int i = 0; i < NUMBER_OF_TOKENS; i++) {
			int wordLen = SIZE_OF_TOKEN_NAMES[i];
			String word;
			do {
				word = createWord(rand, wordLen);
			} while (hasSimilar(list, word, wordLen));
			list.add(word);
		}
		return list;
	}

	private static boolean hasSimilar(List<String> list, String word, int wordLen) {
		for (String otherWord : list)
			if (distance(word, otherWord, wordLen - 1))
				return true;
		return false;
	}

	private static String createWord(Random rand, int wordLen) {

		if (rand.nextDouble() < .666) {
			@SuppressWarnings("unchecked")
			List<String> words = wordsBySize[wordLen - 2];
			return words.get(rand.nextInt(words.size()));
		}

		String s = "";

//		int n = Math.min(7, Math.max(rand.nextDouble() < .1 ? 2 : 3, Math.abs((int) (rand.nextGaussian() + 4))));

		boolean isVowel = rand.nextDouble() < .3;
		for (int i = 0; i < wordLen; i++) {

			String nextLetter = isVowel ? randomVowel(rand) : randomConsonant(rand);
			s += i == 0 ? nextLetter.toUpperCase() : nextLetter;

			if (!isVowel || rand.nextDouble() < .9)
				isVowel = !isVowel;
		}

		return s;
	}

	private static String randomConsonant(Random rand) {
		if (rand.nextDouble() < .1)
			return commonConsonants[rand.nextInt(commonConsonants.length)];
		return allConsonants[rand.nextInt(allConsonants.length)];
	}

	private static String randomVowel(Random rand) {
		if (rand.nextDouble() < .2)
			return commonVowels[rand.nextInt(commonVowels.length)];
		return allVowels[rand.nextInt(allVowels.length)];
	}

	//
	//
	//
	//

	private static boolean distance(String a, String b, int min) {
		return distance(a, b, 0, min);
	}

	private static boolean distance(String a, String b, int n, int min) {
		if (a.equals(b) && n < min) {
			return true;
		}
		if ((a.length() == 1 || b.length() == 1) && a.length() + b.length() > 1) {
			if (a.charAt(0) != b.charAt(0) && n + Math.max(a.length(), b.length()) < min)
				return true;
			if (a.charAt(0) == b.charAt(0) && n + Math.abs(a.length() - b.length()) < min)
				return true;
			return false;
		}
		int size = a.length() < b.length() ? a.length() : b.length();
		for (int i = 0; i < size; i++) {
			if (a.charAt(i) != b.charAt(i)) {
				if (i == size - 1 && n + 1 + Math.abs(a.length() - b.length()) < min)
					return true;
				if (i < size - 1 && n + 1 < min)
					return distance(a.substring(i + 1), b, n + 1, min) || distance(a, b.substring(i + 1), n + 1, min)
							|| distance(a.substring(i + 1), b.substring(i + 1), n + 1, min);
				return false;
			}
		}
		return (n + Math.abs(a.length() - b.length()) < min) ? true : false;
	}

	//
	//
	//

	private static final HashMap<String, String> playerTokenWords = new HashMap<>();

	public static int addFromPlayer(String id, char c) {
		String prev = playerTokenWords.containsKey(id) ? playerTokenWords.get(id) : "";
		List<? extends String> tokenNames = ConfigServer.getTokenNames();

		String word = (prev.length() > 20 ? Util.substring(prev, -20) : prev) + c;
		for (int i = 0; i < tokenNames.size(); i++) {

			if (matches(tokenNames.get(i), word)) {
				return i;
			}
		}

		playerTokenWords.put(id, word);
		return -1;
	}

	private static boolean matches(String tokenName, String word) {
		int l = tokenName.length();
		if (word.length() < l)
			return false;
		return Util.substring(word.toLowerCase(), -l).equals(tokenName.toLowerCase());
	}

}
