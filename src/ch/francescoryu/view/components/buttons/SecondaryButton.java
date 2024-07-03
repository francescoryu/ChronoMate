package ch.francescoryu.view.components.buttons;

import ch.francescoryu.util.CalendarUtil;

import javax.swing.*;
import java.awt.*;

public class SecondaryButton extends JButton
{
    public SecondaryButton(String text, int fontSize)
    {
        super(text);
        init(fontSize);
    }

    private void init(int fontSize)
    {
        this.setFont(CalendarUtil.getCalendarItemsFont(true, fontSize));
        this.setBorderPainted(false);
        this.setOpaque(true);
        this.setBackground(Color.decode("#b6b6b8"));
    }
}
