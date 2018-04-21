package com.hotent.makshi.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 日期工具类 默认使用 "yyyy-MM-dd HH:mm:ss" 格式化日期
 * 
 */
public final class DateUtils {
	
	public static long MILLIS_ONE_DAY = 86400000L;
    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";
    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    /**
     * 中文简写 如：2010年12月01日
     */
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd";
    /**
     * 中文全称 如：2010年12月01日 23时15分06秒
     */
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";
    
	public final static String D_FORMAT = "yyyyMMdd";
	
	private final static String S_TIME_FORMAT = "HH:mm";

	private static Logger logger=Logger.getLogger(DateUtils.class);
    
    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return FORMAT_LONG;
    }

    /**
     * 根据预设格式返回当前日期
     * 
     * @return
     */
    public static String getNow() {
        return format(new Date());
    }

    /**
     * 根据用户格式返回当前日期
     * 
     * @param format
     * @return
     */
    public static String getNow(String format) {
        return format(new Date(), format);
    }

    /**
     * 使用预设格式格式化日期
     * 
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, getDatePattern());
    }

    /**
     * 使用用户格式格式化日期
     * 
     * @param date
     *            日期
     * @param pattern
     *            日期格式
     * @return
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 使用预设格式提取字符串日期
     * 
     * @param strDate
     *            日期字符串
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }

    /**
     * 使用用户格式提取字符串日期
     * 
     * @param strDate
     *            日期字符串
     * @param pattern
     *            日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 在日期上增加数个整月
     * 
     * @param date
     *            日期
     * @param n
     *            要增加的月数
     * @return
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加天数
     * 
     * @param date
     *            日期
     * @param n
     *            要增加的天数
     * @return
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    /**
     * 获取时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取日期年份
     * 
     * @param date
     *            日期
     * @return
     */
    public static String getYear(Date date) {
        return format(date).substring(0, 4);
    }

    /**
     * 按默认格式的字符串距离今天的天数
     * 
     * @param date
     *            日期字符串
     * @return
     */
    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 按用户格式字符串距离今天的天数
     * 
     * @param date
     *            日期字符串
     * @param format
     *            日期格式
     * @return
     */
    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }
    
    
    public static long daysDiff(Date start,Date end){
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	try
    	{
//    	    Date d1 = df.parse(start);
//    	    Date d2 = df.parse(end);
    	    long diff = end.getTime() - start.getTime();
    	    long days = diff / (1000 * 60 * 60 * 24);
    	    return days;
    	}
    	catch (Exception e){
    		logger.error("计算时间差出错");
    	}
    	
    	return 0;
    }
    
    /**
	 * 获取 2 个日期间的天数。忽略 Calendar.HOUR_OF_DAY 以下的单位。
	 * 
	 * @param sDate
	 * @param eDate
	 * @return
	 */
	public static int getDiffDaysByDay(Date sDate, Date eDate) {

		Calendar sCalendar = Calendar.getInstance();
		sCalendar.setTime(sDate);
		sCalendar.set(Calendar.HOUR_OF_DAY, 0);
		sCalendar.set(Calendar.MINUTE, 0);
		sCalendar.set(Calendar.SECOND, 0);
		sCalendar.set(Calendar.MILLISECOND, 0);

		Calendar eCalendar = Calendar.getInstance();
		eCalendar.setTime(eDate);
		eCalendar.set(Calendar.HOUR_OF_DAY, 0);
		eCalendar.set(Calendar.MINUTE, 0);
		eCalendar.set(Calendar.SECOND, 0);
		eCalendar.set(Calendar.MILLISECOND, 0);

		return (int) ((eCalendar.getTimeInMillis() - sCalendar
				.getTimeInMillis()) / MILLIS_ONE_DAY);

	}
	
	/**
	 * 
	 * @Title: formatDate
	 * @Description: TODO
	 * @param date
	 * @return
	 */
	public static String formatDateL(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat(FORMAT_LONG);
		return sf.format(date);
	}

	/**
	 * 
	 * @Title: FormatDateS
	 * @Description: TODO
	 * @param date
	 * @return
	 */
	public static String formatDateS(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat(FORMAT_SHORT);
		return sf.format(date);
	}

	/**
	 * 
	 * @Title: FormatDateD
	 * @Description: TODO
	 * @param date
	 * @return
	 */
	public static String formatDateD(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat(D_FORMAT);
		return sf.format(date);
	}

	/**
	 * 
	 * @Title: FormatTimeS
	 * @Description: TODO
	 * @param date
	 * @return
	 */
	public static String FormatTimeS(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat(S_TIME_FORMAT);
		return sf.format(date);
	}

	/**
	 * 
	 * @Title: formatDate
	 * @Description: TODO
	 * @param date
	 * @return
	 */
	public static Date parseDateL(String dateL) {
		SimpleDateFormat sf = new SimpleDateFormat(FORMAT_LONG);
		Date date = new Date();
		try {
			date = sf.parse(dateL);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 
	 * @Title: FormatDateS
	 * @Description: TODO
	 * @param date
	 * @return
	 */
	public static Date parseDateS(String dateS) {
		SimpleDateFormat sf = new SimpleDateFormat(FORMAT_SHORT);
		Date date = new Date();
		try {
			date = sf.parse(dateS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 
	 * @Title: FormatDateS
	 * @Description: TODO
	 * @param date
	 * @return
	 */
	public static Date parseDate(String dateS, String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		Date date = new Date();
		try {
			date = sf.parse(dateS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 格式化日期显示格式yyyy-MM-dd
	 * 
	 * @param sdate
	 *            原始日期格式
	 * @return yyyy-MM-dd格式化后的日期显示
	 */
	public static String dateFormat(String sdate) {
		return dateFormat(sdate, "yyyy-MM-dd");
	}

	/**
	 * 格式化日期显示格式
	 * 
	 * @param sdate
	 *            原始日期格式
	 * @param format
	 *            格式化后日期格式
	 * @return 格式化后的日期显示
	 */
	public static String dateFormat(String sdate, String format) {
		if (null == sdate || "".equals(sdate))
			return "--";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		java.sql.Date date = java.sql.Date.valueOf(sdate);
		String dateString = formatter.format(date);

		return dateString;
	}
	
	public static String getDateAddDay(String sdate, int dayNum) {
		// Date date = getDateFromString(sdate,format);
		String resultStr = null;
		try {
			Date date = new SimpleDateFormat("yyyyMMdd").parse(sdate);
			resultStr = getRelativeDate(date, 0, 0, dayNum, "yyyyMMdd");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultStr;
	}
	
	public static Date getDateAddDay(Date date, int dayNum) {
		GregorianCalendar gc = new GregorianCalendar();

		gc.setTime(date);
		gc.add(Calendar.DATE, dayNum);
		
		return gc.getTime();
	}
	
	public static String getRelativeDate(java.util.Date date, int iYear,
			int iMonth, int iDate, String sFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(sFormat); // 定义格式
		GregorianCalendar gc = new GregorianCalendar();

		gc.setTime(date);

		gc.add(Calendar.YEAR, iYear);
		gc.add(Calendar.MONTH, iMonth);
		gc.add(Calendar.DATE, iDate);

		return sdf.format(gc.getTime());
	}
	
	/**
	 * 获取当天的星期
	 * @param date
	 * @return
	 */
	public static String getWeekDay(Date date){
		String workArr[]={"日","一","二","三","四","五","六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0){w = 0;}
		return workArr[w];//设置星期
	}
	
	/**
	 * 根据给定的格式，返回时间字符串。 </p>
	 * <p>
	 * 格式参照类描绘中说明.
	 * 
	 * @param format
	 *            日期格式字符串
	 * @return String 指定格式的日期字符串.
	 */
	public static String getFormatCurrentTime(String format) {
		return getFormatDateTime(new Date(), format);
	}
	/**
	 * 根据给定的格式与时间(Date类型的)，返回时间字符串<br>
	 * 
	 * @param date
	 *            指定的日期
	 * @param format
	 *            日期格式字符串
	 * @return String 指定格式的日期字符串.
	 */
	public static String getFormatDateTime(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 获取指定日期期间，所有月份的最后一天
	 * 
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public static List<String> getMonthEndDateList(int startDay, int endDay) {
		List<String> dateList = new ArrayList<String>();
		if (startDay > endDay) {
			System.out.println("出错啦！！！起始日期大于结束日期！");
			return null;
		}

		int startYear = startDay / 10000; // 2009
		int endYear = endDay / 10000; // 2012
		int startMonth = (startDay % (startYear * 10000)) / 100;
		int endMonth = (endDay % (endYear * 10000)) / 100;

		if (startYear == endYear) {
			for (int m = startMonth; m <= endMonth; m++) {
				String monStr = m + "";
				if (m < 10)
					monStr = "0" + m;
				String date = startYear + "" + monStr + ""
						+ getDaysOfCurMonth(startYear + "-" + m);
				dateList.add(date);
			}
		} else {
			for (int y = startYear; y <= endYear; y++) {
				if (y == startYear) {
					for (int m = startMonth; m <= 12; m++) {
						String monStr = m + "";
						if (m < 10)
							monStr = "0" + m;
						String date = y + "" + monStr + ""
								+ getDaysOfCurMonth(y + "-" + m);
						dateList.add(date);
					}
				} else if (y == endYear) {
					for (int m = 1; m <= endMonth; m++) {
						String monStr = m + "";
						if (m < 10)
							monStr = "0" + m;
						String date = y + "" + monStr + ""
								+ getDaysOfCurMonth(y + "-" + m);
						dateList.add(date);
					}
				} else {
					for (int m = 1; m <= 12; m++) {
						String monStr = m + "";
						if (m < 10)
							monStr = "0" + m;
						String date = y + "" + monStr + ""
								+ getDaysOfCurMonth(y + "-" + m);
						dateList.add(date);
					}
				}
			}
		}
		return dateList;
	}
	
	/**
	 * 根据指定的年月 返回指定月份（yyyy-MM）有多少天。
	 * 
	 * @param time
	 *            yyyy-MM
	 * @return 天数，指定月份的天数。
	 */
	public static int getDaysOfCurMonth(final String time) {
		String[] timeArray = time.split("-");
		int curyear = new Integer(timeArray[0]).intValue(); // 当前年份
		int curMonth = new Integer(timeArray[1]).intValue();// 当前月份
		int mArray[] = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,
				31 };
		// 判断闰年的情况 ，2月份有29天；
		if ((curyear % 400 == 0)
				|| ((curyear % 100 != 0) && (curyear % 4 == 0))) {
			mArray[1] = 29;
		}
		if (curMonth == 12) {
			return mArray[0];
		}
		return mArray[curMonth - 1];
		// 如果要返回下个月的天数，注意处理月份12的情况，防止数组越界；
		// 如果要返回上个月的天数，注意处理月份1的情况，防止数组越界；
	}
	
	/**
	 * 给定时间，返回指定年份后的日期
	 * 
	 * @param sdate
	 * @param format
	 * @param monthNum
	 * @return
	 */
	public static String getDateAddYear2(String sdate, int year) {
		// Date date = getDateFromString(sdate,format);
		String resultStr = null;
		try {
			Date date = new SimpleDateFormat("yyyyMMdd").parse(sdate);
			resultStr = getRelativeDate(date, year, 0, 0, "yyyyMMdd");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultStr;
	}
	/**
	 * 
	 * @Title: transDate
	 * @Description: 将时间转换为yyyy-MM-dd形式
	 * @param date
	 * @return
	 */
	public static String transDate(String date) {
		if (date == null || date.trim().equals("null")
				|| date.trim().equals("") || date.trim().equals("--")) {
			return null;
		}
		date = date.trim().replace("年度", "");
		date = date.replace("//", "/");
		String temp[] = date.split("/");

		String result = date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
				+ date.substring(6, 8);

		if (temp.length == 2) {
			date = date + "/01";
		} else if (temp.length == 1) {
			if (date.length() == 8) {
				return date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
						+ date.substring(6, 8);
			} else {
				date = date + "/01/01";
			}
		}
		if (temp.length > 4)
			return null;

		return date.replace("/", "-");
	}
	
	
	
	/** 
	    * 获取过去第几天的日期 
	    * 
	    * @param past 
	    * @return 
	    */  
	   public static String getPastDate(int past) {  
	       Calendar calendar = Calendar.getInstance();  
	       calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);  
	       Date today = calendar.getTime();  
	       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	       String result = format.format(today);  
	       return result;  
	   } 
	   
	   /**
	    * 获取工作日
	 * @throws ParseException 
	    */
	   public static int getWorkDay(Date sDate, Date eDate){
		   Long realTime = getWorkdayTimeInMillisExcWeekendHolidays(sDate.getTime(),eDate.getTime(),null);
		  return (int) (realTime / (1000 * 60 * 60 * 24));

	   }
	   
	   public static Long getWorkdayTimeInMillisExcWeekendHolidays(Long start, Long end,List<Date> listHolidays){
		// 如果起始时间大于结束时间，将二者交换
		if (start > end) {
		long temp = start;
		start = end;
		end = temp;
		}
		// 根据参数获取起始时间与结束时间的日历类型对象
		Calendar sdate = Calendar.getInstance();
		Calendar edate = Calendar.getInstance();

		sdate.setTimeInMillis(start);
		edate.setTimeInMillis(end);

		// 计算指定时间段内法定节假日天数的毫秒数
		long holidays = 0;
		if (listHolidays != null) {
		holidays = getHolidaysInMillis(start, end, listHolidays);
		listHolidays.clear();
		}

		// 如果两个时间在同一周并且都不是周末日期，则直接返回时间差，增加执行效率
		if ((sdate.get(Calendar.YEAR) == edate.get(Calendar.YEAR))
		&& (sdate.get(Calendar.WEEK_OF_YEAR) == edate
		.get(Calendar.WEEK_OF_YEAR))
		&& (sdate.get(Calendar.DAY_OF_WEEK) != 1 && sdate
		.get(Calendar.DAY_OF_WEEK) != 7)
		&& (edate.get(Calendar.DAY_OF_WEEK) != 1 && edate
		.get(Calendar.DAY_OF_WEEK) != 7)) {
		return new Long(end - start- holidays);
		}
		// 如果两个时间在同一周并且都是周末日期，则直接返回0
		if ((sdate.get(Calendar.YEAR) == edate.get(Calendar.YEAR))
		&& (sdate.get(Calendar.WEEK_OF_YEAR) == edate
		.get(Calendar.WEEK_OF_YEAR)-1)
		&& (sdate.get(Calendar.DAY_OF_WEEK) == 1
		|| sdate.get(Calendar.DAY_OF_WEEK) == 7)
		&&
		(edate.get(Calendar.DAY_OF_WEEK) == 1
		|| edate.get(Calendar.DAY_OF_WEEK) == 7)) {
		start=validateStartTime(sdate);
		end=validateEndTime(edate);
		long result=end - start - holidays;
		return new Long(result>0?result:0);
	   }
		start=validateStartTime(sdate);
		end=validateEndTime(edate);

		// 首先取得起始日期与结束日期的下个周一的日期
		Calendar snextM = getNextMonday(sdate);
		Calendar enextM = getNextMonday(edate);

		// 获取这两个周一之间的实际天数
		int days = getDaysBetween(snextM, enextM);

		// 获取这两个周一之间的工作日数(两个周一之间的天数肯定能被7整除，并且工作日数量占其中的5/7)
		int workdays = days / 7 * 5;

		// 计算最终结果，具体为：workdays加上开始时间的时间偏移量，减去结束时间的时间偏移量
		long result = (workdays * 24 * 3600000
		+ calcWorkdayTimeInMillis(sdate, edate, start, end) - holidays);
		return result > 0 ? result : 0;
		}
		/***
		* 验证开始日期是否合法,如果不合法,并返回修复后的正确日期毫秒数
		* @param sdate
		* @return
		*/
		public static long validateStartTime(Calendar sdate)
		{
		if(sdate.get(Calendar.DAY_OF_WEEK) == 1)//开始日期从周日开始,如果开始时间为周末，自动修复为下周的9：00开始
		{
		sdate.add(Calendar.DATE,1);
		sdate.setTimeInMillis(
		sdate.getTime().getTime()- //从9点开始
		(
		((sdate.get(Calendar.HOUR_OF_DAY)-9) * 3600000)
		+ (sdate.get(Calendar.MINUTE) * 60000)
		+ (sdate.get(Calendar.SECOND) * 1000)
		)
		);
		}else if(sdate.get(Calendar.DAY_OF_WEEK) == 7)//开始日期从周六开始
		{
		sdate.add(Calendar.DATE,2);
		sdate.setTimeInMillis(
		sdate.getTime().getTime()- //从9点开始,如果开始时间为周末，自动修复为下周的9：00开始
		(
		((sdate.get(Calendar.HOUR_OF_DAY)-9) * 3600000)
		+ (sdate.get(Calendar.MINUTE) * 60000)
		+ (sdate.get(Calendar.SECOND) * 1000)
		)
		);
		}
		return sdate.getTimeInMillis();
		}

		/***
		* 验证结束日期是否合法,如果不合法,并返回修复后的正确日期毫秒数
		* @param sdate
		* @return
		*/
		public static long validateEndTime(Calendar edate)
		{
		if(edate.get(Calendar.DAY_OF_WEEK) == 1)//结束日期是周日,如果结束日期是周六、周末自动修复为这周五18:00
		{
		edate.add(Calendar.DATE,-2);
		edate.setTimeInMillis(
		edate.getTime().getTime()+
		(
		18*3600000-(
		(edate.get(Calendar.HOUR_OF_DAY) * 3600000)
		+ (edate.get(Calendar.MINUTE) * 60000)
		+ (edate.get(Calendar.SECOND) * 1000)
		)
		)
		);
		}else if(edate.get(Calendar.DAY_OF_WEEK) == 7)//结束日期是周六,如果结束日期是周六、周末自动修复为这周五18:00
		{
		edate.add(Calendar.DATE,-1);
		edate.setTimeInMillis(
		edate.getTime().getTime()+
		(
		18*3600000-(
		(edate.get(Calendar.HOUR_OF_DAY) * 3600000)
		+ (edate.get(Calendar.MINUTE) * 60000)
		+ (edate.get(Calendar.SECOND) * 1000)
		)
		)
		);
		}
		return edate.getTimeInMillis();
		}
		
		/***
		* 计算两个日期间的工作日天数，除周六日
		* 
		* @param sdate
		* @param edate
		* @return
		*/
		public static long calcWorkdayTimeInMillis(Calendar sdate, Calendar edate,long start, long end) {
				// 获取开始时间的偏移量
				long scharge = 0;
				if (sdate.get(Calendar.DAY_OF_WEEK) != 1
				&& sdate.get(Calendar.DAY_OF_WEEK) != 7) {
				// 只有在开始时间为非周末的时候才计算偏移量
				scharge += (sdate.get(Calendar.HOUR_OF_DAY) * 3600000);
				scharge += (sdate.get(Calendar.MINUTE) * 60000);
				scharge += (sdate.get(Calendar.SECOND) * 1000);
				scharge = ((24 * 3600000) - scharge);
				scharge += ((sdate.getTime().getTime() - start) - (3 * 24 * 3600000));
				}
				// 获取结束时间的偏移量
				long echarge = 0;
				if (edate.get(Calendar.DAY_OF_WEEK) != 1
				&& edate.get(Calendar.DAY_OF_WEEK) != (7)) {
				// 只有在结束时间为非周末的时候才计算偏移量
				echarge += (edate.get(Calendar.HOUR_OF_DAY) * 3600000);
				echarge += (edate.get(Calendar.MINUTE) * 60000);
				echarge += (edate.get(Calendar.SECOND) * 1000);
				echarge = ((24 * 3600000) - echarge);
				echarge += (edate.getTime().getTime() - end) - (24 * 3600000);
				echarge -= (2 * 24 * 3600000);
				}
				if (scharge < 0 || echarge < 0)
				scharge = echarge = 0;
				return scharge - echarge;
				}
		
		public static int getDaysBetween(Calendar start, Calendar end) {
			if (start.after(end)) {
			Calendar swap = start;
			start = end;
			end = swap;
			}
			int days = end.get(Calendar.DAY_OF_YEAR)
					- start.get(Calendar.DAY_OF_YEAR);

					int y2 = end.get(Calendar.YEAR);
					if (start.get(Calendar.YEAR) != y2) {
					start = (Calendar) start.clone();
					do {
					days += start.getActualMaximum(Calendar.DAY_OF_YEAR);
					start.add(Calendar.YEAR, 1);
					} while (start.get(Calendar.YEAR) != y2);
					}
					return days;
					}
		
		public static long getHolidaysInMillis(long start, long end,
				List<Date> listHolidays) {
				Calendar scalendar = Calendar.getInstance();
				Calendar ecalendar = Calendar.getInstance();
				int daysofH = 0;
				try {
				scalendar.setTimeInMillis(start);
				ecalendar.setTimeInMillis(end);

				if (listHolidays == null)
				return new Long(0);
				Iterator<Date> iterator = listHolidays.iterator();
				while (iterator.hasNext()) {
				Calendar ca = Calendar.getInstance();
				Date hdate = iterator.next();
				ca.setTime(hdate);
				if (ca.after(scalendar) && ca.before(ecalendar)) {
				daysofH = daysofH + 1;
				} else if (ca.getTimeInMillis() == scalendar.getTimeInMillis()) {
				daysofH = daysofH + 1;
				} else if (ca.getTimeInMillis() == ecalendar.getTimeInMillis()) {
				daysofH = daysofH + 1;
				}
				}

				} catch (Exception e) {
				e.printStackTrace();
				return new Long(0);
				}
				return daysofH * 24 * 3600000L;
				}
		
		public static Calendar getNextMonday(Calendar cal) {
			int addnum = 9 - cal.get(Calendar.DAY_OF_WEEK);
			if (addnum == 8)
			addnum = 1;// 周日的情况
			cal.add(Calendar.DATE, addnum);
			return cal;
			}


}
