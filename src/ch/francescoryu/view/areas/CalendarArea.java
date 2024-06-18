package ch.francescoryu.view.areas;

import ch.francescoryu.model.EventModel;
import ch.francescoryu.model.Events;
import ch.francescoryu.util.CalendarUtil;
import ch.francescoryu.view.components.buttons.PrimaryButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private Events events;

    public CalendarArea(Events events)
    {
        this.events = events;
        initCalendarPanel();
    }

    private void initCalendarPanel()
    {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.decode("#b3b3b3"));
        topPanel.setLayout(new FlowLayout());

        yearComboBox = new JComboBox<>();
        yearComboBox.setFont(CalendarUtil.getCalendarItemsFont(true, 18));

        for (int year = 1900; year <= 2100; year++)
        {
            yearComboBox.addItem(year);
        }

        String[] months = CalendarUtil.MONTHS;
        monthComboBox = new JComboBox<>(months);
        monthComboBox.setFont(CalendarUtil.getCalendarItemsFont(true, 18));

        JButton todayButton = new PrimaryButton("Today", 15);
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
        contentPanel.setBorder(new EmptyBorder(1, 1, 1, 1));

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
            dayLabel.setFont(CalendarUtil.getCalendarItemsFont(true, 12));
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
            scrollPane.setBorder(null);
            scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
            scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
            scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

            JPanel panel = new JPanel();
            panel.setBackground(Color.WHITE);
            panel.setLayout(new BorderLayout());
            panel.add(label, BorderLayout.NORTH);
            panel.add(scrollPane, BorderLayout.CENTER);

            LocalDateTime currentDateTime = LocalDateTime.of(currentYear, currentMonth + 1, day, 0, 0);

            events.getEventList().sort(Comparator.comparing(EventModel::isWholeDay).reversed()
                    .thenComparing((event1, event2) -> event2.getStartDateTime().compareTo(event1.getStartDateTime()))
                    .thenComparing((event1, event2) -> event2.getEndDateTime().compareTo(event1.getEndDateTime())));

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

    public void reload(Events events)
    {
        this.events = events;
        updateCalendar();
    }
}
