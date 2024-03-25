import java.util.ArrayList;

import model.CentralSystem;
import model.Day;
import model.DayTime;
import view.ScheduleFrame;

/**
 * The main class for the application.
 */
public class Main {

  /**
   * The main entry point for the application.
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    // This is the main entry point for the application..

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


    ScheduleFrame frame = new ScheduleFrame(centralSystemWithManyUsers);
    frame.setVisible(true);

  }
}