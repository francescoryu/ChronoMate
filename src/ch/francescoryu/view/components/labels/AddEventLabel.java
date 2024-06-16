package ch.francescoryu.view.components.labels;

import ch.francescoryu.util.CalendarUtil;

import javax.swing.*;

public class AddEventLabel extends JLabel
{
    public AddEventLabel(String title)
    {
        super(title);
        init();
    }

    private void init()
    {
        this.setFont(CalendarUtil.getCalendarItemsFont(true, 15));
    }
}
