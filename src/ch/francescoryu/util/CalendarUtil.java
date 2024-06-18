package ch.francescoryu.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.Objects;

public class CalendarUtil
{
    public static final String[] MONTHS =
            {
                    "January", "February", "March", "April", "May", "June",
                    "July", "August", "September", "October", "November", "December"
            };

    public static Font getCalendarItemsFont(boolean isBold, int fontSize)
    {
        if (isBold)
        {
            return new Font("", Font.BOLD, fontSize);
        }

        return new Font("", Font.PLAIN, fontSize);
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

    public static void addIconToButton(JButton button, String path)
    {
        try
        {
            Image img = ImageIO.read(Objects.requireNonNull(CalendarUtil.class.getResource(path)));
            button.setIcon(new ImageIcon(img));
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    public static void addToolTipToComponent(JComponent component, String text)
    {
        component.setToolTipText(text);
    }
}
