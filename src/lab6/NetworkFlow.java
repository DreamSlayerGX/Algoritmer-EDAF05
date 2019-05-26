package lab6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
		
		public String toString() {
			return (nodeA) + " " + (nodeB) + " " + (weigt);
		}
		
	}
	
	private static int N, M, C, P;
	private static ArrayList<Integer> removeEdge;
	private static Edge[] edges;
	
	private static Map<Integer, ArrayList<Integer>> graph;
	
	private static int leftBound, rightBound;
	private static int binaryIndex;
	
	//Change this to false to stop debug (d_ methods) prints!
	private static final boolean debug = true;
	
	public static void main(String[] args) {
		parseInputData();
		fordFulkerson();
	}
	
	private static void fordFulkerson() {
		
		int i = -1;
		int flow = 0;
		
		while(i++ != 3) {
		//while(i != binaryIndex) {
			//i = binaryIndex;
			setNextBinaryIndex(flow);
			flow = DFS();
			
		}
		
		System.out.println(binaryIndex + " " + flow);
		
	}
	
	private static int DFS() {
		Edge[] edgeSubSet = generateSubSet();
		boolean[] visited = new boolean[edgeSubSet.length];

		if(edgeSubSet == null)
			return 0;
		
		
		return 0;
	}
	
	/*
	 * To generate the subset of edges to go through.
	 * This will take O(N) since we go through the list every time. Save list and modify it?
	 */
	private static Edge[] generateSubSet() {
		Edge[] subSet = new Edge[(edges.length - 1) - binaryIndex];
		List<Integer> subRemove = removeEdge.subList(0, binaryIndex + 1);
		int j = 0;
		
		for(int i = 0; i < edges.length; i++) {
			if(!subRemove.contains(i))
				subSet[j++] = edges[i]; 
		}
		
		d_printSubSet(subSet);
		
		return createNewGraph(subSet) ? subSet : null;
	}
	
	/*
	* Generate the graph corresponding to the subset of edges.
	* Costly to do this every time. Returns false if node Minsk (0) or Lund (N-1) doesn't exist.
	* O(N^2)
	*/
	private static boolean createNewGraph(Edge[] edgeSubSet) {
		graph = new HashMap<>();
		
		for(int i = 0; i < edgeSubSet.length; i++) {
			int keyNode = edgeSubSet[i].nodeA;
			
			if(!graph.containsKey(keyNode))
				graph.put(keyNode, new ArrayList<>());
			
			graph.get(keyNode).add(edgeSubSet[i].nodeB);
			
			for(int j = i + 1; j < edgeSubSet.length; j++) {
				Edge e = edgeSubSet[j];
				
				if(e.nodeA == keyNode)
					graph.get(keyNode).add(e.nodeB);
				else if(e.nodeB == keyNode)
					graph.get(keyNode).add(e.nodeA);
				
			}
		}
		
		d_printGraph();
		
		return (graph.containsKey(0) || graph.containsKey(N - 1));
		
	}
	
	/*
	 * To set the index of which we remove edges (binary search)
	 */
	private static void setNextBinaryIndex(int flow) {
		
		binaryIndex = (rightBound + leftBound)/2;
		
		if(flow < C)
			rightBound = binaryIndex - 1;
		else if(flow > C) 
			leftBound = binaryIndex + 1;
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
		
		removeEdge = new ArrayList<>(P);
		for(int i = 0; i < P; i++) {
			removeEdge.add(Integer.parseInt(sc.nextLine()));
		}
		
		sc.close();
		d_printInputData();
	}
	
	private static void d_printGraph() {
		if(!debug)
			return;
		
		System.out.print("\n\nGraph");
		for(Entry<Integer, ArrayList<Integer>> set : graph.entrySet()) {
			System.out.print("\n" + set.getKey() + " | ");
			for(Integer toNode : set.getValue())
				System.out.print(" " + toNode);
			
		}
	}
	
	private static void d_printSubSet(Edge[] subSet) {
		System.out.println("\n\nSubSet");
		System.out.println("BinaryIndex = " + binaryIndex);
		for(Edge e : subSet) {
			if(e == null) {
				System.out.println("NULL");
			} else {
				System.out.println(e.toString());
			}
		}
	}
	
	private static void d_printInputData() {
		if(!debug)
			return;
		
		System.out.println(N + " " + M + " " + C + " " + P);
		
		for(Edge e : edges) {
			System.out.println(e.toString());
		}
		
		for(int i : removeEdge) {
			System.out.println(i);
		}
	}
}
