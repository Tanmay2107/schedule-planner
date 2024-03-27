package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import controller.SchedulePlannerFeatures;
import model.Day;
import model.DayTime;
import model.InputEvent;
import model.ReadOnlyCentralSystem;
import model.ReadOnlyEvent;

/**
 * Panel for displaying and editing event details.
 * It allows users to view and modify event attributes such as name, location, time, and invitees.
 */
public class EventDetailsPanel extends JPanel implements ActionListener, ScheduleView {

  private ReadOnlyEvent event;
  private ReadOnlyCentralSystem model;
  private String uid;
  private JTextField name;
  private JTextField location;
  private JCheckBox online;
  private JTextField startTime;
  private JTextField endTime;
  private JLabel hostID;
  private  JList userList;
  private JScrollPane scrollPane;
  private JPanel inviteesPanel;
  private JComboBox removeUserBox;
  private JComboBox addUserBox;
  private JPanel formPanel;
  private  JComboBox startDayComboBox;
  private JComboBox endDayComboBox;
  private JButton addUser;
  private JButton removeUser;


  /**
   * Constructs an EventDetailsPanel with the given unique identifier and model.
   * If the event is provided, it displays the details of that event; otherwise,
   * it provides fields for creating a new event.
   * @param uid The unique identifier of the event.
   * @param model The ReadOnlyCentralSystem model to interact with.
   */
  public EventDetailsPanel(String uid, ReadOnlyCentralSystem model) {
    this.uid = uid;
    this.model = model;
    setupPanel();
  }

  /**
   * Constructs an EventDetailsPanel with the given unique identifier, model, and event.
   * It displays the details of the provided event.
   * @param uid The unique identifier of the event.
   * @param model The ReadOnlyCentralSystem model to interact with.
   * @param event The event whose details are to be displayed.
   */
  public EventDetailsPanel(String uid, ReadOnlyCentralSystem model, ReadOnlyEvent event){
    this.uid = uid;
    this.model = model;
    this.event = event;
    setupPanel();
  }

  /**
   * Sets up the panel based on whether an event is provided or not.
   */
  public void setupPanel() {
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createTitledBorder("Event Details"));
    if (event==null){
      setUpAddEvent(); // handles AddEvent, all empty fields, to create a new event.
    }
    else {
      setUpModifyEvent(); // handles modifyEvent, shows existing fields that can be changed.
    }
  }

private void setUpAddEvent(){
  //Event details fields
  this.formPanel = new JPanel();
  this.formPanel.setLayout(new GridLayout(0, 2, 5, 5));
  this.formPanel.add(new JLabel("Event Name:")); // Event name section
  this.name = new JTextField(20);
  this.formPanel.add(this.name);
  this.formPanel.add(new JLabel("Location:")); // Event location section
  this.location = new JTextField(20);
  this.formPanel.add(location);
  this.formPanel.add(new JLabel("Online:")); // online selection section
  this.online = new JCheckBox();
  this.formPanel.add(this.online);
  this.formPanel.add(new JLabel("Start Day:")); // Start Day
  this.startDayComboBox = new JComboBox<>(Day.values());
  this.formPanel.add(this.startDayComboBox);
  this.formPanel.add(new JLabel("Start Time (HH:MM):")); // Start time (String input)
  this.startTime = new JTextField(10);
  this.formPanel.add(this.startTime);
  this.formPanel.add(new JLabel("End Day:")); // End Day
  this.endDayComboBox = new JComboBox<>(Day.values());
  this.formPanel.add(this.endDayComboBox);
  this.formPanel.add(new JLabel("End Time (HH:MM):")); // End time (String input)
  this.endTime = new JTextField(10);
  this.formPanel.add(endTime);
  this.formPanel.add(new JLabel("Host ID:")); // Host ID, set to current user for Add Event
  this.hostID = new JLabel(this.uid);
  this.formPanel.add(hostID);
  this.addUserBox = new JComboBox<>(model.getUserIds().toArray());   // Add user to event
  this.formPanel.add(addUserBox);
  this.addUser = new JButton("Add User");
  this.formPanel.add(addUser);

  ArrayList<String> invitedUsers = new ArrayList<>();
  invitedUsers.add(uid);

  this.removeUserBox = new JComboBox<>(invitedUsers.toArray(new String[0])); //removing user
  this.formPanel.add(removeUserBox);
  this.removeUser = new JButton("Remove User");
  this.formPanel.add(removeUser);

  add(this.formPanel, BorderLayout.NORTH); // Add the form panel to the main panel

  this.inviteesPanel = new JPanel(new BorderLayout());   // Create a panel for invitees
  this.inviteesPanel.add(new JLabel("Invitees:"), BorderLayout.NORTH); // Label for invitees

  ArrayList<String> userIds = new ArrayList<>(); // Get user IDs
  userIds.add(this.uid);

  this.userList = new JList<>(userIds.toArray());   // Create and populate the JList with user IDs
  this.scrollPane = new JScrollPane(this.userList); // Add the JList to the panel with scrollbars
  this.inviteesPanel.add(this.scrollPane, BorderLayout.CENTER);
  add(inviteesPanel, BorderLayout.CENTER); // Add the invitees panel to the main panel
}

