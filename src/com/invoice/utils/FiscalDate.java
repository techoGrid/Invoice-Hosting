package com.invoice.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*Author: Sumukh
 * 
 * Date:22/2/2016
 *
 *Desc: To check whether Date and month comes within financial year or not*/

//Class FiscalDate starts
public class FiscalDate

{
	//private static final int FIRST_FISCAL_MONTH = Calendar.MARCH;  
	private static final int FIRST_FISCAL_MONTH = Calendar.APRIL;

	private Calendar calendarDate;

	public FiscalDate(Calendar calendarDate) {
		this.calendarDate = calendarDate;
	}

	public int getFiscalMonth() {
		int month = calendarDate.get(Calendar.MONTH);
		int result = ((month - FIRST_FISCAL_MONTH - 1) % 12) + 1;
		if (result < 0) {
			result += 12;
		}
		return result;
	}

	public int getFiscalYear() {
		int month = calendarDate.get(Calendar.MONTH);
		int year = calendarDate.get(Calendar.YEAR);
		return (month >= FIRST_FISCAL_MONTH) ? year : year - 1;
	}

	public static String displayFinancialDate(Calendar calendar) {
		FiscalDate fiscalDate = new FiscalDate(calendar);
		int year = fiscalDate.getFiscalYear();

		SimpleDateFormat df = new SimpleDateFormat("yy");
		String year1 = String.valueOf(year + 1);
		String fiscalYear = "";
		try {
			// printing the financial year
			//System.out.println("Fiscal Years : " + year + "-" + df.format(df.parse(year1)));
			fiscalYear = year + "-" + df.format(df.parse(year1));
			// printing the financial month
			//System.out.println("Fiscal Month : " + fiscalDate.getFiscalMonth());
			//System.out.println(" ");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fiscalYear;
	}

	// financial year drop down
	public static List<String> getYOPList() {
		int currentYear = Integer.parseInt(new java.text.SimpleDateFormat(
				"yyyy").format(new java.util.Date()));

		List<String> YOPList = new ArrayList<String>();
		for (int year = currentYear; year >= 1950; year--) {
			YOPList.add(String.valueOf(year) + "-"
					+ (String.valueOf(year + 1).substring(2)));
		}
		return YOPList;

	}

}