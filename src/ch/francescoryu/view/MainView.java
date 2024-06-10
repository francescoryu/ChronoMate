package ch.francescoryu.view;

import ch.francescoryu.model.Events;
import ch.francescoryu.view.panels.CalendarArea;
import ch.francescoryu.view.panels.DayEventArea;
import util.CalendarUtil;
import util.DateChangedListener;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MainView
{
    private JLabel titleLabel;

    private CalendarArea calendarArea;
    private DayEventArea dayEventArea;

    private JPanel titlePanel;
    private JPanel dayEventPanel;
    private JPanel calendarPanel;

    private JSplitPane splitPane;
    private JFrame frame;

    private DateChangedListener dateChangedListener;

    private Events events;

    public MainView(Events events)
    {
        this.events = events;
        init();
    }

    private void init()
    {
        initListeners();
        initTitlePanel();
        initDayEventPanel();
        initCalendarPanel();
        initSplitPane();
        initFrame();
    }

    private void initListeners()
    {
        dateChangedListener = new DateChangedListener()
        {
            @Override
            public void dateChanged(int day, int month, int year)
            {
                dayEventArea.setLabelText(day + "." + month + "." + year);
            }
        };
    }

    private void initTitlePanel()
    {
        titleLabel = CalendarUtil.createTitleLabel("ChronoMate");
        titlePanel = new JPanel();
        titlePanel.add(titleLabel);
        titlePanel.setBackground(Color.decode("#b6ba9c"));
    }

    private void initDayEventPanel()
    {
        dayEventArea = new DayEventArea();
        dayEventPanel = dayEventArea.getPanel();
        dayEventPanel.setBackground(Color.decode("#abd1d0"));
    }

    private void initCalendarPanel()
    {
        calendarArea = new CalendarArea(dateChangedListener, events);
        calendarPanel = calendarArea.getPanel();
        calendarPanel.setBackground(Color.decode("#D1ABAC"));
    }

    private void initSplitPane()
    {
        splitPane = new JSplitPane();
        splitPane.setLeftComponent(dayEventPanel);
        splitPane.setRightComponent(calendarPanel);
        splitPane.setResizeWeight(0.0);
        splitPane.setBorder(null);
        splitPane.setBackground(null);
    }

    private void initFrame()
    {
        frame = new JFrame();
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("ChronoMate");
        frame.setLayout(new BorderLayout());

        initFrameIcon();

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(splitPane, BorderLayout.CENTER);
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
