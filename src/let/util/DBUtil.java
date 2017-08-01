package let.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
/**
 * @des:数据库工具类，获取链接、关闭链接、获取指定位数的随机字母
 * @author ertaoL
 */
public class DBUtil {
	
	private static String url = "jdbc:mysql://localhost:3306/test";
	private static String user = "root";
	private static String pwd = "1234";
	
	private static Connection conn;
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @des:获取数据库连接
	 * @return connn
	 */
	public static Connection getConnection(){
		if(null==conn){
			try{
				conn = DriverManager.getConnection(url,user,pwd);
			}catch(Exception e){
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return conn;
	}
	
	/**
	 * @des:关闭数据库连接
	 * @param conn ：数据库连接
	 * @param pstmt ： 执行器
	 * @param rs ：结果集
	 */
	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs){
		
		if(null!=rs){
			try {
				rs.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		if(null!=pstmt){
			try {
				pstmt.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		if(null!=null){
			try {
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * @des:随机产生固定位数的大写字母
	 * @param a ：指定的数长
	 * @return String
	 */
	public static String getString(int a){
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random ran = new Random();
		String code = "";
		for(int i=0;i<a;i++){
			code += str.charAt(ran.nextInt(str.length()));
		}
		return code;
	}
	
	public static void main(String[] args) {
		DBUtil util = new DBUtil();
		System.out.println(util.getString(4));
	}
	
}
