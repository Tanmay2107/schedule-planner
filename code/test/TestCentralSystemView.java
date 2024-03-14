import org.junit.Test;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import model.CentralSystem;
import model.Day;
import model.DayTime;
import model.Event;
import model.IUsers;
import model.ReadOnlyEvent;
import model.ReadOnlyUsers;
import model.UserSchedule;
import view.CentralSystemTextView;

import static org.junit.Assert.assertEquals;

public class TestCentralSystemView {

  @Test
  public void testCentralSystemView(){
    DayTime OodStartTime = new DayTime(10, 30, Day.MONDAY);
    DayTime OodEndTime = new DayTime(11, 45, Day.MONDAY);
    IUsers nunez = new UserSchedule("Professor Nunez");
    IUsers hamsa = new UserSchedule("Hamsa Madhira");
    IUsers tanmay = new UserSchedule("Tanmay Shah");

    CentralSystem centralSystem = new CentralSystem();
    centralSystem.addUser(nunez.userID());
    centralSystem.addUser(hamsa.userID());
    centralSystem.addUser(tanmay.userID());


    ReadOnlyEvent event1 = new Event("Object Oriented Design", "Churchill",
            false, OodStartTime, OodEndTime, nunez.userID());

    ReadOnlyEvent event2 = new Event("Lab for OOD", "WVH", true,
            new DayTime(10, 0, Day.TUESDAY),
            new DayTime(11, 0, Day.TUESDAY), nunez.userID());

    List<ReadOnlyEvent> userEvents = new ArrayList<>();
    userEvents.add(event1);
    userEvents.add(event2);


    ArrayList<String> invitedUsers = new ArrayList<>();
    invitedUsers.add(nunez.userID());
    invitedUsers.add(tanmay.userID());
    invitedUsers.add(hamsa.userID());


    centralSystem.scheduleEvent(event1.hostID(), event1.name(), event2.location(), event1.online(),
            event1.startTime(), event1.endTime(), invitedUsers);

    CentralSystemTextView.displaySchedule(nunez);
    CentralSystemTextView.displaySchedule(hamsa);
    CentralSystemTextView.displaySchedule(tanmay);
  }

  @Test
  public void testDisplaySchedule() {
    CentralSystem centralSystem = new CentralSystem();
    String userId = "Hamsa Madhira";
    centralSystem.addUser(userId);

    String eventName = "Cognition";
    String location = "Dodge Hall";
    boolean online = true;
    DayTime startTime = new DayTime(10, 0, Day.MONDAY);
    DayTime endTime = new DayTime(11, 0, Day.MONDAY);
    ArrayList<String> invitees = new ArrayList<>();
    invitees.add(userId);

    centralSystem.scheduleEvent(userId, eventName, location, online, startTime, endTime, invitees);

    for (ReadOnlyUsers user : centralSystem.getUsers(userId)) {
      CentralSystemTextView.displaySchedule((UserSchedule) user);

      String expected = "User: Hamsa Madhira\n" +
              "Monday:\n" +
              "  name: Cognition\n" +
              "  time: Monday : 100 -> Monday : 110\n" +
              "  location: Dodge Hall\n" +
              "  online: true\n" +
              "  invitees: Hamsa Madhira\n" +
              "\n" +
              "Tuesday:\n" +
              "  No events scheduled.\n" +
              "Wednesday:\n" +
              "  No events scheduled.\n" +
              "Thursday:\n" +
              "  No events scheduled.\n" +
              "Friday:\n" +
              "  No events scheduled.\n" +
              "Saturday:\n" +
              "  No events scheduled.\n" +
              "Sunday:\n" +
              "  No events scheduled.";
    }
  }
}
