package ch.francescoryu.view.panels;

import ch.francescoryu.model.EventModel;
import ch.francescoryu.model.Events;
import util.CalendarUtil;
import util.DateChangedListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;

public class CalendarArea
{
    private static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
    private static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);

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

    public CalendarArea(DateChangedListener dateChangedListener, Events events)
    {
        this.dateChangedListener = dateChangedListener;
        this.events = events;
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

        GridLayout gridLayout = new GridLayout(0, 7);
        gridLayout.setHgap(1);
        gridLayout.setVgap(1);

        contentPanel = new JPanel(gridLayout);
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

        populateDaysOfWeek();

        Calendar calendar = new GregorianCalendar(currentYear, currentMonth, 1);
        int startDay = calendar.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        populateEmptyLabels(startDay);

        populateDaysWithEvents(daysInMonth);

        contentPanel.revalidate();
        contentPanel.repaint();
    }


    private void populateDaysOfWeek()
    {
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : days)
        {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(CalendarUtil.getCalendarItemsFont(true));
            contentPanel.add(dayLabel);
        }
    }

    private void populateEmptyLabels(int startDay)
    {
        for (int i = 1; i < startDay; i++)
        {
            JLabel emptyLabel = new JLabel();
            contentPanel.add(emptyLabel);
        }
    }

    private void populateDaysWithEvents(int daysInMonth)
    {
        for (int day = 1; day <= daysInMonth; day++)
        {
            JLabel label = new JLabel(String.valueOf(day));
            label.setFont(new Font("", Font.PLAIN, 12));

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBorder(null);
            buttonPanel.setOpaque(true);
            buttonPanel.setBackground(new Color(0, 0, 0, 0));
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

            JScrollPane scrollPane = new JScrollPane(buttonPanel);
            scrollPane.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseEntered(MouseEvent e)
                {
                    scrollPane.setCursor(HAND_CURSOR);
                }

                @Override
                public void mouseExited(MouseEvent e)
                {
                    scrollPane.setCursor(DEFAULT_CURSOR);
                }

                @Override
                public void mouseClicked(MouseEvent e)
                {
                    System.out.println("Clicked");
                }
            });

            scrollPane.setBorder(null);
            scrollPane.getHorizontalScrollBar().setUnitIncrement(16);

            JPanel panel = new JPanel();

            panel.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    System.out.println("Clicked");
                }

                @Override
                public void mouseEntered(MouseEvent e)
                {
                    scrollPane.setCursor(HAND_CURSOR);
                }

                @Override
                public void mouseExited(MouseEvent e)
                {
                    scrollPane.setCursor(DEFAULT_CURSOR);
                }
            });

            panel.setBackground(Color.WHITE);
            panel.setLayout(new BorderLayout());
            panel.add(label, BorderLayout.NORTH);
            panel.add(scrollPane, BorderLayout.CENTER);

            LocalDateTime currentDateTime = LocalDateTime.of(currentYear, currentMonth + 1, day, 0, 0);

            events.getEventList().sort(Comparator.comparing(EventModel::isWholeDay).reversed()
                    .thenComparing((event1, event2) -> event2.getDuration().compareTo(event1.getDuration())));

            for (EventModel event : events.getEventList())
            {
                if (isDateInRange(currentDateTime, event))
                {
                    JLabel eventLabel = new JLabel(event.getTitle());
                    eventLabel.setOpaque(true);
                    eventLabel.setBackground(Color.decode(event.getLabelColor()));
                    eventLabel.setHorizontalAlignment(SwingConstants.LEFT);

                    eventLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, eventLabel.getPreferredSize().height));
                    eventLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                    buttonPanel.add(eventLabel);
                }
            }

            if (day == todayDay && currentMonth == todayMonth && currentYear == todayYear)
            {
                panel.setOpaque(true);
                panel.setBackground(Color.decode("#e3e3e3"));
            }

            panel.setFont(CalendarUtil.getCalendarItemsFont(false));


            contentPanel.add(panel);
        }
    }

    private boolean isDateInRange(LocalDateTime date, EventModel eventModel)
    {
        LocalDate dateOnly = date.toLocalDate();
        LocalDate startOnly = eventModel.getStartDateTime().toLocalDate();
        LocalDate endOnly = eventModel.getEndDateTime().toLocalDate();

        return !dateOnly.isBefore(startOnly) && !dateOnly.isAfter(endOnly);
    }

    public JPanel getPanel()
    {
        return calendarPanel;
    }
}
