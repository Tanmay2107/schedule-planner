import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.AUsers;
import model.IUsers;
import model.InactiveUser;
import model.ReadOnlyEvent;
import model.UserSchedule;
import model.Event;
import model.DayTime;
import model.Day;

import static org.junit.Assert.*;

public class IUsersTest {
  IUsers activeUser1;
  IUsers activeUser2 ;
  IUsers activeUser3 ;
  IUsers inactiveUser1;
  IUsers inactiveUser2 ;
  IUsers inactiveUser3;
  @Before
  public void setUp() {
    activeUser1 = new UserSchedule("activeUser1");
    activeUser2 = new UserSchedule("activeUser2");
    activeUser3 = new UserSchedule("activeUser3");
    inactiveUser1 = new InactiveUser("inactiveUser1");
    inactiveUser2 = new InactiveUser("inactiveUser2");
    inactiveUser3 = new InactiveUser("inactiveUser3");

  }

  @Test
  public void userID() {
    assertEquals("activeUser1", activeUser1.userID());
    assertEquals("activeUser2", activeUser2.userID());
    assertEquals("activeUser3", activeUser3.userID());
    assertEquals("inactiveUser1", inactiveUser1.userID());
    assertEquals("inactiveUser2", inactiveUser2.userID());
    assertEquals("inactiveUser3", inactiveUser3.userID());
  }

  @Test
  public void removeEvent() {
  }

  @Test
  public void inviteUser() {
    //Event(String name, String location, boolean online, DayTime startTime, DayTime endTime,
    //               String hostId)
    Event e = new Event("event","location1",false
            ,new DayTime(10, 00, Day.MONDAY),
            new DayTime(11, 00, Day.MONDAY), "activeUser1");
    activeUser1.inviteUser(e);

    activeUser1.scheduledEvents();
    ReadOnlyEvent thisEvent = activeUser1.scheduledEvents().get(0);
    Assert.assertEquals(1, activeUser1.scheduledEvents().size());
    Assert.assertEquals("event", thisEvent.name());
    Assert.assertEquals("location1", thisEvent.location());
    Assert.assertEquals(false, thisEvent.online());
    Assert.assertEquals(new DayTime(10, 00, Day.MONDAY), thisEvent.startTime());
    Assert.assertEquals(new DayTime(11, 00, Day.MONDAY), thisEvent.endTime());
    Assert.assertTrue(e.eventEquals(thisEvent));

    //Invite another user to the same event
    activeUser2.inviteUser(e);
    inactiveUser1.inviteUser(e);
    ReadOnlyEvent thisEventAfterAdding = activeUser1.scheduledEvents().get(0);
    Assert.assertTrue(e.eventEquals(thisEvent));
    Assert.assertTrue(e.eventEquals(activeUser2.scheduledEvents().get(0)));
    Assert.assertTrue(e.eventEquals(inactiveUser1.scheduledEvents().get(0)));

  }

  @Test(expected = IllegalStateException.class)
  public void inviteUserWithConflict() {
    Event e = new Event("event","location1",false
            ,new DayTime(10, 00, Day.MONDAY),
            new DayTime(11, 00, Day.MONDAY), "activeUser1");
    activeUser1.inviteUser(e);
    activeUser1.scheduledEvents();
    ReadOnlyEvent thisEvent = activeUser1.scheduledEvents().get(0);
    Assert.assertEquals(1, activeUser1.scheduledEvents().size());
    Assert.assertEquals("event", thisEvent.name());
    Assert.assertEquals("location1", thisEvent.location());
    Assert.assertEquals(false, thisEvent.online());
    Assert.assertEquals(new DayTime(10, 00, Day.MONDAY), thisEvent.startTime());
    Assert.assertEquals(new DayTime(11, 00, Day.MONDAY), thisEvent.endTime());
    Assert.assertTrue(e.eventEquals(thisEvent));

    Event e1 = new Event("event2","location1",false
            ,new DayTime(10, 20, Day.MONDAY),
            new DayTime(11, 20, Day.MONDAY), "activeUser1");
    activeUser1.inviteUser(e1);
  }



