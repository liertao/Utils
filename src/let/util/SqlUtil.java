package com.let.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * sql�ļ�������
 * 
 * @author liertao
 * 
 */
public class SqlUtil {

	public static void main(String[] args) throws Exception {
		// Դtxt·��
		String srcPath = "D:\\DeskTop\\33\\sql\\e_log_user.sql";
		// �����ʽ
		String code = "gbk";
		// ���txt·��
		String desPath = "D:\\DeskTop\\33\\sql1\\e_log_user.sql";
		// Ҫ����ı���
		String tableName = "e_log_user";
		createSql(srcPath, desPath, code, tableName);
	}

	/**
	 * @Description:��ĳ�ű��sql�����д��� ��id��Ϊep_emis_basic.uf_get_squence
	 * 
	 * @param srcPath:Դ�ļ�·��
	 * @param desPath:Ŀ���ļ�·��
	 * @param code:����
	 * @param tableName:����
	 * @throws Exception
	 */
	public static void createSql(String srcPath, String desPath, String code,
			String tableName) throws Exception {
		// 1.��ȡ�ļ�
		File file = new File(srcPath);
		InputStreamReader read = new InputStreamReader(
				new FileInputStream(file), code);// ���ǵ������ʽ
		BufferedReader bu = new BufferedReader(read);
		// 2.ƴ���ַ���
		String lineText = null;
		// 3.�߶���д
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(
				new File(desPath)));
		BufferedWriter wr = new BufferedWriter(out);

		while ((lineText = bu.readLine()) != null) {
			System.out.println(lineText);
			// ƴװ�������ɺ���
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
