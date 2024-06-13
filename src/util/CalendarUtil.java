package util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.reflect.Array;

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

    public static JLabel createTitleLabel(String text)
    {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setBorder(new EmptyBorder(20, 20, 20, 20));
        label.setFont(new Font("", Font.BOLD, 35));
        return label;
    }
}
