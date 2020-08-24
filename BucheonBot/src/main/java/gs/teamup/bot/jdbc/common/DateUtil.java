package gs.teamup.bot.jdbc.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



//import org.apache.commons.lang3.StringUtils;

/**
 * DateUtil
 * 기준 시간은 WAS의 시간임
 *
 * @author 배아영
 * @version $Id: DateUtil.java $
 */
public final class DateUtil {

	private DateUtil() {
		throw new AssertionError();
	}

	/**
	 * 현재 날짜를 주어진 포멧의 문자로 리턴
	 *
	 * @param formatStr null이나 빈문자인 경우 기본 포멧(yyyy-MM-dd)
	 * @return
	 */
	public static String getToday(String formatStr) {

		String format = formatStr;
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		Date date = new Date();
		SimpleDateFormat sdate = new SimpleDateFormat(format);

		return sdate.format(date);
	}

	/**
	 * 현재 날짜와 시간을 주어진 포멧의 문자로 리턴
	 *
	 * @param formatStr null이거나 빈문자인 경우 기본 포멧(yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static String getTodayDateTime(String formatStr) {

		String format = formatStr;
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		Date date = new Date();
		SimpleDateFormat sdate = new SimpleDateFormat(format);

		return sdate.format(date);
	}

	/**
	 * Date를 Format 문자열로 리턴
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getFmtDateString(Date date, String format) {
		SimpleDateFormat sdate = new SimpleDateFormat(format);

		return sdate.format(date);
	}

	/**
	 * 문자열 Date를 Format 문자열로 변환 리턴
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getFmtDateString(String date, String format) {
		Date todate = toDate(date);

		return getFmtDateString(todate, format);
	}

	/**
	 * Date를 Format 문자열로 변환 리턴
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getFmtDateString(Long date, String format) {
		SimpleDateFormat sdate = new SimpleDateFormat(format);

		return sdate.format(date);
	}

	/**
	 * 구분자가 추가된 날짜 스트링
	 * 년월일에 delim구분자 추가하여 리턴
	 *
	 * @param date yyyyMMdd
	 * @param delim
	 * @return
	 */
	public static String getDelimDateString(String date, String delim) {
		String unFmtDate = getUnFmtDateString(date);

		StringBuffer buf = new StringBuffer();

		buf.append(unFmtDate.substring(0, 4));
		buf.append(delim);
		buf.append(unFmtDate.substring(4, 6));
		buf.append(delim);
		buf.append(unFmtDate.substring(6, 8));

		return buf.toString();
	}

	/**
	 * 구분자가 제거된 날짜 스트링
	 *
	 * @param fmtDate
	 * @return
	 */
	public static String getUnFmtDateString(String fmtDate) {
		boolean isCharater = false;
		boolean isCorrect = true;

		String strDate = "";
		String date = "";
		String result = "";

		if (fmtDate != null) {
			strDate = fmtDate.trim();
		}

		for (int inx = 0; inx < strDate.length(); inx++) {
			if (Character.isLetter(strDate.charAt(inx)) || strDate.charAt(inx) == ' ') {
				isCorrect = false;

				break;
			}
		}

		if (!isCorrect) {
			return "";
		}

		if (strDate.length() != 8) {
			if (strDate.length() != 6 && strDate.length() != 10) {
				return "";
			}

			if (strDate.length() == 6) {
				if (Integer.parseInt(strDate.substring(0, 2)) > 50) {
					date = "19";
				} else {
					date = "20";
				}

				result = date + strDate;
			}

			if (strDate.length() == 10) {
				result = strDate.substring(0, 4) + strDate.substring(4, 8) + strDate.substring(8, 10);
			}
		} else {
			try {
				Integer.parseInt(strDate);
			} catch (NumberFormatException ne) {
				isCharater = true;
			}

			if (isCharater) {
				date = strDate.substring(0, 2) + strDate.substring(3, 5) + strDate.substring(6, 8);

				if (Integer.parseInt(strDate.substring(0, 2)) > 50) {
					result = "19" + date;
				} else {
					result = "20" + date;
				}
			} else {
				return strDate;
			}
		}

		return result;
	}

