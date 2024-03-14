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