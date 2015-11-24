package framework.auto.utils;

// All the util classes for handling DateTime
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static String defaultDateFormat = "MM/dd/yyyy";
	private static String defaultDateFormatOther = "dd/MM/yyyy";
	public static String REGION_US = "US";
	
	/**
	 * Returns a string date formatted as the defaultDateFormat: MM/dd/yyyy
	 * @param date
	 * @return the date as a String formatted as MM/dd/yyyy
	 */
	public static String getDefaultFormat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(defaultDateFormat);
		return sdf.format(date);
	}

	public static String getDateFormatted(Date date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}

	public static SimpleDateFormat getDefaultDateFormat() {
		return new SimpleDateFormat(defaultDateFormat);
	}

	public static SimpleDateFormat getDefaultDateFormat(String region) {
		if(region.equals(REGION_US))
			return new SimpleDateFormat(defaultDateFormat);
		else
			return new SimpleDateFormat(defaultDateFormatOther);
	}
	
	public static Date getLastDayOfMonth(){
		 Date today = new Date();  
	      Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(today);  

	        calendar.add(Calendar.MONTH, 1);  
	        calendar.set(Calendar.DAY_OF_MONTH, 1);  
	        calendar.add(Calendar.DATE, -1);  

	        Date lastDayOfMonth = calendar.getTime(); 
	        return lastDayOfMonth;
	}

	/**
	 * Retrieves a date n days into the future.
	 * @param numberOfDaysInFuture
	 * @return
	 */
	public static Date getFutureDate(int numberOfDaysInFuture) {
		Calendar cal = Calendar.getInstance();
		return getFutureDate(cal.getTime(), numberOfDaysInFuture);
	}
	public static String getFutureMonths(int numberOfMonthsInFuture){
		Calendar cal = Calendar.getInstance();
		cal.setTime(cal.getTime());
		cal.add(Calendar.MONTH, numberOfMonthsInFuture);
		return getDefaultFormat(cal.getTime());	
}

public static Date getFutureYear(int numberOfYearsInFuture){
	Calendar cal = Calendar.getInstance();
	cal.setTime(cal.getTime());
	cal.add(Calendar.YEAR, numberOfYearsInFuture);
	return cal.getTime();	
}

	public static Date getFutureDate(Date startingDate, int numberOfDaysInFuture) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startingDate);
		cal.add(Calendar.DATE, numberOfDaysInFuture);
		return cal.getTime();	
	}

	/**
	 * Retrieves a date n days into the future formatted with the default format
	 * @param numberOfDaysInFuture
	 * @return
	 */
	public static String getFutureDateFormatted(int numberOfDaysInFuture) {
		Date futureDate = getFutureDate(numberOfDaysInFuture);
		return getDefaultFormat(futureDate);
	}
	public static String getFutureDateFormatted(int numberOfDaysInFuture,String region) {
		Date futureDate = getFutureDate(numberOfDaysInFuture);
		return getDefaultDateFormat(region).format(futureDate);
	}

	/**
	 * Retrieves the current time as a string in the format specified
	 * @param dateFormat
	 * @return
	 */
	public static String now(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}

	public static String now() {
		Calendar cal = Calendar.getInstance();
		return getDefaultDateFormat().format(cal.getTime());
	}

	public static String today(String region) {
		Calendar cal = Calendar.getInstance();
		return getDefaultDateFormat(region).format(cal.getTime());
	}
	
	public static String getTodaysDate(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String getNextWeek(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_MONTH, 1);
		return getDefaultFormat(cal.getTime());
	}

	public static String getNextMonthAndWeek(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_MONTH, 1);
		cal.add(Calendar.MONTH, 1);
		return getDefaultFormat(cal.getTime());
	}

	public static String getNextWeek(String region){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_MONTH, 1);
		return getDefaultDateFormat(region).format(cal.getTime());
	}

	public static String getNextMonthAndWeek(String region){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_MONTH, 1);
		cal.add(Calendar.MONTH, 1);
		return getDefaultDateFormat(region).format(cal.getTime());
	}
	
	public static String getFormattedTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("ssSSS");
		Calendar cal = Calendar.getInstance();
		return sdf.format(cal.getTime());
	}
	
	/**
	 * Returns a string date formatted as the defaultDateFormat: h:m:a
	 * @return the date as a String formatted as h:a:m
	 */
	public static String getClassStartEndDate() {
		String mins = null;
		String end_mins=null;
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("m");
		int minutes= Integer.parseInt(sdf.format(now));
		if(minutes>=0 && minutes<=14){
			mins="00";
		}else if(minutes>=15 && minutes<=29){
			mins="15";
		}else if(minutes>=30 && minutes<=44){
			mins="30";
		}else if(minutes>=45 && minutes<=59){
			mins="45";
		}
		
		SimpleDateFormat hours = new SimpleDateFormat("h");
		String hours_sdf =hours.format(now);
String end_hours_sdf=hours.format(now);
		
		if(mins.equals("00")){
			end_mins="15";
		}else if(mins.equals("15")){
			end_mins="30";
		}else if(mins.equals("30")){
			end_mins="45";
		}else if(mins.equals("45")){
			end_mins="00";
			if(end_hours_sdf.equals("12")){
				end_hours_sdf="1";
			}else{
				int hour_sdf = Integer.parseInt(hours_sdf)+1;
				end_hours_sdf = Integer.toString(hour_sdf);
			}
		}
		SimpleDateFormat ampm = new SimpleDateFormat("a");
		String ampm_sdf =ampm.format(now);
		return hours_sdf+":"+ampm_sdf+":"+mins+":"+end_mins+":"+end_hours_sdf;
	}
}