	/**
	 * 주어진 date로 부터 year년 month월 day일 차이나는 시각을 리턴한다.
	 *
	 * <pre>
	 *  사용예)
	 *  //현재로부터 10일 전의 date/time
	 *  Date newDate = DateUtil.getRelativeDate(new Date(), 0, 0, -10);
	 * </pre>
	 *
	 * @param date
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date getRelativeDate(Date date, int year, int month, int day) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		cal.add(Calendar.DAY_OF_MONTH, day);

		return cal.getTime();
	}

	/**
	 * 주어진 date로 부터 year년 month월 차이나는 시각을 리턴한다.
	 *
	 * <pre>
	 *  사용예)
	 *  //현재로부터 2달 전의 date/time
	 *  Date newDate = DateUtil.getRelativeDate(new Date(), 0, -2);
	 * </pre>
	 *
	 * @param date
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getRelativeDate(Date date, int year, int month) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);

		return cal.getTime();
	}

	/**
	 * 주어진 date로 부터 day일 차이나는 시각을 리턴한다.
	 *
	 * <pre>
	 *  사용예)
	 *  //현재로부터 10일 전의 date/time
	 *  Date newDate = DateUtil.getRelativeDate(new Date(), -10);
	 * </pre>
	 *
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getRelativeDate(Date date, int day) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.DAY_OF_YEAR, day);

		return cal.getTime();
	}

	/**
	 * 주어진 date로 부터 year년 month월 day일 차이나는 시각을 주어진 포멧 문자열로 리턴한다.
	 *
	 * <pre>
	 *  사용예)
	 *  //현재 시각이 '20031001'이면 10일 전 시각인 '20030921'을 리턴
	 *  String date = DateUtil.getRelativeDateString(new Date(), 0,0,-10, "yyyyMMdd");
	 * </pre>
	 *
	 * @param date
	 * @param year
	 * @param month
	 * @param day
	 * @param format
	 * @return
	 */
	public static String getRelativeDateString(Date date, int year, int month, int day, String format) {
		Date relativeDate = getRelativeDate(date, year, month, day);

		return getFmtDateString(relativeDate, format);
	}

	/**
	 * 주어진 date로 부터 년/월/일/시/분을 더한 날짜 구하기
	 *
	 * @param date
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static Date getRelativeDate(Date date, int year, int month, int day, int hour, int minute) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		cal.add(Calendar.DAY_OF_MONTH, day);
		cal.add(Calendar.HOUR_OF_DAY, hour);
		cal.add(Calendar.MINUTE, minute);

		return cal.getTime();
	}

	/**
	 * 주어진 date로 부터 년/월/일/시/분을 더한 날짜를 포멧 문자열로 리턴한다.
	 *
	 * @param date
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param format
	 * @return
	 */
	public static String getRelativeDateString(Date date, int year, int month, int day, int hour, int minute,
			String format) {
		Date relativeDate = getRelativeDate(date, year, month, day, hour, minute);

		return getFmtDateString(relativeDate, format);
	}

	/**
	 * 주어진 date로 부터 년/월/일/시/분을 더한 날짜를 포멧 문자열로 리턴한다.
	 *
	 * @param date yyyyMMddHHmm
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param format
	 * @return
	 */
	public static String getRelativeDateString(String date, int year, int month, int day, int hour, int minute,
			String format) {
		Calendar cal = toCalendar(date.substring(0, 8), Integer.parseInt(date.substring(8, 10)),
				Integer.parseInt(date.substring(10, 12)));

		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		cal.add(Calendar.DAY_OF_MONTH, day);
		cal.add(Calendar.HOUR_OF_DAY, hour);
		cal.add(Calendar.MINUTE, minute);

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(cal.getTime());
	}

	/**
	 * 주어진 date에 days일을 더한 날짜를 포멧 문자열로 리턴한다.
	 *
	 * @param date yyyyMMdd
	 * @param days
	 * @param formatStr
	 * @return
	 */
	public static String getNextDate(String date, int days, String formatStr) {
		if (days < 0) {
			return date;
		}

		String format = formatStr;
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		Calendar cal = toCalendar(date);

		cal.add(Calendar.DATE, days);

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(cal.getTime());
	}

	/**
	 * 주어진 date에 days일을 뺀 날짜를 포멧 문자열로 리턴한다.
	 *
	 * @param date yyyyMMdd
	 * @param days
	 * @param formatStr null이거나 빈문자인 경우 yyyy-MM-dd
	 * @return
	 */
	public static String getPrevDate(String date, int days, String formatStr) {

		if (days < 0) {
			return date;
		}

		String format = formatStr;
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		java.util.Calendar cal = toCalendar(date);

		cal.add(Calendar.DATE, -(days));

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(cal.getTime());
	}

