package view;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import model.CentralSystem;
import model.Day;
import model.DayTime;
import model.IUsers;
import model.ReadOnlyCentralSystem;
import model.ReadOnlyEvent;
import model.UserSchedule;

public class EventWithModifyFrame extends JFrame{

  private ReadOnlyCentralSystem model;
  private String uid;
  private ReadOnlyEvent event;

  public EventWithModifyFrame(String uid, ReadOnlyCentralSystem model, ReadOnlyEvent event) {
    this.model = model;
    this.uid = uid;
    this.event = event;
    setUp();
  }

  public EventWithModifyFrame() {
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

    // Create modify button
    JButton modifyEventButton = new JButton("Modify Event");
    buttonPanel.add(modifyEventButton);

    // Create remove button
    JButton removeEventButton = new JButton("Remove Event");
    buttonPanel.add(removeEventButton);


    // Create an empty panel
    JPanel eventDetailsPanel = new EventDetailsPanel(uid, model, event);

    // Create a container to hold both panels
    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    // Add panels to the content pane
    contentPane.add(buttonPanel, BorderLayout.SOUTH);
    contentPane.add(eventDetailsPanel, BorderLayout.CENTER);

    // Center the frame on the screen
    setLocationRelativeTo(null);

    setVisible(true);

  }

  public static void main(String[] args) {

    CentralSystem centralSystemWithManyUsers = new CentralSystem();
    String userId1 = "Hamsa Madhira";

    ArrayList<String> invitees = new ArrayList<>();
    invitees.add(userId1);
    centralSystemWithManyUsers.addUser(userId1);

    String dummy1 = "John D";
    String dummy2 = "Saariya F";
    String dummy3 = "Devina M";
    String dummy4 = "Nethra R";
    String dummy5 = "Amey P";
    String dummy6 = "Shashwat K";
    String dummy7 = "Mihir W";
    String dummy8 = "Gih M";

    // Add all users to system
    centralSystemWithManyUsers.addUser(dummy1);
    centralSystemWithManyUsers.addUser(dummy2);
    centralSystemWithManyUsers.addUser(dummy3);
    centralSystemWithManyUsers.addUser(dummy4);
    centralSystemWithManyUsers.addUser(dummy5);
    centralSystemWithManyUsers.addUser(dummy6);
    centralSystemWithManyUsers.addUser(dummy7);
    centralSystemWithManyUsers.addUser(dummy8);

    centralSystemWithManyUsers.createEvent(userId1,
            "Cognition", "Dodge Hall", true,
            new DayTime(10, 0, Day.MONDAY)
            , new DayTime(11, 0, Day.MONDAY),
            invitees);

    String userId2 = "Tanmay Shah";
    ArrayList<String> invitees2 = new ArrayList<>();
    invitees2.add(userId2);
    invitees2.add(userId1);

    centralSystemWithManyUsers.createEvent(userId2,
            "OOD Grind", "WVH Lab", false,
            new DayTime(12, 0, Day.FRIDAY)
            , new DayTime(17, 0, Day.FRIDAY),
            invitees2);

    String userId3 = "Prof. Nunez";
    centralSystemWithManyUsers.addUser(userId3);
    ArrayList<String> invitees3 = new ArrayList<>();
    invitees3.add(userId3);
    invitees3.add(userId2);
    invitees3.add(userId1);

    centralSystemWithManyUsers.createEvent(userId3,
            "OOD Class", "Churchill", true,
            new DayTime(9, 50, Day.WEDNESDAY)
            , new DayTime(11, 45, Day.WEDNESDAY),
            invitees3);
    centralSystemWithManyUsers.addUser(userId2);

    IUsers hamsa = new UserSchedule("hamsa");
    IUsers tanmay = new UserSchedule("tanmay");
    IUsers lucia = new UserSchedule("lucia");
    ArrayList<IUsers> invited = new ArrayList<>();
    invited.add(hamsa);
    invited.add(tanmay);
    invited.add(lucia);

    ReadOnlyEvent event = new model.Event("OOD Class", "Churchill", true,
            new DayTime(9,
                    50, Day.WEDNESDAY),
            new DayTime(11, 45, Day.WEDNESDAY),
            invited, hamsa.userID());


    EventWithModifyFrame frame = new EventWithModifyFrame("Hamsa", centralSystemWithManyUsers,
            event);
    frame.setVisible(true);
  }
}
