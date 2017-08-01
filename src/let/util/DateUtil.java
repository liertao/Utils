package let.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 所有日期时间相关的处理工具汇总
 * @author ertaoL
 */
public class DateUtil {
	
	/**
	 * @des：将'yyyyMMdd'的字符串格式为指定的格式，如"yyyy-MM-dd"\"yyyy/MM/dd"
	 * @param str :目标字符串
	 * @param reg :要转换的格式
	 * @return :返回值String类型
	 * @throws Exception
	 */
	public static String formatStr(String str,String reg)throws Exception{
		return new SimpleDateFormat(reg).format(new SimpleDateFormat("yyyyMMdd").parse(str));
	}
	
	/**
	 * @des: 获取给定时间偏移n天的时间点（String类型）
	 * @param str ：给定的时间点
	 * @param differ ：偏移量（正直表示 向后偏移；负值表示 向前偏移）
	 * @return ：返回值String类型
	 * @throws Exception
	 */
	public static String getDesignDateStr(String str, Integer differ)throws Exception{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new SimpleDateFormat("yyyyMMdd").parse(str));
		cal.set(Calendar.DATE, cal.get(Calendar.DATE)+differ);
		return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
	}
	
	/**
	 * @des:获取给定的时间之间的年份/年月/年月日,放入集合中返回
	 * @param begDate ：开始时间
	 * @param endDate ：结束时间
	 * @param reg ：获取集合列表的标志： "YMD" 》》年月日
	 * 								   "YM"  》》年月
	 * 								   "Y"   》》年
	 * @return list
	 * @throws Exception
	 */
	public static List<String> getDateList(String begDate, String endDate, String reg)throws Exception{
		List<String> list = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new SimpleDateFormat("yyyyMMdd").parse(begDate));
		if("YMD".equals(reg)||reg==null){
			list.add(begDate);
			while(!endDate.equals(new SimpleDateFormat("yyyyMMdd").format(cal.getTime()))){
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
				String dat = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
				list.add(dat);
			}
			return list;
		}
		if("YM".equals(reg)){
			while(!(String.valueOf(cal.get(Calendar.YEAR))+(cal.get(Calendar.MONTH)+1)).equals(endDate.substring(0,6))){
				list.add(String.valueOf(cal.get(Calendar.YEAR))+(String.valueOf(cal.get(Calendar.MONTH)+1).length()==1?("0"+String.valueOf(cal.get(Calendar.MONTH)+1)):String.valueOf(cal.get(Calendar.MONTH)+1)));
				cal.add(Calendar.MONTH, 1);
			}
			list.add(endDate.substring(0,6));
			return list;
		}
		if("Y".equals(reg)){
			String begYear = begDate.substring(0,4);
			String endYear = endDate.substring(0,4);
			if(begYear.equals(endYear)){
				list.add(begYear);
				return list;
			}
			while(!begYear.equals(endYear)){
				list.add(begYear);
				begYear = String.valueOf((Integer.parseInt(begYear)+1));
			}
			list.add(endYear);
		}
		return list;
	}
	
	/**
	 * 主函数：测试使用
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args)throws Exception {
		List<String> list = getDateList("20120429", "20161203", "Y");
		for(String str: list){
			System.out.println(str);
		}
		
	}
	
	
}
