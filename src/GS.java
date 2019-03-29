import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GS {
	
	public static void main(String[] args) throws IOException {
		

		File input = new File("‪sample.txt");
		BufferedReader br = new BufferedReader(new FileReader("real1.txt"));

		int n = Integer.parseInt(br.readLine());
		
		//Women
		Map<Integer, int[]> protons = new HashMap<>();
		//Men
		Map<Integer, int[]> electrons = new HashMap<>();
		
		for(int i = 0; i < 2*n; i++) {
			
			String[] lineNumbers = br.readLine().split(" ");
			
			int key = Integer.parseInt(lineNumbers[0]);
			
			String[] preferences = Arrays.copyOfRange(lineNumbers, 1, lineNumbers.length);
			
			
			if(!protons.containsKey(key)) {
				protons.put(key, Arrays.stream(preferences).mapToInt(Integer::parseInt).toArray());
			}
			else {
				electrons.put(key, Arrays.stream(preferences).mapToInt(Integer::parseInt).toArray());
				
			}
			
		}
		
		d_printInput(protons, electrons);
	}
	
	
	
	
	private static void d_printInput(Map<Integer, int[]> p, Map<Integer, int[]> e) {
		
		System.out.println(p.size());
		//System.out.println("Protons / Women");
		for(Integer i : p.keySet()) {
			System.out.print(i + " ");
			for(int x : p.get(i)) {
				System.out.print(x + " ");
			}
			System.out.println();
		}
		
		//System.out.println("Electrons / Men");
		for(Integer i : e.keySet()) {
			System.out.print(i + " ");
			for(int x : e.get(i)) {
				System.out.print(x + " ");
			}
			System.out.println();
		}
		
	}
	

}
