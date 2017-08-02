package let.util;

import java.util.Arrays;

/**
 * @description:数组相关方法及API方法总结
 * @author ertaoL
 */
public class ArrayUtil {
	
	/**
	 * java.util.
	 * Arrays方法总结 : (其中T代表byte short int long float double boolean T(泛型)数组)
	 * 		static T[]: copyOf(T[] original(源数组), newLength(复制长度)) 进行数组复制
	 * 				如果长度大于源数组长度，整数类型或小数类型数组分别补0或0.0
	 * 				如果长度小于源数组长度，表示进行数组截取操作
	 * 		static T[] : copyOfRange(T[] original(源数组),from(复制起始位置),to(复制结束位置))
	 * 		static int : binarySerach(T[] t, int key) 二分查找指定的目标(返回索引位置)
	 * 		static int : binarySerach(T[] t, int fromIndex, int toIndex, int key) 指定范围二分查找指定的目标
	 * 		static boolean : equals(T[] a, T[] b) 比较两个数字是否相等，相等返回true
	 * 		static void : sort(T[] t) 数组的排序
	 * 		static String : toString(T[] t) 将数组以String类型进行输出
	 */
	//数组复制
	public void testCopyOf(){
		int arr[] = new int[]{1,2,3,4,5};
		int b[] = Arrays.copyOf(arr, arr.length);
		System.out.println(Arrays.toString(b));//[1, 2, 3, 4, 5]
	}
	//范围内复制
	public void testCopyOfRange(){
		int arr[] = new int[]{1,2,3,4,5};
		int b[] = Arrays.copyOfRange(arr, 0, 2);
		System.out.println(Arrays.toString(b));//[1, 2]
	}
	//数组复排序
	public void testSort(){
		int arr[] = new int[]{5,2,3,4,1};
		Arrays.sort(arr);
		System.out.println(Arrays.toString(arr));//[1, 2, 3, 4, 5]
	}
	//数组相等比较
	public void testEqauls(){
		int a[] = new int[]{1,2,3,4,5};
		int b[] = new int[]{1,2,3,4,5};
		char cha[] = new char[]{'a','b','c'};
		char chb[] = new char[]{'a','b','c'};
		System.out.println(Arrays.equals(a, b));//true
		System.out.println(Arrays.equals(cha, chb));//true
	}
	//数组中查找指定的数据，返回数据大于0标识，存在数据，返回的整数是索引
	public void testBinarySerach(){
		int a[] = new int[]{1,2,3,4,5};
		System.out.println(Arrays.binarySearch(a, 1));//0
		System.out.println(Arrays.binarySearch(a, -1));//-1
		System.out.println(Arrays.binarySearch(a, 2, 5, 1));//-3
	}
	
	/**
	 * java.lang.
	 * System方法总结:
	 * 		static void : arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
	 * 			src		: 源数组
	 * 			srcPos	: 源数组要复制的起始位置
	 * 			dest	: 目标数组
	 * 			destPos	: 目标是复制后放置的位置 
	 * 			length	: 复制的长度
	 */
	public void testArrayCopy(){
		int[] a = new int[]{1,2,3,4,5};
		int[] b = new int[5];
		System.arraycopy(a, 2, b, 3, 2);//从a数组的2索引开始复制2个长度从b数组的3索引位置开始放置
		System.out.println(Arrays.toString(b));//[0, 0, 0, 3, 4]
	}
	
	/**
	 * 主函数测试使用
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayUtil a = new ArrayUtil();
		a.testArrayCopy();
	}
}
