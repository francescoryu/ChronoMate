package ch.francescoryu.view.panels;

import ch.francescoryu.model.EventDate;
import util.CalendarUtil;

import javax.swing.*;
import java.awt.*;

public class DayEventArea
{
    private JLabel dateLabel;
    private JPanel dayEventPanel;

    public DayEventArea()
    {
        initPanel();
    }

    private void initPanel()
    {
        dateLabel = new JLabel();
        dateLabel.setBackground(Color.RED);
        dateLabel.setVerticalAlignment(SwingConstants.CENTER);
        dateLabel.setFont(CalendarUtil.getCalendarItemsFont(false));
        dayEventPanel = new JPanel();
        dayEventPanel.add(dateLabel);
    }

    public JPanel getPanel()
    {
        return dayEventPanel;
    }

    public void setLabelText(String text)
    {
        dateLabel.setText(text);
    }
}
