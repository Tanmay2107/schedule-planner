import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import model.CentralSystem;
import model.Day;
import model.DayTime;
import model.ReadOnlyUsers;
import model.UserSchedule;

import static org.junit.Assert.assertEquals;

// Checks methods with Central System specifically.
public class TestCentralSystem {

  @Before
  public void setUp(){
    // Set up a central system

  }

  @Test
  public void testAddNewUser(){}

  @Test
  public void testAddExistingActiveUser(){}

  @Test
  public void testAddInactiveUser(){}

  @Test
  public void testAddNullUser(){}


  @Test
  public void testAddValidEvent(){}

  @Test
  public void testAddEventInactiveHost(){}

  @Test
  public void testAddEventActiveHost(){}

  @Test
  public void testAddEventNonExistentHost(){}
  @Test
  public void testAddEventNullName(){}

  @Test
  public void testAddEventNullLocation(){}

  @Test
  public void testAddEventNullStartTime(){}

  @Test
  public void testAddEventNullEndTime(){}

  @Test
  public void testAddEventNullUsers(){}

  /*
  public void removeEvent(String uid, IEvent e){
    this.checkForActiveUser(uid);
    UserSchedule user = activeUserMap.get(uid);
    user.removeEvent(e);
  }



  public void modifyEvent(Event event, EventCommand command) {
    EventModifier modifier = new EventModifier(command);
    modifier.executeModification();
  }
   */


  @Test
  public void testScheduleEvent() {
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
      UserSchedule userSchedule = (UserSchedule) user;
      assertEquals(1, userSchedule.scheduledEvents().size());
      assertEquals(eventName, userSchedule.scheduledEvents().get(0).name());
      assertEquals(location, userSchedule.scheduledEvents().get(0).location());
      assertEquals(online, userSchedule.scheduledEvents().get(0).online());
      assertEquals(startTime, userSchedule.scheduledEvents().get(0).startTime());
      assertEquals(endTime, userSchedule.scheduledEvents().get(0).endTime());
    }
  }
}
