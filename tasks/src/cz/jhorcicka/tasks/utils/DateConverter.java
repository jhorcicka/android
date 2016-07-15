package cz.jhorcicka.tasks.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    private static final int SECOND = 1000;
    private static final int MINUTE = SECOND * 60;
    private static final int HOUR = MINUTE * 60;
    private static final int DAY = HOUR * 24;
    private static final String DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";
    private static DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    public static String getNow() {
        return dateToString(new Date());
    }

    public static String getLengthString(long timeInMiliseconds) {
        long days = timeInMiliseconds / DAY;
        timeInMiliseconds -= days * DAY;
        long hours = timeInMiliseconds / HOUR;
        timeInMiliseconds -= hours * HOUR;
        long minutes = timeInMiliseconds / MINUTE;
        timeInMiliseconds -= minutes * MINUTE;
        long seconds = timeInMiliseconds / SECOND;
        timeInMiliseconds -= seconds * SECOND;

        return days + "d " + hours + "h " + minutes + "m ";
    }

    public static String dateToString(Date date) {
        return formatter.format(date);
    }

    public static Date stringToDate(String string) {
        Date date = null;

        try {
            date = formatter.parse(string);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return date;
    }
}
