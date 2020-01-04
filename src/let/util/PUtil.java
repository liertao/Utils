package com.let.ProUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import oracle.jdbc.OracleConnection;

/**
 * 项目开发常用工具类
 * 
 * @author liertao
 * 
 */
public class PUtil {

	// 数据库基础配置
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	private static final String USERNAME = "mes_23";
	private static final String PASSWORD = "mes_23";

	// 文件输出路径
	private static final String dp1 = "D:\\DeskTop\\comm\\find.sql";
	private static final String dp2 = "D:\\DeskTop\\comm\\model.sql";
	private static final String dp3 = "D:\\DeskTop\\comm\\grid.sql";

	private static final String SQL = "SELECT * FROM ";// 数据库操作

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取连接
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			// 设置可以读取REMARKS
			if (conn instanceof OracleConnection) {// 设置Oracle数据库的表注释可读
				((OracleConnection) conn).setRemarksReporting(true);// 设置连接属性,使得可获取到表的REMARK(备注)
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭连接
	 * 
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取所有表
	 * 
	 * @return
	 */
	public static List<String> getTableNames() {
		List<String> tableNames = new ArrayList<String>();
		Connection conn = getConnection();
		ResultSet rs = null;
		try {
			// 获取数据库的元数据
			DatabaseMetaData db = conn.getMetaData();
			// 从元数据中获取到所有的表名
			rs = db.getTables(null, null, null, new String[] { "TABLE" });
			while (rs.next()) {
				tableNames.add(rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				closeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tableNames;
	}

	/**
	 * 根据表明获取所有的列注释
	 * 
	 * @param conn
	 * @param tableName
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public static LinkedHashMap<String, String> getColumnsCommentsByTable(
			Connection conn, String tableName) throws SQLException, Exception {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		ResultSet rs = conn.getMetaData().getColumns(null, getSchema(conn),
				tableName.toUpperCase(), "%");
		while (rs.next()) {
			map.put(rs.getString("COLUMN_NAME"), rs.getString("REMARKS"));
		}
		return map;
	}

	/**
	 * 获取数表的 所有字段信息 存入MAP中，key是列名，value是“类型，长度，注释”；
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static LinkedHashMap<String, String> getColumnsInfo(String tableName)
			throws Exception {
		List<Map<String, String>> columnNames = new ArrayList<Map<String, String>>();
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		// 与数据库的连接
		Connection conn = getConnection();
		PreparedStatement pStemt = null;
		String tableSql = SQL + tableName;
		try {
			pStemt = conn.prepareStatement(tableSql);
			// 结果集元数据
			ResultSetMetaData rsmd = pStemt.getMetaData();
			// 表列数
			int size = rsmd.getColumnCount();
			LinkedHashMap<String, String> commMap = getColumnsCommentsByTable(
					conn, tableName);
			for (int i = 0; i < size; i++) {
				map
						.put(
								rsmd.getColumnName(i + 1),
								rsmd.getColumnTypeName(i + 1)
										+ ","
										+ rsmd.getPrecision(i + 1)
										+ ","
										+ (commMap.get(rsmd
												.getColumnName(i + 1)) == null ? "占位"
												: commMap.get(rsmd
														.getColumnName(i + 1))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pStemt != null) {
				try {
					pStemt.close();
					closeConnection(conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}

	/**
	 * 其他数据库不需要这个方法 oracle和db2需要
	 * 
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	private static String getSchema(Connection conn) throws Exception {
		String schema;
		schema = conn.getMetaData().getUserName();
		if ((schema == null) || (schema.length() == 0)) {
			throw new Exception("ORACLE数据库模式不允许为空");
		}
		return schema.toUpperCase().toString();

	}

	/**
	 * 生成findSql, 生成model.json,生成 grid.json
	 * 
	 * @param tableName:表明
	 * @param alias：别名
	 * @param tarPath：目标路径
	 * @param type：生成类型1-findSql，2-model.json,3-grid.json
	 * @return
	 * @throws Exception
	 */
	public static String createSql(String tableName, String alias,
			String tarPath, Integer type) throws Exception {
		LinkedHashMap<String, String> map = getColumnsInfo(tableName);
		if (type == 1) {// 生成 find.sql文件
			String findSql = "select ";
			Set s = map.keySet();
			Iterator<String> it = s.iterator();
			while (it.hasNext()) {
				String cell = it.next();
				findSql += alias + "." + cell + " as \"" + hump(cell) + "\""
						+ ",";
			}
			findSql = findSql.substring(0, findSql.length() - 1) + " from "
					+ tableName + " " + alias;
			OutputStreamWriter out = new OutputStreamWriter(
					new FileOutputStream(new File(tarPath)));
			BufferedWriter wr = new BufferedWriter(out);
			wr.write(findSql);
			wr.close();
		}
		if (type == 2) {// 生成model文件
			String temp = "{views:{fields:[";
			Set s = map.keySet();
			Iterator<String> it = s.iterator();
			while (it.hasNext()) {
				String cell = it.next();
				String info = map.get(cell);
				String[] arr = info.split(",");
				if (arr[0].equals("NUMBER"))
					temp += "{name:\"" + hump(cell) + "\",dbname:\"" + alias
							+ "." + cell + "\",type:\"number\"},";
				else if (arr[0].equals("DATE"))
					temp += "{name:\"" + hump(cell) + "\",dbname:\"" + alias
							+ "." + cell + "\",type:\"date\"},";
				else
					temp += "{name:\"" + hump(cell) + "\",dbname:\"" + alias
							+ "." + cell + "\"},";
			}
			temp = temp.substring(0, temp.length() - 1) + "]}}";
			OutputStreamWriter out = new OutputStreamWriter(
					new FileOutputStream(new File(tarPath)));
			BufferedWriter wr = new BufferedWriter(out);
			wr.write(temp);
			wr.close();
		}
		if (type == 3) {// 生成grid文件
			String temp = "";
			Set s = map.keySet();
			Iterator<String> it = s.iterator();
			while (it.hasNext()) {
				String cell = it.next();
				String info = map.get(cell);
				String[] arr = info.split(",");
				if (cell.equals("ID"))
					temp += "{header:\"" + arr[2] + "\",dataIndex:\""
							+ hump(cell) + "\",hidden:true},";
				else if (cell.equals("CREATE_USER"))
					temp += "{header:\""
							+ arr[2]
							+ "\",dataIndex:\""
							+ hump(cell)
							+ "\",xtype:\"combobox\",store:\"logidhelpinfo.store.All\",displayField:\"logName\",valueField:\"logId\",readOnly : true,align:\"center\",width : 60},";
				else if (cell.equals("CREATE_DATE"))
					temp += "{header:\""
							+ arr[2]
							+ "\",dataIndex:\""
							+ hump(cell)
							+ "\",xtype:\"datefield\",format:\"Y-m-d\",defaultValue:\"{today}\",readOnly : true,align:\"center\",width : 80},";
				else if (cell.equals("COMMENTS"))
					temp += "{header:\"" + arr[2] + "\",dataIndex:\""
							+ hump(cell) + "\",xtype:\"textfield\",width:150},";
				else if (cell.indexOf("_ID") > 0)
					temp += "{header:\"" + arr[2] + "\",dataIndex:\""
							+ hump(cell) + "\",hidden:true},";
				else {
					if (arr[0].equals("NUMBER") && arr[1].equals("1"))
						temp += "{header:\""
								+ arr[2]
								+ "\",dataIndex:\""
								+ hump(cell)
								+ "\",xtype:\"combobox\",storeConfig:{fields:[\"code\",\"name\"], data:[{\"code\":\"0\",\"name\":\"正常\"}]},displayField:\"name\",valueField:\"code\",readOnly	: true,width 		: 80},";
					else if (arr[0].equals("NUMBER") && !arr[1].equals("1"))
						temp += "{header:\""
								+ arr[2]
								+ "\",dataIndex:\""
								+ hump(cell)
								+ "\",xtype:\"numberfield\",format:\"##,###,###,#00.00####\",align:\"right\",width:60},";
					else if (arr[0].equals("DATE"))
						temp += "{header:\"" + arr[2] + "\",dataIndex:\""
								+ hump(cell)
								+ "\",xtype:\"datefield\",width:90},";
					else
						temp += "{header:\"" + arr[2] + "\",dataIndex:\""
								+ hump(cell)
								+ "\",xtype:\"textfield\",width:120},";
				}
			}
			temp = temp.substring(0, temp.length() - 1);
			OutputStreamWriter out = new OutputStreamWriter(
					new FileOutputStream(new File(tarPath)));
			BufferedWriter wr = new BufferedWriter(out);
			wr.write(temp);
			wr.close();
		}
		return null;
	}

	/**
	 * 将字符串转换成驼峰，或者编小写
	 * 
	 * @param str
	 * @return
	 */
	public static String hump(String str) {
		String prefix = "", sufix = "";
		int i = 0;
		if (str.indexOf("_") < 0)
			str = str.toLowerCase();
		while (str.indexOf("_") > 0) {
			if (i == 0)
				prefix = str.substring(0, str.indexOf("_")).toLowerCase();
			else
				prefix = str.substring(0, str.indexOf("_"));
			if (i == 0)
				sufix = str.substring(str.indexOf("_") + 1, str.length())
						.toLowerCase();
			else
				sufix = str.substring(str.indexOf("_") + 1, str.length());
			sufix = sufix.substring(0, 1).toUpperCase() + sufix.substring(1);
			str = prefix + sufix;
			i++;
		}
		return str;
	}

	public static void main(String[] args) throws Exception {
		createSql("E_QIS_INFO_HEADER", "t", dp1, 1);
		createSql("E_QIS_INFO_HEADER", "t", dp2, 2);
		createSql("E_QIS_INFO_HEADER", "t", dp3, 3);
		System.out.println(getColumnsInfo("E_QIS_INFO_HEADER"));
	}
}