private void setUpModifyEvent(){
  // Create a panel for the form fields
  this.formPanel = new JPanel();
  this.formPanel.setLayout(new GridLayout(0, 2, 5, 5));

  //Event details fields
  this.formPanel.add(new JLabel("Event Name:"));
  this.name = new JTextField(this.event.name(), 20);
  this.formPanel.add(this.name);
  this.formPanel.add(new JLabel("Host ID:"));
  hostID = new JLabel(event.hostID());
  this.formPanel.add(hostID);
  this.formPanel.add(new JLabel("Location:"), BorderLayout.NORTH);
  boolean eventOnline = event.online();
  this.online = new JCheckBox("Online", eventOnline);
  this.formPanel.add(this.online);
  this.formPanel.add(new JLabel("Location Name:"));
  this.location = new JTextField(event.location(), 20);
  this.formPanel.add(location);
  this.formPanel.add(new JLabel("Start Day:"));
  this.startDayComboBox = new JComboBox<>(Day.values());
  this.formPanel.add(startDayComboBox);
  this.formPanel.add(new JLabel("Start Time (HH:MM):"));
  this.startTime = new JTextField(event.startTime().timeAsString(), 20);
  this.formPanel.add(startTime);
  this.formPanel.add(new JLabel("End Day:"));
  this.endDayComboBox = new JComboBox<>(Day.values());
  this.formPanel.add(endDayComboBox);
  this.formPanel.add(new JLabel("End Time (HH:MM):"));
  this.endTime = new JTextField(event.endTime().timeAsString(), 20);
  this.formPanel.add(endTime);

  // Add the form panel to the main panel
  add(formPanel, BorderLayout.NORTH);
  this.inviteesPanel = new JPanel(new BorderLayout());  // panel for invitees

  // Add a label for the invitees
  this.inviteesPanel.add(new JLabel("Invitees:"), BorderLayout.NORTH);
  ArrayList<String> invitedUserIds = new ArrayList<>();   // Get user IDs
  invitedUserIds.addAll(this.event.listOfInvitees());
  this.userList = new JList<>(invitedUserIds.toArray()); // List with invitees

  // Add the JList to the panel with scrollbars
  this.scrollPane = new JScrollPane(this.userList);
  this.inviteesPanel.add(scrollPane, BorderLayout.CENTER);

  this.addUserBox = new JComboBox<>(this.model.getUserIds().toArray(new String[0]));
  this.formPanel.add(addUserBox);
  this.addUser = new JButton("Add User");
  this.formPanel.add(this.addUser);

  this.removeUserBox = new JComboBox<>(this.event.listOfInvitees().toArray(new String[0]));
  this.formPanel.add(this.removeUserBox);
  this.removeUser = new JButton("Remove User");
  this.formPanel.add(this.removeUser);
  add(this.inviteesPanel, BorderLayout.CENTER);
}


  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.addUser) {
      System.out.println("Adding user");
    }
    else if (e.getSource() == this.removeUser) {
      System.out.println("Remove user");
    }
  }



  /**
   * Converts a string representing start time to a new DayTime object.
   *
   * @param timeString The string representing the start time (format: HH:MM).
   * @param day        The day of the event.
   * @return A new DayTime object representing the start time.
   */
  private DayTime convertToDayTime(String timeString, Day day) {
    String[] parts = timeString.split(":");
   try {
     this.DayTimeValidator(timeString);
   }
   catch (NumberFormatException e) {
     throw new IllegalArgumentException();
   }
   int hours = Integer.parseInt(parts[0]);
    int minutes = Integer.parseInt(parts[1]);
    return new DayTime(hours, minutes, day);
  }


  private boolean DayTimeValidator(String dayTime){
    String[] parts = dayTime.split(":");
    if (parts.length != 2) {
      throw new IllegalArgumentException();
    }
    int hours = Integer.parseInt(parts[0]);
    int minutes = Integer.parseInt(parts[1]);
    try {
      if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
        // Handle invalid time values
        throw new IllegalArgumentException();
      }
    }
    catch (NumberFormatException e) {
        throw new IllegalArgumentException();
    }
    return true;
  }



  /**
   * Adds features to the panel.
   * @param features The SchedulePlannerFeatures object providing features to be added.
   */
  @Override
  public void addFeatures(SchedulePlannerFeatures features) {
    this.addUser.addActionListener(evt -> {
      features.addInvitee(uid, this.addUserBox.getSelectedItem().toString(),this.giveEvent());
    });
    this.removeUser.addActionListener(evt -> {
      features.removeEvent(this.removeUserBox.getSelectedItem().toString(),this.giveEvent());
    });

  }

  /**
   * Makes the panel visible.
   */
  @Override
  public void makeVisible() {
    setVisible(true);

  }



  /**
   * Constructs an InputEvent object based on the panel's input fields.
   * @return The constructed InputEvent object.
   * @throws IllegalArgumentException If any input field has invalid data.
   */
  public InputEvent giveEvent() {
    String eventName = name.getText();
    String eventLocation = location.getText();
    boolean isOnline = online.isSelected();
    Day startDay = (Day) startDayComboBox.getSelectedItem();
    String startTimeText = startTime.getText();
    Day endDay = (Day) endDayComboBox.getSelectedItem();
    String endTimeText = endTime.getText();
    String hostId = hostID.getText();


    ArrayList list = new ArrayList(this.userList.getModel().getSize());

    for (int i = 0; i < this.userList.getModel().getSize(); i++) {
      list.add(this.userList.getModel().getElementAt(i));
    }
    try {
    // Create and return the ReadOnlyEvent object
    return new InputEvent(eventName,  isOnline, eventLocation,
            convertToDayTime(startTimeText, startDay),
            convertToDayTime(endTimeText,endDay),hostId, list); }
    catch (IllegalArgumentException e) {
      throw new IllegalArgumentException();
    }
  }



}
