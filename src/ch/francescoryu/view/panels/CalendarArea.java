package ch.francescoryu.view.panels;

import ch.francescoryu.model.EventDate;
import ch.francescoryu.model.EventDay;
import ch.francescoryu.model.Events;
import util.CalendarUtil;
import util.DateChangedListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

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

    private final DateChangedListener dateChangedListener;
    private Events events;

    private ArrayList<Integer> dayList;
    private ArrayList<Integer> monthList;
    private ArrayList<Integer> yearList;

    public CalendarArea(DateChangedListener dateChangedListener, Events events)
    {
        this.dateChangedListener = dateChangedListener;
        this.events = events;
        initLists();
        initCalendarPanel();
    }

    private void initLists()
    {
        dayList = new ArrayList<>();
        monthList = new ArrayList<>();
        yearList = new ArrayList<>();
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
            yearList.add(year);
        }

        for (int month = 1; month <= 12; month++)
        {
            monthList.add(month);
        }

        for (int day = 1; day <= 31; day++)
        {
            dayList.add(day);
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
        contentPanel.setBackground(null);

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
            JLabel emptyLabel = new JLabel();
            contentPanel.add(emptyLabel);
        }

        for (int day = 1; day <= daysInMonth; day++)
        {
            JLabel label = new JLabel(String.valueOf(day));
            label.setFont(new Font("", Font.PLAIN, 12));

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(0, 1));
            buttonPanel.setBorder(null);
            buttonPanel.setOpaque(true);
            buttonPanel.setBackground(new Color(0, 0, 0, 0));

            for (EventDate eventDate : events.getEventDateList())
            {
                if (eventDate.getMonth() == (currentMonth + 1) && eventDate.getYear() == currentYear && eventDate.getDay() == day)
                {
                    for (EventDay eventDay : eventDate.getEventDayList())
                    {

                        JLabel eventLabel = new JLabel(eventDay.getEvent());
                        eventLabel.setOpaque(true);
                        eventLabel.setBackground(Color.decode("#a1c9e3"));
                        buttonPanel.add(eventLabel);
                    }
                }
            }

            JButton dayButton = new JButton();
            dayButton.setBackground(null);
            dayButton.setLayout(new BorderLayout());
            dayButton.add(label, BorderLayout.NORTH);
            dayButton.add(buttonPanel, BorderLayout.CENTER);

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
                    dateChangedListener.dateChanged(finalDay, currentMonth + 1, currentYear);
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