	/**
	 * 주어진 date에 weeks주를 더한 날짜를 리턴한다.
	 *
	 * @param date yyyyMMdd
	 * @param weeks
	 * @param format null이거나 빈문자이면 yyyy-MM-dd
	 * @return
	 */
	public static String getNextWeekDate(String date, int weeks, String paramFormat) {
		String format = paramFormat;

		if (weeks < 0) {
			return date;
		}

		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		java.util.Calendar cal = toCalendar(date);

		cal.add(Calendar.DATE, weeks * 7);

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(cal.getTime());
	}

	/**
	 * 주어진 date에 weeks주를 뺀 날짜를 리턴한다.
	 *
	 * @param date yyyyMMdd
	 * @param weeks
	 * @param format null이거나 빈문자이면 yyyy-MM-dd
	 * @return
	 */
	public static String getPrevWeekDate(String date, int weeks, String paramFormat) {
		String format = paramFormat;

		if (weeks < 0) {
			return date;
		}

		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		java.util.Calendar cal = toCalendar(date);

		cal.add(Calendar.DATE, weeks * (-7));

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(cal.getTime());
	}

	/**
	 * 주어진 date로 부터 months월을 더한 날짜를 리턴한다.
	 *
	 * @param date yyyyMMdd
	 * @param months
	 * @param format null이거나 빈문자이면 yyyy-MM-dd
	 * @return
	 */
	public static String getNextMonthDate(String date, int months, String paramFormat) {
		String format = paramFormat;
		if (months < 0) {
			return date;
		}

		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		java.util.Calendar cal = toCalendar(date);

		cal.add(Calendar.MONTH, months);

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(cal.getTime());
	}

	/**
	 * 주어진 date로 부터 months월을 뺀 날짜를 리턴한다.
	 *
	 * @param date yyyyMMdd
	 * @param months
	 * @param format null이거나 빈문자이면 yyyy-MM-dd
	 * @return
	 */
	public static String getPrevMonthDate(String date, int months, String paramFormat) {
		String format = paramFormat;

		if (months < 0) {
			return date;
		}

		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		java.util.Calendar cal = toCalendar(date);

		cal.add(Calendar.MONTH, -(months));

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		return dateFormat.format(cal.getTime());
	}

	/**
	 * 주어진 date를 Calendar 객체로 반환한다.
	 *
	 * @param fmtDate yyyyMMdd
	 * @return
	 */
	public static Calendar toCalendar(String fmtDate) {
		String date = getUnFmtDateString(fmtDate);

		GregorianCalendar calendar = new GregorianCalendar();

		calendar.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1,
				Integer.parseInt(date.substring(6, 8)));

