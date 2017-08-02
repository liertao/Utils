package let.algorithm;

import java.util.Arrays;
/**
 * @description:冒泡排序法
 * 		最多做n-1次循环  所以i的取值为 i<arr.length-1
 * 		j的范围很重要，是在不断缩小的j<arr.length-i-1
 * @author ertaoL
 */
public class BubbleSort {
	
	public static void main(String[] args) {
		int[] arr = new int[]{8,2,6,3,0,1,5,9,4};
		System.out.println(Arrays.toString(arr));
		for(int i=0;i<arr.length-1;i++){           //△最多做n-1次循环
			for(int j=0;j<arr.length-i-1;j++){     //△第二次循环的范围主键变小
				if(arr[j]>arr[j+1]){               //△把小值交换到后面
					int temp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = temp;
				}
			}
		}
		System.out.println(Arrays.toString(arr));
	}
	
}
