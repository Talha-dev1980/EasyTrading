package com.crypto.trading.bot.easytrading.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormat {
    public Date millisToTime(long millis) {

       /* long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        String time = days + ":" + hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;
*/
        // we create instance of the Date and pass milliseconds to the constructor
        Date res = new Date(millis);
        // now we format the res by using SimpleDateFormat


        // return "" + obj.format(res);
        return res;
    }

    public String twoDatesInterval(Long date1Millis, Long date2Millis) {
        Date date1 = millisToTime(date1Millis);
        Date date2 = millisToTime(date2Millis);
        DateFormat dateFormat = null;
        if (date1.getYear() == date2.getYear()) {
            dateFormat = new SimpleDateFormat("dd MMM HH:mm");
        } else {
            dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
        }
        return dateFormat.format(date1) + "-" + dateFormat.format(date2);
    }
}
