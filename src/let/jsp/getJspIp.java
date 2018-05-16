package let.jsp;

/**
 * @Description:
 * 	此代码用于jsp中作为jsp中的java代码片段，
 * 	可在jsp中直接获取当前客户机的真实ip地址,
 * 
 * 	注意：在开发模式下（服务端和客户机在同一机器上，获取的
 * 	ip始终是127.0.0.1）
 * 
 * @author 李二涛
 * @time 2018-05-16
 */
public class getJspIp {
	
	public static void main(String[] args) {
		//使用时，直接将下面的代码拷贝到jsp中即可得到ip的值
		//拷贝开始
		String ip;
		ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		//拷贝结束
	}
}
