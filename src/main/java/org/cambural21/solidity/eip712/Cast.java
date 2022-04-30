package org.cambural21.solidity.eip712;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class Cast {

    private Cast(){}

    public static BigInteger toDateBigInteger(String dateInString){
        Date date = toDate(dateInString);
        return date != null?BigInteger.valueOf(date.getTime()):BigInteger.ZERO;
    }

    private static Date toDate(String dateInString){
        Date date = null;
        if(dateInString != null && !dateInString.isEmpty()){
            SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.ENGLISH);
            formatter.setTimeZone(TimeZone.getTimeZone("America/New_York"));
            try{
                date = formatter.parse(dateInString);
            }catch (Exception e){
                e.printStackTrace();
                date = null;
            }
        }
        return date;
    }

}
