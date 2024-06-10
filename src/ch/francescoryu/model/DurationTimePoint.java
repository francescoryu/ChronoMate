package ch.francescoryu.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class DurationTimePoint
{
    private int hour;
    private int minute;

    public DurationTimePoint()
    {
        this(0, 0);
    }

    public DurationTimePoint(int hour, int minute)
    {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour()
    {
        return hour;
    }

    public void setHour(int hour)
    {
        this.hour = hour;
    }

    public int getMinute()
    {
        return minute;
    }

    public void setMinute(int minute)
    {
        this.minute = minute;
    }
}
