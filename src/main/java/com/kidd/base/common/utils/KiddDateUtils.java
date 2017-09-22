package com.kidd.base.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.kidd.base.common.enums.KiddSymbolEnum;

public class KiddDateUtils {
	/** 日期格式yyyyMMddHHmmss **/
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	/** 日期格式yyyyMMddHHmmssSSS */
	public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";

	/** 日期格式yyyyMMdd **/
	public static final String yyyyMMdd = "yyyyMMdd";
	/** 日期格式yyyyMMddHH **/
	public static final String yyyyMMddHH = "yyyyMMddHH";
	/** 时间格式HHmmss **/
	public static final String HHmmss = "HHmmss";
	/** 日期格式yyyy/MM/dd **/
	public static final String FORMAT_DATE_SHOW1 = "yyyy/MM/dd";
	/** 日期格式yyyy年MM月dd日 **/
	public static final String FORMAT_DATE_SHOW2 = "yyyy年MM月dd日";

	/** 日期格式 YYYY年MM月DD日HH时MM分SS秒 */
	public static final String FORMAT_DATE_TIME_SHOW = "yyyy年MM月dd日 HH时mm分ss秒";

	/** 日期格式 yyyy-MM-dd HH:mm:ss **/
	public static final String FORMAT_TIME_SHOW = "yyyy-MM-dd HH:mm:ss";
	/** 日期格式 yyyy-MM-dd **/
	public static final String FORMAT_DATE_SHOW3 = "yyyy-MM-dd";

	/** 日期格式 HH:mm **/
	public static final String FORMAT_HOUR_SHOW = "HH:mm";

	/** 日期格式 HH:mm:ss **/
	public static final String FORMAT_ONLY_TIME_SHOW = "HH:mm:ss";

	/** 日期格式 HHmm **/
	public static final String HHmm = "HHmm";

	/** 日期格式 YYMM **/
	public static final String yyMM = "yyMM";

	/** 日期格式 YYYY **/
	public static final String yyyy = "yyyy";

	/**
	 * 以毫秒为单位
	 */
	public static final long DATE_TIME_BASE = 3600 * 24 * 1000;

	/**
	 * 一分为单位
	 */
	public static final long DATE_MINU_BASE = 24 * 60;

	/**
	 * 将yyyyMMddHHmmss格式 格式化为yyyy-MM-dd
	 * 
	 * @param date
	 *            yyyyMMddHHmmss格式
	 * @return yyyy-MM-dd
	 */
	public static String parseToDate(String date) {
		if (KiddStringUtils.isBlank(date)) {
			return KiddSymbolEnum.Blank.symbol();
		}
		SimpleDateFormat format = new SimpleDateFormat(yyyyMMddHHmmss);
		SimpleDateFormat fo = new SimpleDateFormat(FORMAT_DATE_SHOW3);
		try {
			Date date1 = format.parse(date);
			return fo.format(date1);
		} catch (ParseException e) {
			return KiddSymbolEnum.Blank.symbol();
		}
	}

	/**
	 * 将yyyyMMddHHmmss格式 格式化为yyyy-MM-dd
	 * 
	 * @param date
	 *            yyyyMMddHHmmss格式
	 * @return yyyyMMdd
	 */
	public static String parseToDate3(String date) {
		if (KiddStringUtils.isBlank(date)) {
			return KiddSymbolEnum.Blank.symbol();
		}
		SimpleDateFormat format = new SimpleDateFormat(yyyyMMddHHmmss);
		SimpleDateFormat fo = new SimpleDateFormat(yyyyMMdd);
		try {
			Date date1 = format.parse(date);
			return fo.format(date1);
		} catch (ParseException e) {
			return KiddSymbolEnum.Blank.symbol();
		}
	}

