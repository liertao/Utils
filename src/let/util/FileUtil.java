package let.util;

import java.io.File;

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
	 * 主程序测试
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		String path = "D:"+File.separator+"Work Note"+File.separator+"test"+File.separator+"222.txt";
		//测试创建目录
//		if(createDir(path))
//			System.out.println("创建成功");
		//测试创建文件
		createFile(path);
		
	}
	
}
