package fr.univ_nantes.bigfollow.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class Util {

    public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    public static Date stringToDate(String dateAsString) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.FRANCE);
        Date date = null;

        try {
            date = df.parse(dateAsString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String dateToString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT, Locale.FRANCE);
        return df.format(date);
    }
}
