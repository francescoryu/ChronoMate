import ch.francescoryu.model.EventModel;
import ch.francescoryu.model.Events;
import jakarta.xml.bind.JAXBException;
import xml.XMLController;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestData
{
    public static void main(String[] args)
    {
        LocalDateTime s1 = LocalDateTime.of(2024, 6, 11, 12, 15);
        LocalDateTime e1 = LocalDateTime.of(2024, 6, 13, 12, 15);

        EventModel event1 = new EventModel("make nap", "I need it", s1, e1, "#addbba", true);

        LocalDateTime s2 = LocalDateTime.of(2024, 6, 12, 0, 0);
        LocalDateTime e2 = LocalDateTime.of(2024, 6, 18, 0, 0);

        EventModel event2 = new EventModel("PTO", "I need it", s2, e2, "#ceaddb", true);

        LocalDateTime s3 = LocalDateTime.of(2024, 6, 28, 12, 15);
        LocalDateTime e3 = LocalDateTime.of(2024, 6, 28, 13, 15);

        EventModel event3 = new EventModel("ONE DAY", "I need it", s3, e3, "#ceaddb", false);

        List<EventModel> eventDayList = new ArrayList<>();
        eventDayList.add(event1);
        eventDayList.add(event2);
        eventDayList.add(event3);

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
