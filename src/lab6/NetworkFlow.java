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
	private static int[] removeEdge;
	private static Edge[] edges;
	
	private static int leftBound, rightBound;
	private static int binaryIndex;
	
	//Change this to false to stop debug (d_ methods) prints!
	private static final boolean debug = false;
	
	public static void main(String[] args) {
		parseInputData();
		fordFulkerson();
	}
	
	private static void fordFulkerson() {
		
		int i = 0;
		int flow = 0;
		while(i++ < 10) {
			setNextBinaryIndex(flow);
			flow = DFS();
			
		}
		
		System.out.println(binaryIndex + " " + flow);
		
	}
	
	private static void setNextBinaryIndex(int flow) {
		
		binaryIndex = (rightBound + leftBound)/2;
		
		if(flow < C)
			rightBound = binaryIndex - 1;
		else if(flow > C) 
			leftBound = binaryIndex + 1;
	}
	
	private static int DFS() {
		
		return 0;
	}
	
	private static void parseInputData() {
		Scanner sc = new Scanner(System.in);
		
		int[] sizes = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		N = sizes[0];
		M = sizes[1];
		C = sizes[2];
		P = sizes[3];
		
		leftBound = 0;
		rightBound = P-1;
		
		edges = new Edge[M];
		for(int i = 0; i < M; i++) {
			int[] edgeInfo = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			edges[i] = new Edge(edgeInfo[0], edgeInfo[1], edgeInfo[2]);
		}
		
		removeEdge = new int[P];
		for(int i = 0; i < P; i++) {
			removeEdge[i] = Integer.parseInt(sc.nextLine());
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
		
		for(int i : removeEdge) {
			System.out.println(i);
		}
	}
}
