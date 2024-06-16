import ch.francescoryu.model.EventModel;
import ch.francescoryu.model.Events;
import jakarta.xml.bind.JAXBException;
import ch.francescoryu.xml.XMLController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestData
{
    public static void main(String[] args)
    {
        List<EventModel> eventDayList = new ArrayList<>();

        LocalDateTime s1 = LocalDateTime.of(2024, 6, 11, 12, 15);
        LocalDateTime e1 = LocalDateTime.of(2024, 6, 13, 12, 15);
        EventModel event1 = new EventModel("make nap", "I need it", s1, e1, "#addbba", true);
        eventDayList.add(event1);

        // Event 2
        LocalDateTime s2 = LocalDateTime.of(2024, 6, 12, 0, 0);
        LocalDateTime e2 = LocalDateTime.of(2024, 6, 18, 0, 0);
        EventModel event2 = new EventModel("PTO", "I need it", s2, e2, "#ceaddb", true);
        eventDayList.add(event2);

        // Event 3
        LocalDateTime s3 = LocalDateTime.of(2024, 6, 28, 12, 15);
        LocalDateTime e3 = LocalDateTime.of(2024, 6, 28, 13, 15);
        EventModel event3 = new EventModel("ONE DAY", "I need it", s3, e3, "#ceaddb", false);
        eventDayList.add(event3);

        // Additional test data
        // Event 4
        LocalDateTime s4 = LocalDateTime.of(2024, 7, 1, 9, 0);
        LocalDateTime e4 = LocalDateTime.of(2024, 7, 1, 17, 0);
        EventModel event4 = new EventModel("Work", "Full day work", s4, e4, "#ffcc00", false);
        eventDayList.add(event4);

        // Event 5
        LocalDateTime s5 = LocalDateTime.of(2024, 7, 2, 14, 0);
        LocalDateTime e5 = LocalDateTime.of(2024, 7, 2, 15, 0);
        EventModel event5 = new EventModel("Meeting", "Project meeting", s5, e5, "#00ffcc", false);
        eventDayList.add(event5);

        // Event 6
        LocalDateTime s6 = LocalDateTime.of(2024, 7, 3, 18, 0);
        LocalDateTime e6 = LocalDateTime.of(2024, 7, 3, 20, 0);
        EventModel event6 = new EventModel("Dinner", "Dinner with friends", s6, e6, "#ff6699", false);
        eventDayList.add(event6);

        // Event 7
        LocalDateTime s7 = LocalDateTime.of(2024, 7, 4, 0, 0);
        LocalDateTime e7 = LocalDateTime.of(2024, 7, 4, 23, 59);
        EventModel event7 = new EventModel("Holiday", "Independence Day", s7, e7, "#ff0000", true);
        eventDayList.add(event7);

        // Event 8
        LocalDateTime s8 = LocalDateTime.of(2024, 7, 5, 10, 0);
        LocalDateTime e8 = LocalDateTime.of(2024, 7, 5, 12, 0);
        EventModel event8 = new EventModel("Workshop", "Tech workshop", s8, e8, "#6666ff", false);
        eventDayList.add(event8);

        // Event 9
        LocalDateTime s9 = LocalDateTime.of(2024, 7, 6, 6, 0);
        LocalDateTime e9 = LocalDateTime.of(2024, 7, 6, 8, 0);
        EventModel event9 = new EventModel("Exercise", "Morning run", s9, e9, "#00ff00", false);
        eventDayList.add(event9);

        // Event 10
        LocalDateTime s10 = LocalDateTime.of(2024, 7, 7, 11, 0);
        LocalDateTime e10 = LocalDateTime.of(2024, 7, 7, 13, 0);
        EventModel event10 = new EventModel("Brunch", "Brunch with family", s10, e10, "#ff9966", false);
        eventDayList.add(event10);

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
