import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Algorithm {

	public static void main(String[] args) {

		// Women
		Map<Integer, int[]> protons = new HashMap<>();
		// Men
		Map<Integer, int[]> electrons = new HashMap<>();

		Scanner sc = new Scanner(System.in);

		int n = Integer.parseInt(sc.nextLine());

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 2 * n * (n + 1); i++) {
			sb.append(sc.next() + " ");
		}

		// System.out.println();
		// System.out.println(sb.toString());

		String[] lineNumbers = sb.toString().split(" ");
		int delta = 0;

		for (int i = 0; i < 2 * n; i++) {

			String[] person = Arrays.copyOfRange(lineNumbers, delta, delta + n + 1);

			int key = Integer.parseInt(person[0]);

			String[] preferences = Arrays.copyOfRange(person, 1, person.length);

			if (!protons.containsKey(key)) {

				int[] menPref = Arrays.stream(preferences).mapToInt(Integer::parseInt).toArray();
				int[] inversePref = new int[n];

				for (int j = 0; j < menPref.length; j++) {
					inversePref[menPref[j] - 1] = j;
				}

				protons.put(key, inversePref);
			} else {
				electrons.put(key, Arrays.stream(preferences).mapToInt(Integer::parseInt).toArray());

			}
			delta = delta + n + 1;
		}

		sc.close();

		// d_printInput(protons, electrons);
		gale_shapley(protons, electrons);
	}

	private static void gale_shapley(Map<Integer, int[]> w, Map<Integer, int[]> m) {

		ArrayList<Integer> singles = new ArrayList<Integer>();

		for (Integer i : m.keySet()) {
			singles.add(i);
		}

		HashMap<Integer, Integer> pairs = new HashMap<Integer, Integer>();

		int n = w.size();
		Integer man = singles.remove(0);

		while (man != null) {
			for (int j = 0; j < n; j++) {
				int woman = m.get(man)[j];

				int[] womanPrefs = w.get(woman);
				// System.out.println("Man: " + man + " | Choice: " + j + " = woman " + woman);
				if (!pairs.containsKey(woman)) {
					pairs.put(woman, man);
					// System.out.println("mached");
					break;
				} else if (womanPrefs[man - 1] < womanPrefs[pairs.get(woman) - 1]) {
					singles.add(pairs.get(woman));
					pairs.replace(woman, man);
					// System.out.println("swaped");
					break;
				}
			}
			try {
				man = singles.remove(0);
			} catch (Exception ex) {
				man = null;
			}

		}

		for (int woman : pairs.keySet()) {
			System.out.println(pairs.get(woman));
		}

	}

	private static void d_printInput(Map<Integer, int[]> p, Map<Integer, int[]> e) {

		System.out.println(p.size());
		System.out.println("Protons / Women");
		for (Integer i : p.keySet()) {
			System.out.print(i + " ");
			for (int x : p.get(i)) {
				System.out.print(x + " ");
			}
			System.out.println();
		}

		System.out.println("Electrons / Men");
		for (Integer i : e.keySet()) {
			System.out.print(i + " ");
			for (int x : e.get(i)) {
				System.out.print(x + " ");
			}
			System.out.println();
		}

	}

}
