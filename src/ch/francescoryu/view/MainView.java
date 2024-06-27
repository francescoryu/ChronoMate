package ch.francescoryu.view;

import ch.francescoryu.model.EventModel;
import ch.francescoryu.model.Events;
import ch.francescoryu.util.AddEventListener;
import ch.francescoryu.util.CalendarListener;
import ch.francescoryu.util.MenuAreaListener;
import ch.francescoryu.view.areas.CalendarArea;
import ch.francescoryu.view.areas.MenuArea;
import ch.francescoryu.view.dialogs.EventDialog;
import ch.francescoryu.xml.XMLController;
import jakarta.xml.bind.JAXBException;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.net.URL;

public class MainView
{
    private MenuArea menuArea;
    private CalendarArea calendarArea;

    private JPanel menuPanel;
    private JPanel calendarPanel;

    private JFrame frame;

    private MenuAreaListener menuAreaListener;
    private AddEventListener addEventListener;

    private EventDialog eventDialog;

    private Events events;

    public MainView(Events events)
    {
        this.events = events;
        init();
    }

    private void init()
    {
        initListeners();
        initMenuArea();
        initCalendarPanel();
        initFrame();
    }

    private void initListeners()
    {
        addEventListener = new AddEventListener()
        {
            @Override
            public void onSave()
            {
                EventModel eventModel = eventDialog.getEvent();
                events.addEvent(eventModel);
                try
                {
                    XMLController.marshal(events);
                }
                catch (JAXBException e)
                {
                    throw new RuntimeException(e);
                }

                try
                {
                    events = XMLController.unmarshal();
                }
                catch (JAXBException | FileNotFoundException e)
                {
                    throw new RuntimeException(e);
                }

                calendarArea.reload(events);

                frame.revalidate();
                frame.repaint();
            }
        };

        menuAreaListener = new MenuAreaListener()
        {
            @Override
            public void pressedAddEventButton()
            {
                eventDialog = new EventDialog(frame, addEventListener);
            }

            @Override
            public void pressedSettingsButton()
            {

            }
        };
    }

    private void initMenuArea()
    {
        menuArea = new MenuArea(menuAreaListener);
        menuPanel = menuArea.getPanel();
    }

    private void initCalendarPanel()
    {
        calendarArea = new CalendarArea(events, frame);
        calendarPanel = calendarArea.getPanel();
        calendarPanel.setBackground(Color.decode("#a3ccbe"));
    }

    private void initFrame()
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("ChronoMate");
        frame.setLayout(new BorderLayout());

        initFrameIcon();

        frame.add(menuPanel, BorderLayout.NORTH);
        frame.add(calendarPanel, BorderLayout.CENTER);

        //frame.setUndecorated(true);
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initFrameIcon()
    {
        URL iconURL = MainView.class.getResource("/icons/icon.png");

        if (iconURL != null)
        {
            ImageIcon icon = new ImageIcon(iconURL);
            frame.setIconImage(icon.getImage());
        }
        else
        {
            System.err.println("Icon not found");
        }
    }
}
