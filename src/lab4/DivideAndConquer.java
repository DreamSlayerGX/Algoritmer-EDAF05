package lab4;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

public class DivideAndConquer {
	
	public static class Point{
		public int x, y;
		
		Point(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	
	private static int N;
	private static Point[] Px, Py;
	private static Point[] P;

	private static DecimalFormat df = new DecimalFormat("#.######");
	
	
	public static void main(String[] args) {
		parseInputData();
		d_printInputData();
		double result = divideAndConquer(Px, N);
		
		
		System.out.printf("\n%.6f", result);
	}
	
	private static double divideAndConquer(Point[] px, int n) {
		if(px.length == 1) {
			return Double.MAX_VALUE;
		} else if(px.length == 2) {
			return getDistance(px[0], px[1]);
		}
		
		
		int halfSize = n/2;
		Point[] Lx = Arrays.copyOfRange(px, 0, halfSize);
		//Point[] Ly = Arrays.copyOfRange(py, 0, halfSize);
		
		Point[] Rx = Arrays.copyOfRange(px, halfSize, n);
		//Point[] Ry = Arrays.copyOfRange(py, halfSize, n);
		
		double leftResult = divideAndConquer(Lx, halfSize);
		double rightResult = divideAndConquer(Rx, halfSize);
		
		double delta = Math.min(leftResult, rightResult);
		double min = delta;
		
		Point midPoint = Rx[0];
		Point[] insideDelta = new Point[n];
		
		int j = 0;
		for(int i = 0; i < halfSize; i++) {
			if(Math.pow(Rx[i].x - midPoint.x, 2) > delta)
				break;
			
			insideDelta[j++] = Rx[i];
		}
		
		for(int i = halfSize-1; i >= 0; i--) {
			if(Math.pow(Lx[i].x - midPoint.x, 2) > delta)
				break;
			
			insideDelta[j++] = Lx[i];
		}
		
		d_printSubArray(insideDelta, min);
		
		for(int i = 0; i < insideDelta.length && insideDelta[i] != null; i++)
			for(int k = i + 1; k < insideDelta.length && insideDelta[k] != null; k++) {
				double distance = getDistance(insideDelta[i], insideDelta[k]);
				
				if(distance < min)
					min = distance;
			}
					
					
		return min;
		
	}
	
	
	private static double getDistance(Point a, Point b) {
		return (double) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
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
			
			sc.close();
		} catch (Exception e) {
			
		}
		
	}
	
	private static void d_printSubArray(Point[] p, double min) {
		System.out.println(p.length + " | Current min: " + Double.valueOf(df.format(min)));
		for(Point pp : p)
			if(pp != null)
				System.out.println(pp.x + " " + pp.y);
		System.out.println();
	}
	
	private static void d_printInputData() {
		System.out.println(N);
		for(int i = 0; i < N; i++)
			System.out.println("Sorted X: " + Px[i].x + " " + Px[i].y + " | Sorted Y:  " + Py[i].x + " " + Py[i].y);
		System.out.println();
	}

}
