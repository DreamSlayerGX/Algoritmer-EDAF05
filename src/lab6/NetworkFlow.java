package lab6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class NetworkFlow {

	private static class Edge {

		public final int nodeA, nodeB;
		public final int weigt;

		public Edge(int nodeA, int nodeB, int weigt) {
			this.nodeA = nodeA;
			this.nodeB = nodeB;
			this.weigt = weigt;
		}

	}

	// N nodes, E edges, C throughtput of students, P routes in plan
	private static int N, M, C, P;
	private static int[] removeNode;
	private static Edge[] edges;

	// Change this to false to stop debug (d_ methods) prints!
	private static final boolean debug = false;

	/**
	 * This is the main method. It parses the input file, then calcualtes the
	 * optimal number of route drops for the network.
	 */
	public static void main(String[] args) {
		parseInputData();
		binaryDrop(0, P - 1);
	}

	/**
	 * A binary search algorithm that will use the Ford Fulkersson algorithm to
	 * calculate maximum number of routs that can be dropped while still allowing
	 * for the minumum required throughput of students from Minsk (source node) to
	 * Lund (sink node). The Binary search is conducted by calculating the
	 * throughput when half of the possible routes have been dropped and (depending
	 * of the the throughput requirements) will either add back half of the removed
	 * routes or drop half of the remaining possible routes. Eventually the
	 * binaryDrop method will home in on the optimal number of routes to drop that
	 * still maintains the required throughput.
	 */
	private static void binaryDrop(int low, int high) {
		int range = high - low;
		
		if (range > 3) {
			int mid = range / 2 + low;
			int flux = fordFulkerson(makeGraphFromEdges(mid));
			if (flux >= C) {
				binaryDrop(mid, high);
			} else if (flux <= C) {
				binaryDrop(low, mid);
			}
		} else {
			int drops = -1;
			for (int i = low; i <= high; i++) {
				int flux = fordFulkerson(makeGraphFromEdges(i));
				if (flux < C && drops < 0) {
					drops = i - 1;
				}
			}
			if (drops < 0) {
				drops = high;
			}
			System.out.println(drops + " " + fordFulkerson(makeGraphFromEdges(drops)));
		}

	}

	/**
	 * This is an implementation of the Ford Fulkerson algorithm for calculating the
	 * total flux through a network. The method takes a graph with edge weights and
	 * calculates the maximum possible throughput of that graph (assuming source
	 * node at index 0 and sink node at index N-1).
	 */
	private static int fordFulkerson(int[][] g) {
		int[][] rGraph;
		int max_flow = 0;
		int m = N - 1;
		
		rGraph = new int[N][N];
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				rGraph[i][j] = g[i][j];
			}
		}
		
		ArrayList<Integer> foundPath = DFS(rGraph);
		d_printGraph(rGraph);
		
		while (foundPath != null) {
			int flux = foundPath.get(0);
			foundPath.remove(0);
			Collections.reverse(foundPath);
			int[] path = foundPath.stream().mapToInt(Integer::intValue).toArray();
			for (int n = 0; n < path.length - 1; n++) {
				rGraph[path[n]][path[n + 1]] = rGraph[path[n]][path[n + 1]] - flux;
				rGraph[path[n + 1]][path[n]] = rGraph[path[n + 1]][path[n]] - flux;
			}
			max_flow = max_flow + flux;
			foundPath = DFS(rGraph);
		}
		return max_flow;
	}

	/**
	 * The top level depth first search method. Given a graph, it will return an
	 * ArrayList<Integer> where the first element is the smallest wedge weight, and
	 * every subsiquent element is the path taken from the source node (0) and the
	 * sink node (N-1)
	 */
	private static ArrayList<Integer> DFS(int[][] g) {
		boolean[] visited = new boolean[g.length];
		ArrayList<Integer> path = recDFS(g, visited, 0);
		return path;
	}

	/**
	 * This is the recursion for the depth first search. The method requires that
	 * you pass the graph with edge weights as well as a vector that denotes which
	 * nodes have been visited, as well as the node index for the node you want to
	 * start the DFS on. The DFS always terminates when it reaches the sink node
	 * (N-1)
	 */
	private static ArrayList<Integer> recDFS(int[][] g, boolean[] visited, int current) {
		visited[current] = true;
		ArrayList<Integer> path = null;
		if (current == g.length - 1) {
			path = new ArrayList<Integer>();
			path.add(Integer.MAX_VALUE);
			path.add(current);
			return path;
		} else {
			for (int i = g.length - 1; i >= 0; i--) {
				if (g[current][i] != 0 && visited[i] == false) {
					path = recDFS(g, visited, i);
				}
				if (path != null) {
					path.add(current);
					if (path.get(0) > g[current][i]) {
						path.set(0, g[current][i]);
					}
					return path;
				}
			}
			return null;
		}
	}

	/**
	 * Tell this method how many routes to drop, it will build a graph with those
	 * routes dropped.
	 */
	private static int[][] makeGraphFromEdges(int drops) {
		Set<Integer> drop_Set = new HashSet<Integer>();
		for (int j = 0; j < drops; j++) {
			drop_Set.add(removeNode[j]);
		}
		int[][] g = new int[N][N];
		if (debug) {
			System.out.println("drops: " + drops);
			System.out.println("drop_Set: " + drop_Set);
		}
		for (int i = 0; i < M; i++) {
			Edge e = edges[i];
			if (drop_Set.contains(i)) {
				if (debug) {
					System.out.println("dropped i: " + i + " & dropped edges[i]: [" + e.nodeA + " " + e.nodeB + " "
							+ e.weigt + "]");
				}
			} else {
				g[e.nodeA][e.nodeB] = e.weigt;
				g[e.nodeB][e.nodeA] = e.weigt;
				if (debug) {
					System.out.println("i: " + i + " edges[i]: [" + e.nodeA + " " + e.nodeB + " " + e.weigt + "]");
				}
			}
		}
		return g;
	}

	// -----------------------------
	// Parse ALL the Input
	// -----------------------------

	private static void parseInputData() {
		Scanner sc = new Scanner(System.in);

		int[] sizes = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
		N = sizes[0]; // Nodes
		M = sizes[1]; // Edges
		C = sizes[2]; // Min Flux
		P = sizes[3]; // Routes

		edges = new Edge[M];
		for (int i = 0; i < M; i++) {
			int[] edgeInfo = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
			edges[i] = new Edge(edgeInfo[0], edgeInfo[1], edgeInfo[2]);
		}

		removeNode = new int[P];
		for (int i = 0; i < P; i++) {
			removeNode[i] = Integer.parseInt(sc.nextLine());
		}

		sc.close();
		d_printInputData();
	}

	// -----------------------------
	// Print ALL the things
	// -----------------------------

	/** Help method for debugging. */
	private static void d_printPath(ArrayList<Integer> path) {
		if (!debug)
			return;

		if (path != null) {
			System.out.println(" ");
			for (Integer i : path) {
				System.out.print(i + "<-");
			}
			System.out.print("Path");
		} else {
			System.out.print("Path not found");
		}
	}

	/** Help method for debugging. */
	private static void d_printGraph(int[][] g) {
		if (!debug)
			return;

		for (int i = 0; i < g.length; i++) {
			System.out.println(" ");
			for (int j = 0; j < g.length; j++) {
				System.out.print(" " + g[i][j]);
			}
		}
		System.out.println(" ");
	}

	/** Help method for debugging. */
	private static void d_printInputData() {
		if (!debug)
			return;

		System.out.println(N + " " + M + " " + C + " " + P);

		for (Edge e : edges) {
			System.out.println(e.nodeA + " " + e.nodeB + " " + e.weigt);
		}

		for (int i : removeNode) {
			System.out.println(i);
		}
	}

	/** Help method for debugging. */
	private static void printInputData() {

		System.out.println(N + " " + M + " " + C + " " + P);

		for (Edge e : edges) {
			System.out.println(e.nodeA + " " + e.nodeB + " " + e.weigt);
		}

		for (int i : removeNode) {
			System.out.println(i);
		}
	}
}
