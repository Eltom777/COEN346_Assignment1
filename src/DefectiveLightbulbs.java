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
			String[] number = readFile(args[0]).split("\\n");
			int size = number.length;

			//Convert String Builder to Integer Array for Manipulation
			int[] input = new int[size];
			for (int i = 0; i < size; i++) {
				input[i] = Integer.parseInt(number[i]);
				if(i > 0 && input[i] != 1 && input[i] != 0) { //Ensure the array of bulbs only contains 1s or 0s
					System.out.println( input[i] + " is and invalid entry." );
					System.exit(0);
				}
			}

			System.out.println("Input content: " + Arrays.toString(input));
			
			if(input[0] == size-1) {

				DefectiveLightbulbs DL = new DefectiveLightbulbs(input,1,size-1);

				DL.start(); //creating the first thread
				DL.numOfThread++;

				try {
					DL.join(); //waiting for all subthreads to finish before printing out numOfThread
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				System.out.println("Number of threads used: " + DL.numOfThread);
			}
			else {
				System.out.println("Input size in file does not match the actaully number of bulbs in the array.");
				System.out.println("Number of bulbs in input file: "+ input[0]);
				System.out.println("Actual number of bulbs: " + (size-1));	
			}
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
				int pivot = (size >> 1) + start; // determine the middle of the array
				
				//System.out.println("left array"); // uncomment to show concurrency 
				DefectiveLightbulbs DL1 = new DefectiveLightbulbs(a,start,pivot-1); // constructing leftarr
				
				//System.out.println("right array"); // uncomment to show concurrency
				DefectiveLightbulbs DL2 = new DefectiveLightbulbs(a,pivot,end); // constructing rightarr
				
				DL1.start();
				numOfThread++;
				DL2.start(); //creating two new threads for left and right arr
				numOfThread++;
				
				try {
					DL1.join();
					DL2.join(); // waiting for all sub threads to finish to have the correct numOfThread
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void run() { // every thread will execute FindDefective
		FindDefective();
	}
	
	public boolean CheckDefective() { //checks to see if there is at least one zero in the array
		for(int i = start; i <= end; i++ ) {
			if(a[i] == 0) {
				return true;
			}
		}
		return false;
	}
	
	private static String readFile(String name) { // method to extract content from a .txt and convert it to an array of strings
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
