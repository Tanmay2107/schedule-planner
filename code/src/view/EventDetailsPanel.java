package view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import model.Day;
import model.ReadOnlyCentralSystem;
import model.ReadOnlyEvent;

public class EventDetailsPanel extends JPanel {
  private ReadOnlyEvent event;
  private ReadOnlyCentralSystem model;
  private String uid;

  private JTextField name;
  private JTextField location;
  private JCheckBox online;
  private JTextField startTime;
  private JTextField endTime;
  private JTextField hostID;
  private ArrayList<JCheckBox> inviteeCheckbox;

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
      JPanel formPanel = new JPanel();
      formPanel.setLayout(new GridLayout(0, 2, 5, 5));

      // Add form fields to the form panel
      formPanel.add(new JLabel("Event Name:"));
      name = new JTextField(20);
      formPanel.add(name);

      formPanel.add(new JLabel("Location:"));
      location = new JTextField(20);
      formPanel.add(location);

      formPanel.add(new JLabel("Online:"));
      online = new JCheckBox();
      formPanel.add(online);

      // Add a combo box for Day selection
      formPanel.add(new JLabel("Start Day:"));
      JComboBox startDayComboBox = new JComboBox<>(Day.values());
      formPanel.add(startDayComboBox);

      formPanel.add(new JLabel("Start Time (HH:MM):"));
      startTime = new JTextField(10);
      formPanel.add(startTime);


      formPanel.add(new JLabel("End Day:"));
      JComboBox endDayComboBox = new JComboBox<>(Day.values());
      formPanel.add(endDayComboBox);


      formPanel.add(new JLabel("End Time (HH:MM):"));
      endTime = new JTextField(10);
      formPanel.add(endTime);

      formPanel.add(new JLabel("Host ID:"));
      hostID = new JTextField(20);
      formPanel.add(hostID);

      // Add user to event
      JComboBox addUserBox = new JComboBox<>(model.getUserIds().toArray());
      formPanel.add(addUserBox);
      JButton addUser = new JButton("Add User");
      formPanel.add(addUser);

      ArrayList<String> invitedUsers = new ArrayList<>();

      JComboBox removeUserBox = new JComboBox<>(invitedUsers.toArray(new String[0]));
      formPanel.add(removeUserBox);
      invitedUsers.add(uid);
      JButton removeUser = new JButton("Remove User");
      formPanel.add(removeUser);

      // Add the form panel to the main panel
      add(formPanel, BorderLayout.NORTH);

      // Add the form panel to the main panel
      add(formPanel, BorderLayout.NORTH);

      // Create a panel for invitees
      JPanel inviteesPanel = new JPanel(new BorderLayout());

      // Add a label for the invitees
      inviteesPanel.add(new JLabel("Invitees:"),
              BorderLayout.NORTH);

      // Get user IDs
      ArrayList<String> userIds = new ArrayList<>();
      userIds.add(uid);

      // Create and populate the JList with user IDs
      JList userList = new JList<>(userIds.toArray());

      // Add the JList to the panel with scrollbars
      JScrollPane scrollPane = new JScrollPane(userList);
      inviteesPanel.add(scrollPane, BorderLayout.CENTER);

      // Add the invitees panel to the main panel
      add(inviteesPanel, BorderLayout.CENTER);
    }
    else{
      setLayout(new BorderLayout());
      setBorder(BorderFactory.createTitledBorder("Event Details"));

      // Create a panel for the form fields
      JPanel formPanel = new JPanel();
      formPanel.setLayout(new GridLayout(0, 2, 5, 5));

      // Add form fields to the form panel
      formPanel.add(new JLabel("Event Name:"));
      name = new JTextField(event.name(), 20);
      formPanel.add(name);

      formPanel.add(new JLabel("Host ID:"));
      hostID = new JTextField(event.hostID(), 20);
      formPanel.add(hostID);


      formPanel.add(new JLabel("Location:"),
              BorderLayout.NORTH);

      boolean eventOnline = event.online();
      online = new JCheckBox("Online", eventOnline);
      formPanel.add(online);


      formPanel.add(new JLabel("Location Name:"));
      location = new JTextField(event.location(), 20);
      formPanel.add(location);


      // Add a combo box for Day selection
      formPanel.add(new JLabel("Start Day:"));
      JComboBox startDayComboBox = new JComboBox<>(Day.values());
      formPanel.add(startDayComboBox);

      formPanel.add(new JLabel("Start Time (HH:MM):"));
      startTime = new JTextField(20);
      formPanel.add(startTime);

      formPanel.add(new JLabel("End Day:"));
      JComboBox endDayComboBox = new JComboBox<>(Day.values());
      formPanel.add(endDayComboBox);


      formPanel.add(new JLabel("End Time (HH:MM):"));
      endTime = new JTextField(20);
      formPanel.add(endTime);

      // Add the form panel to the main panel
      add(formPanel, BorderLayout.NORTH);

      // Create a panel for invitees
      JPanel inviteesPanel = new JPanel(new BorderLayout());

      // Add a label for the invitees
      inviteesPanel.add(new JLabel("Invitees:"),
              BorderLayout.NORTH);

      // Get user IDs
      ArrayList<String> inviteduserIds = new ArrayList<>();
      inviteduserIds.addAll(event.listOfInvitees());


      // Create and populate the JList with user IDs
      JList userList = new JList<>(inviteduserIds.toArray());

      // Add the JList to the panel with scrollbars
      JScrollPane scrollPane = new JScrollPane(userList);
      inviteesPanel.add(scrollPane, BorderLayout.CENTER);

      // Add user to event
      JComboBox addUserBox = new JComboBox<>(model.getUserIds().toArray(new String[0]));
      formPanel.add(addUserBox);
      JButton addUser = new JButton("Add User");
      formPanel.add(addUser);

      JComboBox removeUserBox = new JComboBox<>(event.listOfInvitees().toArray(new String[0]));
      formPanel.add(removeUserBox);
      JButton removeUser = new JButton("Remove User");
      formPanel.add(removeUser);

      // Add the invitees panel to the main panel
      add(inviteesPanel, BorderLayout.CENTER);

    }
  }


}
