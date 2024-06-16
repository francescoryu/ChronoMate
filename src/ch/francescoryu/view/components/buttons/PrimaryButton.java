package ch.francescoryu.view.components.buttons;

import ch.francescoryu.util.CalendarUtil;

import javax.swing.*;
import java.awt.*;

public class PrimaryButton extends JButton
{
    public PrimaryButton(String text, int fontSize)
    {
        super(text);
        init(fontSize);
    }

    private void init(int fontSize)
    {
        this.setFont(CalendarUtil.getCalendarItemsFont(true, fontSize));
        this.setForeground(Color.decode("#e3e3e3"));
        this.setBorderPainted(false);
        this.setOpaque(true);
        this.setBackground(Color.decode("#42537a"));
    }
}
