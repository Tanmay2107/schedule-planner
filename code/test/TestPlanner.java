import org.junit.Test;

import model.Day;
import model.DayTime;
import model.Event;
import model.IUsers;
import model.InactiveUser;
import model.TimeSlot;
import model.CentralSystem;
import model.UserSchedule;
import model.Utils;

import java.security.cert.TrustAnchor;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestPlanner {

  @Test
  public void testTimeToIntValidInputs(){
    DayTime t1 = new DayTime(9, 30, Day.MONDAY);
    DayTime t2 = new DayTime(10, 45, Day.MONDAY);
    DayTime t3 = new DayTime(23, 45, Day.TUESDAY);
    DayTime t4 = new DayTime(7, 34, Day.TUESDAY);
    DayTime t5 = new DayTime(0, 0, Day.WEDNESDAY);
    DayTime t6 = new DayTime(3, 45, Day.WEDNESDAY);
    DayTime t7 = new DayTime(4, 59, Day.THURSDAY);
    DayTime t8 = new DayTime(4, 16, Day.THURSDAY);
    DayTime t9 = new DayTime(2, 27, Day.FRIDAY);
    DayTime t10 = new DayTime(6, 0, Day.FRIDAY);
    DayTime t11 = new DayTime(13, 24, Day.SATURDAY);
    DayTime t12 = new DayTime(22, 22, Day.SATURDAY);
    DayTime t13 = new DayTime(1, 2, Day.SUNDAY);
    DayTime t14 = new DayTime(7, 56, Day.SUNDAY);

    assertEquals(20930, t1.toInt());
    assertEquals(21045, t2.toInt());
    assertEquals(32345, t3.toInt());
    assertEquals(30734, t4.toInt());
    assertEquals(40000, t5.toInt());
    assertEquals(40345, t6.toInt());
    assertEquals(50459, t7.toInt());
    assertEquals(50416, t8.toInt());
    assertEquals(60227, t9.toInt());
    assertEquals(60600, t10.toInt());
    assertEquals(71324, t11.toInt());
    assertEquals(72222, t12.toInt());
    assertEquals(10102, t13.toInt());
    assertEquals(10756, t14.toInt());

  }

  @Test (expected = IllegalArgumentException.class)
  public void testTimeBadMinutes() {

    DayTime t1 = new DayTime(19, 77, Day.MONDAY);
  }
  @Test (expected = IllegalArgumentException.class)
  public void testTimeMinLessThan0(){
    DayTime t1 = new DayTime(19, -1, Day.SATURDAY);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testTimeHourLessThan0(){

    DayTime t1 = new DayTime(-10, 9, Day.THURSDAY);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testTimeHourMoreThan23(){

    DayTime t1 = new DayTime(25, 9, Day.TUESDAY);
  }

  // tests for conflict
  // should return false since event 2 starts and finishes after event 1 so no overlap.
  @Test
  public void sameDayValidTimeSlot(){
    DayTime startTime1 = new DayTime(9, 30, Day.MONDAY);
    DayTime endTime1 = new DayTime(10, 45, Day.MONDAY);
    TimeSlot event1 = new TimeSlot(startTime1, endTime1);

    DayTime startTime2 = new DayTime(11, 45, Day.MONDAY);
    DayTime endTime2 = new DayTime(12, 45, Day.MONDAY);
    TimeSlot event2 = new TimeSlot(startTime2, endTime2);

    assertTrue(event2.conflict(event1));
  }

  // should return true since event 2 starts while event 1 is still happening.
  @Test
  public void newEventWithinExistingEvent(){
    DayTime startTime1 = new DayTime(9, 30, Day.MONDAY);
    DayTime endTime1 = new DayTime(10, 45, Day.MONDAY);
    TimeSlot event1 = new TimeSlot(startTime1, endTime1);

    DayTime startTime2 = new DayTime(9, 45, Day.MONDAY);
    DayTime endTime2 = new DayTime(10, 30, Day.MONDAY);
    TimeSlot event2 = new TimeSlot(startTime2, endTime2);

    assertTrue(event2.conflict(event1));
  }

  // should return true since event 2 starts before and after event 1 is.
  @Test
  public void newEventStartAndEndOverlapsOldEvent(){
    DayTime startTime1 = new DayTime(9, 30, Day.MONDAY);
    DayTime endTime1 = new DayTime(10, 45, Day.MONDAY);
    TimeSlot event1 = new TimeSlot(startTime1, endTime1);

    DayTime endTime2 = new DayTime(11, 30, Day.MONDAY);
    TimeSlot event2 = new TimeSlot(endTime1, endTime2);

    assertFalse(event2.conflict(event1));
  }

  // returns true. Example with overflowing week.
  @Test
  public void eventExtendsIntoNextWeekPlusConflict(){
    DayTime startTime1 = new DayTime(9, 30, Day.SATURDAY);
    DayTime endTime1 = new DayTime(10, 45, Day.MONDAY);
    TimeSlot event1 = new TimeSlot(startTime1, endTime1);

    DayTime startTime2 = new DayTime(9, 45, Day.MONDAY);
    DayTime endTime2 = new DayTime(10, 30, Day.MONDAY);
    TimeSlot event2 = new TimeSlot(startTime2, endTime2);

    assertTrue(event2.conflict(event1));
  }

  @Test
  public void simpleNoConflict(){
    DayTime startTime1 = new DayTime(9, 30, Day.MONDAY);
    DayTime endTime1 = new DayTime(10, 45, Day.MONDAY);
    TimeSlot event1 = new TimeSlot(startTime1, endTime1);

    DayTime startTime2 = new DayTime(10, 46, Day.MONDAY);
    DayTime endTime2 = new DayTime(11, 30, Day.MONDAY);
    TimeSlot event2 = new TimeSlot(startTime2, endTime2);

    assertFalse(event2.conflict(event1));
  }

  // returns true.
  @Test
  public void twoOverFlowEvents(){
    DayTime startTime1 = new DayTime(9, 30, Day.MONDAY);
    DayTime endTime1 = new DayTime(10, 45, Day.MONDAY);
    TimeSlot event1 = new TimeSlot(startTime1, endTime1);

    DayTime startTime2 = new DayTime(9, 45, Day.MONDAY);
    DayTime endTime2 = new DayTime(10, 30, Day.MONDAY);
    TimeSlot event2 = new TimeSlot(startTime2, endTime2);

    assertTrue(event2.conflict(event1));
  }

  // test for with OverFlow, but STart time of event 2 = end time of event 1. conflict is false.
  @Test
  public void TestOverflowNoConflict() {
    DayTime startTime1 = new DayTime(9, 30, Day.SATURDAY);
    DayTime endTime1 = new DayTime(10, 45, Day.MONDAY);
    TimeSlot event1 = new TimeSlot(startTime1, endTime1);

    DayTime startTime2 = new DayTime(10, 45, Day.MONDAY);
    DayTime endTime2 = new DayTime(10, 30, Day.TUESDAY);
    TimeSlot event2 = new TimeSlot(startTime2, endTime2);

    assertFalse(event2.conflict(event1));
  }

  // test with overflow. start time of event 2 == end time of event 1. conflict is false.
  @Test
  public void TestNoOverflowNoConflict() {
    DayTime startTime1 = new DayTime(9, 30, Day.SATURDAY);
    DayTime endTime1 = new DayTime(10, 45, Day.SATURDAY);
    TimeSlot event1 = new TimeSlot(startTime1, endTime1);

    DayTime startTime2 = new DayTime(10, 30, Day.TUESDAY);
    TimeSlot event2 = new TimeSlot(startTime2, startTime1);
    assertFalse(event2.conflict(event1));
  }

  // test for noOverFlow, but Start time of event 2 = end time of event 1, but conflict is true.
  @Test
  public void SameStartAndEndButWithConflict() {
    DayTime startTime1 = new DayTime(9, 30, Day.SATURDAY);
    DayTime endTime1 = new DayTime(10, 45, Day.SATURDAY);
    TimeSlot event1 = new TimeSlot(startTime1, endTime1);

    DayTime startTime2 = new DayTime(10, 30, Day.SATURDAY);
    TimeSlot event2 = new TimeSlot(startTime2, startTime1);
    assertTrue(event2.conflict(event1));
  }

  // test with overflow. start time of event 2 == end time of event 1, but conflict is true.
  @Test
  public void OverflowWithConflict() {
    DayTime startTime1 = new DayTime(9, 30, Day.SATURDAY);
    DayTime endTime1 = new DayTime(10, 45, Day.TUESDAY);
    TimeSlot event1 = new TimeSlot(startTime1, endTime1);

    DayTime startTime2 = new DayTime(10, 30, Day.MONDAY);
    TimeSlot event2 = new TimeSlot(startTime2, startTime1);
    assertTrue(event2.conflict(event1));
  }

  // tests for Event:

  // Test for event construction with a null name
  @Test(expected = IllegalArgumentException.class)
  public void testEventConstructionBadName() {
    new Event(null, "Location", true,
            new DayTime(10, 0, Day.MONDAY),
            new DayTime(12, 0, Day.MONDAY), "hostID");
  }

  // Test for event construction with a null location
  @Test(expected = IllegalArgumentException.class)
  public void testEventConstructionBadLocation() {
    new Event("EventName", null, true,
            new DayTime(10, 0, Day.MONDAY),
            new DayTime(12, 0, Day.MONDAY), "hostID");
  }

  // Test for event construction with a null start time
  @Test(expected = IllegalArgumentException.class)
  public void testEventConstructionBadStartTime() {
    new Event("EventName", "Location", true,
            null, new DayTime(12, 0, Day.MONDAY), "hostID");
  }

  // Test for event construction with a null end time
  @Test(expected = IllegalArgumentException.class)
  public void testEventConstructionBadEndTime() {
    new Event("EventName", "Location", true,
            new DayTime(10, 0, Day.MONDAY), null, "hostID");
  }

  // Test for event construction with a null host ID
  @Test(expected = IllegalArgumentException.class)
  public void testEventConstructionNullHost() {
    new Event("EventName", "Location", true,
            new DayTime(10, 0, Day.MONDAY),
            new DayTime(12, 0, Day.MONDAY), null);
  }

  // Test for event construction with bad invitees
  @Test(expected = IllegalArgumentException.class)
  public void testEventConstructionBadInvitees() {
    new Event("EventName", "Location", true,
            new DayTime(10, 0, Day.MONDAY),
            new DayTime(12, 0, Day.MONDAY), null, "Hamsa");
  }

  @Test
  public void testValidModifyName() {
    DayTime startTime = new DayTime(9, 50, Day.TUESDAY);
    DayTime endTime = new DayTime(11, 35, Day.TUESDAY);
    UserSchedule host = new UserSchedule("Nunez");

    UserSchedule invitee1 = new UserSchedule("Hamsa");
    UserSchedule invitee2 = new UserSchedule("Tanmay");

    ArrayList<IUsers> invitees = new ArrayList<IUsers>();
    invitees.add(invitee1);
    invitees.add(invitee2);
    invitees.add(host);

    Event event = new Event("CS 3500", "Churchill", true,
            startTime, endTime, host.userID());

    event.modifyName("Object Oriented Design");
    assertEquals("Object Oriented Design", event.name());
  }

  @Test
  public void testNullModifyName() {
    DayTime startTime = new DayTime(9, 50, Day.TUESDAY);
    DayTime endTime = new DayTime(11, 35, Day.TUESDAY);
    UserSchedule host = new UserSchedule("Nunez");

    UserSchedule invitee1 = new UserSchedule("Hamsa");
    UserSchedule invitee2 = new UserSchedule("Tanmay");
    UserSchedule invitee3 = new UserSchedule("Chloe");

    ArrayList<IUsers> invitees = new ArrayList<IUsers>();
    invitees.add(invitee1);
    invitees.add(invitee2);
    invitees.add(invitee3);
    invitees.add(host);

    Event event = new Event("Object Oriented Design", "Churchill", true,
            startTime, endTime, host.userID());
    assertThrows(IllegalArgumentException.class, () -> event.modifyName(null));
  }

  @Test
  public void testActiveAddInvitee() {
    DayTime startTime = new DayTime(9, 50, Day.TUESDAY);
    DayTime endTime = new DayTime(11, 35, Day.TUESDAY);
    IUsers host = new UserSchedule("Nunez");
    IUsers hamsa = new UserSchedule("Hamsa");

    Event event = new Event("Event", "Location", true, startTime, endTime,
            host.userID());

    event.addInvitee(host);
    event.addInvitee(hamsa);
    assertTrue(event.listOfInvitees().contains(host.userID()));
    assertTrue(event.listOfInvitees().contains(hamsa.userID()));
  }

  @Test
  public void testInactiveAddInvitee() {
    DayTime startTime = new DayTime(9, 50, Day.TUESDAY);
    DayTime endTime = new DayTime(11, 35, Day.TUESDAY);
    IUsers host = new UserSchedule("Nunez");
    IUsers tanmay = new InactiveUser("Tanmay");

    Event event = new Event("Event", "Location", true, startTime, endTime,
            host.userID());

    event.addInvitee(host);
    assertTrue(event.listOfInvitees().contains(host.userID()));

    try {
      event.addInvitee(tanmay);
    } catch (IllegalStateException e) {
      // activate works since user was not active but now is.
      fail("Activation should not throw an IllegalStateException");
    }
    assertTrue(event.listOfInvitees().contains(tanmay.userID()));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testNullAddInvitee() {
    IUsers activeUser = new UserSchedule("Hamsa");
    DayTime startTime = new DayTime(9, 50, Day.TUESDAY);
    DayTime endTime = new DayTime(11, 35, Day.TUESDAY);
    Event event = new Event("Statistics", "Snell", true, startTime, endTime,
            activeUser.userID());
    event.addInvitee(null);
  }

  @Test
  public void testAlreadyInvitedAddInvitee(){
    Event event = new Event("Test Event", "Location", true,
            new DayTime(10, 0, Day.MONDAY), new DayTime(12, 0, Day.MONDAY),
            "host");

    IUsers invitedUser1 = new UserSchedule("hamsa");
    IUsers invitedUser2 = new UserSchedule("tanmay");
    event.addInvitee(invitedUser1);
    event.addInvitee(invitedUser2);

    try {
      event.addInvitee(invitedUser1);
    } catch (IllegalArgumentException e) {
      // IllegalArgumentException to add an added user.
    }
    assertEquals(2, event.listOfInvitees().size());
  }

  @Test
  public void testValidDeleteEvent(){}

  @Test
  public void testNonExistentDeleteEvent(){}

  @Test
  public void testDeleteEventByNonInvitee(){}

  @Test
  public void testDeleteEventByInvitee(){}

  @Test
  public void testDeleteEventByHost(){}

  @Test
  public void testIsHostTrue() {
    DayTime startTime = new DayTime(9, 50, Day.TUESDAY);
    DayTime endTime = new DayTime(11, 35, Day.TUESDAY);
    IUsers host = new UserSchedule("Hamsa Madhira");
    Event event = new Event("Event", "Location", true, startTime, endTime,
            host.userID());
    assertTrue(event.isHost(host));
  }

  @Test
  public void testIsHostFalse() {
    DayTime startTime = new DayTime(9, 50, Day.TUESDAY);
    DayTime endTime = new DayTime(11, 35, Day.TUESDAY);
    IUsers host = new UserSchedule("Hamsa Madhira");
    IUsers otherUser = new UserSchedule("Tanmay Shah");

    Event event = new Event("Event", "Location", true, startTime, endTime,
            host.userID());
    assertFalse(event.isHost(otherUser));
  }

  @Test
  public void testRemoveInviteeForHost() {
    DayTime startTime = new DayTime(9, 50, Day.TUESDAY);
    DayTime endTime = new DayTime(11, 35, Day.TUESDAY);
    IUsers host = new UserSchedule("Nunez");


    Event event = new Event("OOD", "Churchill", true, startTime, endTime,
            host.userID());
    event.addInvitee(host);
    event.removeInvitee(host);

    assertFalse(event.listOfInvitees().contains(host.userID()));
    assertFalse(host.scheduledEvents().contains(event));
  }

  @Test
  public void testRemoveInviteeForOtherUsers(){

    DayTime startTime = new DayTime(9, 50, Day.TUESDAY);
    DayTime endTime = new DayTime(11, 35, Day.TUESDAY);
    IUsers host = new UserSchedule("Nunez");
    IUsers invitee1 = new UserSchedule("Hamsa");
    IUsers invitee2 = new UserSchedule("Tanmay");

    Event event = new Event("OOD", "Churchill", true, startTime, endTime,
            host.userID());
    event.addInvitee(invitee1);
    event.addInvitee(invitee2);

    event.removeInvitee(invitee1);

    assertFalse(event.listOfInvitees().contains(invitee1.userID()));
    assertTrue(event.listOfInvitees().contains(invitee2.userID()));
    assertFalse(invitee1.scheduledEvents().contains(event));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveInviteeForNullUsers() {
    DayTime startTime = new DayTime(9, 50, Day.TUESDAY);
    DayTime endTime = new DayTime(11, 35, Day.TUESDAY);
    IUsers host = new UserSchedule("Hamsa");

    Event event = new Event("Music", "Curry", true, startTime, endTime,
            host.userID());

    event.removeInvitee(null);
  }

  // No change. Just returns same name as is.
  @Test
  public void testModifyNameWithSameName() {
    DayTime startTime = new DayTime(9, 50, Day.TUESDAY);
    DayTime endTime = new DayTime(11, 35, Day.TUESDAY);
    UserSchedule host = new UserSchedule("Nunez");

    UserSchedule invitee1 = new UserSchedule("Hamsa");
    UserSchedule invitee2 = new UserSchedule("Tanmay");

    ArrayList<IUsers> invitees = new ArrayList<IUsers>();
    invitees.add(invitee1);
    invitees.add(invitee2);
    invitees.add(host);

    Event event = new Event("CS 3500", "Churchill", true,
            startTime, endTime, host.userID());

    event.modifyName("CS 3500");
    assertEquals("CS 3500", event.name());
  }
  @Test
  public void testValidModifyLocation() {
    DayTime startTime = new DayTime(9, 50, Day.TUESDAY);
    DayTime endTime = new DayTime(11, 35, Day.TUESDAY);
    UserSchedule host = new UserSchedule("Nunez");

    UserSchedule invitee1 = new UserSchedule("Hamsa");
    UserSchedule invitee2 = new UserSchedule("Tanmay");

    ArrayList<IUsers> invitees = new ArrayList<IUsers>();
    invitees.add(invitee1);
    invitees.add(invitee2);
    invitees.add(host);

    Event event = new Event("CS 3500", "Churchill 101", true,
            startTime, endTime, host.userID());

    event.modifyLocation("Snell 109");
    assertEquals("Snell 109", event.location());
  }

  @Test
  public void testNullModifyLocation() {
    DayTime startTime = new DayTime(9, 50, Day.TUESDAY);
    DayTime endTime = new DayTime(11, 35, Day.TUESDAY);
    UserSchedule host = new UserSchedule("Nunez");

    UserSchedule invitee1 = new UserSchedule("Hamsa");
    UserSchedule invitee2 = new UserSchedule("Tanmay");
    UserSchedule invitee3 = new UserSchedule("Chloe");

    ArrayList<IUsers> invitees = new ArrayList<IUsers>();
    invitees.add(invitee1);
    invitees.add(invitee2);
    invitees.add(invitee3);
    invitees.add(host);

    Event event = new Event("OriginalName", "Churchill", true,
            startTime, endTime, host.userID());

    assertThrows(IllegalArgumentException.class, () -> event.modifyLocation(null));
  }

  // No error. Just remains unchanged.
  @Test
  public void testModifyLocationWithSameLocation() {
    DayTime startTime = new DayTime(9, 50, Day.TUESDAY);
    DayTime endTime = new DayTime(11, 35, Day.TUESDAY);
    UserSchedule host = new UserSchedule("Nunez");

    UserSchedule invitee1 = new UserSchedule("Hamsa");
    UserSchedule invitee2 = new UserSchedule("Tanmay");
    UserSchedule invitee3 = new UserSchedule("Chloe");

    ArrayList<IUsers> invitees = new ArrayList<IUsers>();
    invitees.add(invitee1);
    invitees.add(invitee2);
    invitees.add(invitee3);
    invitees.add(host);

    Event event = new Event("CS 3500", "Churchill 101", true,
            startTime, endTime, host.userID());

    event.modifyLocation("Churchill 101");
    assertEquals("Churchill 101", event.location());

  }

  @Test
  public void testModifyNullStartTime(){
    DayTime startTime = new DayTime(9, 50, Day.TUESDAY);
    DayTime endTime = new DayTime(11, 35, Day.TUESDAY);
    UserSchedule host = new UserSchedule("Nunez");

    UserSchedule invitee1 = new UserSchedule("Hamsa");
    UserSchedule invitee2 = new UserSchedule("Tanmay");
    UserSchedule invitee3 = new UserSchedule("Chloe");

    ArrayList<IUsers> invitees = new ArrayList<IUsers>();
    invitees.add(invitee1);
    invitees.add(invitee2);
    invitees.add(invitee3);
    invitees.add(host);

    Event event = new Event("OriginalName", "Churchill", true,
            startTime, endTime, host.userID());

    assertThrows(IllegalArgumentException.class, () -> event.modifyStartTime(null));

  }

  @Test
  public void testModifyEndTimeEqualStartTime(){
    DayTime startTime = new DayTime(9, 50, Day.TUESDAY);
    DayTime endTime = new DayTime(11, 35, Day.TUESDAY);
    UserSchedule host = new UserSchedule("Nunez");

    UserSchedule invitee1 = new UserSchedule("Hamsa");
    UserSchedule invitee2 = new UserSchedule("Tanmay");
    UserSchedule invitee3 = new UserSchedule("Chloe");

    ArrayList<IUsers> invitees = new ArrayList<IUsers>();
    invitees.add(invitee1);
    invitees.add(invitee2);
    invitees.add(invitee3);
    invitees.add(host);

    Event event = new Event("OriginalName", "Churchill", true,
            startTime, endTime, host.userID());

    assertThrows(IllegalStateException.class, () -> event.modifyEndTime(startTime));
  }
  @Test
  public void testModifyNullEndTime(){
    DayTime startTime = new DayTime(9, 50, Day.TUESDAY);
    DayTime endTime = new DayTime(11, 35, Day.TUESDAY);
    UserSchedule host = new UserSchedule("Nunez");

    UserSchedule invitee1 = new UserSchedule("Hamsa");
    UserSchedule invitee2 = new UserSchedule("Tanmay");
    UserSchedule invitee3 = new UserSchedule("Chloe");

    ArrayList<IUsers> invitees = new ArrayList<IUsers>();
    invitees.add(invitee1);
    invitees.add(invitee2);
    invitees.add(invitee3);
    invitees.add(host);

    Event event = new Event("OriginalName", "Churchill", true,
            startTime, endTime, host.userID());

    assertThrows(IllegalArgumentException.class, () -> event.modifyEndTime(null));
  }

  @Test
  public void testUtilsWriteXML(){
    Utils.writeToFile();
  }






}