	/**
	 * 给定指定模式，指定日期字符串
	 * 
	 * @param pattern
	 *            模式如(yyyyMMddHHmmss...)
	 * @param date
	 *            日期
	 * @return 解释后返回的日期对象
	 */
	public static Date parseDate(String pattern, String date) {
		if (KiddStringUtils.isBlank(pattern) || KiddStringUtils.isBlank(date)) {
			return null;
		}

		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			Logger.getLogger("DateUtil").warn(e.getMessage(), e);
			return new Date();
		}
	}

	/**
	 * 给定指定模式，指定日期字符串
	 * 
	 * @param pattern
	 *            模式如(yyyyMMddHHmmss...)
	 * @param date
	 *            日期
	 * @return 解释后返回的日期对象
	 */
	public static String parseDate(String pattern, Date date) {
		if (null == date || KiddStringUtils.isBlank(pattern)) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 将yyyyMMddHHmmss格式 格式化为HH:mm:ss
	 * 
	 * @param date
	 *            yyyyMMddHHmmss格式的日期
	 * @return HH:mm:ss
	 */
	public static String parseToTimeHHmmss(String date) {
		if (KiddStringUtils.isBlank(date)) {
			return KiddSymbolEnum.Blank.symbol();
		}
		SimpleDateFormat format = new SimpleDateFormat(yyyyMMddHHmmss);
		SimpleDateFormat fo = new SimpleDateFormat("HH:mm:ss");
		try {
			Date date1 = format.parse(date);
			return fo.format(date1);
		} catch (ParseException e) {
			return KiddSymbolEnum.Blank.symbol();
		}
	}

	/**
	 * 将yyyyMMddHHmm格式 格式化为HH:mm
	 * 
	 * @param date
	 *            yyyyMMddHHmm格式
	 * @return HH:mm
	 */
	public static String parseToTimeHHMM(String date) {
		if (KiddStringUtils.isBlank(date)) {
			return KiddSymbolEnum.Blank.symbol();
		}
		SimpleDateFormat format = new SimpleDateFormat(yyyyMMddHHmmss);
		SimpleDateFormat fo = new SimpleDateFormat(FORMAT_HOUR_SHOW);
		try {
			Date date1 = format.parse(date);
			return fo.format(date1);
		} catch (ParseException e) {
			return KiddSymbolEnum.Blank.symbol();
		}
	}

	/**
	 * 将yyyyMMddHHmmss格式 格式化为yyyy/MM/dd HH:mm:ss
	 * 
	 * @param date
	 *            yyyyMMddHHmmss格式
	 * @return yyyy/MM/dd HH:mm:ss
	 */
	public static String parseToDateYYYYMMDDHHMMSS(String date) {
		if (KiddStringUtils.isBlank(date)) {
			return KiddSymbolEnum.Blank.symbol();
		}
		SimpleDateFormat format = new SimpleDateFormat(yyyyMMddHHmmss);
		SimpleDateFormat fo = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			Date date1 = format.parse(date);
			return fo.format(date1);
		} catch (ParseException e) {
			return KiddSymbolEnum.Blank.symbol();
		}
	}

	/**
	 * 将yyyyMMddHHmmss格式 格式化为yyyy年MM月dd日
	 * 
	 * @param date
	 *            yyyyMMddHHmmss格式
	 * @return yyyy/MM/dd HH:mm:ss
	 */
	public static String parseToDateYYYYMMDD(String date) {
		if (KiddStringUtils.isBlank(date)) {
			return KiddSymbolEnum.Blank.symbol();
		}
		SimpleDateFormat format = null;
		if (date.length() > 8) {
			format = new SimpleDateFormat(yyyyMMddHHmmss);
		} else {
			format = new SimpleDateFormat(yyyyMMdd);
		}

		SimpleDateFormat fo = new SimpleDateFormat("yyyy年MM月dd日");
		try {
			Date date1 = format.parse(date);
			return fo.format(date1);
		} catch (ParseException e) {
			return KiddSymbolEnum.Blank.symbol();
		}
	}

	public static String formatDate2YYYYMMDD(Date date) {
		return parseDate(FORMAT_DATE_SHOW3, date);
	}

	/**
	 * 返回默认时间格式 14位:yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		SimpleDateFormat format = new SimpleDateFormat(yyyyMMddHHmmss);
		return format.format(new Date());
	}

	/**
	 * 返回指定的时间格式
	 * 
	 * @return
	 */
	public static String getCurrentDate(String parrten) {
		if (KiddStringUtils.isBlank(parrten)) {
			return KiddSymbolEnum.Blank.symbol();
		}
		SimpleDateFormat format = new SimpleDateFormat(parrten);
		return format.format(new Date());
	}

	/**
	 * 日期比较，大于currDate，返回false
	 * 
	 * @param format
	 *            日期字符串格式
	 * @param baseDate
	 *            被比较的日期
	 * @param compareDate
	 *            比较日期
	 * @return
	 */
	public static boolean before(String format, String baseDate,
			String compareDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			Date base = simpleDateFormat.parse(baseDate);
			Date compare = simpleDateFormat.parse(compareDate);

			return compare.before(base);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 比较目标时间是否小于当前时间:大于currDate，返回true
	 * 
	 * @param format
	 * @param targetTime
	 * @return
	 */
	public static boolean afterCurrDate(String format, String targetTime) {
		try {
			return new SimpleDateFormat(format).parse(targetTime).after(
					new Date());
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 根据格式得到 step时间间隔以后的时间字符串。
	 * 
	 * @param format
	 * @param baseTime
	 *            基准时间
	 * @param step
	 *            以分钟为单位
	 * @return 返回的格式 为 format
	 */
	public static String getNextTime(String format, String baseTime, long step) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			Date base = simpleDateFormat.parse(baseTime);
			long nextTime = base.getTime() + step * 60 * 1000;

			Date newTime = new Date();
			newTime.setTime(nextTime);

			return simpleDateFormat.format(newTime);

		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 根据基准时间,得到新的时间
	 * 
	 * @param baseDate
	 *            基准时间
	 * @param step
	 *            新时间间隔, 以分为单位
	 * @return
	 */
	public static Date getNextTime(Date baseDate, long step) {

		long nextTime = baseDate.getTime() + step * 60 * 1000;

		Date newDate = new Date();
		newDate.setTime(nextTime);

		return newDate;
	}

	/**
	 * 将yyyy-MM-dd格式 格式化为yyyyMMdd
	 * 
	 * @param date
	 *            yyyy-MM-dd格式
	 * @return yyyyMMdd
	 */
	public static String parseToDateYYYYMMdd(String date) {
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE_SHOW3);
		SimpleDateFormat fo = new SimpleDateFormat(yyyyMMdd);
		try {
			Date date1 = format.parse(date);
			return fo.format(date1);
		} catch (ParseException e) {
			return KiddSymbolEnum.Blank.symbol();
		}
	}

	/**
	 * 将yyyyMMdd格式 格式化为yyyy-MM-dd
	 * 
	 * @param date
	 *            yyyyMMdd格式
	 * @return yyyy-MM-dd
	 */
	public static String parseToDate2(String date) {
		SimpleDateFormat format = new SimpleDateFormat(yyyyMMdd);
		SimpleDateFormat fo = new SimpleDateFormat(FORMAT_DATE_SHOW3);
		try {
			Date date1 = format.parse(date);
			return fo.format(date1);
		} catch (ParseException e) {
			return KiddSymbolEnum.Blank.symbol();
		}
	}

	/**
	 * 日期字符串格式转换通用方法
	 * 
	 * @param date
	 *            要转换的值
	 * @param sourceFormate
	 *            源日期格式
	 * @param objectiveFormate
	 *            目标日期格式
	 * @return
	 */
	public static String parseToDate(String date, String sourceFormate,
			String objectiveFormate) {
		SimpleDateFormat format = new SimpleDateFormat(sourceFormate);
		SimpleDateFormat fo = new SimpleDateFormat(objectiveFormate);
		try {
			Date date1 = format.parse(date);
			return fo.format(date1);
		} catch (ParseException e) {
			return KiddSymbolEnum.Blank.symbol();
		}
	}

	/**
	 * 得到前两个月的最后一天的日期和 前一个月的最后一天的日期(如果为<=0取默认的) 参数可选
	 * 
	 * @param startMonth
	 *            (默认2)
	 * @param endMonth
	 *            (默认1)
	 * @param datePattern
	 *            日期格式(默认yyyy-MM-dd)
	 * @return key[startDate],key[endDate]
	 */
	public static Map<String, String> getDateForSet(Integer startMonth,
			Integer endMonth, String datePattern) {
		if (startMonth == null) {
			startMonth = -2;
		} else {
			startMonth = Integer.parseInt("-" + startMonth);
		}
		if (endMonth == null) {
			endMonth = -1;
		} else {
			endMonth = Integer.parseInt("-" + startMonth);
		}
		if (KiddStringUtils.isBlank(datePattern)) {
			datePattern = FORMAT_DATE_SHOW3;
		}

		SimpleDateFormat format = new SimpleDateFormat(datePattern);
		Calendar start = Calendar.getInstance();
		start.add(Calendar.MONTH, startMonth);
		start.set(Calendar.DAY_OF_MONTH,
				start.getActualMaximum(Calendar.DAY_OF_MONTH));
		String startDate = format.format(start.getTime());

		Calendar end = Calendar.getInstance();
		end.add(Calendar.MONTH, endMonth);
		end.set(Calendar.DAY_OF_MONTH,
				end.getActualMaximum(Calendar.DAY_OF_MONTH));
		String endDate = format.format(end.getTime());
		Map<String, String> rs = new HashMap<String, String>(1);
		rs.put("startDate", startDate);
		rs.put("endDate", endDate);
		return rs;
	}

	/**
	 * 将yyyyMMddHHmmss格式 格式化为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 *            yyyyMMddHHmmss格式
	 * @return yyyy/MM/dd HH:mm:ss
	 */
	public static String parseToDateAD(String date) {
		if (KiddStringUtils.isBlank(date)) {
			return KiddSymbolEnum.Blank.symbol();
		}
		SimpleDateFormat format = new SimpleDateFormat(yyyyMMddHHmmss);
		SimpleDateFormat fo = new SimpleDateFormat(FORMAT_TIME_SHOW);
		try {
			Date date1 = format.parse(date);
			return fo.format(date1);
		} catch (ParseException e) {
			return KiddSymbolEnum.Blank.symbol();
		}
	}

	/**
	 * 将yyyy-MM-dd HH:mm:ss格式 格式转化为yyyyMMddHHmmss
	 * 
	 * @param yyyyMMddHHmmss
	 * @param date
	 * @return
	 */
	public static String parseToDateS(String date) {
		if (KiddStringUtils.isBlank(date)) {
			return null;
		}

		SimpleDateFormat format1 = new SimpleDateFormat(FORMAT_TIME_SHOW);
		SimpleDateFormat format2 = new SimpleDateFormat(yyyyMMddHHmmss);

		try {
			Date date1 = format1.parse(date);
			return format2.format(date1);
		} catch (ParseException e) {
			return null;
		}

	}

	/**
	 * 将yyyyMMddHHmmss格式 格式化为YYYY年MM月DD日HH时MM分SS秒
	 * 
	 * @param date
	 *            yyyyMMddHHmmss格式
	 * @return yyyy/MM/dd HH:mm:ss
	 */
	public static String parseToDateAD3(String date) {
		if (KiddStringUtils.isBlank(date)) {
			return KiddSymbolEnum.Blank.symbol();
		}
		SimpleDateFormat format = new SimpleDateFormat(yyyyMMddHHmmss);
		SimpleDateFormat fo = new SimpleDateFormat(FORMAT_DATE_TIME_SHOW);
		try {
			Date date1 = format.parse(date);
			return fo.format(date1);
		} catch (ParseException e) {
			return KiddSymbolEnum.Blank.symbol();
		}
	}

	/**
	 * 取当前时间前一天和当前时间
	 * 
	 */
	public static Map<String, String> getLastCurrDay() {
		String datePattern = "yyyy-MM-dd";
		SimpleDateFormat format = new SimpleDateFormat(datePattern);
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.add(Calendar.DATE, -1); // 得到前一天
		String startDate = format.format(calendarStart.getTime());
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.add(Calendar.DATE, 0); // 得到前一天
		String endDate = format.format(calendarEnd.getTime());
		Map<String, String> rs = new HashMap<String, String>(1);
		rs.put("startDate", startDate);
		rs.put("endDate", endDate);
		return rs;
	}

	/**
	 * 取得输入日期的间隔日期 默认输入格式为(yyyyMMdd)
	 * 
	 * @param originalDate
	 * @param intervalDay
	 * @return
	 */
	public static String getIntervalDay(String originalDate, int intervalNumber) {
		Date currentDate = parseDate(yyyyMMdd, originalDate);
		Date tempIntervalDay = addDays(currentDate, intervalNumber);
		return parseDate(yyyyMMdd, tempIntervalDay);

	}

	/**
	 * 取当月第一天和当前时间
	 * 
	 */
	public static Map<String, String> getFirstDayInMonth() {
		String datePattern = FORMAT_DATE_SHOW3;
		SimpleDateFormat format = new SimpleDateFormat(datePattern);
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.set(Calendar.DAY_OF_MONTH, 1); // 得到当月第一天
		String startDate = format.format(calendarStart.getTime());
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.add(Calendar.DATE, 0); // 得到当天日期
		String endDate = format.format(calendarEnd.getTime());
		Map<String, String> rs = new HashMap<String, String>(1);
		rs.put("startDate", startDate);
		rs.put("endDate", endDate);
		return rs;
	}

	/**
	 * 取当月第一天和当前时间
	 * 
	 */
	public static Map<String, String> getFirstDayInMonth(String datePattern) {
		SimpleDateFormat format = new SimpleDateFormat(datePattern);
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.set(Calendar.DAY_OF_MONTH, 1); // 得到当月第一天
		String startDate = format.format(calendarStart.getTime());
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.add(Calendar.DATE, 0); // 得到当天日期
		String endDate = format.format(calendarEnd.getTime());
		Map<String, String> rs = new HashMap<String, String>(1);
		rs.put("startDate", startDate);
		rs.put("endDate", endDate);
		return rs;
	}

	/**
	 * 比较两个日期的顺序
	 * 
	 * @param format日期字符串格式
	 * @param baseDate被比较的日期
	 * @param compareDate比较日期
	 * @return 如果参数 compareDate 等于此 baseDate，则返回值 0；如果此 compareDate 在baseDate
	 *         参数之前，则返回大于 0 的值；如果此 compareDate在 baseDate 参数之后，则返回小于 0 的值。
	 */
	public static int compareTo(String format, String compareDate,
			String baseDate) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			Date base = simpleDateFormat.parse(baseDate);
			Date compare = simpleDateFormat.parse(compareDate);
			return base.compareTo(compare);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 为日期增加天数
	 * 
	 * @param curDateType
	 * @param days
	 * @return
	 */
	public static Date addDays(Date curDateType, int days) {
		Calendar expiration = Calendar.getInstance();
		expiration.setTime(curDateType);
		expiration.add(Calendar.DAY_OF_MONTH, days);

		return expiration.getTime();
	}

	/**
	 * 为日期增加月数
	 * 
	 * @param curDateType
	 * @param expTime
	 * @return
	 */
	public static Date addMonths(Date curDateType, int expTime) {
		Calendar expiration = Calendar.getInstance();
		expiration.setTime(curDateType);
		expiration.add(Calendar.MONTH, expTime);

		return expiration.getTime();
	}
	
	/**
	 * 为日期增加秒数
	 * 
	 * @param curDateType
	 * @param seconds
	 * @return
	 */
	public static Date addSeconds(Date curDateType, int seconds) {
		Calendar expiration = Calendar.getInstance();
		expiration.setTime(curDateType);
		expiration.add(Calendar.SECOND, seconds);

		return expiration.getTime();
	}

	/**
	 * 返回两个日期相差的天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getDistDates(Date startDate, Date endDate) {
		long totalDate = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);

		long timestart = calendar.getTimeInMillis();
		calendar.setTime(endDate);
		long timeend = calendar.getTimeInMillis();

		totalDate = (timeend - timestart) / (1000 * 60 * 60 * 24);
		return totalDate;
	}

	/**
	 * 接受YYYY-MM-DD的日期字符串参数,返回两个日期相差的天数
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws ParseException
	 */
	public static long getDistDates(String start, String end)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_SHOW3);
		Date startDate = sdf.parse(start);
		Date endDate = sdf.parse(end);
		return getDistDates(startDate, endDate);
	}

	/**
	 * 计算天数差
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static long getDiffDay(String beginDate, String endDate)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE_SHOW3);
		Date dbeginDate = null;
		Date dendDate = null;
		dbeginDate = formatter.parse(beginDate);
		dendDate = formatter.parse(endDate);
		long daysBetween = (dendDate.getTime() - dbeginDate.getTime() + 1000000L)
				/ (3600 * 24 * 1000);
		return daysBetween;
	}

	/**
	 * 返回两个日期相差的天数
	 * 
	 * @param start
	 *            [开始日期]
	 * @param startPattern
	 *            [开始日期格式]
	 * @param end
	 *            [结束日期]
	 * @param endPattern
	 *            [结束日期格式]
	 * @return
	 * @throws ParseException
	 */
	public static long getDistDays(String start, String startPattern,
			String end, String endPattern) throws ParseException {
		SimpleDateFormat startSdf = new SimpleDateFormat(startPattern);
		SimpleDateFormat endSdf = new SimpleDateFormat(endPattern);
		return getDistDates(startSdf.parse(start), endSdf.parse(end));
	}

	/**
	 * 接受YYYY-MM-DD的日期字符串参数,返回两个日期相差的月数
	 * 
	 * @param 　beginDate
	 * @param 　endDate
	 * @return 相差月数
	 * @throws ParseException
	 */
	public static int getDiffMonth(String beginDate, String endDate)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE_SHOW3);
		Date dbeginDate = null;
		Date dendDate = null;
		dbeginDate = formatter.parse(beginDate);
		dendDate = formatter.parse(endDate);
		return getDiffMonth(dbeginDate, dendDate);
	}

	/**
	 * 返回两个日期相差的月数
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return 相差月数
	 */
	public static int getDiffMonth(Date beginDate, Date endDate) {
		Calendar calbegin = Calendar.getInstance();
		Calendar calend = Calendar.getInstance();
		calbegin.setTime(beginDate);
		calend.setTime(endDate);
		int mBegin = calbegin.get(Calendar.MONTH) + 1; // 获得合同开始日期月份
		int mEnd = calend.get(Calendar.MONTH) + 1;
		// 获得合同结束日期月份
		int checkmonth = mEnd - mBegin
				+ (calend.get(Calendar.YEAR) - calbegin.get(Calendar.YEAR))
				* 12;
		// 获得合同结束日期于开始的相差月份
		return checkmonth;
	}

	/**
	 * 把页面上的时间格式 yyyy-MM-dd 或者 yyyy-MM-dd HH:mm:ss 转换为瀚银时间格式 yyyyMMdd 或者
	 * yyyyMMddHHmmss
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getHpDate(String date) {
		String result = null;
		try {
			if (KiddStringUtils.isBlank(date)) {
				result = KiddSymbolEnum.Blank.symbol();
			} else {
				result = new SimpleDateFormat(yyyyMMddHHmmss)
						.format(new SimpleDateFormat("yyyy-MM-dd H:m:s")
								.parse(date));
			}
		} catch (Exception e) {
			result = date.replace("-", KiddSymbolEnum.Blank.symbol());
		}
		return result;
	}

	/**
	 * 把瀚银时间格式转换成页面显示的时间格式 yyyyMMdd 或者 yyyyMMddHHmmss 转换为 yyyy-MM-dd 或者
	 * yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateFromHpDate(String date) {
		String result = null;
		if (KiddStringUtils.isBlank(date)) {
			result = KiddSymbolEnum.Blank.symbol();
		} else {
			if (8 == date.length()) {
				result = parseToDate2(date);
			}
			if (14 == date.length()) {
				result = parseToDateAD(date);
			}
		}
		return result;
	}

	/**
	 * 接受yyyyMMddHHmmss的日期字符串参数,返回两个日期相差的毫秒数
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws ParseException
	 */
	public static long getDistTimeInMillis(String start, String end)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(yyyyMMddHHmmss);
		Date startDate = sdf.parse(start);
		Date endDate = sdf.parse(end);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);

		long timestart = calendar.getTimeInMillis();
		calendar.setTime(endDate);
		long timeend = calendar.getTimeInMillis();

		return (timeend - timestart);
	}

	/**
	 * 获取两个日期之间的日期(包含两端的日期)
	 * 
	 * @param beginDate
	 *            起始日期(格式:yyyyMMdd)
	 * @param endDate
	 *            终结日期(格式:yyyyMMdd) 如果beginDate大于endDate,则互换值
	 * @return
	 */
	public static List<String> getBetweenDates(String beginDate, String endDate) {
		String temp = KiddSymbolEnum.Blank.symbol();
		if (compareTo(yyyyMMdd, beginDate, endDate) < 0) {
			temp = beginDate;
			beginDate = endDate;
			endDate = temp;
		}

		long tmpTime = parseDate(yyyyMMdd, beginDate).getTime();
		long endTime = parseDate(yyyyMMdd, endDate).getTime();
		List<String> result = new ArrayList<String>();
		while (tmpTime <= endTime) {
			Date targetDate = new Date(tmpTime);
			result.add(parseDate(yyyyMMdd, targetDate));
			tmpTime += DATE_TIME_BASE;
		}
		return result;
	}

	/**
	 * 判断某一天是否是月末
	 * 
	 * @param datePattern
	 *            格式yyyyMMddHHmmss
	 * @return
	 */
	public static boolean isMonthLastDay(String datePattern) {
		Date before = parseDate(yyyyMMddHHmmss, datePattern);
		Calendar beforeCal = Calendar.getInstance();// 当天日期
		beforeCal.setTime(before);
		final int beforeMonth = beforeCal.get(Calendar.MONTH);

		beforeCal.add(Calendar.DAY_OF_MONTH, 1);
		final int afterMonth = beforeCal.get(Calendar.MONTH);
		return !(beforeMonth == afterMonth);
	}

	@SuppressWarnings("deprecation")
	public int getDutyDays(String strStartDate, String strEndDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = df.parse(strStartDate);
			endDate = df.parse(strEndDate);
		} catch (ParseException e) {
		}

		int result = 0;
		while (null != startDate && null != endDate
				&& startDate.compareTo(endDate) <= 0) {
			if (startDate.getDay() != 6 && startDate.getDay() != 0) {
				result++;
			}
			startDate.setDate(startDate.getDate() + 1);
		}

		return result;
	}

	/**
	 * 得到当前日期的下一个N天
	 * 
	 * @param formatPattern
	 *            如果传入值为空，会默认为YYYYMMDD
	 * @param inputDate
	 * @param stepDay
	 *            间隔天数
	 * @return 返回格式同formatPattern一样
	 */
	public static String getNextNDay(String formatPattern, String inputDate,
			int stepDay) {
		if (formatPattern == null || formatPattern.trim().length() == 0) {
			formatPattern = yyyyMMdd;
		}
		Date nextBeginDay = addDays(parseDate(formatPattern, inputDate),
				stepDay);
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatPattern);
		return dateFormat.format(nextBeginDay);
	}

	/**
	 * 获取当前日期
	 * 
	 * @return
	 */
	public static String getCurrentDay() {
		String currentDay = null;
		SimpleDateFormat format = new SimpleDateFormat(yyyyMMddHHmmss);
		currentDay = format.format(new Date());
		return currentDay;
	}

	public static String getNextDay() {
		Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int date = c.get(Calendar.MONDAY);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		String nextYear = year - 1 + KiddSymbolEnum.Blank.symbol() + month + KiddSymbolEnum.Blank.symbol() + date + KiddSymbolEnum.Blank.symbol() + hour + KiddSymbolEnum.Blank.symbol()
				+ minute + KiddSymbolEnum.Blank.symbol() + second;
		return nextYear;
	}

	public static Date getTAddTwoDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int count = 0;
		while (count != 2) {

			if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
					&& cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
				count++;
			} else {
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}

			// if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY ||
			// cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
			// cal.add(Calendar.DAY_OF_MONTH, 1);
			// count ++;
			// }else{
			// cal.add(Calendar.DAY_OF_MONTH, 1);
			// }
		}

		return cal.getTime();

	}

	/**
	 * 根据当前日期计算间隔interval天后的日期(默认格式是"yyyy-MM-dd"
	 * 
	 * @param interval
	 * @return
	 */
	public static String calcDateOfInterval(int interval) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) + interval);
		Date endDate = null;
		try {
			endDate = dft.parse(dft.format(date.getTime()));
		} catch (ParseException e) {
			System.out.println("非法的日期格式,无法进行转换");
		}
		return parseDate("yyyy-MM-dd", endDate);
	}

	public static String getDayOfWee(String formatPattern) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(formatPattern);// 设置日期格式
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return df.format(cal.getTime());
	}

	/**
	 * 检测日期：yyyyMMdd
	 * 
	 * @param value
	 * @param datePattern
	 * @return
	 */
	public static boolean isDate(String value) {
		String str = "^(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0?[1-9]|1[0-2])([-/.]?)(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])([-/.]?)(?:29|30)|(?:0?[13578]|1[02])([-/.]?)31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-/.]?)0?2([-/.]?)29)$";
		Pattern p = Pattern.compile(str);
		String s = value.replaceAll("-", "/");
		return p.matcher(s).matches();
	}

	/**
	 * 检测时间：HHmmss
	 * 
	 * @param value
	 * @param datePattern
	 * @return
	 */
	public static boolean isTime(String value) {
		String str = "([01][0-9]|2[0-3])[0-5][0-9][0-5][0-9]";
		Pattern p = Pattern.compile(str);
		String s = value.replaceAll("-", "/");
		return p.matcher(s).matches();
	}

	public static String ystDay() {
		return formatDate2YYYYMMDD(addDays(new Date(), -1));
	}

	/**
	 * @Description ★ 根据时间，推出所属年份
	 * @param time
	 *            时间，格式：MMddHHmmss 或 MMdd
	 * @return
	 */
	public static String getYearByFormatTime(String time) {
		if (KiddStringUtils.isBlank(time)) {
			return KiddSymbolEnum.Blank.symbol();
		}
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		int currentMonth = cal.get(Calendar.MONTH) + 1;
		int currentYearAndMonth = currentYear + currentMonth;

		String month = time.substring(0, 2);
		int yearAndMonth = currentYear + Integer.valueOf(month).intValue();

		if (currentYearAndMonth >= yearAndMonth) {
			return currentYear + time;
		}
		return (currentYear - 1) + time;
	}

}
