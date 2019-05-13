package lab4;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class DivideAndConquer {
	
	public static class Point{
		public int x, y;
		
		Point(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	
	public static class Pair{
		public float minDist;
		public int[][] points;
		public int[][] distPoints;
		
		Pair(float minDist, int[][] points, int[][] distPoints){
			this.minDist = minDist;
			this.points = points;
			this.distPoints = distPoints;
		}
	}
	
	private static int N;
	private static Point[] Px, Py;
	private static Point[] P;

	
	
	public static void main(String[] args) {
		parseInputData();
		d_printInputData();
//		Pair result = divideAndConquer(Px, Py, N);
//		
//		System.out.println(result.minDist);
	}
	
	private static Pair divideAndConquer(Point[] px2, Point[] py2, int n) {

		int halfSize = n/2;
		Point[] Lx = Arrays.copyOfRange(px2, 0, halfSize);
		Point[] Ly = Arrays.copyOfRange(py2, 0, halfSize);
		Point[] Rx = Arrays.copyOfRange(px2, halfSize, n);
		Point[] Ry = Arrays.copyOfRange(py2, halfSize, n);
		
		Pair leftResult = divideAndConquer(Lx, Ly, halfSize);
		Pair rightResult = divideAndConquer(Rx, Ry, halfSize);
		
		return null;
		
	}
	
	
	private static float getDistance(int[] a, int[] b) {
		return (float) Math.sqrt(Math.abs(a[0] - b[0]) * Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]) * Math.abs(a[1] - b[1]));
	}
	
	private static void parseInputData() {
		try {
			Scanner sc = new Scanner(System.in);
						
			N = Integer.parseInt(sc.nextLine());
			
			Px = new Point[N];
			Py = new Point[N];
			P = new Point[N];
			
			for(int i = 0; i < N; i++) {
				int[] points = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
				Point p = new Point(points[0], points[1]);
				P[i] = p;
				Px[i] = p;
				Py[i] = p;
			}
			
			
			Arrays.sort(Px, (p1, p2) -> p1.x - p2.x);
			Arrays.sort(Py, (p1, p2) -> p1.y - p2.y);
			
		} catch (Exception e) {
			
		}
		
	}
	
	private static void d_printInputData() {
		System.out.println(N);
		for(int i = 0; i < N; i++)
			System.out.println("Sorted X: " + Px[i].x + " " + Px[i].y + " | Sorted Y:  " + Py[i].x + " " + Py[i].y);
	}

}
