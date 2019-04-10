package lab2;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import javafx.util.Pair;

public class BFS {

	private static int N, Q;
	private static String[] words;
	private static String[][] queries;
	private static Map<String, ArrayList<String>> graph;

	public static void main(String[] args) {
		parseInputData();
		createGraph();
		// d_printInputData();
		// d_printGraph();
		//bdfs(true, false);
		iterativeBFS(true);
		
	}

	private static void parseInputData() {
		try {
			Scanner sc = new Scanner(System.in);

			String[] size = sc.nextLine().split(" ");

			N = Integer.parseInt(size[0]);
			Q = Integer.parseInt(size[1]);

			words = new String[N];
			queries = new String[Q][2];

			for (int i = 0; i < N + Q; i++) {
				if (i < N) {
					words[i] = sc.nextLine();
				} else {
					String[] querieWords = sc.nextLine().split(" ");

					queries[i - N][0] = querieWords[0];
					queries[i - N][1] = querieWords[1];
				}
			}

			sc.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private static void createGraph() {
		graph = new HashMap<>();

		for (int i = 0; i < words.length; i++) {
			String key = words[i];
			graph.put(key, new ArrayList<>());

			for (int k = 0; k < words.length; k++) {
				if (k == i)
					continue;

				String word = words[k];
				String isMatched = word;

				for (int j = 1; j < word.length(); j++)
					isMatched = isMatched.replaceFirst(String.valueOf(key.charAt(j)), "");

				if (isMatched.length() == 1)
					graph.get(key).add(word);

				// System.out.println("Key, word, isMatched: " + key + " | " + word + " | " +
				// isMatched );

			}

		}

	}

	private static void bdfs(boolean bfs, boolean takeData) {
		ArrayList<Long> times = new ArrayList<>(); 
		for (int i = 0; i < queries.length; i++) {

			ArrayList<Pair<String, Integer>> frontier = new ArrayList<>();
			ArrayList<String> visited = new ArrayList<>();
			
			int answer;
			long start = System.currentTimeMillis();
			if(bfs)
				answer = bfs(queries[i][0], queries[i][1], frontier, visited, 0);
			else
				answer = dfs(queries[i][0], queries[i][1], visited, 0);
			times.add(System.currentTimeMillis() - start);
			
			if (answer < 0)
				System.out.println("Impossible");
			else
				System.out.println(answer);
			
		}
		
		if(takeData)
			statistic(times);
	}

	private static int bfs(String currentWord, String search, ArrayList<Pair<String, Integer>> frontier,
			ArrayList<String> visited, int n) {

//		System.out.println(currentWord + " | " + search);
//		System.out.println(frontier.size() + " | " + visited.size() + " | " + n);

		if (currentWord.equals(search)) {
			frontier = new ArrayList<>();
			return n;
		}

		visited.add(currentWord);
		for (String nextVertex : graph.get(currentWord)) {
			if (!visited.contains(nextVertex))
				frontier.add(new Pair<>(nextVertex, n + 1));

		}

		if (frontier.isEmpty()) {
			return -1;
		} else {
			Pair<String, Integer> nextVertex = frontier.remove(0);
			return bfs(nextVertex.getKey(), search, frontier, visited, nextVertex.getValue());
		}
	}

	private static int dfs(String currentWord, String search, ArrayList<String> visited, int n) {

//		System.out.println(currentWord + " | " + search);
//		System.out.println(frontier.size() + " | " + visited.size() + " | " + n);
//		System.out.println(currentWord + " " + n);

		if (currentWord.equals(search))
			return n;

		int bestOfAdjecent = -1;

		visited.add(currentWord);
		for (String nextVertex : graph.get(currentWord)) {

			int result = -1;
			if (!visited.contains(nextVertex)) {
				result = dfs(nextVertex, search, visited, n + 1);

				if (result >= 0 && (result < bestOfAdjecent || bestOfAdjecent < 0)) {
					bestOfAdjecent = result;
				}
			}

		}

		return bestOfAdjecent;
	}

	private static void iterativeBFS(boolean takeData) {
		ArrayList<Long> times = new ArrayList<>();
		for (int i = 0; i < queries.length; i++) {
			long start = System.currentTimeMillis();
			String answer = iterativeBFS(graph, queries[i][0], queries[i][1]);
			times.add(System.currentTimeMillis() - start);
			System.out.println(answer);
		}
		if(takeData)
			statistic(times);
	}

	private static String iterativeBFS(Map<String, ArrayList<String>> graph, String start, String target) {
		int level = 0;
		HashMap<String, Integer> levels = new HashMap<>();
		
		ArrayList<String> queue = new ArrayList<>();
		queue.add(start);
		
		HashMap<String, Boolean> visited = new HashMap<>();
		
		for (String node : graph.keySet()) {
			visited.put(node, false);
		}
		
		String currentNode = queue.remove(0);
		levels.put(currentNode, level);
		visited.replace(currentNode, true);
		
		while (currentNode != null) {
			level = levels.get(currentNode);
			if (currentNode.equals(target)) {
				return String.valueOf(levels.get(currentNode));
			}
			
			for (String neighbor : graph.get(currentNode)) {
				if (!visited.get(neighbor)) {
					levels.put(neighbor, level + 1);
					visited.replace(neighbor, true);
					queue.add(neighbor);
				}
			}
			
			try {
				currentNode = queue.remove(0);
			} catch (Exception e) {
				currentNode = null;
			}
		}
		return "Impossible";
	}
	
	private static void statistic(ArrayList<Long> times) {
		Long average = 0L;
		
		for(Long t : times)
			average += t;
		
		average /= times.size();
		
		File f = new File("BFS-statistic.txt");
		try {
			BufferedWriter br = new BufferedWriter(new FileWriter(f, true));
			String data = Integer.toString(N) + " " + Integer.toString(Q) + " " + Long.toString(average);
			br.write(data);
			br.newLine();
			
			br.flush();
			br.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	private static void d_printInputData() {
		System.out.println(N);
		System.out.println(Q);

		for (int i = 0; i < words.length; i++)
			System.out.println(words[i]);

		for (int i = 0; i < queries.length; i++)
			System.out.println(queries[i][0] + " " + queries[i][1]);

	}

	private static void d_printGraph() {
		for (Entry<String, ArrayList<String>> set : graph.entrySet()) {
			System.out.println(set.getKey());
			for (String vertex : set.getValue()) {
				System.out.println("  " + vertex);
			}
		}
	}

}
