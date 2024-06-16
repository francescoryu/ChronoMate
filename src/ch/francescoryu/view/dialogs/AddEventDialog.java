package ch.francescoryu.view.dialogs;

import ch.francescoryu.util.CalendarUtil;
import ch.francescoryu.view.DatePicker;
import ch.francescoryu.view.MainView;
import ch.francescoryu.view.components.buttons.PrimaryButton;
import ch.francescoryu.view.components.labels.AddEventLabel;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddEventDialog
{
    private final Window parent;

    private JTextField titleTextField;
    private JTextArea descriptionTextArea;
    private JTextField startDateTextField;
    private JTextField endDateTextField;
    private JButton startDateButton;
    private JButton endDateButton;

    private JPanel contentPanel;

    private JFrame frame;

    public AddEventDialog(Window parent)
    {
        this.parent = parent;
        init();
    }

    private void init()
    {
        initComponents();
        initContentPanel();
        initFrame();
        initFrameIcon();
    }

    private void initComponents()
    {
        titleTextField = new JTextField(20);
        titleTextField.setFont(CalendarUtil.getCalendarItemsFont(false, 15));

        descriptionTextArea = new JTextArea(5, 20);
        descriptionTextArea.setFont(CalendarUtil.getCalendarItemsFont(false, 15));
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);

        startDateTextField = createDateTextField();

        endDateTextField = createDateTextField();

        startDateButton = new PrimaryButton("...", 15);
        startDateButton.addActionListener(e -> createNewDatePicker(startDateTextField));

        endDateButton = new PrimaryButton("...", 15);
        endDateButton.addActionListener(e -> createNewDatePicker(endDateTextField));
    }

    private JTextField createDateTextField()
    {
        JTextField textField = new JTextField(20);
        textField.setFont(CalendarUtil.getCalendarItemsFont(false, 15));
        textField.setText(getDateFormatted(LocalDate.now()));

        return textField;
    }

    private void initContentPanel()
    {
        contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        //-------------------------------------------------------------------

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(new AddEventLabel("Title"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        contentPanel.add(titleTextField, gbc);

        //-------------------------------------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0;
        contentPanel.add(new AddEventLabel("Description"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        contentPanel.add(new JScrollPane(descriptionTextArea), gbc);

        //-------------------------------------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0;
        contentPanel.add(new AddEventLabel("Start Date"), gbc);

        JPanel startDatePanel = new JPanel(new BorderLayout());
        startDatePanel.add(startDateTextField, BorderLayout.CENTER);
        startDatePanel.add(startDateButton, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        contentPanel.add(startDatePanel, gbc);

        //-------------------------------------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0;
        contentPanel.add(new AddEventLabel("End Date"), gbc);

        JPanel endDatePanel = new JPanel(new BorderLayout());
        endDatePanel.add(endDateTextField, BorderLayout.CENTER);
        endDatePanel.add(endDateButton, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        contentPanel.add(endDatePanel, gbc);
    }

    private void initFrame()
    {
        frame = new JFrame("Add Event");
        frame.setLayout(new BorderLayout());
        frame.add(contentPanel, BorderLayout.CENTER);

        JButton button = new PrimaryButton("Confirm", 15);
        button.addActionListener(e ->
        {
            frame.setVisible(false);
        });

        frame.pack();
        frame.setLocationRelativeTo(parent);
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

    private String getDateFormatted(LocalDate localDate)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return localDate.format(formatter);
    }

    private void createNewDatePicker(JTextField titleTextField)
    {
        DatePicker datePicker = new DatePicker(frame);

        try
        {
            LocalDate localDate = datePicker.getSelectedDate();
            titleTextField.setText(getDateFormatted(localDate));
        }

        catch (DateTimeException ignored)
        {
            //Ignore exception
        }
    }
}
