package lab4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DivideAndConquer {
	
	private static class Point {
		public final int x, y;
		
		Point(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	
	private static int N;
	private static List<Point> locations;
	
	
	public static void main(String[] args) {
		parseInputData();
		d_printInputData();
	}
	
	
	private static float getDistance(Point a, Point b) {
		return (float) Math.sqrt(Math.abs(a.x - b.x) * Math.abs(a.x - b.x) + Math.abs(a.y - b.y) * Math.abs(a.y - b.y));
	}
	
	private static void parseInputData() {
		try {
			Scanner sc = new Scanner(System.in);
			
			locations = new ArrayList<>();
			
			N = Integer.parseInt(sc.nextLine());
			
			for(int i = 0; i < N; i++) {
				int[] points = Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
				locations.add(new Point(points[0], points[1]));
			}
			
			
		} catch (Exception e) {
			
		}
		
	}
	
	private static void d_printInputData() {
		System.out.println(N);
		for(Point p : locations)
			System.out.println(p.x + " " + p.y);
	}

}
