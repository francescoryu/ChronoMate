package util;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;

public class CalendarUtil
{
    public static final String[] MONTHS =
            {
                    "January", "February", "March", "April", "May", "June",
                    "July", "August", "September", "October", "November", "December"
            };

    public static Font getCalendarItemsFont(boolean isBold)
    {
        if (isBold)
        {
            return new Font("", Font.BOLD, 20);
        }

        return new Font("", Font.PLAIN, 20);
    }

    public static String getMonthWithIndex(int i)
    {
        return (String) Array.get(MONTHS, i);
    }
}
