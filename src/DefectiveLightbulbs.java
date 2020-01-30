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
				
				FindDefective(a,start,pivot-1); //leftArr
				FindDefective(a,pivot,end); //rightArr
				//thread joins here
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
