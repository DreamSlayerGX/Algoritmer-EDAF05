package lab5;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javafx.util.Pair;

public class DynamicPrograming {
	
	private static int N, Q;
	private static Map<String, Integer> characters;
	private static int[][] weightTable;
	private static Pair<String, String>[] queries;
	
	private static final int insertCost = -4;
	
	public static void main(String[] args) {
		parseInputData();
		d_printInput();
		dynoPro(N-1, N-1);
		
	}
	
	private static int dynoPro(int i, int k) {
		 
	}
	
	private static void parseInputData() {
		try {
			Scanner sc = new Scanner(System.in);
			
			characters = new HashMap<>();
			
			
			String [] chars= sc.nextLine().split(" ");
			N = chars.length;
			
			for(int i = 0; i < N; i++)
				characters.put(chars[i], i);
			
			weightTable = new int[N][N];
			
			for(int i = 0; i < N; i++) {
				int[] row = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
				for(int k = 0; k < row.length; k++)
					weightTable[i][k] = row[k];
			}
			
			Q = Integer.parseInt(sc.nextLine());
			queries = new Pair[Q];
			for(int i = 0; i < Q; i++) {
				String[] queryRow = sc.nextLine().split(" ");
				queries[i] = new Pair<String, String>(queryRow[0], queryRow[1]);
			}
				
				
			sc.close();
		} catch (Exception e) {
		
		}	
	}
	
	private static void d_printInput() {
		for(String s : characters)
			System.out.print(s + " ");
		
		for(int i = 0; i < N; i++) {
			System.out.println();
			for(int k = 0; k < N; k++) {
				System.out.print(weightTable[i][k] + " ");
				
			}
		}
		
		System.out.println("\n"+Q);
		for(Pair<String, String> p : queries)
			System.out.println(p.getKey() + " " + p.getValue());
		
	}

}