  @Test
  public void activateInactiveUsers() {
    Event e = new Event("event","location1",false
            ,new DayTime(10, 00, Day.MONDAY),
            new DayTime(11, 00, Day.MONDAY), "activeUser1");
    inactiveUser1.inviteUser(e);
    try{
    inactiveUser1.giveXMLString();
    }catch (IllegalStateException ex){
      Assert.assertEquals("Inactive user can not give XML string", ex.getMessage());
    }
    IUsers activated = inactiveUser1.activate();
    Assert.assertEquals("\n<event>\n" +
            "<name>event</name>\n" +
            "<time>\n" +
            "<start-day>Monday</start-day>\n" +
            "<start>1000</start>\n" +
            "<end-day>Monday</end-day>\n" +
            "<end>1100</end>\n" +
            "</time>\n" +
            "<location>\n" +
            "<online>false</online>\n" +
            "<place>location1</place>\n" +
            "</location>\n" +
            "<users>\n" +
            "<uid>inactiveUser1</uid>\n" +
            "</users>\n" +
            "</event>\n",activated.giveXMLString());

  }

  @Test(expected = IllegalStateException.class)
  public void activateActiveUser() {
    activeUser1.activate();
  }

  @Test
  public void scheduledEvents() {
    Event e = new Event("event","location1",false
            ,new DayTime(10, 00, Day.MONDAY),
            new DayTime(11, 00, Day.MONDAY), "activeUser1");
    activeUser1.inviteUser(e);
    Assert.assertEquals(1, activeUser1.scheduledEvents().size());
    Assert.assertEquals("event", activeUser1.scheduledEvents().get(0).name());
    Assert.assertEquals("location1", activeUser1.scheduledEvents().get(0).location());
    Assert.assertEquals(false, activeUser1.scheduledEvents().get(0).online());
    Assert.assertEquals(new DayTime(10, 00, Day.MONDAY), activeUser1.scheduledEvents().get(0).startTime());
    Assert.assertEquals(new DayTime(11, 00, Day.MONDAY), activeUser1.scheduledEvents().get(0).endTime());
    Event e1 = new Event("event","location1",false
            ,new DayTime(12, 00, Day.MONDAY),
            new DayTime(13, 00, Day.MONDAY), "activeUser1");
    activeUser1.inviteUser(e1);
    Assert.assertEquals(2, activeUser1.scheduledEvents().size());
    Assert.assertTrue(e.eventEquals(activeUser1.scheduledEvents().get(0)));
    Assert.assertTrue(e1.eventEquals(activeUser1.scheduledEvents().get(1)));
  }

  @Test
  public void giveXMLString() {
    Assert.assertEquals("\n", activeUser1.giveXMLString());
    Event e = new Event("event","location1",false
            ,new DayTime(10, 00, Day.MONDAY),
            new DayTime(11, 00, Day.MONDAY), "activeUser1");
    activeUser1.inviteUser(e);
    Event e1 = new Event("event1","location1",false
            ,new DayTime(12, 00, Day.MONDAY),
            new DayTime(13, 00, Day.MONDAY), "activeUser1");
    activeUser1.inviteUser(e1);
    Assert.assertEquals("\nevent>\n" +
            "<name>event</name>\n" +
            "<time>\n" +
            "<start-day>Monday</start-day>\n" +
            "<start>1000</start>\n" +
            "<end-day>Monday</end-day>\n" +
            "<end>1100</end>\n" +
            "</time>\n" +
            "<location>\n" +
            "<online>false</online>\n" +
            "<place>location1</place>\n" +
            "</location>\n" +
            "<users>\n" +
            "<uid>activeUser1</uid>\n" +
            "</users>\n" +
            "</event>\n" +
            "<event>\n" +
            "<name>event1</name>\n" +
            "<time>\n" +
            "<start-day>Monday</start-day>\n" +
            "<start>1200</start>\n" +
            "<end-day>Monday</end-day>\n" +
            "<end>1300</end>\n" +
            "</time>\n" +
            "<location>\n" +
            "<online>false</online>\n" +
            "<place>location1</place>\n" +
            "</location>\n" +
            "<users>\n" +
            "<uid>activeUser1</uid>\n" +
            "</users>\n" +
            "</event>\n", activeUser1.giveXMLString());
    Assert.assertEquals("\n", activeUser2.giveXMLString());
    Assert.assertEquals("\n", activeUser3.giveXMLString());


  }

  @Test(expected = IllegalStateException.class)
  public void giveXMLStringforInactive(){
    inactiveUser1.giveXMLString();
  }

  @Test
  public void overlappingEventExists() {

  }

  @Test
  public void modifyEvent() {
  }
}