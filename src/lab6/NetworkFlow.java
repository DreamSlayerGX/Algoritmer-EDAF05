package lab6;

import java.util.Arrays;
import java.util.Scanner;

public class NetworkFlow {
	
	private static class Edge{
		
		public final int nodeA, nodeB;
		public final int weigt;
		
		
		public Edge(int nodeA, int nodeB, int weigt) {
			this.nodeA = nodeA;
			this.nodeB = nodeB;
			this.weigt = weigt;
		}

	}
	
	private static int N, M, C, P;
	private static int[] removeNode;
	private static Edge[] edges;
	
	//Change this to false to stop debug (d_ methods) prints!
	private static final boolean debug = true;
	
	public static void main(String[] args) {
		parseInputData();
		
		
	}
	
	private static void parseInputData() {
		Scanner sc = new Scanner(System.in);
		
		int[] sizes = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		N = sizes[0];
		M = sizes[1];
		C = sizes[2];
		P = sizes[3];
		
		edges = new Edge[M];
		for(int i = 0; i < M; i++) {
			int[] edgeInfo = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			edges[i] = new Edge(edgeInfo[0], edgeInfo[1], edgeInfo[2]);
		}
		
		removeNode = new int[P];
		for(int i = 0; i < P; i++) {
			removeNode[i] = Integer.parseInt(sc.nextLine());
		}
		
		sc.close();
		d_printInputData();
	}
	
	private static void d_printInputData() {
		if(!debug)
			return;
		
		System.out.println(N + " " + M + " " + C + " " + P);
		
		for(Edge e : edges) {
			System.out.println(e.nodeA + " " + e.nodeB + " " + e.weigt);
		}
		
		for(int i : removeNode) {
			System.out.println(i);
		}
	}
}
