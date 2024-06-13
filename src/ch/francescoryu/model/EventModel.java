package ch.francescoryu.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
public class EventModel
{
    private String title;
    private String description;


    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime startDateTime;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime endDateTime;

    private String labelColor;

    private boolean isWholeDay;

    public EventModel()
    {

    }

    public EventModel(String title, String description, LocalDateTime startDateTime, LocalDateTime endDateTime, String labelColor, boolean isWholeDay)
    {
        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.labelColor = labelColor;
        this.isWholeDay = isWholeDay;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public LocalDateTime getStartDateTime()
    {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime)
    {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime()
    {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime)
    {
        this.endDateTime = endDateTime;
    }

    public String getLabelColor()
    {
        return labelColor;
    }

    public void setLabelColor(String labelColor)
    {
        this.labelColor = labelColor;
    }

    public boolean isWholeDay()
    {
        return isWholeDay;
    }

    public void setWholeDay(boolean wholeDay)
    {
        isWholeDay = wholeDay;
    }

    public Duration getDuration()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start = LocalDateTime.parse(startDateTime.toString(), formatter);
        LocalDateTime end = LocalDateTime.parse(endDateTime.toString(), formatter);
        return Duration.between(start, end);
    }
}
