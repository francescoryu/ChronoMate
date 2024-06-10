package ch.francescoryu.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class EventDay
{
    private String event;
    private String desc;

    private DurationTimePoint startDurationTimePoint;
    private DurationTimePoint endDurationTimePoint;

    public EventDay()
    {
        this("", "", null, null);
    }

    public EventDay(String event, String desc, DurationTimePoint startDurationTimePoint, DurationTimePoint endDurationTimePoint)
    {
        this.event = event;
        this.desc = desc;
        this.startDurationTimePoint = startDurationTimePoint;
        this.endDurationTimePoint = endDurationTimePoint;
    }

    public String getEvent()
    {
        return event;
    }

    public void setEvent(String event)
    {
        this.event = event;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public DurationTimePoint getStartDurationTimePoint()
    {
        return startDurationTimePoint;
    }

    public void setStartDurationTimePoint(DurationTimePoint startDurationTimePoint)
    {
        this.startDurationTimePoint = startDurationTimePoint;
    }

    public DurationTimePoint getEndDurationTimePoint()
    {
        return endDurationTimePoint;
    }

    public void setEndDurationTimePoint(DurationTimePoint endDurationTimePoint)
    {
        this.endDurationTimePoint = endDurationTimePoint;
    }
}
