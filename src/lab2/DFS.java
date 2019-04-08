package lab2;
import java.util.Scanner;

public class DFS {

	private static int N, Q;
	private static String[] words;
	private static String[][] queries;
	
	public static void main(String[] args) {
		parseInputData();
		d_printInputData();
		buildGraph();
		BFS();
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
	
	private static void buildGraph() {
		
	}
	
	private static void BFS() {
		
	}
	
	private static void d_printInputData(){
		System.out.println(N);
		System.out.println(Q);
		
		for(int i = 0; i < words.length; i++)
			System.out.println(words[i]);
		
		for(int i = 0; i < queries.length; i++)
			System.out.println(queries[i][0] + " " + queries[i][1]);
		
	}
	
}
