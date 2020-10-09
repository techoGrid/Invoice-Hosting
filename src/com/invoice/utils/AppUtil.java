package com.invoice.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class AppUtil {

	public static String currentTime() {
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Calendar calendarTime = Calendar.getInstance();
		String time = timeFormat.format(calendarTime.getTime());
		return time;
	}

	public static String currentDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Calendar calendarDate = Calendar.getInstance();
		String date = dateFormat.format(calendarDate.getTime());
		return date;
	}
	
	public static String currentDates() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendarDate = Calendar.getInstance();
		String date = dateFormat.format(calendarDate.getTime());
		return date;
	}


	public static String currentDateWithClash() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendarDate = Calendar.getInstance();
		String date = dateFormat.format(calendarDate.getTime());
		return date;
	}

	public static String currentYear() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Calendar calendarDate = Calendar.getInstance();
		String year = dateFormat.format(calendarDate.getTime());
		return year;
	}

	public static String currentMonthNameAndYear() {
		DateFormat dateFormat = new SimpleDateFormat("MMMMM, yyyy");
		Calendar calendarDate = Calendar.getInstance();
		String currentMonthNameAndYear = dateFormat.format(calendarDate.getTime());
		return currentMonthNameAndYear;
	}

	public static String currentMonthInDigits() {
		DateFormat dateFormat = new SimpleDateFormat("MM");
		Calendar calendarDate = Calendar.getInstance();
		String currentMonth = dateFormat.format(calendarDate.getTime());
		return currentMonth;
	}

	public static String currentDayOfMonth() {
		DateFormat dateFormat = new SimpleDateFormat("dd");
		Calendar calendarDate = Calendar.getInstance();
		String day = dateFormat.format(calendarDate.getTime());
		return day;
	}

	/*public static String getCurrentDayOfMonthSuffix() {
	int n = Integer.parseInt(currentDayOfMonth()) ;
	System.out.println("CurrentDayOfMonth: "+n);
	if (n >= 1 && n <= 31) {
		if (n >= 11 && n <= 13) {
			return n+"th";
		}
		switch (n % 10) {
		case 1:
			return n+"st";
		case 2:
			return n+"nd";
		case 3:
			return n+"rd";
		default:
			return n+"th";
		}
	}else{
		return currentDayOfMonth();
	}
  }*/
	
	public static String getCurrentDayOfMonthSuffix() {
		int n = Integer.parseInt(currentDayOfMonth());
		System.out.println("CurrentDayOfMonth: " + n);
		if (n >= 1 && n <= 31) {
			if (n >= 11 && n <= 13) {
				return "th";
			}
			switch (n % 10) {
			case 1:
				return "st";
			case 2:
				return "nd";
			case 3:
				return "rd";
			default:
				return "th";
			}
		} else {
			return currentDayOfMonth();
		}
	}

	public static String getDayOfMonthSuffix(String date) {
		int n = Integer.parseInt(dayOfMonth(date));
		System.out.println("DayOfMonth: " + n);
		if (n >= 1 && n <= 31) {
			if (n >= 11 && n <= 13) {
				return "th";
			}
			switch (n % 10) {
			case 1:
				return "st";
			case 2:
				return "nd";
			case 3:
				return "rd";
			default:
				return "th";
			}
		} else {
			return dayOfMonth(date);
		}
	}

	public static String currentMonthInWords() {
		DateFormat getMonth = new SimpleDateFormat("MMMM");
		String monthInWords = getMonth.format(new Date());
		return monthInWords;
	}

	public static String previousMonthInWords() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		String previousMonthInWords = new SimpleDateFormat("MMMM").format(cal.getTime());
		return previousMonthInWords;
	}
	
	
	public static String addDays(int days, String endDate)
	{
		Calendar cal = Calendar.getInstance();
		Date endDate1 = null;
		try {
			endDate1 = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.setTime(endDate1);
		cal.add(Calendar.MONTH, 1);
		String nextMonths = new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime());
		
		
		return nextMonths;
	   
	}
	
	

	public static String previousYear() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		String previousYear = new SimpleDateFormat("yyyy").format(cal.getTime());
		return previousYear;
	}

	public static String lastDateOfCurrentMonth() {
		java.util.Calendar calender = java.util.Calendar.getInstance();
		calender.set(java.util.Calendar.DATE, calender.getActualMaximum(java.util.Calendar.DATE));
		Date lastDayOfMonth = calender.getTime();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String lastDateMonth = format.format(lastDayOfMonth);
		return lastDateMonth;
	}

	public static String lastDayOfCurrentMonth() {
		java.util.Calendar calender = java.util.Calendar.getInstance();
		calender.set(java.util.Calendar.DATE, calender.getActualMaximum(java.util.Calendar.DATE));
		Date lastDayOfMonth = calender.getTime();
		SimpleDateFormat format = new SimpleDateFormat("dd");
		String lastDateMonth = format.format(lastDayOfMonth);
		return lastDateMonth;
	}

	public static boolean isCurrentDateWithInRange(String date, String start, String end) {
		try {
			Date currentDate = new SimpleDateFormat("dd-MM-yyyy").parse(date);
			Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(start);
			Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(end);
			// flag= testDate.after(startDate) && testDate.before(endDate);
			return currentDate.compareTo(startDate) >= 0 && currentDate.compareTo(endDate) <= 0;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean isCurrentDateWithInRanges(String date, String start, String end) {
		try {
			Date currentDate = new SimpleDateFormat("dd-MM-yyyy").parse(date);
			//Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(start);
			Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(end);
			// flag= testDate.after(startDate) && testDate.before(endDate);
			return currentDate.compareTo(endDate) <= 0;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	


	public static String getFiveDigitsWithZero(int number) {
		number++;
		DecimalFormat df = new DecimalFormat("00000");
		String numberAsString = df.format(number);
		return numberAsString;
	}

	public static String changeDateFormat(String mydate) {
		Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(mydate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(date);
		return dateFormat;
	}

	public static String monthInWordsWithDate(String mydate) {
		Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(mydate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String monthInWords = new SimpleDateFormat("MMMM").format(date);
		;
		return monthInWords;
	}

	public static String monthInDigits(String mydate) {
		Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(mydate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String monthInDigits = new SimpleDateFormat("MM").format(date);
		return monthInDigits;
	}

	public static String dayOfMonth(String mydate) {
		Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(mydate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String dayOfMonth = new SimpleDateFormat("dd").format(date);
		return dayOfMonth;
	}

	public static String yearWithDate(String mydate) {
		Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(mydate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String yearWithDate = new SimpleDateFormat("yyyy").format(date);
		return yearWithDate;
	}

	public static String financialYear() {
		int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
		String yearInString = String.valueOf(year);
		int currentMonth = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1;
		String date = String.valueOf(currentMonth) + "-" + yearInString;
		DateFormat df = new SimpleDateFormat("MM-yyyy");
		java.util.Calendar cal = java.util.Calendar.getInstance();
		try {
			cal.setTime(df.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String financialYear = FiscalDate.displayFinancialDate(cal);
		return financialYear;
	}

	public static String financialYearWithAnyDate(String mydate) {
		Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(mydate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String yearWithDate = new SimpleDateFormat("yyyy").format(date);
		String month = new SimpleDateFormat("MM").format(date);

		String dateInStr = month + "-" + yearWithDate;
		DateFormat df = new SimpleDateFormat("MM-yyyy");
		java.util.Calendar cal = java.util.Calendar.getInstance();
		try {
			cal.setTime(df.parse(dateInStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String financialYear = FiscalDate.displayFinancialDate(cal);
		return financialYear;
	}

	public static String prevFinancialYear() {
		int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
		String yearInString = String.valueOf(year);
		int currentMonth = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
		String date = String.valueOf(currentMonth) + "-" + yearInString;
		DateFormat df = new SimpleDateFormat("MM-yyyy");
		java.util.Calendar cal = java.util.Calendar.getInstance();
		try {
			cal.setTime(df.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String financialYear = FiscalDate.displayFinancialDate(cal);
		return financialYear;
	}
	
	public static String changeDateFormatToClash(String mydate) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(mydate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String dateFormat = new SimpleDateFormat("dd/MM/yyyy").format(date);
		return dateFormat;
	}

	public static String monthNameAndYear(String mydate) {
		Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(mydate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String monthNameAndYear = new SimpleDateFormat("MMMMM, yyyy").format(date);
		return monthNameAndYear;
	}

	public static boolean isCurrentDateCompareWithEndDate(String date, String end) {
		try {
			Date currentDate = new SimpleDateFormat("dd-MM-yyyy").parse(date);
			Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(end);
			return currentDate.compareTo(endDate) <= 0;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean isCurrentDateCompareWithStartDate(String date, String start) {
		try {
			Date currentDate = new SimpleDateFormat("dd-MM-yyyy").parse(date);
			Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(start);
			return currentDate.compareTo(startDate) < 0;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("static-access")
	public static String firstDayOfMonthWithCustomDate(String mydate) {
		Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(mydate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.util.Calendar calender = java.util.Calendar.getInstance();
		calender.setTime(date);
		calender.set(calender.DAY_OF_MONTH, 1);
		Date firstDayOfMonth = calender.getTime();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String firstDayOfMonthWithCustomDate = format.format(firstDayOfMonth);
		return firstDayOfMonthWithCustomDate;
	}
	
	public static String decryptParticularsAmount(String particularsAmount) {
		String[] particularsAmountArray = particularsAmount.split(",");
		String[] decryptedParticularsAmount = new String[particularsAmountArray.length];
		for (int i = 0; i < particularsAmountArray.length; i++) {
			decryptedParticularsAmount[i] = Crypto.DecryptText(particularsAmountArray[i]);
		}
		return Arrays.toString(decryptedParticularsAmount).substring(1,
				Arrays.toString(decryptedParticularsAmount).length() - 1).replace(" ", "");
	}
	
	public static String currentDateWithTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Calendar calendarDate = Calendar.getInstance();
		String date = dateFormat.format(calendarDate.getTime());
		String time = timeFormat.format(calendarDate.getTime());
		return date+" "+time;
	}

}
