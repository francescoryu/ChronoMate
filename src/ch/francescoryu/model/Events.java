package ch.francescoryu.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Events
{
    private List<EventModel> eventModelList;

    public Events()
    {
        eventModelList = new ArrayList<>();
    }

    public Events(List<EventModel> eventModelList)
    {
        this.eventModelList = eventModelList;
    }

    public List<EventModel> getEventList()
    {
        return eventModelList;
    }

    public void setEvenList(List<EventModel> eventModelList)
    {
        this.eventModelList = eventModelList;
    }
}
