package miscUtil;

import java.util.Calendar;

public class EDateTime {
	
	public static long getTimeLong() { return Calendar.getInstance().getTimeInMillis(); }
	
	//--------------
	
	public static String getDate() { return getMonth() + "/" + getDay() + "/" + getYear(); }
	public static String getTime() {
		String hour = getHour() + "";
		String min = getMinute() + "";
		String sec = getSecond() + "";
		
		hour = (hour.length() == 1) ? "0" + hour : hour;
		min = (min.length() == 1) ? "0" + min : min;
		sec = (sec.length() == 1) ? "0" + sec : sec;
		
		return hour + ":" + min + ":" + sec;
	}
	
	//--------------
	// Date Methods
	//--------------
	
	public static int getDay() { return Calendar.getInstance().get(Calendar.DAY_OF_MONTH); }
	public static int getMonth() { return Calendar.getInstance().get(Calendar.MONTH) + 1; }
	public static int getYear() { return Calendar.getInstance().get(Calendar.YEAR); }
	
	public static EDate parseDate(String dateString) { return EDate.of(dateString); }
	public static EDate parseDate(String dateString, EDate.EDateFormat format) { return EDate.of(dateString, format); }
	
	//--------------
	// Time Methods
	//--------------
	
	public static int getSecond() { return Calendar.getInstance().get(Calendar.SECOND); }
	public static int getMinute() { return Calendar.getInstance().get(Calendar.MINUTE); }
	public static int getHour() {
		int hour = Calendar.getInstance().get(Calendar.HOUR);
		return (hour == 0) ? 12 : hour;
	}
	
}
