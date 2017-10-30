package let.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;

/**
 * @Des:文件操作工具类
 * @author ertaoL
 */
public class FileUtil {
	
	/**
	 * 创建指定目录
	 * 		
	 * 		校验输入字符串必须为目录
	 * 
	 * @param dirName
	 * @return
	 */
	public static boolean createDir(String dirName){
		File dir = new File(dirName);
		if(!dirName.endsWith(".")){
			System.out.println("文件路径不是目录");
			return false;
		}
		//先校验目录是否存在，不存在进行创建，如果存在强行创建不影响源目录中的文件
		if(dir.exists())
			return false;
		dir.mkdirs();
		return true;
	}
	
	/**
	 * 创建指定文件 
	 * 
	 * 		可以校验输入路径必须是文件而不是目录，
	 * 		校验文件是否已经存在，不存在才进行创建			
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean createFile(String filePath)throws Exception{
		File file = new File(filePath);
		//如果指定文件路径是目录 直接返回
		if(filePath.endsWith(File.separator)){
			System.out.println("文件路径不能为目录");
			return false;
		}
		//如果目标文件存在 直接返回
		if(file.exists()){
			System.out.println("指定文件已经存在");
			return false;
		}
		//判断目标文件的目录是否存在
		if(!file.getParentFile().exists()){
			if(file.getParentFile().mkdirs()){
				if(file.createNewFile()){
					System.out.println("文件创建成功");
					return true;
				}else{
					System.out.println("文件创建失败");
					return false;
				}
			}
		}
		if(file.createNewFile()){
			System.out.println("文件创建成功");
			return true;
			
		}else{
			System.out.println("文件创建失败");
			return false;
		}
	}
	
	/**
	 * 将制定的字符串写入到指定的文件中,使用（FileOutputStream）
	 * 
	 * 		其中FileOutputStream fs = new FileOutputStream(filePath,true)中的true参数
	 * 		表示在文档末尾追加数据，如果没有true则表示每次覆盖文档内容
	 * 
	 * @param str
	 * @param filePath
	 * @return
	 */
	public static boolean str2File2(String str,String filePath)throws Exception{
		createFile(filePath);
		FileOutputStream fs = new FileOutputStream(filePath,true);
		if(str==null)return false;//如果字符串为null直接返回
		str.getBytes();
		fs.write(str.getBytes());
		fs.close();//关闭流，节省资源
		return true;
	}
	
	/**
	 * 将制定的字符串写入到指定的文件中,使用（FileWriter）
	 * 
	 * 
	 * @param str
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static boolean str2File3(String str,String filePath)throws Exception{
		createFile(filePath);
		FileWriter fw = new FileWriter(filePath,true);
		BufferedWriter bf = new BufferedWriter(fw);
		if(str==null) return false;
		bf.write(str);
		bf.append(str);
		bf.close();
		fw.close();
		return true;
	}
	
	/**
	 * 将制定的字符串写入到指定的文件中,使用（PrintStream）
	 * 
	 * 		使用  PrintStream  快速输出，本程序每次执行是直接覆盖，如果是追加
	 * 		则使用：ps.append(str2);
	 * 		如果转行：在str中加入  \r\n  实现转行
	 * 
	 * @param str
	 * @param String
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean str2File(String str,String filePath)throws Exception{
		
		createFile(filePath);
		PrintStream ps = new PrintStream(new FileOutputStream(new File(filePath)));
		ps.println(str);
		ps.close();//关闭流，节省资源
		return true;
	}
	
	/**
	 * 主程序测试
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		String path = "D:"+File.separator+"Work Note"+File.separator+"test"+File.separator+"222.txt";
		//测试创建目录
//		if(createDir(path))
//			System.out.println("创建成功");
		//测试创建文件
		//createFile(path);
		String str = "你好，世界";
		
		str2File2(str,"D:\\Work Note\\test\\111.txt");
		
		//测试字符串输出到指定文件
		//String str = "as大红色的递四方速递发多少发多少发生\r\n的发生的发送地斯蒂芬sad发生的发多少发生的发生的发多少发生的";
		//str2File(str,"D:\\Work Note\\test\\111.txt");
		
	}
	
}
