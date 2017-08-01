package let.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * 工具类：日常开发集中式解决方案方法汇总
 * @author ertaoL
 * @time 2017-07-27
 */
public class MessageUtil {
	
	/**
	 * @des方法描述：给指定的手机号发送短信
	 * @param desPhone ：目标手机号码，可以是单个号码也可以是多个号码（类型是String），
	 * 						多号码的手机号用"，"隔开，格式如： 1863487653，18787639827,
	 * 						方法会自动分割处理进行逐个发送信息
	 * 
	 * 		需要注册中国网建账号，
	 * 			获取utf/gbk的短信路径、用户名、
	 * 			密钥、还要设置短信开头机构名称。
	 * 			△：还需要引入官方的三个jar包
	 * 			至于返回的状态码 请参见中国网建api接口说明	
	 * @param text ：短信内容
	 * @throws Exception
	 */
	public void sendMessage(String desPhone, String text)throws Exception{
		HttpClient client = null;//打开IE浏览器
		PostMethod post = null;//创建请求
		if(desPhone!=null && !"".equals(desPhone)){
			for(int i=0;i<desPhone.split(",").length;i++){
				client = new HttpClient();
				post = new PostMethod("http://utf8.api.smschinese.cn/");
				//设置消息头
				post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
				//网建账号
				post.setParameter("Uid", "litiepeng");
				//密钥
				post.setParameter("Key", "bc7db90a409d2d39c6ff");
				//目标手机号
				post.setParameter("smsMob", desPhone.split(",")[i]);
				//短信内容
				post.setParameter("smsText", text);
				//提交请求
				int code = client.executeMethod(post);
				System.out.println("http返回的状态码："+code);
				String result = post.getResponseBodyAsString();
				System.out.println("短信发送结果为："+result);
			}
		}
	}
	
	/**
	 * #@des:向指定的邮箱发送邮件
	 * @param email ： 一个标准的邮件地址例如： 2376333323@qq.com
	 * @return void
	 */
	public void sendEmail(String email){
		Properties prop = new Properties();//登录邮件客户端
		Session session = null;
		Message message = null;
		Transport transport = null;
		try{
			prop.setProperty("mail.transport.protocal", "smtp");//设置协议
			session = Session.getDefaultInstance(prop);//开启session
			message = new MimeMessage(session);//写信
			message.setSubject("订单支付成功邮件");//标题
			message.setContent("订单已经支付成功,请您进行确认信息。","text/html;charset=utf-8");//内容
			message.setFrom(new InternetAddress("liertao01@163.com"));//发件人
			transport = session.getTransport();
			transport.connect("smtp.163.com","liertao01@163.com","100101");//和邮件认证服务器链接认证信息
			transport.sendMessage(message, new InternetAddress[]{new InternetAddress(email)});
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			try {
				transport.close();
			} catch(MessagingException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * 主函数：测试函数
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args)throws Exception {
		MessageUtil util = new MessageUtil();
		String address = "237632268@qq.com";
		util.sendEmail(address);
	}
	
}
