package ch.francescoryu.view.areas;

import ch.francescoryu.util.MenuAreaListener;
import ch.francescoryu.view.components.buttons.SecondaryButton;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MenuArea
{
    private MenuAreaListener menuAreaListener;

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
        settingsButton = new SecondaryButton("", 0);
        settingsButton.setIcon(new ImageIcon(Objects.requireNonNull(MenuArea.class.getResource("/icons/setting.png"))));
        settingsButton.setBackground(Color.WHITE);

        addButton = new SecondaryButton("", 0);
        addButton.setIcon(new ImageIcon(Objects.requireNonNull(MenuArea.class.getResource("/icons/add.png"))));
        addButton.setBackground(Color.WHITE);

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
        contentPanel.add(addButton);
        contentPanel.add(settingsButton);
    }

    public JPanel getPanel()
    {
        return contentPanel;
    }
}
