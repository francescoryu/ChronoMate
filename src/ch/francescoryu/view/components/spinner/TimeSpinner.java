package ch.francescoryu.view.components.spinner;

import ch.francescoryu.util.CalendarUtil;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class TimeSpinner extends JSpinner
{
    public TimeSpinner()
    {
        init();
    }

    private void init()
    {
        initComponents();
    }

    private void initComponents()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        Date initialDate = calendar.getTime();

        SpinnerDateModel model = new SpinnerDateModel(initialDate, null, null, Calendar.MINUTE);
        this.setModel(model);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(this, "HH:mm");
        DateFormatter formatter = (DateFormatter) editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);

        this.setEditor(editor);
        this.setFont(CalendarUtil.getCalendarItemsFont(false, 15));
    }

    public LocalTime getHour()
    {
        Date date = (Date) this.getValue();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        return LocalTime.of(hours, minutes);
    }
}
