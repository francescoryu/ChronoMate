package ch.francescoryu.view.components.buttons;

import ch.francescoryu.util.CalendarUtil;

import javax.swing.*;
import java.awt.*;

public class SecondaryButton extends JButton
{
    public SecondaryButton(String text)
    {
        super(text);
        init();
    }

    private void init()
    {
        this.setFont(CalendarUtil.getCalendarItemsFont(true, 15));
        this.setBorderPainted(false);
        this.setOpaque(true);
        this.setBackground(Color.decode("#b6b6b8"));
    }
}
