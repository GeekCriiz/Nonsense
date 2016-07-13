package utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UsefulMethods {
	
	public static void main(String[] args) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date dateAfter;
		Date dateBefore;
		try {
			dateAfter = dateFormat.parse("04/04/2016 12:00");
			checkValidDateWithActualDate(dateAfter);
			dateBefore = dateFormat.parse("04/02/2016 12:00");
			checkValidDateWithActualDate(dateBefore);
		} catch(ParseException ex){
			ex.printStackTrace();
		}


	}
	
	public static boolean checkValidDateWithActualDate(Date date1) {
		boolean result;
		String stringActualDate;
		Date dateActual;
		
		result = false;
		
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			//get current date time with Date()
			dateActual = new Date();
			stringActualDate = dateFormat.format(dateActual);
			System.out.println(stringActualDate);
			dateActual = dateFormat.parse(stringActualDate);
			
            if(date1.after(dateActual)){
                System.out.println("Date is after actual date");
                result = true;
            }

            if(date1.before(dateActual)){
                System.out.println("Date is before actual date");
                result = false;
            }

            if(date1.equals(dateActual)){
                System.out.println("Date is equal actual date");
                result = true;
            }

		} catch(ParseException ex){
			ex.printStackTrace();
			result = false;
		}
		
		return result;
		
	}
	
	public static boolean checkValidTwoDates(Date date1, Date date2) {
		boolean result;
		
		result = false;
			
        if(date1.after(date2)){
        	System.out.println("Date is after date2");
            result = true;
        }

        if(date1.before(date2)){
            System.out.println("Date is before date2");
            result = false;
        }

        if(date1.equals(date2)){
            System.out.println("Date is equal date2");
            result = true;
        }
		
		return result;
		
	}
	
	public static boolean checkValidDateWithRangeOfDates(Date date1, Date date2, Date date3) {
		boolean result;
		
		result = false;
			
        if(date1.after(date2) && date1.before(date3)){
        	System.out.println("Date is between date2 and date3");
            result = true;
        } else {
        	System.out.println("Date is not between date2 and date3");
        	result = false;
        }


		
		return result;
		
	}

}
