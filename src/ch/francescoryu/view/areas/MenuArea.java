package ch.francescoryu.view.areas;

import ch.francescoryu.util.MenuAreaListener;
import ch.francescoryu.view.dialogs.AddEventDialog;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MenuArea
{
    private MenuAreaListener menuAreaListener;

    private JButton dashboardButton;
    private JButton settingsButton;
    private JButton addButton;

    private JPanel contentPanel;

    public MenuArea(MenuAreaListener menuAreaListener)
    {
        this.menuAreaListener = menuAreaListener;
        init();
    }

    private void init()
    {
        initMenuButtons();
        initContentPanel();
    }

    private void initMenuButtons()
    {
        dashboardButton = new JButton(new ImageIcon(Objects.requireNonNull(MenuArea.class.getResource("/icons/calendar.png"))));
        dashboardButton.setBackground(null);

        settingsButton = new JButton(new ImageIcon(Objects.requireNonNull(MenuArea.class.getResource("/icons/setting.png"))));
        settingsButton.setBackground(null);

        addButton = new JButton(new ImageIcon(Objects.requireNonNull(MenuArea.class.getResource("/icons/add.png"))));
        addButton.setBackground(null);

        addListeners();
    }

    private void addListeners()
    {
        addButton.addActionListener(e ->
        {
            menuAreaListener.pressedAddEventButton();
        });
    }

    private void initContentPanel()
    {

        contentPanel = new JPanel();
        contentPanel.setBackground(Color.decode("#757575"));
        contentPanel.add(dashboardButton);
        contentPanel.add(addButton);
        contentPanel.add(settingsButton);
    }

    public JPanel getPanel()
    {
        return contentPanel;
    }
}
