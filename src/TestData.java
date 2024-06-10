import ch.francescoryu.model.DurationTimePoint;
import ch.francescoryu.model.EventDate;
import ch.francescoryu.model.EventDay;
import ch.francescoryu.model.Events;
import jakarta.xml.bind.JAXBException;
import xml.XMLController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestData
{
    public static void main(String[] args)
    {
        DurationTimePoint s1 = new DurationTimePoint(9, 30);
        DurationTimePoint e1 = new DurationTimePoint(18, 30);

        DurationTimePoint s2 = new DurationTimePoint(11, 30);
        DurationTimePoint e2 = new DurationTimePoint(12, 45);

        EventDay eventDay1 = new EventDay("Tea Party", "I need to buy tea", s1, e1);
        EventDay eventDay2 = new EventDay("Quick Nap", "Eat after nap", s2, e2);

        EventDate eventDate1 = new EventDate(1, 1, 2024, Arrays.asList(eventDay1, eventDay2));
        EventDate eventDate2 = new EventDate(2, 2, 2024, Arrays.asList(eventDay1, eventDay2));

        List<EventDate> eventDayList = new ArrayList<>();
        eventDayList.add(eventDate1);
        eventDayList.add(eventDate2);

        Events events = new Events(eventDayList);

        try
        {
            XMLController.marshal(events);
        }
        catch (JAXBException e)
        {
            throw new RuntimeException(e);
        }
    }
}
