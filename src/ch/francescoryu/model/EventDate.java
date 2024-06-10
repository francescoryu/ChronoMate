package ch.francescoryu.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class EventDate
{
    private int day;
    private int month;
    private int year;
    private List<EventDay> eventDayList;

    public EventDate()
    {
        this(0, 0, 0, new ArrayList<EventDay>());
    }

    public EventDate(int day, int month, int year, List<EventDay> eventDayList)
    {
        this.day = day;
        this.month = month;
        this.year = year;
        this.eventDayList = eventDayList;
    }

    public int getDay()
    {
        return day;
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    public int getMonth()
    {
        return month;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public List<EventDay> getEventDayList()
    {
        return eventDayList;
    }

    public void setEventDayList(List<EventDay> eventDayList)
    {
        this.eventDayList = eventDayList;
    }
}
