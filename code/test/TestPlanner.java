import org.junit.Test;

import model.Day;
import model.DayTime;
import model.Event;
import model.IUsers;
import model.TimeSlot;
import model.CentralSystem;
import model.UserSchedule;
import model.Utils;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class TestPlanner {

  // Examples and tests for TimeToInt
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

    assertEquals(10930, t1.toInt());
    assertEquals(11045, t2.toInt());
    assertEquals(22345, t3.toInt());
    assertEquals(20734, t4.toInt());
    assertEquals(30000, t5.toInt());
    assertEquals(30345, t6.toInt());
    assertEquals(40459, t7.toInt());
    assertEquals(40416, t8.toInt());
    assertEquals(50227, t9.toInt());
    assertEquals(50600, t10.toInt());
    assertEquals(61324, t11.toInt());
    assertEquals(62222, t12.toInt());
    assertEquals(70102, t13.toInt());
    assertEquals(70756, t14.toInt());

  }

  // Invalid examples throw errors in the constructors itself:
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

  // Event constructor tests:

  @Test
  public void testValidOnlineEventConstruction(){

  }

  @Test
  public void testValidInPersonEventConstruction(){

  }

  @Test
  public void testEventConstructionBadName(){

  }


  @Test
  public void testEventConstructionBadLocation(){

  }

  @Test
  public void testEventConstructionBadStartTime(){

  }

  @Test
  public void testEventConstructionBadEndTime(){

  }

  @Test
  public void testEventConstructionInactiveHost(){

  }

  @Test
  public void testEventConstructionNullHost(){

  }

  @Test
  public void testEventConstructionBadInvitees(){

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
  public void testActiveAddInvitee(){}

  @Test
  public void testInactiveAddInvitee(){}

  @Test
  public void testNonExistingUserAddInvitee(){}

  @Test
  public void testNullAddInvitee(){}

  @Test
  public void testAlreadyInvitedAddInvitee(){}

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
  public void testIsHostTrue(){}


  @Test
  public void testIsHostFalse(){}

  @Test
  public void testEventAtGivenTimeTrue(){
  }

  @Test
  public void testEventAtGivenTimeFalse(){
  }

  @Test
  public void testRemoveInviteeForHost(){
  }

  @Test
  public void testRemoveInviteeForOtherUsers(){
  }

  @Test
  public void testRemoveInviteeForNonExistentUsers(){
  }

  @Test
  public void testRemoveInviteeForNullUsers(){
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
  public void testModifyStartTimeIncreaseDuration(){

  }

  @Test
  public void testModifyNullStartTime(){

  }

  @Test
  public void testModifyStartTimeGreaterThanEndTime(){

  }

  @Test
  public void testModifyStartTimeReduceDuration(){

  }

  @Test
  public void testModifyStartTimeEqualEndTime(){

  }

  @Test
  public void testModifyEndTimeReduceDuration(){

  }

  @Test
  public void testModifyEndTimeIncreaseDuration(){

  }

  @Test
  public void testModifyEndTimeSmallerThanStartTime(){

  }

  @Test
  public void testModifyEndTimeEqualStartTime(){

  }

  @Test
  public void testModifyNullEndTime(){

  }

  @Test
  public void testUtilsWriteXML(){
    Utils.writeToFile();
  }




}