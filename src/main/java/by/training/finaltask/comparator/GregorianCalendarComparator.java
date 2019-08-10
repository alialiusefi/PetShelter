package by.training.finaltask.comparator;

import java.sql.Date;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;

/**
 * Comparator to compare 2 gregorian calendars using dates.
 */
public class GregorianCalendarComparator implements Comparator<GregorianCalendar> {
    @Override
    public int compare(GregorianCalendar o1, GregorianCalendar o2) {
        Date dateo1 = new Date(o1.get(Calendar.YEAR) - 1900,
                o1.get(Calendar.MONTH) + 1, o1.get(Calendar.DAY_OF_MONTH));
        Date dateo2 = new Date(o2.get(Calendar.YEAR) - 1900,
                o2.get(Calendar.MONTH) + 1, o2.get(Calendar.DAY_OF_MONTH));
        return dateo1.compareTo(dateo2);
    }
}
