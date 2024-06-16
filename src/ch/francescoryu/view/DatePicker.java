package ch.francescoryu.view;

import ch.francescoryu.util.CalendarUtil;
import ch.francescoryu.view.components.buttons.PrimaryButton;
import ch.francescoryu.view.components.buttons.SecondaryButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DatePicker
{
    private final Window parent;

    private int currentYear;
    private int currentMonth;
    private int currentDay;

    private int todayDay;
    private int todayMonth;
    private int todayYear;
    private JComboBox<Integer> yearComboBox;
    private JComboBox<String> monthComboBox;

    private JButton cancelButton;

    private JPanel calendarPanel;
    private JPanel contentPanel;

    private JDialog dialog;

    public DatePicker(Window parent)
    {
        this.parent = parent;
        initCancelButton();
        initCalendarPanel();
        initFrame();
    }

    private void initCancelButton()
    {
        cancelButton = new SecondaryButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());
    }

    private void initCalendarPanel()
    {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.decode("#b3b3b3"));
        topPanel.setLayout(new FlowLayout());

        yearComboBox = new JComboBox<>();
        yearComboBox.setFont(CalendarUtil.getCalendarItemsFont(true, 13));

        for (int year = 1900; year <= 2100; year++)
        {
            yearComboBox.addItem(year);
        }

        String[] months = CalendarUtil.MONTHS;
        monthComboBox = new JComboBox<>(months);
        monthComboBox.setFont(CalendarUtil.getCalendarItemsFont(true, 13));

        JButton todayButton = new PrimaryButton("Today", 10);
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
        calendarPanel.add(cancelButton, BorderLayout.SOUTH);

        setCalendarToCurrentDate();
        initListeners();
    }

    private void initFrame()
    {
        dialog = new JDialog(parent, "Select Date", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.add(calendarPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
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

            JButton button = new JButton(String.valueOf(day));
            button.setLayout(new BoxLayout(button, BoxLayout.Y_AXIS));

            int finalDay = day;
            button.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    currentDay = finalDay;
                    dialog.dispose();
                }
            });

            if (day == todayDay && currentMonth == todayMonth && currentYear == todayYear)
            {
                button.setOpaque(true);
                button.setBackground(Color.decode("#e3e3e3"));
            }

            contentPanel.add(button);
        }
    }

    public LocalDate getSelectedDate()
    {
        return LocalDate.of(currentYear, currentMonth + 1, currentDay);
    }
}
