import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class DefectiveLightbulbs extends Thread {
	
	int start;
	int end;
	int[] a;
	static int numOfThread = 0;
	
	public static void main(String args[]) {
		
		if(args.length > 1) {
			System.out.println("Invalid path to input.txt! Recieved more than one argument");
		}
		else {
			//Read file into a String Builder
			String[] number = readFile("src/Input.txt").split("\\n");

			//Convert String Builder to Integer Array for Manipulation
			int[] input = new int[number.length];
			for (int i = 0; i<number.length; i++) {
				input[i] = Integer.parseInt(number[i]);
			}

			System.out.println(Arrays.toString(input));

			//int[] input = {8,1,1,0,1,1,0,1,1};
			int size = input[0];

			DefectiveLightbulbs DL = new DefectiveLightbulbs(input,1,size-1);

			DL.start();
			DL.numOfThread++;

			try {
				DL.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Number of threads used: " + DL.numOfThread);
		}
	}
	
	public DefectiveLightbulbs(int[] a,int start, int end) {
		this.start = start;
		this.end = end;
		this.a = a;
	}
	
	public void FindDefective() {
		
		if(start == end) { //base case
			if(a[start] == 0) {
				System.out.println("Defective Bulb at position: " + start);
			}
		}
		else {
			if(CheckDefective()) {
				
				int size = (end-start+1);
				int pivot = (size >> 1) + start;
				
				//System.out.println("left array"); // uncomment to show concurrency 
				DefectiveLightbulbs DL1 = new DefectiveLightbulbs(a,start,pivot-1);
				numOfThread++;
				
				//System.out.println("right array"); // uncomment to show concurrency
				DefectiveLightbulbs DL2 = new DefectiveLightbulbs(a,pivot,end);
				numOfThread++;
				
				DL1.start();
				DL2.start();
				
				try {
					DL1.join();
					DL2.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void run() {
		FindDefective();
	}
	
	public boolean CheckDefective() {
		for(int i = start; i <= end; i++ ) {
			if(a[i] == 0) {
				return true;
			}
		}
		return false;
	}
	
	private static String readFile(String name) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(name)));
			StringBuilder sb = new StringBuilder();
			String line = "";
			
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}
	
}
