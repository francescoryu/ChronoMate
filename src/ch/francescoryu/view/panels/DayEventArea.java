package ch.francescoryu.view.panels;

import util.CalendarUtil;

import javax.swing.*;
import java.awt.*;

public class DayEventArea
{
    private JLabel dateLabel;
    private JPanel dayEventPanel;

    public DayEventArea()
    {
        initComponents();
        initPanel();
    }

    private void initPanel()
    {
        dayEventPanel = new JPanel();
        dayEventPanel.add(dateLabel);
    }

    private void initComponents()
    {
        dateLabel = new JLabel();
        dateLabel.setBackground(Color.RED);
        dateLabel.setVerticalAlignment(SwingConstants.CENTER);
        dateLabel.setFont(CalendarUtil.getCalendarItemsFont(false));
    }

    public JPanel getPanel()
    {
        return dayEventPanel;
    }

}
