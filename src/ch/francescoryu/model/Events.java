package ch.francescoryu.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorOrder;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Events
{
    private List<EventDate> eventDateList;

    public Events()
    {
        eventDateList = new ArrayList<>();
    }

    public Events(List<EventDate> eventDateList)
    {
        this.eventDateList = eventDateList;
    }

    public List<EventDate> getEventDateList()
    {
        return eventDateList;
    }

    public void setEventDayList(List<EventDate> eventDateList)
    {
        this.eventDateList = eventDateList;
    }
}
