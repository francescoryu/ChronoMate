package ch.francescoryu.view.panels;

import util.CalendarUtil;
import util.DateChangedListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarArea
{
    private int currentYear;
    private int currentMonth;
    private int todayDay;
    private int todayMonth;
    private int todayYear;
    private JComboBox<Integer> yearComboBox;
    private JComboBox<String> monthComboBox;

    private JPanel calendarPanel;
    private JPanel contentPanel;

    private DateChangedListener dateChangedListener;

    public CalendarArea(DateChangedListener dateChangedListener)
    {
        this.dateChangedListener = dateChangedListener;
        initCalendarPanel();
    }

    private void initCalendarPanel()
    {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.setLayout(new FlowLayout());

        yearComboBox = new JComboBox<>();
        yearComboBox.setFont(CalendarUtil.getCalendarItemsFont(false));
        yearComboBox.setBackground(null);
        for (int year = 1900; year <= 2100; year++)
        {
            yearComboBox.addItem(year);
        }

        String[] months = CalendarUtil.MONTHS;
        monthComboBox = new JComboBox<>(months);
        monthComboBox.setFont(CalendarUtil.getCalendarItemsFont(false));
        monthComboBox.setBackground(null);

        JButton todayButton = new JButton("Today");
        todayButton.setFont(CalendarUtil.getCalendarItemsFont(false));
        todayButton.setBackground(null);
        todayButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setCalendarToCurrentDate();
                updateCalendar();
            }
        });

        topPanel.add(yearComboBox);
        topPanel.add(monthComboBox);
        topPanel.add(todayButton);

        contentPanel = new JPanel(new GridLayout(0, 7));

        calendarPanel = new JPanel();
        calendarPanel.setLayout(new BorderLayout());
        calendarPanel.add(topPanel, BorderLayout.NORTH);
        calendarPanel.add(contentPanel, BorderLayout.CENTER);

        setCalendarToCurrentDate();
        initListeners();
    }

    private void initListeners()
    {
        monthComboBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                currentMonth = monthComboBox.getSelectedIndex();
                updateCalendar();
            }
        });

        yearComboBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                currentYear = (int) yearComboBox.getSelectedItem();
                updateCalendar();
            }
        });
    }

    private void setCalendarToCurrentDate()
    {
        Calendar calendar = new GregorianCalendar();
        todayDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);

        todayMonth = currentMonth;
        todayYear = currentYear;

        monthComboBox.setSelectedIndex(currentMonth);
        yearComboBox.setSelectedItem(currentYear);

        updateCalendar();
    }

    private void updateCalendar()
    {
        contentPanel.removeAll();

        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : days)
        {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(CalendarUtil.getCalendarItemsFont(true));
            contentPanel.add(dayLabel);
        }

        Calendar calendar = new GregorianCalendar(currentYear, currentMonth, 1);
        int startDay = calendar.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i < startDay; i++)
        {
            contentPanel.add(new JLabel());
        }

        for (int day = 1; day <= daysInMonth; day++)
        {
            JLabel label = new JLabel(String.valueOf(day));

            JButton dayButton = new JButton();
            dayButton.setBorder(new EmptyBorder(10, 10, 10, 10));
            dayButton.setLayout(new BorderLayout());
            dayButton.add(label, BorderLayout.NORTH);

            if (day == todayDay && currentMonth == todayMonth && currentYear == todayYear)
            {
                label.setBackground(Color.decode("#e3e3e3"));
                label.setOpaque(true);
            }

            dayButton.setFont(CalendarUtil.getCalendarItemsFont(false));

            int finalDay = day;
            dayButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    dateChangedListener.dateChanged(finalDay, CalendarUtil.getMonthWithIndex(currentMonth), currentYear);
                }
            });

            contentPanel.add(dayButton);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public JPanel getPanel()
    {
        return calendarPanel;
    }
}
