public class DefectiveLightbulbs extends Thread {
	
	int start;
	int end;
	int[] a;
	static int numOfThread = 0;
	
	public static void main(String args[]) {
		
		int[] input = {8,1,1,0,1,1,0,1,1};
		int size = input[0];
		
		DefectiveLightbulbs DL = new DefectiveLightbulbs(input,1,size-1);
		
		DL.start();
		
		try {
			DL.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Number of threads used: " + DL.numOfThread);
	}
	
	public DefectiveLightbulbs(int[] a,int start, int end) {
		this.start = start;
		this.end = end;
		this.a = a;
	}
	
	public void FindDefective(int a[],int start,int end) {
		
		if(start == end) { //base case
			if(a[start] == 0) {
				System.out.println("Defective Bulb at position: " + start);
			}
			//thread joins here
		}
		else {
			if(CheckDefective(a, start, end)) {
				
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
			//thread joins here
		}
	}
	
	public void run() {
		FindDefective(a,start,end);
	}
	
	public boolean CheckDefective(int[] a, int start, int end) {
		for(int i = start; i <= end; i++ ) {
			if(a[i] == 0) {
				return true;
			}
		}
		return false;
	}
}
