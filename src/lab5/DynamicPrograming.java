package lab5;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import javafx.util.Pair;

public class DynamicPrograming {

	private static int N, Q;
	private static Map<String, Integer> characters;
	private static int[][] weightTable;
	private static Pair<String, String>[] queries;

	private static final int delta = -4;

	private static final boolean debug = false;

	public static void main(String[] args) {
		parseInputData();

		for (Pair<String, String> p : queries) {
			dynoPro(p.getKey(), p.getValue());
		}

	}

	private static void dynoPro(String a, String b) {
		int[][] M = generateMArray(a, b);
		String[] result = findShortestPath(M, a.length(), b.length(), new StringBuilder(), new StringBuilder(), a, b);
		System.out.println(result[0] + " " + result[1]);
	}

	private static String[] findShortestPath(int[][] M, int i, int j, StringBuilder subA, StringBuilder subB, String a,
			String b) {
	
		if (i == 0 && j != 0) {
			String prevCharB = String.valueOf(b.charAt(j - 1));
			subA.insert(0, "*");
			subB.insert(0, prevCharB);
			j--;
			findShortestPath(M, i, j, subA, subB, a, b);
			
		} else if (j == 0 && i != 0) {
			String prevCharA = String.valueOf(a.charAt(i - 1));
			subA.insert(0, prevCharA);
			subB.insert(0, "*");
			i--;
			findShortestPath(M, i, j, subA, subB, a, b);
			
		} else if (i != 0 && j != 0) {
			int prevDiagCost = M[i - 1][j - 1];
			int prevDownCost = M[i][j - 1];

			String prevCharA = String.valueOf(a.charAt(i - 1));
			String prevCharB = String.valueOf(b.charAt(j - 1));
			
			int costOfDiag = weightTable[characters.get(prevCharA)][characters.get(prevCharB)];
			
			if (M[i][j] == prevDiagCost + costOfDiag) {
				subA.insert(0, prevCharA);
				subB.insert(0, prevCharB);
				i--;
				j--;
			} else if (M[i][j] == prevDownCost + delta) {
				subA.insert(0, "*");
				subB.insert(0, prevCharB);
				j--;
			} else {
				subA.insert(0, prevCharA);
				subB.insert(0, "*");
				i--;
			}
			
			findShortestPath(M, i, j, subA, subB, a, b);
		}
		
		return new String[] { subA.toString(), subB.toString() };

	}

	private static int[][] generateMArray(String a, String b) {
		int[][] M = new int[a.length() + 1][b.length() + 1];

		int loopLength = Math.max(a.length(), b.length());
		for (int i = 0; i <= loopLength; i++) {
			if (i <= a.length())
				M[i][0] = i * delta;

			if (i <= b.length())
				M[0][i] = i * delta;

		}

		for (int i = 1; i <= a.length(); i++)
			for (int j = 1; j <= b.length(); j++) {
				int x = M[i - 1][j - 1] + weightTable[characters.get(String.valueOf(a.charAt(i - 1)))][characters
						.get(String.valueOf(b.charAt(j - 1)))];

				int y = M[i - 1][j] + delta;
				int z = M[i][j - 1] + delta;

				int midMax = Math.max(x, y);
				M[i][j] = Math.max(midMax, z);
			}

		d_printMArray(M, a, b);

		return M;
	}

	private static void parseInputData() {
		try {
			Scanner sc = new Scanner(System.in);

			characters = new HashMap<>();

			String[] chars = sc.nextLine().split(" ");
			N = chars.length;

			for (int i = 0; i < N; i++)
				characters.put(chars[i], i);

			weightTable = new int[N][N];

			for (int i = 0; i < N; i++) {
				int[] row = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
				for (int k = 0; k < row.length; k++)
					weightTable[i][k] = row[k];
			}

			Q = Integer.parseInt(sc.nextLine());
			queries = new Pair[Q];
			for (int i = 0; i < Q; i++) {
				String[] queryRow = sc.nextLine().split(" ");
				queries[i] = new Pair<String, String>(queryRow[0], queryRow[1]);
			}

			d_printInput();

			sc.close();
		} catch (Exception e) {

		}
	}

	private static void d_printMArray(int[][] M, String a, String b) {
		if (!debug)
			return;
		System.out.println("\n");
		System.out.println(a + "  " + b);
		for (int i = a.length(); i >= 0; i--) {
			for (int j = 0; j <= b.length(); j++)
				System.out.print(M[i][j] + "\t");
			System.out.println();
		}

	}

	private static void d_printInput() {
		if (!debug)
			return;

		for (Entry s : characters.entrySet())
			System.out.print(s.getKey() + " ");

		for (int i = 0; i < N; i++) {
			System.out.println();
			for (int k = 0; k < N; k++) {
				System.out.print(weightTable[i][k] + " ");

			}
		}

		System.out.println("\n" + Q);
		for (Pair<String, String> p : queries)
			System.out.println(p.getKey() + " " + p.getValue());

	}

}

/*
 * if(isCached(...)) { return getCache(...); }
 * 
 * int cost = calculate(...); setCache(cost, ...); return cost;
 */
