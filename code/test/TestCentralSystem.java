import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import model.CentralSystem;
import model.Day;
import model.DayTime;
import model.Event;
import model.EventCommand;
import model.EventModifier;
import model.IUsers;
import model.ModifyEventStartTimeCommand;
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
  public void testModifyEventChangeStartTime() {
    UserSchedule host = new UserSchedule("Prof Nunez");
    UserSchedule invitee1 = new UserSchedule("Hamsa");
    UserSchedule invitee2 = new UserSchedule("Tanmay");
    ArrayList<IUsers> invitees = new ArrayList<IUsers>();
    invitees.add(invitee1);
    invitees.add(invitee2);
    invitees.add(host);

    Event event = new Event("CS 3500", "Churchill 101", false,
            new DayTime(13, 35, Day.TUESDAY),
            new DayTime(3, 25, Day.TUESDAY), host, invitees);
    DayTime newStartTime = new DayTime(13, 25, Day.TUESDAY);

    EventCommand command = new ModifyEventStartTimeCommand(event, newStartTime);
    //CentralSystem centralSystem = new CentralSystem(invitees);
    //centralSystem.modifyEvent(event, command);
    assertEquals(newStartTime, event.startTime());
  }
}
