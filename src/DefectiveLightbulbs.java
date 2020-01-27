
public class DefectiveLightbulbs {
	
	int[] leftArr;
	int[] rightArr;
	//int numThread;
	//boolean isDefective;
	
	
	public static void main(String args[]) {
		
		int[] input = {1,1,0,1,1,0,1,1};
		int size = input.length;
		
		DefectiveLightbulbs DL = new DefectiveLightbulbs(size);
		
		DL.FindDefective(input,size);
	}
	
	public DefectiveLightbulbs(int psize) {
		leftArr = new int [psize];
		rightArr = new int[psize];
	}
	
	public void FindDefective(int[] a,int psize) {
		
		if(psize == 1) { //base case
			if(a[0] == 0) {
				System.out.println("Defective Bulb at position: " + a[0]);
			}
			//thread joins here
		}
		else {
			
			if(CheckDefective(a,psize)) {
				System.arraycopy(a, 0, leftArr, 0, psize>>1 -1);
				System.arraycopy(a,psize>>1,rightArr,0,psize>>1);
				
				//System.out.println(leftArr.length);
				
				FindDefective(leftArr,leftArr.length);
				FindDefective(rightArr,rightArr.length);
			}
			
			//thread joins here
		}
	}
	
	public boolean CheckDefective(int[] a, int psize) {
		for(int i = 0; 0 < psize; i++ ) {
			if(a[i] == 0) {
				return true;
			}
		}
		return false;
	}
}
