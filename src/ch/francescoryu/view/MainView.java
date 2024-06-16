package ch.francescoryu.view;

import ch.francescoryu.model.Events;
import ch.francescoryu.util.DateChangedListener;
import ch.francescoryu.util.MenuAreaListener;
import ch.francescoryu.view.areas.CalendarArea;
import ch.francescoryu.view.areas.MenuArea;
import ch.francescoryu.view.dialogs.AddEventDialog;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MainView
{
    private MenuArea menuArea;
    private CalendarArea calendarArea;

    private JPanel menuPanel;
    private JPanel calendarPanel;

    private JFrame frame;

    private DateChangedListener dateChangedListener;
    private MenuAreaListener menuAreaListener;

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
        dateChangedListener = eventDate ->
        {
            frame.revalidate();
            frame.repaint();
        };

        menuAreaListener = new MenuAreaListener()
        {
            @Override
            public void pressedAddEventButton()
            {
                new AddEventDialog(frame);
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
        calendarArea = new CalendarArea(dateChangedListener, events);
        calendarPanel = calendarArea.getPanel();
        calendarPanel.setBackground(Color.decode("#a3ccbe"));
        //calendarPanel.setBackground(Color.decode("#d1d1d1"));
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
