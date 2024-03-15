import org.junit.Test;

import model.Day;
import model.DayTime;
import model.Event;
import model.IUsers;
import model.InactiveUser;
import model.TimeSlot;
import model.UserSchedule;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests for the planner(backend functionality for conflicts e.t.c).
 */
public class TestPlanner {

  /**
   * Tests for time with valid inpots.
   */
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

  /**
   * Tests for time with minutes more than 59.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testTimeBadMinutes() {

    DayTime t1 = new DayTime(19, 77, Day.MONDAY);
  }

  /**
   * Tests for time with negative minutes.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testTimeMinLessThan0(){
    DayTime t1 = new DayTime(19, -1, Day.SATURDAY);
  }

  /**
   * Tests for time with negative hours.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testTimeHourLessThan0(){

    DayTime t1 = new DayTime(-10, 9, Day.THURSDAY);
  }

  /**
   * Tests for time with hours more than 23.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testTimeHourMoreThan23(){

    DayTime t1 = new DayTime(25, 9, Day.TUESDAY);
  }

  // tests for conflict
  // should return false since event 2 starts and finishes after event 1 so no overlap.
  /**
   * Tests for conflict for valid non overlapping time slots on the same day.
   */
  @Test
  public void sameDayValidTimeSlot(){
    DayTime startTime1 = new DayTime(9, 30, Day.MONDAY);
    DayTime endTime1 = new DayTime(10, 45, Day.MONDAY);
    TimeSlot event1 = new TimeSlot(startTime1, endTime1);

    DayTime startTime2 = new DayTime(11, 45, Day.MONDAY);
    DayTime endTime2 = new DayTime(12, 45, Day.MONDAY);
    TimeSlot event2 = new TimeSlot(startTime2, endTime2);

    assertFalse(event1.conflict(event2));
  }

  // should return true since event 2 starts while event 1 is still happening.
  /**
   * Tests for conflict for valid overlapping time slots on the same day.
   */
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
  /**
   * Tests for conflict for valid overlapping time slots on the same day.
   */
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

  /**
   * Tests for conflict for valid overlapping time slots on different days.
   */
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

  /**
   * Tests for not having a conflict for valid non overlapping.
   */
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
  /**
   * Tests for conflict for valid overlapping and overflowwing time slots.
   */
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
  /**
   * Tests for no conflict for 2 overflowing time slots.
   */
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

  /**
   * Tests for no conflict for 2 non-overflowing time slots.
   */
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
  /**
   * Tests for conflict for 2 non-overflowing time slots.
   */
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
  /**
   * Tests for conflict for 2 overflowing time slots.
   */
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

  /**
   * Tests for event construction with a null name.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testEventConstructionBadName() {
    new Event(null, "Location", true,
            new DayTime(10, 0, Day.MONDAY),
            new DayTime(12, 0, Day.MONDAY), "hostID");
  }

  // Test for event construction with a null location
  /**
   * Tests for event construction with a null location.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testEventConstructionBadLocation() {
    new Event("EventName", null, true,
            new DayTime(10, 0, Day.MONDAY),
            new DayTime(12, 0, Day.MONDAY), "hostID");
  }

  // Test for event construction with a null start time
  /**
   * Tests for event construction with a null start time.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testEventConstructionBadStartTime() {
    new Event("EventName", "Location", true,
            null, new DayTime(12, 0, Day.MONDAY), "hostID");
  }

  // Test for event construction with a null end time
  /**
   * Tests for event construction with a null end time.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testEventConstructionBadEndTime() {
    new Event("EventName", "Location", true,
            new DayTime(10, 0, Day.MONDAY), null, "hostID");
  }

  // Test for event construction with a null host ID
  /**
   * Tests for event construction with a null host ID.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testEventConstructionNullHost() {
    new Event("EventName", "Location", true,
            new DayTime(10, 0, Day.MONDAY),
            new DayTime(12, 0, Day.MONDAY), null);
  }

  // Test for event construction with bad invitees
  /**
   * Tests for event construction with null invitees.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testEventConstructionBadInvitees() {
    new Event("EventName", "Location", true,
            new DayTime(10, 0, Day.MONDAY),
            new DayTime(12, 0, Day.MONDAY), null, "Hamsa");
  }

  /**
   * Tests for modifying name.
   */
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

  /**
   * Tests for modifying name with a null name.
   */
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

  /**
   * Tests for adding an active invitee.
   */
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

  /**
   * Tests for adding an inactive invitee.
   */
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


  /**
   * Tests for adding a null invitee.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullAddInvitee() {
    IUsers activeUser = new UserSchedule("Hamsa");
    DayTime startTime = new DayTime(9, 50, Day.TUESDAY);
    DayTime endTime = new DayTime(11, 35, Day.TUESDAY);
    Event event = new Event("Statistics", "Snell", true, startTime, endTime,
            activeUser.userID());
    event.addInvitee(null);
  }

  /**
   * Tests for adding an already invited invitee.
   */
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



  /**
   * Tests for checkcing for the host returning true.
   */
  @Test
  public void testIsHostTrue() {
    DayTime startTime = new DayTime(9, 50, Day.TUESDAY);
    DayTime endTime = new DayTime(11, 35, Day.TUESDAY);
    IUsers host = new UserSchedule("Hamsa Madhira");
    Event event = new Event("Event", "Location", true, startTime, endTime,
            host.userID());
    assertTrue(event.isHost(host));
  }

  /**
   * Tests for checkcing for the host returning false.
   */
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

  /**
   * Tests for removing an invitee for the host.
   */
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



  /**
   * Tests for removing an invitee for other users.
   */
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

  /**
   * Tests for removing an invitee for a null user.
   */
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
  /**
   * Tests for modifying name with the same name.
   */
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

  /**
   * Tests for modifying location with the valid location.
   */
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

  /**
   * Tests for modifying location with the null location.
   */
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
  /**
   * Tests for modifying location with the same location.
   */
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

  /**
   * Tests for modifying start time with the null start time.
   */
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

  /**
   * Tests for modifying start time equating to the current end time.
   */
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

    event.modifyEndTime(startTime);
    assertEquals(startTime, event.endTime());
  }

  /**
   * Tests for modifying end time with the null end time.
   */
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

}