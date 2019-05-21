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
	
	private static final boolean debug = true;
	
	public static void main(String[] args) {
		parseInputData();
		
		for(Pair<String, String> p : queries) {
			dynoPro(p.getKey(), p.getValue());
			
		}
		
		
	}
	
	private static int dynoPro(String a, String b) {
		int[][] M = generateMArray(a,b);
		
		
		 return 0;
	}
	
	private static int[][] generateMArray(String a, String b){
		int[][] M = new int[a.length() + 1][b.length() + 1];
		
		int loopLength = Math.max(a.length(), b.length());
		for(int i = 0; i <= loopLength; i++) {
			if(i <= a.length())
				M[i][0] = i*delta;
			
			if(i <= b.length())
				M[0][i] = i*delta;
			
		}
		
		for(int i = 1; i <= a.length(); i++)
			for(int j = 1; j <= b.length(); j++) {
				int x = M[i-1][j-1] + weightTable[characters.get(String.valueOf(a.charAt(i-1)))][characters.get(String.valueOf(b.charAt(j-1)))];
				int y = M[i-1][j] + delta;
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
			
			d_printInput();
				
			sc.close();
		} catch (Exception e) {
		
		}	
	}
	
	private static void d_printMArray(int[][] M, String a, String b) {
		if(!debug)
			return;
		System.out.println("\n");
		System.out.println(a + "  " + b);
		for(int i = a.length(); i >= 0 ; i--) {
			for(int j = 0; j <= b.length(); j++)
				System.out.print(M[i][j] + "\t");
			System.out.println();
		}
		
	}
	
	private static void d_printInput() {
		if(!debug)
			return;
		
		for(Entry s : characters.entrySet())
			System.out.print(s.getKey() + " ");
		
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

/*
 * 	if(isCached(...)) {
    	return getCache(...);
	}
	
	int cost = calculate(...);
	setCache(cost, ...);
	return cost;
 */
