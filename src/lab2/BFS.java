package lab2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class BFS {

	private static int N, Q;
	private static String[] words;
	private static String[][] queries;
	private static Map<String, ArrayList<String>> graph;
	
	public static void main(String[] args) {
		parseInputData();
		createGraph();
		//d_printInputData();
		d_printGraph();
		bfs();
	}
	
	private static void parseInputData() {
		try {
			Scanner sc = new Scanner(System.in);
			
			String[] size = sc.nextLine().split(" ");
			
			N = Integer.parseInt(size[0]);
			Q = Integer.parseInt(size[1]);
			
			words = new String[N];
			queries = new String[Q][2];
			
			for (int i = 0; i < N+Q; i++) {
				if(i < N) {
					words[i] = sc.nextLine();
				} else {
					String[] querieWords = sc.nextLine().split(" ");
					
					queries[i-N][0] = querieWords[0];
					queries[i-N][1] = querieWords[1];
				}
			}
			
			sc.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	private static void createGraph(){
		graph = new HashMap<>();
		
		for(int i = 0; i < words.length; i++) {
			String key = words[i];
			graph.put(key, new ArrayList<>());
			
			for(int k = 0; k < words.length; k++) {
				if(k == i)
					continue;
				
				String word = words[k];
				String isMatched = word;
				
				for(int j = 1; j < word.length(); j++) 
					isMatched = isMatched.replaceFirst(String.valueOf(key.charAt(j)), "");
				
				if(isMatched.length() == 1)
					graph.get(key).add(word);
				
				//System.out.println("Key, word, isMatched: " + key + " | " + word + " | " + isMatched );
				
			}
	
		}

	}
	
	private static void bfs() {
		for(int i = 0; i < 1; i++) {

			ArrayList<String> frontier = new ArrayList<>();
			ArrayList<String> visited = new ArrayList<>();

			String answer = bfs(queries[i][0], queries[i][1], frontier, visited, 0);
			System.out.println(answer);
		}
	}
	
	private static String bfs(String currentWord, String search, 
			ArrayList<String> frontier, ArrayList<String> visited, int n) {

		System.out.println(currentWord + " | " + search);
		System.out.println(frontier.size() + " | " + visited.size() + " | " + n);
		
		if(currentWord.equals(search))
			return String.valueOf(n);
		
		visited.add(currentWord);
		for(String nextVertex : graph.get(currentWord)) {
			if(visited.contains(nextVertex))
				continue;
			else
				frontier.add(nextVertex);
			
		}
		
		if(frontier.isEmpty())
			return "Impossible";
		else
			return bfs(frontier.remove(0), search, frontier, visited, n++);
	}
	
	private static void d_printInputData(){
		System.out.println(N);
		System.out.println(Q);
		
		for(int i = 0; i < words.length; i++)
			System.out.println(words[i]);
		
		for(int i = 0; i < queries.length; i++)
			System.out.println(queries[i][0] + " " + queries[i][1]);
		
	}
	
	private static void d_printGraph() {
		for(Entry<String, ArrayList<String>> set : graph.entrySet()) {
			System.out.println(set.getKey());
			for(String vertex : set.getValue()) {
				System.out.println("  " + vertex);
			}
		}
	}
	
}