		return calendar;
	}

	/**
	 * 주어진 date를 Calendar 객체로 반환한다.
	 *
	 * @param fmtDate yyyyMMdd
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static Calendar toCalendar(String fmtDate, int hour, int minute) {

		String date = getUnFmtDateString(fmtDate);

		GregorianCalendar calendar = new GregorianCalendar();

		calendar.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1,
				Integer.parseInt(date.substring(6, 8)), hour, minute);

		return calendar;
	}

	/**
	 * 주어진 date를 Calendar 객체로 반환한다.
	 *
	 * @param fmtDate yyyyMMdd
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Calendar toCalendar(String fmtDate, int hour, int minute, int second) {
		String date = getUnFmtDateString(fmtDate);

		GregorianCalendar calendar = new GregorianCalendar();

		calendar.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1,
				Integer.parseInt(date.substring(6, 8)), hour, minute, second);

		return calendar;
	}

	/**
	 * 주어진 문자열 datee를 Date 객체로 반환한다.
	 *
	 * @param fmtDate yyyyMMdd
	 * @return
	 */
	public static Date toDate(String fmtDate) {
		return toCalendar(fmtDate).getTime();
	}

	/**
	 * 주어진 date가 속한 주간에 order요일의 날짜를 리턴한다.
	 *
	 * @param date yyyyMMdd
	 * @param order 1(일) ~ 7(토)
	 * @return
	 */
	public static String getWeekDay(String date, int order) {
		String returnDay = null;
		Calendar curr = Calendar.getInstance();
		curr.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1,
				Integer.parseInt(date.substring(6, 8)));
		int weekday = curr.get(Calendar.DAY_OF_WEEK);

		if (order == weekday) {
			returnDay = date;
		} else {
			curr.add(Calendar.DATE, order - weekday);
			SimpleDateFormat sdate = new SimpleDateFormat("yyyyMMdd");
			returnDay = sdate.format(curr.getTime());
		}
		return returnDay;
	}

	/**
	 * 주어진 date가 속한 주간에 order요일의 날짜를 리턴한다.
	 *
	 * @param date yyyyMMdd
	 * @param order 1(일) ~ 7(토)
	 * @return
	 */
	public static Date getWeekDay(Date date, int order) {
		Date returnDay;
		Calendar curr = Calendar.getInstance();
		curr.setTime(date);
		int weekday = curr.get(Calendar.DAY_OF_WEEK);

		if (order == weekday) {
			returnDay = date;
		} else {
			curr.add(Calendar.DATE, order - weekday);
			returnDay = curr.getTime();
		}
		return returnDay;
	}

	/**
	 * 주어진 달의 1일의 요일을 리턴한다
	 *
	 * @param year
	 * @param month
	 * @return 1(일) ~ 7(토)
	 */
	public static int getFirstDay(int year, int month) {
		int firstday = 0;

		Calendar curr = Calendar.getInstance();

		curr.set(year, month - 1, 1);

		firstday = curr.get(Calendar.DAY_OF_WEEK);
		return firstday;
	}

	/**
	 * 주어진 달의 마지막날짜를 리턴한다
	 *
	 * @param year
	 * @param month
	 * @return 28 ~ 31
	 */
	public static int getLastDate(int year, int month) {

		int yy = year;
		int mm = month;

		switch (mm) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;

		case 4:
		case 6:
		case 9:
		case 11:
			return 30;

		default:
			if (((yy % 4 == 0) && (yy % 100 != 0)) || (yy % 400 == 0)) {
				return (29);
			} else {
				return (28);
			}
		}
	}

	/**
	 * 주어진 date의 요일을 리턴한다.
	 *
	 * @param date yyyyMMdd
	 * @return 1(일) ~ 7(토)
	 */
	public static int getWeekDayCount(String date) {
		Calendar curr = Calendar.getInstance();
		curr.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1,
				Integer.parseInt(date.substring(6, 8)));
		int weekday = curr.get(Calendar.DAY_OF_WEEK);

		return weekday;
	}

	/**
	 * 주어진 date의 요일을 리턴한다.
	 *
	 * @param date
	 * @return 1(일) ~ 7(토)
	 */
	public static int getWeekDayCount(Date strDate) {

		String date = getFmtDateString(strDate,"yyyyMMdd");
		Calendar curr = Calendar.getInstance();
		curr.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1,
				Integer.parseInt(date.substring(6, 8)));
		int weekday = curr.get(Calendar.DAY_OF_WEEK);

		return weekday;
	}


	/**
	 * 그 날이 그 달의 몇번째 week 수인지 구함
	 *
	 * @param day
	 * @return
	 */
	public static int getWeekCountMonth(int day) {
		int weekCount;
		int remainCount = day - (day / 7) * 7;

		if (remainCount > 0) {
			weekCount = (day / 7) + 1;
		} else {
			weekCount = (day / 7);
		}

		return weekCount;
	}

	/**
	 * 주어진 월의 주간 수를 리턴한다.
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getWeekCount(int year, int month) {
		Calendar curr = Calendar.getInstance();
		curr.set(year, month - 1, getLastDate(year, month));
		return curr.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 주어진 date에 val 날짜를 더한 포멧 문자열을 리턴한다.
	 *
	 * @param dateStr yyyyMMdd
	 * @param val 날짜
	 * @param format
	 * @return
	 */
	public static String calcDate(String dateStr, int val, String format) {

		String date = dateStr;
		Calendar curr = Calendar.getInstance();
		date = unFmtDate(date);
		curr.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1,
				Integer.parseInt(date.substring(6, 8)));
		curr.add(Calendar.DATE, val);
		SimpleDateFormat sdate = new SimpleDateFormat(format);
		return sdate.format(curr.getTime());
	}

	/**
	 * 주어진 date에 val 분을 더한 포멧 문자열을 리턴한다.
	 *
	 * @param dateTimeStr yyyyMMddHHmm
	 * @param val 분
	 * @param format
	 * @return
	 */
	public static String calcDateTime(String dateTimeStr, int val, String format) {
		String dateTime = dateTimeStr;
		Calendar curr = Calendar.getInstance();
		dateTime = unFmtDate(dateTime);
		curr.set(Integer.parseInt(dateTime.substring(0, 4)), Integer.parseInt(dateTime.substring(4, 6)) - 1,
				Integer.parseInt(dateTime.substring(6, 8)), Integer.parseInt(dateTime.substring(8, 10)),
				Integer.parseInt(dateTime.substring(10, 12)));
		curr.add(Calendar.MINUTE, val);
		SimpleDateFormat sdate = new SimpleDateFormat(format);
		return sdate.format(curr.getTime());
	}

	/**
	 * 날짜 포멧중 "-"를 제거한 문자열을 리턴한다.
	 *
	 * @param fmtdate yyyy-MM-dd
	 * @return
	 */
	public static String unFmtDate(String fmtdate) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < fmtdate.length(); i++) {
			if (fmtdate.charAt(i) != '-') {
				buf.append(fmtdate.charAt(i));
			}
		}

		return buf.toString();
	}

	/**
	 * 주어진 날짜로 부터 한주간의 날짜 목록을 리턴한다.
	 * <p>
	 * 주어진 날짜가 주간의 첫번째 날짜이면 해당 주간의 전체 날짜를 리턴받을 수 있다.
	 *
	 * @param firstday 기준 일자
	 * @param format
	 * @return
	 */
	public static String[] getDatesInWeek(String firstday, String format) {
		String[] weekdays = new String[7];
		for (int i = 0; i < 7; i++) {
			weekdays[i] = calcDate(firstday, i, format);
		}
		return weekdays;
	}

	/**
	 * 주어진 기간내의 날짜목록을 리턴한다.
	 *
	 * @param startDate yyyyMMdd
	 * @param endDate yyyyMMdd
	 * @param format
	 * @return
	 */
	public static String[] getDatesInPeriod(String startDate, String endDate, String format) {
		int dateDiffCount = getTwoDatesDifference(startDate, endDate);

		String[] days = new String[dateDiffCount + 1];

		for (int i = 0; i < dateDiffCount + 1; i++) {
			days[i] = calcDate(startDate, i, format);
		}

		return days;
	}

	/**
	 * 주어진 기간내의 시간목록을 30분 간격으로 리턴한다.
	 *
	 * @param startDateTime yyyyMMddHHmm
	 * @param endDateTime yyyyMMddHHmm
	 * @param format
	 * @return
	 */
	public static String[] getDateTimesInPeriod(String startDateTime, String endDateTime, String format) {
		int dateTimeDiffCount = getTwoDateTimesDifference(startDateTime, endDateTime);

		String[] dateTimes = new String[dateTimeDiffCount + 1];

		for (int i = 0; i < dateTimes.length; i++) {
			dateTimes[i] = calcDateTime(startDateTime, i * 30, format);
		}

		return dateTimes;
	}

	/**
	 * 두날짜 사이의 일수 구하기
	 *
	 * @param strDate yyyyMMdd
	 * @param strComp yyyyMMdd
	 * @return
	 */
	public static int getTwoDatesDifference(String strDate, String strComp) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		int year = Integer.parseInt(strDate.substring(0, 4));
		int month = Integer.parseInt(strDate.substring(4, 6));
		int day = Integer.parseInt(strDate.substring(6, 8));

		int compYear = Integer.parseInt(strComp.substring(0, 4));
		int compMonth = Integer.parseInt(strComp.substring(4, 6));
		int compDay = Integer.parseInt(strComp.substring(6, 8));

		cal1.set(year, month - 1, day);
		cal2.set(compYear, compMonth - 1, compDay);
		long cal1sec = cal1.getTime().getTime();
		long cal2sec = cal2.getTime().getTime();
		long gap = cal2sec - cal1sec;
		int gapday = Integer.parseInt(String.valueOf((gap / 86400) / 1000));

		return gapday;
	}

	/**
	 * 현재일로 부터 주어진 일자까지의 일수 구하기
	 *
	 * @param endComp yyyyMMdd
	 * @return
	 */
	public static int getDatesDifference(Date endComp) {

		String strDate = getToday("yyyyMMdd");
		String strComp = getFmtDateString(endComp,"yyyyMMdd");
		Date startDate = new Date();
		int curWeekDay = 0;
		long diff = 0;

		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		int year = Integer.parseInt(strDate.substring(0, 4));
		int month = Integer.parseInt(strDate.substring(4, 6));
		int day = Integer.parseInt(strDate.substring(6, 8));

		int compYear = Integer.parseInt(strComp.substring(0, 4));
		int compMonth = Integer.parseInt(strComp.substring(4, 6));
		int compDay = Integer.parseInt(strComp.substring(6, 8));

		cal1.set(year, month - 1, day);
		cal2.set(compYear, compMonth - 1, compDay);
		long cal1sec = cal1.getTime().getTime();
		long cal2sec = cal2.getTime().getTime();
		long gap = cal2sec - cal1sec;
		int gapday = Integer.parseInt(String.valueOf((gap / 86400) / 1000));

		if(gapday < 0){
			gapday = gapday * -1;
		}

		if(gapday > 1){

			long duration = (endComp.getTime() - startDate.getTime()) / 1000;
			curWeekDay = getWeekDayCount(endComp) - getWeekDayCount(startDate) ;
			diff = duration / (60 * 60 * 24);

			if(diff <= 7 && curWeekDay > 0) {
				gapday = 2;

			}else if(diff <= 14) {
				gapday = 3;
			}

		}

		return gapday ;
	}

