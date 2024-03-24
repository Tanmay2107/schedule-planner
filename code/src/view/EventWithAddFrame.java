package view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import model.CentralSystem;
import model.Day;
import model.DayTime;
import model.ReadOnlyCentralSystem;

public class EventWithAddFrame extends JFrame{

  private ReadOnlyCentralSystem model;
  private String uid;

  public EventWithAddFrame(String uid , ReadOnlyCentralSystem model) {
    this.uid = uid;
    this.model = model;
    setUp();
  }

  protected void setUp(){
    // Setting the title of the frame
    setTitle("Event");

    // Setting the size of the frame
    setSize(500, 600);

    // When the frame is closed, exit.
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // Centering the frame on the screen
    setLocationRelativeTo(null);

    // Create a panel to hold buttons
    JPanel buttonPanel = new JPanel();
    // Create add button
    JButton addEventButton = new JButton("Add Event");
    buttonPanel.add(addEventButton);


    // Create an empty panel
    JPanel eventDetailsPanel = new EventDetailsPanel(uid, model);

    // Create a container to hold both panels
    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    // Add panels to the content pane
    contentPane.add(buttonPanel, BorderLayout.SOUTH);
    contentPane.add(eventDetailsPanel, BorderLayout.CENTER);

    // Center the frame on the screen
    setLocationRelativeTo(null);

  }

  public static void main(String[] args) {
    CentralSystem centralSystemWith3User = new CentralSystem();
    String userId1 = "Hamsa Madhira";

    ArrayList<String> invitees = new ArrayList<>();
    invitees.add(userId1);
    centralSystemWith3User.addUser(userId1);


    centralSystemWith3User.createEvent(userId1,
            "Cognition", "Dodge Hall", true,
            new DayTime(10, 0, Day.MONDAY)
            , new DayTime(11, 0, Day.MONDAY),
            invitees);

    String userId2 = "Tanmay Shah";
    ArrayList<String> invitees2 = new ArrayList<>();
    invitees2.add(userId2);
    invitees2.add(userId1);

    centralSystemWith3User.createEvent(userId2,
            "OOD Grind", "WVH Lab", false,
            new DayTime(12, 0, Day.FRIDAY)
            , new DayTime(17, 0, Day.FRIDAY),
            invitees2);

    String userId3 = "Prof. Nunez";
    centralSystemWith3User.addUser(userId3);
    ArrayList<String> invitees3 = new ArrayList<>();
    invitees3.add(userId3);
    invitees3.add(userId2);
    invitees3.add(userId1);

    centralSystemWith3User.createEvent(userId3,
            "OOD Class", "Churchill", true,
            new DayTime(9, 50, Day.WEDNESDAY)
            , new DayTime(11, 45, Day.WEDNESDAY),
            invitees3);
    centralSystemWith3User.addUser(userId2);

    EventWithAddFrame frame = new EventWithAddFrame("Hamsa", centralSystemWith3User);
    frame.setVisible(true);
  }
}