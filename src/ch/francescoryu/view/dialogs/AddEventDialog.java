package ch.francescoryu.view.dialogs;

import ch.francescoryu.util.CalendarUtil;
import ch.francescoryu.view.DatePicker;
import ch.francescoryu.view.MainView;
import ch.francescoryu.view.components.buttons.PrimaryButton;
import ch.francescoryu.view.components.buttons.SecondaryButton;
import ch.francescoryu.view.components.labels.AddEventLabel;
import ch.francescoryu.view.components.spinner.TimeSpinner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddEventDialog
{
    private LocalDate startLocalDate;
    private LocalDate endLocalDate;

    private final Window parent;

    private JTextField titleTextField;
    private JTextArea descriptionTextArea;
    private JTextField startDateTextField;
    private JTextField endDateTextField;

    private JButton startDateButton;
    private JButton endDateButton;
    private JButton cancelButton;
    private JButton saveButton;

    private JCheckBox wholeDayCheckBox;

    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JSpinner startSpinner;
    private JSpinner endSpinner;

    private DatePicker startDatePicker;
    private DatePicker endDatePicker;

    private JFrame frame;

    public AddEventDialog(Window parent)
    {
        this.parent = parent;
        init();
    }

    private void init()
    {
        initLocalDates();
        initComponents();
        initContentPanel();
        initButtonPanel();
        initFrame();
        initFrameIcon();
    }

    private void initLocalDates()
    {
        startLocalDate = LocalDate.now();
        endLocalDate = LocalDate.now();
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
        startDateButton.addActionListener(e ->
        {
            startDatePicker = new DatePicker(frame);
            createNewDatePicker(startDateTextField, startDatePicker, startLocalDate);
        });

        endDateButton = new PrimaryButton("...", 15);
        endDateButton.addActionListener(e ->
        {
            endDatePicker = new DatePicker(frame);
            createNewDatePicker(endDateTextField, endDatePicker, endLocalDate);
        });

        startSpinner = new TimeSpinner();
        endSpinner = new TimeSpinner();

        wholeDayCheckBox = new JCheckBox();
        wholeDayCheckBox.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                    startSpinner.setEnabled(false);
                    endSpinner.setEnabled(false);
                }
                else
                {
                    startSpinner.setEnabled(true);
                    endSpinner.setEnabled(true);
                }
            }
        });
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

        //-------------------------------------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0;
        contentPanel.add(new AddEventLabel("Whole day"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        contentPanel.add(wholeDayCheckBox, gbc);

        //-------------------------------------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0;
        contentPanel.add(new AddEventLabel("Start Time"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        contentPanel.add(startSpinner, gbc);

        //-------------------------------------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0;
        contentPanel.add(new AddEventLabel("End Time"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        contentPanel.add(endSpinner, gbc);
    }

    private void initButtonPanel()
    {
        cancelButton = new SecondaryButton("Cancel");
        saveButton = new PrimaryButton("Save", 15);

        buttonPanel = new JPanel();
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        initActions();
    }

    private void initActions()
    {
        cancelButton.addActionListener(e -> frame.dispose());

        saveButton.addActionListener(e ->
        {

        });
    }

    private void initFrame()
    {
        frame = new JFrame("Add Event");
        frame.setLayout(new BorderLayout());
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

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

    private void createNewDatePicker(JTextField titleTextField, DatePicker datePicker, LocalDate localDate)
    {

        try
        {
            localDate = datePicker.getSelectedDate();
            titleTextField.setText(getDateFormatted(localDate));
        }

        catch (DateTimeException ignored)
        {
            //Ignore exception
        }
    }
}
