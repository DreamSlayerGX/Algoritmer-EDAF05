package lab3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Prim {

	private static class Edge {
		public int v;
		public int u;
		public int w;

		public Edge(int v, int u, int w) {
			this.v = v;
			this.u = u;
			this.w = w;
		}

	}

	private static int N, M;
	private static Map<Integer, ArrayList<Edge>> forest;

	private static final boolean recursive = false;

	public static void main(String[] args) {
		parseInputData();
		d_printInputData();
		prims();

	}

	private static void parseInputData() {
		try {
			Scanner sc = new Scanner(System.in);

			String[] size = sc.nextLine().split(" ");

			N = Integer.parseInt(size[0]);
			M = Integer.parseInt(size[1]);

			forest = new HashMap<>();

			for (int i = 0; i < M; i++) {
				int[] edge = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

				int v = edge[0];
				int u = edge[1];
				int w = edge[2];

				if (!forest.containsKey(v))
					forest.put(v, new ArrayList<>());
				if (!forest.containsKey(u))
					forest.put(u, new ArrayList<>());

				Edge e = new Edge(v, u, w);
				forest.get(v).add(e);
				forest.get(u).add(e);

				// Sorts the same way, just two different methods.
				forest.get(v).sort((e1, e2) -> e1.w - e2.w);
				forest.get(u).sort((e1, e2) -> {
					if (e1.w <= e2.w)
						return -1;
					else
						return 1;
				});

			}

		} catch (Exception e) {

		}

	}

	private static void prims() {

		boolean[] vertexConnected = new boolean[forest.size()];
		PriorityQueue<Edge> edgeSet = new PriorityQueue<>((e1, e2) -> (e1.w <= e2.w) ? -1 : 1);

		edgeSet.addAll(forest.get(1));
		vertexConnected[forest.get(1).get(0).v - 1] = true;
		int totalWeight = 0;

		if (recursive) {
			totalWeight = primsRec(edgeSet, vertexConnected, 0);
		} else {
			totalWeight = primsIter(edgeSet, vertexConnected);
		}

		System.out.println(totalWeight);

	}

	private static int primsRec(PriorityQueue<Edge> edgeSet, boolean[] vertexConnected, int totalWeight) {

		Edge e = edgeSet.poll();

		if (e == null)
			return totalWeight;

//		System.out.println(e.v + " " + e.u + " " + e.w);
//		System.out.println("Size of set: " + edgeSet.size());
//		System.out.println("Visited index " + (e.v) + " and " + e.u + ": " + vertexConnected[e.u - 1] +"\n");

		if (vertexConnected[e.v - 1] && vertexConnected[e.u - 1]) {
			return primsRec(edgeSet, vertexConnected, totalWeight);
		}

		int nextVertex = 0;
		if (vertexConnected[e.v - 1]) {
			vertexConnected[e.u - 1] = true;
			nextVertex = e.u;
		} else {
			vertexConnected[e.v - 1] = true;
			nextVertex = e.v;
		}

		for (Edge adjVertex : forest.get(nextVertex)) {
			// System.out.print("Visited " + adjVertex.v + " and/or " + adjVertex.u + "?");
			if (vertexConnected[e.v - 1] && vertexConnected[e.u - 1])
				edgeSet.add(adjVertex);
		}

		return primsRec(edgeSet, vertexConnected, totalWeight + e.w);
	}

	private static int primsIter(PriorityQueue<Edge> edgeSet, boolean[] vertexConnected) {

		int totalWeight = 0;
		
		while(!edgeSet.isEmpty()) {
			Edge e = edgeSet.poll();
			int nextVertex = 0;
			
			if (vertexConnected[e.v - 1] && vertexConnected[e.u - 1]) {
				continue;
			} else if (vertexConnected[e.v - 1]) {
				vertexConnected[e.u - 1] = true;
				nextVertex = e.u;
			} else {
				vertexConnected[e.v - 1] = true;
				nextVertex = e.v;
			}

			for (Edge adjVertex : forest.get(nextVertex)) {
				if ((vertexConnected[e.v - 1] && vertexConnected[e.u - 1]))
					edgeSet.add(adjVertex);
			}
			
			totalWeight += e.w;
			
		}
		
		return totalWeight;
	}

	private static void d_printInputData() {
		System.out.println(N + " " + M);

		for (Entry<Integer, ArrayList<Edge>> s : forest.entrySet()) {
			System.out.println(s.getKey());
			for (Edge e : s.getValue())
				System.out.println("  " + e.v + " " + e.u + " " + e.w);
		}
		System.out.println();

	}

}
