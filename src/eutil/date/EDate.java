package eutil.date;

import eutil.strings.StringUtil;
import java.sql.Date;

/**
 * A simplified calendar date library.
 * 
 * @author Hunter Bragg
 * @since 1.0.1
 */
public class EDate implements Comparable<EDate> {

	private EDateFormat format;
	private final int month;
	private final int day;
	private final int year;
	
	//--------------
	// Constructors
	//--------------
	
	public EDate() { this(EDateTime.getMonth(), EDateTime.getDay(), EDateTime.getYear(), EDateFormat.MDY); }
	public EDate(EDateFormat formatIn) { this(EDateTime.getMonth(), EDateTime.getDay(), EDateTime.getYear(), formatIn); }
	public EDate(int monthIn, int dayIn, int yearIn) { this(monthIn, dayIn, yearIn, EDateFormat.MDY); }
	public EDate(int monthIn, int dayIn, int yearIn, EDateFormat formatIn) {
		format = (formatIn != null) ? formatIn : EDateFormat.MDY;
		month = monthIn;
		day = dayIn;
		year = yearIn;
	}
	
	public EDate(String dateString) { this(dateString, EDateFormat.MDY); }
	public EDate(String dateString, EDateFormat formatIn) {
		format = (formatIn != null) ? formatIn : EDateFormat.MDY;
		
		int m = EDateTime.getMonth();
		int d = EDateTime.getDay();
		int y = EDateTime.getYear();
		
		try {
			Character c = StringUtil.testCharR(dateString.charAt(2), '/', '\\', '-', '|', '_', '.');
			if (c != null) {
				String[] parts = dateString.split(c + "");
				
				switch (formatIn) {
				case MDY:
					m = Integer.parseInt(parts[0]);
					d = Integer.parseInt(parts[1]);
					break;
				case DMY:
					d = Integer.parseInt(parts[0]);
					m = Integer.parseInt(parts[1]);
					break;
				}
				
				y = Integer.parseInt(parts[2]);
			}
		}
		catch (Exception e) {}
		
		month = m;
		day = d;
		year = y;
	}
	
	//-----------
	// Overrides
	//-----------
	
	@Override
	public int compareTo(EDate d) {
		if (d != null) {
			if (year >= d.year) { return -1; }
			if (month >= d.month) { return -1; }
			if (day >= d.day) { return -1; }
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return (format == EDateFormat.MDY) ? (month + "/" + day + "/" + year) : (day + "/" + month + "/" + year);
	}
	
	//---------
	// Methods
	//---------
	
	public String getDayName() {
		return StringUtil.capitalFirst(Date.valueOf(year + "-" + month + "-" + day).toLocalDate().getDayOfWeek().toString().toLowerCase());
	}
	
	public String getMonthName() {
		switch (month) {
		case 1: return "January";
		case 2: return "February";
		case 3: return "March";
		case 4: return "April";
		case 5: return "May";
		case 6: return "June";
		case 7: return "July";
		case 8: return "August";
		case 9: return "September";
		case 10: return "October";
		case 11: return "November";
		case 12: return "December";
		default: return "NULL";
		}
	}
	
	/** Returns true if this EDate object represents the current real day. */
	public boolean isToday() {
		return (month == EDateTime.getMonth()) && (day == EDateTime.getDay()) && (year == EDateTime.getYear());
	}
	
	public EDate convertTo(EDateFormat formatIn) {
		return new EDate(day, month, year, (formatIn != null) ? formatIn : EDateFormat.MDY);
	}
	
	public EDate setFormat(EDateFormat formatIn) {
		format = (formatIn != null) ? formatIn : EDateFormat.MDY;
		return this;
	}
	
	//---------
	// Getters
	//---------
	
	public int getDay() { return day; }
	public int getMonth() { return month; }
	public int getYear() { return year; }
	/** Returns whether this date is represented as a 'MM/DD/YYYY' or 'DD/MM/YYYY' format. */
	public EDateFormat getFormat() { return format; }
	
	//----------------
	// Static Methods
	//----------------
	
	public static EDate of(String dateString) { return new EDate(dateString); }
	public static EDate of(String dateString, EDateFormat format) { return new EDate(dateString, format); }
	
	//------------------
	// EDateFormat Enum
	//------------------
	
	public static enum EDateFormat {
		/** Month-Day-Year */
		MDY,
		/** Day-Month-Year */
		DMY;
	}
	
}
