import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.CentralSystem;
import model.Day;
import model.DayTime;
import model.Event;
import model.IUsers;
import model.ReadOnlyEvent;
import model.UserSchedule;
import view.CentralSystemTextView;

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
}
