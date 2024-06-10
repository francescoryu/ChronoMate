package ch.francescoryu.view;

import ch.francescoryu.view.panels.CalendarArea;
import util.DateChangedListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

public class MainView
{
    private JLabel titleLabel;

    private CalendarArea calendarArea;

    private JPanel dayEventPanel;
    private JPanel calendarPanel;

    private JSplitPane splitPane;
    private JFrame frame;

    private DateChangedListener dateChangedListener;

    public MainView()
    {
        init();
    }

    private void init()
    {
        initListeners();
        initTitleLabel();
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
            public void dateChanged(int day, String month, int year)
            {

            }
        };
    }

    private void initTitleLabel()
    {
        titleLabel = createTitleLabel();
    }

    private JLabel createTitleLabel()
    {
        JLabel label = new JLabel("ChronoMate", SwingConstants.CENTER);
        label.setBorder(new EmptyBorder(20, 20, 20, 20));
        label.setFont(new Font("", Font.BOLD, 35));
        return label;
    }

    private void initDayEventPanel()
    {
        dayEventPanel = new JPanel();
    }

    private void initCalendarPanel()
    {
        calendarArea = new CalendarArea(dateChangedListener);
        calendarPanel = calendarArea.getPanel();
    }

    private void initSplitPane()
    {
        splitPane = new JSplitPane();
        splitPane.setLeftComponent(dayEventPanel);
        splitPane.setRightComponent(calendarPanel);
        splitPane.setResizeWeight(0.0);
        splitPane.setBorder(null);
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

        frame.add(titleLabel, BorderLayout.NORTH);
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
