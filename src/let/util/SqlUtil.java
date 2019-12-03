package com.let.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * sql文件处理工具
 * 
 * @author liertao
 * 
 */
public class SqlUtil {

	public static void main(String[] args) throws Exception {
		// 源txt路径
		String srcPath = "D:\\DeskTop\\33\\sql\\e_log_user.sql";
		// 编码格式
		String code = "gbk";
		// 输出txt路径
		String desPath = "D:\\DeskTop\\33\\sql1\\e_log_user.sql";
		// 要导入的表名
		String tableName = "e_log_user";
		createSql(srcPath, desPath, code, tableName);
	}

	/**
	 * @Description:对某张表的sql语句进行处理， 把id变为ep_emis_basic.uf_get_squence
	 * 
	 * @param srcPath:源文件路径
	 * @param desPath:目标文件路径
	 * @param code:编码
	 * @param tableName:表名
	 * @throws Exception
	 */
	public static void createSql(String srcPath, String desPath, String code,
			String tableName) throws Exception {
		// 1.读取文件
		File file = new File(srcPath);
		InputStreamReader read = new InputStreamReader(
				new FileInputStream(file), code);// 考虑到编码格式
		BufferedReader bu = new BufferedReader(read);
		// 2.拼接字符串
		String lineText = null;
		// 3.边读边写
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(
				new File(desPath)));
		BufferedWriter wr = new BufferedWriter(out);

		while ((lineText = bu.readLine()) != null) {
			System.out.println(lineText);
			// 拼装主键生成函数
			if (lineText.indexOf("(") > 0 && lineText.contains("values")) {
				lineText = lineText.substring(0, lineText.indexOf("(") + 1)
						+ "ep_emis_basic.uf_get_sequence('" + tableName
						+ "_id')" + lineText.substring(lineText.indexOf(","));
			}
			wr.write(lineText + "\r\n");
		}
		read.close();
		wr.close();
	}
}
