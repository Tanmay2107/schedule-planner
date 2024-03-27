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


  public EventDetailsPanel(String uid, ReadOnlyCentralSystem model) {
    this.uid = uid;
    this.model = model;
    setupPanel();
  }

  public EventDetailsPanel(String uid, ReadOnlyCentralSystem model, ReadOnlyEvent event){
    this.uid = uid;
    this.model = model;
    this.event = event;
    setupPanel();
  }

  public void setupPanel() {
    if (event==null){
      setLayout(new BorderLayout());
      setBorder(BorderFactory.createTitledBorder("Event Details"));

      // Create a panel for the form fields
      this.formPanel = new JPanel();
      this.formPanel.setLayout(new GridLayout(0, 2, 5, 5));

      // Add form fields to the form panel
      this.formPanel.add(new JLabel("Event Name:"));
      this.name = new JTextField(20);
      this.formPanel.add(this.name);

      this.formPanel.add(new JLabel("Location:"));
      this.location = new JTextField(20);
      this.formPanel.add(location);

      this.formPanel.add(new JLabel("Online:"));
      this.online = new JCheckBox();
      this.formPanel.add(this.online);

      // Add a combo box for Day selection
      this.formPanel.add(new JLabel("Start Day:"));
      this.startDayComboBox = new JComboBox<>(Day.values());
      this.formPanel.add(this.startDayComboBox);

      this.formPanel.add(new JLabel("Start Time (HH:MM):"));
      this.startTime = new JTextField(10);
      this.formPanel.add(this.startTime);


      this.formPanel.add(new JLabel("End Day:"));
      this.endDayComboBox = new JComboBox<>(Day.values());
      this.formPanel.add(this.endDayComboBox);


      this.formPanel.add(new JLabel("End Time (HH:MM):"));
      this.endTime = new JTextField(10);
      this.formPanel.add(endTime);

      this.formPanel.add(new JLabel("Host ID:"));
      this.hostID = new JLabel(this.uid);
      this.formPanel.add(hostID);

      // Add user to event
      this.addUserBox = new JComboBox<>(model.getUserIds().toArray());
      this.formPanel.add(addUserBox);
      this.addUser = new JButton("Add User");
      this.formPanel.add(addUser);

      ArrayList<String> invitedUsers = new ArrayList<>();
      invitedUsers.add(uid);

      this.removeUserBox = new JComboBox<>(invitedUsers.toArray(new String[0]));
      this.formPanel.add(removeUserBox);
      this.removeUser = new JButton("Remove User");
      this.formPanel.add(removeUser);

      // Add the form panel to the main panel
      add(this.formPanel, BorderLayout.NORTH);

      // Add the form panel to the main panel
      add(this.formPanel, BorderLayout.NORTH);

      // Create a panel for invitees
      this.inviteesPanel = new JPanel(new BorderLayout());

      // Add a label for the invitees
      this.inviteesPanel.add(new JLabel("Invitees:"),
              BorderLayout.NORTH);

      // Get user IDs
      ArrayList<String> userIds = new ArrayList<>();
      userIds.add(this.uid);

      // Create and populate the JList with user IDs
      this.userList = new JList<>(userIds.toArray());

      // Add the JList to the panel with scrollbars
      this.scrollPane = new JScrollPane(this.userList);
      this.inviteesPanel.add(this.scrollPane, BorderLayout.CENTER);

      // Add the invitees panel to the main panel
      add(inviteesPanel, BorderLayout.CENTER);
    }
    else {
      setLayout(new BorderLayout());
      setBorder(BorderFactory.createTitledBorder("Event Details"));

      // Create a panel for the form fields
      this.formPanel = new JPanel();
      this.formPanel.setLayout(new GridLayout(0, 2, 5, 5));

      // Add form fields to the form panel
      this.formPanel.add(new JLabel("Event Name:"));
      this.name = new JTextField(this.event.name(), 20);
      this.formPanel.add(this.name);

      this.formPanel.add(new JLabel("Host ID:"));
      hostID = new JLabel(event.hostID());
      this.formPanel.add(hostID);

      this.formPanel.add(new JLabel("Location:"),
              BorderLayout.NORTH);

      boolean eventOnline = event.online();
      this.online = new JCheckBox("Online", eventOnline);
      this.formPanel.add(this.online);


      this.formPanel.add(new JLabel("Location Name:"));
      this.location = new JTextField(event.location(), 20);
      this.formPanel.add(location);


      // Add a combo box for Day selection
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

      // Create a panel for invitees
      this.inviteesPanel = new JPanel(new BorderLayout());

      // Add a label for the invitees
      this.inviteesPanel.add(new JLabel("Invitees:"),
              BorderLayout.NORTH);

      // Get user IDs
      ArrayList<String> invitedUserIds = new ArrayList<>();
      invitedUserIds.addAll(this.event.listOfInvitees());


      // Create and populate the JList with user IDs
      this.userList = new JList<>(invitedUserIds.toArray());

      // Add the JList to the panel with scrollbars
      this.scrollPane = new JScrollPane(this.userList);
      this.inviteesPanel.add(scrollPane, BorderLayout.CENTER);

      // Add user to event
      this.addUserBox = new JComboBox<>(this.model.getUserIds().toArray(new String[0]));
      this.formPanel.add(addUserBox);
      this.addUser = new JButton("Add User");
      this.formPanel.add(this.addUser);

      this.removeUserBox = new JComboBox<>(this.event.listOfInvitees().toArray(new String[0]));
      this.formPanel.add(this.removeUserBox);
      this.removeUser = new JButton("Remove User");
      this.formPanel.add(this.removeUser);

      // Add the invitees panel to the main panel
      add(this.inviteesPanel, BorderLayout.CENTER);

    }
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
    if (parts.length != 2) {
      return null;   }

    try {
      int hours = Integer.parseInt(parts[0]);
      int minutes = Integer.parseInt(parts[1]);

      // Ensure hours and minutes are within valid ranges
      if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
        // Handle invalid time values
        return null;
      }

      return new DayTime(hours, minutes, day);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  @Override
  public void addFeatures(SchedulePlannerFeatures features) {
    this.addUser.addActionListener(evt -> {
      features.addInvitee(uid, this.addUserBox.getSelectedItem().toString(),this.giveEvent());
    });
    this.removeUser.addActionListener(evt -> {
      features.removeEvent(this.removeUserBox.getSelectedItem().toString(),this.giveEvent());
    });

  }

  @Override
  public void makeVisible() {
    setVisible(true);

  }
  ///

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


    // Create and return the ReadOnlyEvent object
    return new InputEvent(eventName,  isOnline, eventLocation,
            convertToDayTime(startTimeText, startDay),
            convertToDayTime(endTimeText,endDay),hostId, list);
    //public InputEvent(String name, boolean online, String location, DayTime startTime,
    // DayTime endTime, String hostId)

  }



}
