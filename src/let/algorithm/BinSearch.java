package let.algorithm;
/**
 * @description:二分查找法的两种实现方式
 * 				第一种：非递归实现
 * 				第二种：递归实现
 * @author ertaoL
 */
public class BinSearch {
	/**
	 * 二分法非递归实现
	 * @param arr：数组
	 * @param a：查询目标
	 * @return int
	 */
	public static int binSerach(int[] arr, int a){
		int lo = 0;
		int hi = arr.length-1;
		int mid;
		while(lo<=hi){
			mid = (lo+hi)/2;
			if(arr[mid]==a){
				return mid+1;
			}else if(arr[mid]<a){
				lo = mid+1;
			}else{
				hi = mid - 1;
			}
		}
		return -1;
	}
	/**
	 * 二分法递归实现
	 * @param arr：数组
	 * @param a：查询目标
	 * @param lo：其实位置
	 * @param hi：结束位置
	 * @return：int
	 */
	public static int binSearch(int arr[], int a, int lo, int hi){
		if(lo<=hi){
			int mid = (lo+hi)/2;
			if(arr[mid]==a){
				return mid+1;
			}else if(arr[mid]>a){
				return binSearch(arr, a, lo, mid-1);
			}else{
				return binSearch(arr, a, mid+1, hi);
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		BinSearch bin = new BinSearch();
		int[] arr = new int[]{2,3,7,8,9};
		System.out.println(bin.binSerach(arr, 1));
		System.out.println(bin.binSearch(arr, 2, 0, arr.length-1));
	}
	
}