//	/**
//	 * 주어진 시간사이의 차를 type을 기준으로 구한다.
//	 *
//	 * @param date
//	 * @param compareDate
//	 * @param type S/M/H/D(초/분/시/일)
//	 * @return
//	 * @exception IllegalArgumentException
//	 */
//	public static long getTwoDateDiff(Date date, Date compareDate, String type) {
//		if (date == null) {
//			throw new IllegalArgumentException("date is null");
//		}
//		if (compareDate == null) {
//			throw new IllegalArgumentException("compareDate is null");
//		}
//
//		Long compared = date.getTime() - compareDate.getTime();
//
//		if (StringUtils.equals(type, "S")) {
//			return compared / 1000;
//
//		} else if (StringUtils.equals(type, "M")) {
//			return compared / (60 * 1000);
//
//		} else if (StringUtils.equals(type, "H")) {
//			return compared / (60 * 60 * 1000);
//
//		} else if (StringUtils.equals(type, "D")) {
//			return compared / (24 * 60 * 60 * 1000);
//
//		} else {
//			throw new IllegalArgumentException("type is allowed S, M, H, D");
//		}
//
//	}

	/**
	 * 주어진 시간 사이의 시차를 30분 간격으로 구한다.
	 *
	 * @param strDateTime
	 * @param strCompTime
	 * @return
	 */
	public static int getTwoDateTimesDifference(String strDateTime, String strCompTime) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		int year = Integer.parseInt(strDateTime.substring(0, 4));
		int month = Integer.parseInt(strDateTime.substring(4, 6));
		int day = Integer.parseInt(strDateTime.substring(6, 8));
		int hour = Integer.parseInt(strDateTime.substring(8, 10));
		int minute = Integer.parseInt(strDateTime.substring(10, 12));

		int compYear = Integer.parseInt(strCompTime.substring(0, 4));
		int compMonth = Integer.parseInt(strCompTime.substring(4, 6));
		int compDay = Integer.parseInt(strCompTime.substring(6, 8));
		int compHour = Integer.parseInt(strCompTime.substring(8, 10));
		int compMinute = Integer.parseInt(strCompTime.substring(10, 12));

		cal1.set(year, month - 1, day, hour, minute);
		cal2.set(compYear, compMonth - 1, compDay, compHour, compMinute);
		long cal1sec = cal1.getTime().getTime();
		long cal2sec = cal2.getTime().getTime();
		long gap = cal2sec - cal1sec;
		int gapDateTime = Integer.parseInt(String.valueOf((gap / 1800) / 1000));

		return gapDateTime;
	}

	/**
	 * 주어진 시간으로 부터 현재까지 경과한 시간을 리턴한다.
	 * @param sDate
	 * @param locale en/ko/jp
	 * @return
	 */
	public static String getTimeInterval(Date sDate, String locale) {
		return getTimeInterval(sDate, new Date(), locale);
	}

	/**
	 * 주어진 시간으로 부터 경과한 시간을 리턴한다.
	 *
	 * @param sDate
	 * @param eDate
	 * @param localeStr en/ko/jp
	 * @return
	 */
	public static String getTimeInterval(Date sDate, Date eDate, String localeStr) {

		String diffStr = "";
		long diff = 0;

		String locale = localeStr;
		if (StringUtil.isEmpty(locale)) {
			locale = "en";
		}

		long duration = (eDate.getTime() - sDate.getTime()) / 1000;

		if (duration > (60 * 60 * 24 * 30 * 12)) {
			diff = duration / (60 * 60 * 24 * 30 * 12);
			if (locale.equals("ko")) { //HD 수정
				diffStr = diff + " 년전";
			} else if(locale.equals("jp")){
				diffStr = diff + " 年前";
			}else{
				diffStr = diff + " year ago";
			}
		} else if (duration > (60 * 60 * 24 * 30)) {
			diff = duration / (60 * 60 * 24 * 30);
			if (locale.equals("ko")) { //HD 수정
				diffStr = diff + " 달전";
			} else if(locale.equals("jp")){
				diffStr = diff + " ヶ月前";
			} else {
				diffStr = diff + " month ago";
			}
		} else if (duration > (60 * 60 * 24)) {
			diff = duration / (60 * 60 * 24);
			if (locale.equals("ko")) { //HD 수정
				diffStr = diff + " 일전";
			} else if(locale.equals("jp")){
				diffStr = diff + " 日前";
			} else {
				diffStr = diff + " day ago";
			}
		} else if (duration > (60 * 60)) {
			diff = duration / (60 * 60);
			if (locale.equals("ko")) { //HD 수정
				diffStr = diff + " 시간전";
			} else if(locale.equals("jp")){
				diffStr = diff + " 時間前";
			} else {
				diffStr = diff + " hour ago";
			}
		} else if (duration > (60)) {
			diff = duration / (60);
			if (locale.equals("ko")) { //HD 수정
				diffStr = diff + " 분전";
			}else if(locale.equals("jp")){
				diffStr = diff + " 分前";
			}else {
				diffStr = diff + " minute ago";
			}
		} else {
			if (locale.equals("ko")) { //HD 수정
				diffStr = "1 분전";
			} else if(locale.equals("jp")){
				diffStr = " 1分前";
			} else {
				diffStr = "1 second ago";
			}
		}

		return diffStr;
	}

	/**
	 * 주어진 시간으로 부터 현재까지 경과한 시간을 리턴한다.
	 * @param sDate
	 * @param locale en/ko/jp
	 * @return
	 */
	public static String getTimeIntervalRss(Date sDate, String locale) {
		return getTimeIntervalRss(sDate, new Date(), locale);
	}

	/**
	 * 주어진 시간으로 부터 경과한 시간을 리턴한다.
	 *
	 * @param sDate
	 * @param eDate
	 * @param localeStr en/ko/jp
	 * @return
	 */
	public static String getTimeIntervalRss(Date sDate, Date eDate, String localeStr) {

		String diffStr = "";
		long diff = 0;

		String locale = localeStr;
		if (StringUtil.isEmpty(locale)) {
			locale = "en";
		}

		long duration = (eDate.getTime() - sDate.getTime()) / 1000;

		if (duration > (60 * 60 * 24 * 30 * 12)) {
			diff = duration / (60 * 60 * 24 * 30 * 12);
			if (locale.equals("ko")) { //HD 수정
				diffStr = diff + " 년";
			} else if(locale.equals("jp")){
				diffStr = diff + " 年";
			}else{
				diffStr = diff + " year";
			}
		} else if (duration > (60 * 60 * 24 * 30)) {
			diff = duration / (60 * 60 * 24 * 30);
			if (locale.equals("ko")) { //HD 수정
				diffStr = diff + " 달";
			} else if(locale.equals("jp")){
				diffStr = diff + " ヶ月";
			} else {
				diffStr = diff + " month";
			}
		} else if (duration > (60 * 60 * 24)) {
			diff = duration / (60 * 60 * 24);
			if (locale.equals("ko")) { //HD 수정
				diffStr = diff + " 일";
			} else if(locale.equals("jp")){
				diffStr = diff + " 日";
			} else {
				diffStr = diff + " day";
			}
		} else if (duration > (60 * 60)) {
			diff = duration / (60 * 60);
			if (locale.equals("ko")) { //HD 수정
				diffStr = diff + " 시간";
			} else if(locale.equals("jp")){
				diffStr = diff + " 時間";
			} else {
				diffStr = diff + " hour";
			}
		} else if (duration > (60)) {
			diff = duration / (60);
			if (locale.equals("ko")) { //HD 수정
				diffStr = diff + " 분";
			}else if(locale.equals("jp")){
				diffStr = diff + " 分";
			}else {
				diffStr = diff + " minute";
			}
		} else {
			if (locale.equals("ko")) { //HD 수정
				diffStr = "1 분";
			} else if(locale.equals("jp")){
				diffStr = "1分";
			} else {
				diffStr = "1 second";
			}
		}

		return diffStr;
	}


	/**
	 * 주어진 시간으로 부터 경과한 시간의 구분을 리턴한다.
	 *
	 * @param eDate
	 * @param localeStr en/ko/jp
	 * @return T/Y/TW/LW/OT (오늘/어제/금주/지난주/other)
	 */
	public static String getDateInterval(Date eDate, String localeStr) {

		String diffStr = "";

		String locale = localeStr;
		if (StringUtil.isEmpty(locale)) {
			locale = "en";
		}

		Calendar day1 = Calendar.getInstance();
		Calendar day2 = Calendar.getInstance();

		day2.add(Calendar.DATE,-day2.get(Calendar.DAY_OF_WEEK)+1);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		int strEDate = Integer.parseInt(dateFormat.format(eDate.getTime())); // 등록 및 수정일
		int startDate = Integer.parseInt(dateFormat.format(day1.getTime())); // 오늘 일자
		int preDate = Integer.parseInt(getPrevDate(dateFormat.format(day1.getTime()),1,"yyyyMMdd")); // 어제일자
		int endDate = Integer.parseInt(dateFormat.format(day2.getTime())); // 이번주 시작일
		int preWeekDate = Integer.parseInt(getPrevWeekDate(dateFormat.format(day2.getTime()),1,"yyyyMMdd")); //이전주 시작일

		if(startDate == strEDate) {
			diffStr = "T";

		}else if(strEDate == preDate) {
			diffStr = "Y";

		}else if(endDate <= strEDate) {
			diffStr = "TW";

		}else if(endDate > strEDate && preWeekDate <= strEDate) {
			diffStr = "LW";

		}else if(preWeekDate > strEDate) {
			diffStr = "OT"; //dateFormat.format(eDate.getTime()).substring(0, 4) + "-" + (Integer.parseInt(dateFormat.format(eDate.getTime()).substring(4, 6)) - 1);
		}

		return diffStr;
	}

	/**
	 * Date 스트링의 유효여부를 체크하고 유효한 Date 스트링을 반환
	 *
	 * @param yyyymmdd
	 * @return
	 */
	public static String getValidDate(String yyyymmdd) {
		String strYear = "";
		String strMonth = "";
		String strDay = "";
		int validMonthDay = 0;
		String validDate = "";

		if (yyyymmdd.length() == 8) {
			strYear = yyyymmdd.substring(0, 4);
			strMonth = yyyymmdd.substring(4, 6);
			strDay = yyyymmdd.substring(6, 8);

			validMonthDay = getLastDate(Integer.parseInt(strYear), Integer.parseInt(strMonth));

			if (Integer.parseInt(strDay) <= validMonthDay) {
				validDate = yyyymmdd;
			} else {
				validDate = strYear + strMonth + String.valueOf(validMonthDay);
			}

			return validDate;
		} else {
			return "";
		}
	}

	/**
	 * 오늘 날짜에서 지정한 날수를 계산한 날짜 반환
	 *
	 * @param iType 앞/뒤로 계산할 단위 (1:일 단위, 2:월 단위, 3:년 단위)
	 * @param i 앞/뒤로 증가/감소 시킬 수
	 * @param dFormat 날짜 포맷
	 */
	public static String getDateCla(int iType, int i, String dFormat) {
		if (i == 0)
			return getToday(dFormat);
		else {
			Calendar cal = Calendar.getInstance();

			if (iType == 2)
				cal.add(Calendar.MONTH, i); // 월 단위
			else if (iType == 3)
				cal.add(Calendar.YEAR, i); // 년 단위
			else
				cal.add(Calendar.DATE, i); // 일 단위

			SimpleDateFormat sdf = new SimpleDateFormat(dFormat);
			String sNewDate = sdf.format(cal.getTime());

			return sNewDate;
		}
	}
	
	public static String getTomorrow(String formatStr) {
		Calendar today = Calendar.getInstance ( );
		today.add ( Calendar.DATE, 1 );
		Date tomorrow = today.getTime ( );
		
		String format = formatStr;
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		SimpleDateFormat sdate = new SimpleDateFormat(format);
		return sdate.format(tomorrow);
	}
	
	
	
}