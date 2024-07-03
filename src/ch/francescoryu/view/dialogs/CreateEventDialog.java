package ch.francescoryu.view.dialogs;

import ch.francescoryu.model.EventModel;
import ch.francescoryu.util.AddEventListener;
import ch.francescoryu.util.CalendarUtil;
import ch.francescoryu.util.ToolTipHolder;
import ch.francescoryu.view.MainView;
import ch.francescoryu.view.components.buttons.PrimaryButton;
import ch.francescoryu.view.components.buttons.SecondaryButton;
import ch.francescoryu.view.components.labels.AddEventLabel;
import ch.francescoryu.view.components.spinner.TimeSpinner;
import ch.francescoryu.view.pickers.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CreateEventDialog
{
    private final AddEventListener addEventListener;

    private LocalDate startLocalDate;
    private LocalDate endLocalDate;

    private final Window parent;

    private JTextField titleTextField;
    private JTextArea descriptionTextArea;
    private JTextField startDateTextField;
    private JTextField endDateTextField;

    private JButton startDateButton;
    private JButton endDateButton;
    private JButton colorPickerButton;
    private JButton cancelButton;
    private JButton saveButton;

    private JCheckBox wholeDayCheckBox;

    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JSpinner startSpinner;
    private JSpinner endSpinner;

    private DatePicker startDatePicker;
    private DatePicker endDatePicker;

    private final EventModel eventModel;

    private JFrame frame;

    public CreateEventDialog(Window parent, AddEventListener addEventListener)
    {
        this.addEventListener = addEventListener;
        this.parent = parent;
        eventModel = new EventModel();
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
            startLocalDate = startDatePicker.getSelectedDate();
            startDateTextField.setText(getDateFormatted(startLocalDate));
        });

        endDateButton = new PrimaryButton("...", 15);
        endDateButton.addActionListener(e ->
        {
            endDatePicker = new DatePicker(frame);
            endLocalDate = endDatePicker.getSelectedDate();
            endDateTextField.setText(getDateFormatted(endLocalDate));
        });

        colorPickerButton = new JButton("Your event");
        colorPickerButton.setFont(new Font("", Font.PLAIN, 15));
        colorPickerButton.setBorderPainted(false);
        colorPickerButton.setOpaque(true);
        colorPickerButton.setBackground(Color.decode("#ceaddb"));
        colorPickerButton.addActionListener(e ->
        {

        });
        CalendarUtil.addToolTipToComponent(colorPickerButton, ToolTipHolder.COLOR_PICKER_BUTTON);

        startSpinner = new TimeSpinner();
        endSpinner = new TimeSpinner();

        wholeDayCheckBox = new JCheckBox();
        wholeDayCheckBox.addItemListener(e ->
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
        int y = 0;

        contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        //-------------------------------------------------------------------

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = y;
        contentPanel.add(new AddEventLabel("Title"), gbc);

        gbc.gridx = 1;
        gbc.gridy = y;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        contentPanel.add(titleTextField, gbc);

        //-------------------------------------------------------------------

        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0;
        contentPanel.add(new AddEventLabel("Description"), gbc);

        gbc.gridx = 1;
        gbc.gridy = y;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        contentPanel.add(new JScrollPane(descriptionTextArea), gbc);

        //-------------------------------------------------------------------

        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0;
        contentPanel.add(new AddEventLabel("Start Date"), gbc);

        JPanel startDatePanel = new JPanel(new BorderLayout());
        startDatePanel.add(startDateTextField, BorderLayout.CENTER);
        startDatePanel.add(startDateButton, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.gridy = y;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        contentPanel.add(startDatePanel, gbc);

        //-------------------------------------------------------------------

        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0;
        contentPanel.add(new AddEventLabel("End Date"), gbc);

        JPanel endDatePanel = new JPanel(new BorderLayout());
        endDatePanel.add(endDateTextField, BorderLayout.CENTER);
        endDatePanel.add(endDateButton, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.gridy = y;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        contentPanel.add(endDatePanel, gbc);

        //-------------------------------------------------------------------

        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0;
        contentPanel.add(new AddEventLabel("Whole day"), gbc);

        gbc.gridx = 1;
        gbc.gridy = y;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        contentPanel.add(wholeDayCheckBox, gbc);

        //-------------------------------------------------------------------

        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0;
        contentPanel.add(new AddEventLabel("Start Time"), gbc);

        gbc.gridx = 1;
        gbc.gridy = y;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        contentPanel.add(startSpinner, gbc);

        //-------------------------------------------------------------------

        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
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

        //-------------------------------------------------------------------

        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0;
        contentPanel.add(new AddEventLabel("Select color"), gbc);

        gbc.gridx = 1;
        gbc.gridy = y;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        contentPanel.add(colorPickerButton, gbc);
    }

    private void initButtonPanel()
    {
        cancelButton = new SecondaryButton("Cancel", 15);
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
            createNewEventModel();
            addEventListener.onSave();
            frame.dispose();
        });
    }

    private void createNewEventModel()
    {
        eventModel.setTitle(titleTextField.getText());
        eventModel.setDescription(descriptionTextArea.getText());
        eventModel.setWholeDay(wholeDayCheckBox.isSelected());

        LocalTime startLocalTime = convertToLocalTime((Date) startSpinner.getValue());
        LocalDateTime startLocalDateTime = LocalDateTime.of(startLocalDate, startLocalTime);
        eventModel.setStartDateTime(startLocalDateTime);

        LocalTime endLocalTime = convertToLocalTime((Date) endSpinner.getValue());
        LocalDateTime endLocalDateTime = LocalDateTime.of(endLocalDate, endLocalTime);
        eventModel.setEndDateTime(endLocalDateTime);

        Color labelColor = colorPickerButton.getBackground();
        int r = labelColor.getRed();
        int g = labelColor.getGreen();
        int b = labelColor.getBlue();

        String hex = String.format("#%02x%02x%02x", r, g, b);

        eventModel.setLabelColor(hex);
    }

    private LocalTime convertToLocalTime(Date date)
    {
        Instant instant = date.toInstant();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return LocalTime.ofInstant(instant, defaultZoneId);
    }

    private void initFrame()
    {
        frame = new JFrame("Add Event");
        frame.setLayout(new BorderLayout());
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(new Dimension(600, 500));
        frame.setLocationRelativeTo(parent);
        frame.setResizable(false);
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

    public EventModel getEvent()
    {
        return eventModel;
    }
}
