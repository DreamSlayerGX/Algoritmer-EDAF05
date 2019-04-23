package lab1;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GaleShapley {

	private static final File test = new File("src/lab1/1.in");

	private static final File t0 = new File("src/lab1/0testsmall.in");
	private static final File t1 = new File("src/lab1/1testsmallmessy.in");
	private static final File t2 = new File("src/lab1/2testmid.in");
	private static final File t3 = new File("src/lab1/3testlarge.in");
	private static final File t4 = new File("src/lab1/4testhuge.in");
	private static final File t5 = new File("src/lab1/5testhugemessy.in");

	private static final File[] testFiles = { t0, t1, t2, t3, t4, t5 };

	private static boolean gatherDataTimes = true;

	// Women
	private static Map<Integer, int[]> protons;
	
	// Men
	private static Map<Integer, int[]> electrons;

	public static void main(String[] args) {

		protons = new HashMap<>();
		electrons = new HashMap<>();

		if (gatherDataTimes) {
			gatherData();
		} else {
			runSetup(null);
			gale_shapley();
		}

		// d_printInput(protons, electrons);
	}


	private static void runSetup(File input) {
		try {
			Scanner sc;
			if(input == null) {
				sc = new Scanner(System.in);
			}
			else {
				sc = new Scanner(input);				
			}

			int n = Integer.parseInt(sc.nextLine());

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < 2 * n * (n + 1); i++) {
				sb.append(sc.next() + " ");
			}

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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void gatherData() {

		long[] result = new long[testFiles.length];

//		runSetup(test);
//		System.out.println(gale_shapley());
		
		for (int i = 0; i <= 5; i++) {
			try {
				runSetup(testFiles[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}

			result[i] = gale_shapley();

			protons = new HashMap<>();
			electrons = new HashMap<>();

			System.out.println("Test file " + testFiles[i].getName() + " | Time: " + result[i]);
		}

	}

	private static long gale_shapley() {

		ArrayList<Integer> singles = new ArrayList<Integer>();

		for (Integer i : electrons.keySet()) {
			singles.add(i);
		}

		HashMap<Integer, Integer> pairs = new HashMap<Integer, Integer>();

		int n = protons.size();
		Integer man = singles.remove(0);

		long start = System.currentTimeMillis();

		while (man != null) {
			for (int j = 0; j < n; j++) {
				int woman = electrons.get(man)[j];

				int[] womanPrefs = protons.get(woman);
				if (!pairs.containsKey(woman)) {
					pairs.put(woman, man);
					break;
				} else if (womanPrefs[man - 1] < womanPrefs[pairs.get(woman) - 1]) {
					singles.add(pairs.get(woman));
					pairs.replace(woman, man);
					break;
				}
			}
			try {
				man = singles.remove(0);
			} catch (Exception ex) {
				man = null;
			}

		}

		long end = System.currentTimeMillis();

		if(!gatherDataTimes) {
			for (int woman : pairs.keySet())
				System.out.println(pairs.get(woman));
		}
		
		return end - start;

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
