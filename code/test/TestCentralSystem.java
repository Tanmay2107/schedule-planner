import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import model.CentralSystem;
import model.Day;
import model.DayTime;
import model.Event;
import model.EventCommand;
import model.EventModifier;
import model.IEvent;
import model.IUsers;
import model.InactiveUser;
import model.ModifyEventEndTimeCommand;
import model.ModifyEventLocationCommand;
import model.ModifyEventOnlineStatus;
import model.ModifyEventStartTimeCommand;
import model.UserSchedule;
import view.CentralSystemTextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

// Checks methods with Central System specifically.
public class TestCentralSystem {

  CentralSystem centralSystemWith1User;
  CentralSystem centralSystemWith3User;

  @Before
  public void setUp(){
    // Set up a central system

    centralSystemWith1User = new CentralSystem();
    String userId = "Hamsa Madhira";
    centralSystemWith1User.addUser(userId);

    String eventName = "Cognition";
    String location = "Dodge Hall";
    boolean online = true;
    DayTime startTime = new DayTime(10, 0, Day.MONDAY);
    DayTime endTime = new DayTime(11, 0, Day.MONDAY);
    ArrayList<String> invitees = new ArrayList<>();
    invitees.add(userId);

    centralSystemWith1User.scheduleEvent(userId, eventName, location, online, startTime, endTime,
            invitees);

    centralSystemWith3User = new CentralSystem();
    String userId1 = "Hamsa Madhira";
    centralSystemWith3User.addUser(userId);


    centralSystemWith3User.scheduleEvent(userId1,
            "Cognition","Dodge Hall", true,
            new DayTime(10, 0, Day.MONDAY)
            , new DayTime(11, 0, Day.MONDAY),
            invitees);

    String userId2 = "Tanmay Shah";
    ArrayList<String> invitees2 = new ArrayList<>();
    invitees2.add(userId2);
    invitees2.add(userId1);

    centralSystemWith3User.scheduleEvent(userId2,
            "OOD Grind","WVH Lab", false,
            new DayTime(12, 0, Day.FRIDAY)
            , new DayTime(17, 0, Day.FRIDAY),
            invitees2);

    String userId3 = "Prof. Nunez";
    centralSystemWith3User.addUser(userId3);
    ArrayList<String> invitees3 = new ArrayList<>();
    invitees3.add(userId3);
    invitees3.add(userId2);
    invitees3.add(userId1);

    centralSystemWith3User.scheduleEvent(userId3,
            "OOD Class","Churchill", true,
            new DayTime(9, 50, Day.WEDNESDAY)
            , new DayTime(11, 45, Day.WEDNESDAY),
            invitees3);
    centralSystemWith3User.addUser(userId2);

  }

  @Test
  public void testAddNewUser(){
    centralSystemWith1User.addUser("Tanmay Shah");
    CentralSystemTextView view = new CentralSystemTextView(centralSystemWith1User);
    Assert.assertEquals("User: Hamsa Madhira\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  name: Cognition\n" +
            "  time: Monday : 10:00 -> Monday : 11:00\n" +
            "  location: Dodge Hall\n" +
            "  online: true\n" +
            "  invitees: Hamsa Madhira\n" +
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
            "User: Tanmay Shah\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  No events scheduled.\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  No events scheduled.\n" +
            "Saturday:\n" +
            "  No events scheduled.\n", view.displayScheduleAsString("Tanmay Shah"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddExistingActiveUser(){
    centralSystemWith3User.addUser("Prof. Nunez");

  }

  @Test
  public void testAddInactiveUser(){
    ArrayList<String> inviteesForNewEvent = new ArrayList<>();
    inviteesForNewEvent.add("Prof. Nunez");
    inviteesForNewEvent.add("Prof. Park");

    centralSystemWith3User.scheduleEvent("Prof. Nunez",
            "Faculty meeting","WVH Lab", false,
            new DayTime(16, 0, Day.FRIDAY)
            , new DayTime(17, 0, Day.FRIDAY),
            inviteesForNewEvent);

    centralSystemWith3User.addUser("Prof. Park");
    CentralSystemTextView view = new CentralSystemTextView(centralSystemWith3User);
    Assert.assertEquals("User: Hamsa Madhira\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  name: Cognition\n" +
            "  time: Monday : 10:00 -> Monday : 11:00\n" +
            "  location: Dodge Hall\n" +
            "  online: true\n" +
            "  invitees: Hamsa Madhira\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 12:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" +
            "User: Prof. Park\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  No events scheduled.\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: Faculty meeting\n" +
            "  time: Friday : 16:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Prof. Nunez, Prof. Park\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" +
            "User: Prof. Nunez\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: Faculty meeting\n" +
            "  time: Friday : 16:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Prof. Nunez, Prof. Park\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" +
            "User: Tanmay Shah\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 12:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "Saturday:\n" +
            "  No events scheduled.\n", view.displayScheduleAsString("Prof. Park"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNullUser(){
    centralSystemWith3User.addUser(null);
  }


  @Test
  public void testAddValidEvent(){}

  @Test
  public void testAddEventNonExistantHost(){
    ArrayList<String> invitees = new ArrayList<>();
    invitees.add("Prof. Nunez");
    invitees.add("Prof. Park");
    centralSystemWith3User.scheduleEvent("Prof. Park",
            "Faculty meeting","WVH Lab", false,
            new DayTime(16, 0, Day.FRIDAY)
            , new DayTime(17, 0, Day.FRIDAY),
            invitees);
    CentralSystemTextView view = new CentralSystemTextView(centralSystemWith3User);
    Assert.assertEquals("User: Hamsa Madhira\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  name: Cognition\n" +
            "  time: Monday : 10:00 -> Monday : 11:00\n" +
            "  location: Dodge Hall\n" +
            "  online: true\n" +
            "  invitees: Hamsa Madhira\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 12:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" +
            "User: Prof. Nunez\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: Faculty meeting\n" +
            "  time: Friday : 16:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Prof. Nunez, Prof. Park\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" +
            "User: Tanmay Shah\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 12:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" ,view.displayScheduleAsString("Prof. Nunez"));
  }



  @Test
  public void testAddEventNonInactiveHost(){
    ArrayList<String> invitees = new ArrayList<>();
    invitees.add("Prof. Nunez");
    invitees.add("Prof. Park");
    centralSystemWith3User.scheduleEvent("Prof. Nunez",
            "Faculty meeting","WVH Lab", false,
            new DayTime(16, 0, Day.FRIDAY)
            , new DayTime(17, 0, Day.FRIDAY),
            invitees);
    centralSystemWith3User.scheduleEvent("Prof. Park",
            "Faculty meeting 2","WVH Lab", false,
            new DayTime(19, 0, Day.FRIDAY)
            , new DayTime(20, 0, Day.FRIDAY),
            invitees);
    CentralSystemTextView view = new CentralSystemTextView(centralSystemWith3User);
    Assert.assertEquals("User: Hamsa Madhira\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  name: Cognition\n" +
            "  time: Monday : 10:00 -> Monday : 11:00\n" +
            "  location: Dodge Hall\n" +
            "  online: true\n" +
            "  invitees: Hamsa Madhira\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 12:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" +
            "User: Prof. Nunez\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: Faculty meeting\n" +
            "  time: Friday : 16:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Prof. Nunez, Prof. Park\n" +
            "  name: Faculty meeting 2\n" +
            "  time: Friday : 19:00 -> Friday : 20:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Prof. Nunez, Prof. Park\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" +
            "User: Tanmay Shah\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 12:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "Saturday:\n" +
            "  No events scheduled.\n",view.displayScheduleAsString("Prof. Nunez"));
  }
  @Test(expected = IllegalArgumentException.class)
  public void testAddEventNullName(){
    ArrayList<String> invitees = new ArrayList<>();
    invitees.add("Tanmay Shah");
    centralSystemWith3User.scheduleEvent("Tanmay Shah",
            null,"Hastings Hall", true,
            new DayTime(9, 0, Day.WEDNESDAY)
            , new DayTime(11, 0, Day.WEDNESDAY),
            invitees);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddEventNullLocation(){
    ArrayList<String> invitees = new ArrayList<>();
    invitees.add("Tanmay Shah");
    centralSystemWith3User.scheduleEvent("Tanmay Shah",
            "DD",null, true,
            new DayTime(9, 0, Day.WEDNESDAY)
            , new DayTime(110, 0, Day.WEDNESDAY),
            invitees);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddEventNullStartTime(){
    ArrayList<String> invitees = new ArrayList<>();
    invitees.add("Tanmay Shah");
    centralSystemWith3User.scheduleEvent("Tanmay Shah",
            "DD","Hastings Hall", true,
            null
            , new DayTime(110, 0, Day.WEDNESDAY),
            invitees);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddEventNullEndTime(){
    ArrayList<String> invitees = new ArrayList<>();
    invitees.add("Tanmay Shah");
    centralSystemWith3User.scheduleEvent("Tanmay Shah",
            "DD","Hastings Hall", true,
            new DayTime(9, 0, Day.WEDNESDAY)
            , null,
            invitees);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddEventNullUsers(){
    ArrayList<String> invitees = new ArrayList<>();
    invitees.add("Tanmay Shah");
    centralSystemWith3User.scheduleEvent("Tanmay Shah",
            "DD","Hastings Hall", true,
            new DayTime(9, 0, Day.WEDNESDAY)
            , new DayTime(110, 0, Day.WEDNESDAY),
            null);
  }

  @Test
  public void testRemoveExistingEventNotHost(){

    ArrayList<IUsers> invitees = new ArrayList<>();
    invitees.add(new UserSchedule(
            "Tanmay Shah"));
    invitees.add(new UserSchedule(
            "Hamsa Madhira"));

    Event e = new Event("OOD Grind", "WVH Lab", false,
            new DayTime(12, 0, Day.FRIDAY),
            new DayTime(17, 0, Day.FRIDAY), invitees, "Tanmay Shah");

    centralSystemWith3User.removeEvent("Hamsa Madhira", e);

    CentralSystemTextView view = new CentralSystemTextView(centralSystemWith3User);
    Assert.assertEquals("User: Hamsa Madhira\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  name: Cognition\n" +
            "  time: Monday : 10:00 -> Monday : 11:00\n" +
            "  location: Dodge Hall\n" +
            "  online: true\n" +
            "  invitees: Hamsa Madhira\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  No events scheduled.\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" +
            "User: Prof. Nunez\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  No events scheduled.\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" +
            "User: Tanmay Shah\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 12:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah\n" +
            "Saturday:\n" +
            "  No events scheduled.\n", view.displayScheduleAsString());

  }

  @Test
  public void testRemoveExistingEventHost(){

    ArrayList<IUsers> invitees = new ArrayList<>();
    invitees.add(new UserSchedule(
            "Tanmay Shah"));
    invitees.add(new UserSchedule(
            "Hamsa Madhira"));

    Event e = new Event("OOD Grind", "WVH Lab", false,
            new DayTime(12, 0, Day.FRIDAY),
            new DayTime(17, 0, Day.FRIDAY), invitees, "Tanmay Shah");


    centralSystemWith3User.removeEvent("Tanmay Shah", e);

    CentralSystemTextView view = new CentralSystemTextView(centralSystemWith3User);
    view.displayScheduleAsString();
    Assert.assertEquals("User: Hamsa Madhira\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  name: Cognition\n" +
            "  time: Monday : 10:00 -> Monday : 11:00\n" +
            "  location: Dodge Hall\n" +
            "  online: true\n" +
            "  invitees: Hamsa Madhira\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  No events scheduled.\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" +
            "User: Prof. Nunez\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  No events scheduled.\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" +
            "User: Tanmay Shah\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  No events scheduled.\n" +
            "Saturday:\n" +
            "  No events scheduled.\n", view.displayScheduleAsString());

  }

  @Test(expected = IllegalStateException.class)
  public void testRemoveExistingEventNonInvitee(){
    ArrayList<IUsers> invitees = new ArrayList<>();
    invitees.add(new UserSchedule(
            "Tanmay Shah"));
    invitees.add(new UserSchedule(
            "Hamsa Madhira"));

    Event e = new Event("OOD Grind", "WVH Lab", false,
            new DayTime(12, 0, Day.FRIDAY),
            new DayTime(17, 0, Day.FRIDAY), invitees, "Tanmay Shah");

    centralSystemWith3User.removeEvent("Prof. Nunez", e);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNonExistingUser(){
    ArrayList<IUsers> invitees = new ArrayList<>();
    invitees.add(new UserSchedule(
            "Tanmay Shah"));
    invitees.add(new UserSchedule(
            "Hamsa Madhira"));

    Event e = new Event("OOD Grind", "WVH Lab", false,
            new DayTime(12, 0, Day.FRIDAY),
            new DayTime(17, 0, Day.FRIDAY), invitees, "Tanmay Shah");

    centralSystemWith3User.removeEvent("Prof. Park ", e);
  }

  @Test(expected = IllegalStateException.class)
  public void testRemoveNonExistingEvent(){
    ArrayList<IUsers> invitees = new ArrayList<>();
    invitees.add(new UserSchedule(
            "Tanmay Shah"));
    invitees.add(new UserSchedule(
            "Hamsa Madhira"));

    Event e = new Event("OOD TA Meeting", "WVH Lab", false,
            new DayTime(12, 0, Day.FRIDAY),
            new DayTime(13, 0, Day.FRIDAY), invitees, "Tanmay Shah");

    centralSystemWith3User.removeEvent("Prof. Nunez", e);
  }


  @Test
  public void testModifyEventLocation(){
    ArrayList<IUsers> invitees = new ArrayList<>();
    IUsers tanmay = new UserSchedule("Tanmay Shah");
    Event e = new Event("OOD Grind", "WVH Lab", false,
            new DayTime(5, 0, Day.FRIDAY),
            new DayTime(7, 0, Day.FRIDAY), invitees, tanmay.userID());
    e.addInvitee(new UserSchedule(
            "Tanmay Shah"));
    e.addInvitee(new UserSchedule(
            "Hamsa Madhira"));

    centralSystemWith3User.scheduleEvent("Tanmay Shah", e.name(), e.location(), e.online(),
            e.startTime(),
            e.endTime(), e.listOfInvitees());
    EventCommand command = new ModifyEventLocationCommand(e, "Snell Library");
    CentralSystemTextView view = new CentralSystemTextView(centralSystemWith3User);
    centralSystemWith3User.modifyEvent(e, command, tanmay.userID());
    String actual = view.displayScheduleAsString();
    String expected = "User: Hamsa Madhira\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  name: Cognition\n" +
            "  time: Monday : 10:00 -> Monday : 11:00\n" +
            "  location: Dodge Hall\n" +
            "  online: true\n" +
            "  invitees: Hamsa Madhira\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 12:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 05:00 -> Friday : 07:00\n" +
            "  location: Snell Library\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" +
            "User: Prof. Nunez\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  No events scheduled.\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" +
            "User: Tanmay Shah\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 12:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 05:00 -> Friday : 07:00\n" +
            "  location: Snell Library\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "Saturday:\n" +
            "  No events scheduled.\n";
    assertEquals(expected, actual);

  }

  @Test (expected = IllegalArgumentException.class)
  public void testModifyEventNullLocation(){
    ArrayList<IUsers> invitees = new ArrayList<>();
    invitees.add(new UserSchedule(
            "Tanmay Shah"));
    invitees.add(new UserSchedule(
            "Hamsa Madhira"));

    Event e = new Event("OOD Grind", "WVH Lab", false,
            new DayTime(12, 0, Day.FRIDAY),
            new DayTime(17, 0, Day.FRIDAY), invitees, "Tanmay Shah");

    EventCommand command = new ModifyEventLocationCommand(e, null);
    centralSystemWith3User.modifyEvent(e, command, "Tanmay Shah");
  }

  @Test
  public void testModifyEventToOnline(){
    ArrayList<IUsers> invitees = new ArrayList<>();
    IUsers tanmay = new UserSchedule("Tanmay Shah");
    Event e = new Event("OOD Grind", "WVH Lab", false,
            new DayTime(5, 0, Day.FRIDAY),
            new DayTime(7, 0, Day.FRIDAY), invitees, tanmay.userID());
    e.addInvitee(new UserSchedule(
            "Tanmay Shah"));
    e.addInvitee(new UserSchedule(
            "Hamsa Madhira"));

    centralSystemWith3User.scheduleEvent("Tanmay Shah", e.name(), e.location(), e.online(),
            e.startTime(),
            e.endTime(), e.listOfInvitees());
    EventCommand command = new ModifyEventOnlineStatus(e);
    String expected ="User: Hamsa Madhira\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  name: Cognition\n" +
            "  time: Monday : 10:00 -> Monday : 11:00\n" +
            "  location: Dodge Hall\n" +
            "  online: true\n" +
            "  invitees: Hamsa Madhira\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 12:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 05:00 -> Friday : 07:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" +
            "User: Prof. Nunez\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  No events scheduled.\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" +
            "User: Tanmay Shah\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 12:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 05:00 -> Friday : 07:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "Saturday:\n" +
            "  No events scheduled.\n";

    CentralSystemTextView view = new CentralSystemTextView(centralSystemWith3User);
    String actual = view.displayScheduleAsString();
    centralSystemWith3User.modifyEvent(e, command, tanmay.userID());
    assertEquals(expected, actual);
  }

  @Test
  public void testModifyEventToOffline(){
    ArrayList<IUsers> invitees = new ArrayList<>();
    invitees.add(new UserSchedule(
            "Tanmay Shah"));
    invitees.add(new UserSchedule(
            "Hamsa Madhira"));

    ArrayList<String> invitee = new ArrayList<>();
    invitee.add("Tanmay Shah");
    invitee.add("Hamsa Madhira");
    Event e = new Event("OOD Grind", "WVH Lab", true,
            new DayTime(6, 0, Day.SATURDAY),
            new DayTime(8, 0, Day.SATURDAY), invitees, "Tanmay Shah");

    centralSystemWith3User.scheduleEvent(e.hostID(), e.name(), e.location(), e.online(),
            e.startTime(),
            e.endTime(), e.listOfInvitees());
    EventCommand command = new ModifyEventOnlineStatus(e);
    String expected ="User: Hamsa Madhira\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  name: Cognition\n" +
            "  time: Monday : 10:00 -> Monday : 11:00\n" +
            "  location: Dodge Hall\n" +
            "  online: true\n" +
            "  invitees: Hamsa Madhira\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 12:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "Saturday:\n" +
            "  name: OOD Grind\n" +
            "  time: Saturday : 06:00 -> Saturday : 08:00\n" +
            "  location: WVH Lab\n" +
            "  online: true\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "User: Prof. Nunez\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  No events scheduled.\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" +
            "User: Tanmay Shah\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 12:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "Saturday:\n" +
            "  name: OOD Grind\n" +
            "  time: Saturday : 06:00 -> Saturday : 08:00\n" +
            "  location: WVH Lab\n" +
            "  online: true\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" ;

    CentralSystemTextView view = new CentralSystemTextView(centralSystemWith3User);
    String actual = view.displayScheduleAsString();
    centralSystemWith3User.modifyEvent(e, command, "Tanmay Shah");
    assertEquals(expected, actual);

  }

  @Test
  public void testReadAndWriteXML(){
    this.setUp();

    centralSystemWith3User.writeUserToXMLFile("Hamsa Madhira", "Hamsa.xml");
    centralSystemWith3User.writeUserToXMLFile("Prof. Nunez", "Professor.xml");
    centralSystemWith3User.writeUserToXMLFile("Tanmay Shah", "Tanmay.xml");


    CentralSystem centralSystemForXML = new CentralSystem();
    centralSystemForXML.loadUserFromXML("Hamsa.xml");
    centralSystemForXML.loadUserFromXML("Professor.xml");
    centralSystemForXML.loadUserFromXML("Tanmay.xml");

    CentralSystemTextView view = new CentralSystemTextView(centralSystemForXML);
    CentralSystemTextView viewPreXML = new CentralSystemTextView(this.centralSystemWith3User);
    assertEquals(viewPreXML.displayScheduleAsString(),
            view.displayScheduleAsString());
    assertEquals(viewPreXML.displayScheduleAsString(),
            view.displayScheduleAsString());
    assertEquals(viewPreXML.displayScheduleAsString(),
            view.displayScheduleAsString());



  }

  @Test(expected = IllegalArgumentException.class)
  public void testWriteXMLNullPath(){
    centralSystemWith3User.writeUserToXMLFile("Hamsa Madhira", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWriteXMLNullUser(){
    centralSystemWith3User.writeUserToXMLFile(null, "Hamsa.xml");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadXMLNullPath(){
    centralSystemWith3User.loadUserFromXML(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testReadXMLWithWrongPath(){
    centralSystemWith3User.loadUserFromXML
            ("random.xml");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyInviteesList() {
    String name = "Meeting";
    String location = "Conference Room";
    boolean online = false;
    DayTime startTime = new DayTime(20, 30, Day.WEDNESDAY);
    DayTime endTime = new DayTime(20, 40, Day.WEDNESDAY);
    ArrayList<String> invitees = new ArrayList<>();
   centralSystemWith1User.scheduleEvent(name, location, online, startTime, endTime, invitees);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testScheduleEventNullName() {
    String location = "Conference Room";
    boolean online = false;
    DayTime startTime = new DayTime(20, 30, Day.WEDNESDAY);
    DayTime endTime = new DayTime(20, 40, Day.WEDNESDAY);
    ArrayList<String> invitees = new ArrayList<>();
    invitees.add("Hamsa");
    centralSystemWith1User.scheduleEvent(null, location, online, startTime, endTime, invitees);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testScheduleEventNullLocation() {
    String name = "tanmay";
    boolean online = false;
    DayTime startTime = new DayTime(20, 30, Day.WEDNESDAY);
    DayTime endTime = new DayTime(20, 40, Day.WEDNESDAY);
    ArrayList<String> invitees = new ArrayList<>();
    invitees.add("Hamsa");
    centralSystemWith1User.scheduleEvent(name, null, online, startTime, endTime, invitees);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testScheduleEventStartTime() {
    String name = "tanmay";
    String loc = "Conference room";
    boolean online = false;
    DayTime endTime = new DayTime(20, 40, Day.WEDNESDAY);
    ArrayList<String> invitees = new ArrayList<>();
    invitees.add("Hamsa");
    centralSystemWith1User.scheduleEvent(name, loc, online, null, endTime, invitees);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testScheduleEventEndTime() {
    String name = "tanmay";
    String loc = "Conference room";
    boolean online = false;
    DayTime startTime = new DayTime(20, 30, Day.WEDNESDAY);
    ArrayList<String> invitees = new ArrayList<>();
    invitees.add("Hamsa");
    centralSystemWith1User.scheduleEvent(name, loc, online, startTime, null, invitees);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullParameters() {
    centralSystemWith1User.scheduleEvent(null, null, false,
            null, null, null);
  }

  @Test
  public void testValidEventScheduling() {
    centralSystemWith1User.addUser("user1");
    centralSystemWith1User.addUser("user2");
    String name = "Meeting";
    String location = "Conference Room";
    boolean online = false;
    DayTime startTime = new DayTime(20, 30, Day.WEDNESDAY);
    DayTime endTime = new DayTime(20, 40, Day.WEDNESDAY);
    ArrayList<String> invitees = new ArrayList<>();
    invitees.add("user1");
    invitees.add("user2");

    CentralSystemTextView view = new CentralSystemTextView(centralSystemWith1User);
    String actual = view.displayScheduleAsString();
    centralSystemWith1User.scheduleEvent(name, location, online, startTime, endTime, invitees);
    String expected = "User: user1\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
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
            "User: Hamsa Madhira\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  name: Cognition\n" +
            "  time: Monday : 10:00 -> Monday : 11:00\n" +
            "  location: Dodge Hall\n" +
            "  online: true\n" +
            "  invitees: Hamsa Madhira\n" +
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
            "User: user2\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  No events scheduled.\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  No events scheduled.\n" +
            "Saturday:\n" +
            "  No events scheduled.\n";

    assertEquals(expected, actual);


  }

  @Test(expected = IllegalArgumentException.class)
  public void testScheduleConflict() {
    CentralSystem centralSystem = new CentralSystem();
    String user1 = "User1";
    String user2 = "User2";
    centralSystem.addUser(user1);
    centralSystem.addUser(user2);

    String eventName1 = "Chemistry Lab";
    String location1 = "Hastings Hall";
    boolean online1 = false;
    DayTime startTime1 = new DayTime(14, 0, Day.SATURDAY);
    DayTime endTime1 = new DayTime(16, 0, Day.SATURDAY);
    ArrayList<String> invitees1 = new ArrayList<>();
    invitees1.add(user1);

    centralSystem.scheduleEvent(user1, eventName1, location1, online1, startTime1, endTime1,
            invitees1);

    String eventName2 = "Event 2";
    String location2 = "Location 2";
    boolean online2 = false;
    DayTime startTime2 = new DayTime(15, 0, Day.SATURDAY); // Overlaps with Event 1
    DayTime endTime2 = new DayTime(17, 0, Day.SATURDAY);
    ArrayList<String> invitees2 = new ArrayList<>();
    invitees2.add(user1);

   centralSystem.scheduleEvent(user2, eventName2, location2, online2,
           startTime2, endTime2, invitees2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testModifyEventStartTimeWithConflict() {
    DayTime newStartTime = new DayTime(1, 30, Day.SUNDAY);
    ArrayList<IUsers> invitees = new ArrayList<>();
    IUsers tanmay = new UserSchedule("Tanmay Shah");
    Event e = new Event("Office Hours", "Meserve Hall", false,
            new DayTime(5, 30, Day.FRIDAY),
            new DayTime(7, 0, Day.FRIDAY), invitees, tanmay.userID());
    e.addInvitee(new UserSchedule(
            "Tanmay Shah"));
    e.addInvitee(new UserSchedule(
            "Hamsa Madhira"));

    centralSystemWith3User.scheduleEvent("Tanmay Shah", e.name(), e.location(), e.online(),
            e.startTime(),
            e.endTime(), e.listOfInvitees());
    EventCommand command = new ModifyEventStartTimeCommand(e, newStartTime);
    centralSystemWith3User.modifyEvent(e, command, tanmay.userID());
  }

  @Test
  public void testModifyEventValidStartTime() {
    DayTime newStartTime = new DayTime(5, 30, Day.SUNDAY);
    ArrayList<IUsers> invitees = new ArrayList<>();
    IUsers tanmay = new UserSchedule("Tanmay Shah");
    IUsers hamsa = new UserSchedule("Hamsa Madhira");
    invitees.add(tanmay);
    invitees.add(hamsa);

    Event e = new Event("Study Session", "ISEC", false,
            new DayTime(5, 0, Day.SUNDAY),
            new DayTime(7, 0, Day.SUNDAY), invitees, tanmay.userID());


    centralSystemWith3User.scheduleEvent("Tanmay Shah", e.name(), e.location(), e.online(),
            e.startTime(),
            e.endTime(), e.listOfInvitees());
    EventCommand command = new ModifyEventStartTimeCommand(e, newStartTime);
    CentralSystemTextView view = new CentralSystemTextView(centralSystemWith3User);
    centralSystemWith3User.modifyEvent(e, command, tanmay.userID());
    String actual = view.displayScheduleAsString();
    String expected = "User: Hamsa Madhira\n" +
            "Sunday:\n" +
            "  name: Study Session\n" +
            "  time: Sunday : 05:30 -> Sunday : 07:00\n" +
            "  location: ISEC\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "Monday:\n" +
            "  name: Cognition\n" +
            "  time: Monday : 10:00 -> Monday : 11:00\n" +
            "  location: Dodge Hall\n" +
            "  online: true\n" +
            "  invitees: Hamsa Madhira\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 12:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" +
            "User: Prof. Nunez\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  No events scheduled.\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" +
            "User: Tanmay Shah\n" +
            "Sunday:\n" +
            "  name: Study Session\n" +
            "  time: Sunday : 05:30 -> Sunday : 07:00\n" +
            "  location: ISEC\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 12:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "Saturday:\n" +
            "  No events scheduled.\n";
    assertEquals(expected, actual);
  }
  //

  @Test (expected = IllegalArgumentException.class)
  public void testModifyEventNullStartTime(){

    ArrayList<IUsers> invitees = new ArrayList<>();
    invitees.add(new UserSchedule(
            "Tanmay Shah"));
    invitees.add(new UserSchedule(
            "Hamsa Madhira"));

    Event e = new Event("OOD Grind", "WVH Lab", false,
            new DayTime(12, 0, Day.FRIDAY),
            new DayTime(17, 0, Day.FRIDAY), invitees, "Tanmay Shah");

    EventCommand command = new ModifyEventStartTimeCommand(e, null);
    centralSystemWith3User.modifyEvent(e, command, "Tanmay Shah");
  }
  @Test
  public void testModifyEventValidEndTime(){
    DayTime newEndTime = new DayTime(9, 30, Day.SUNDAY);
    ArrayList<IUsers> invitees = new ArrayList<>();
    IUsers tanmay = new UserSchedule("Tanmay Shah");
    IUsers hamsa = new UserSchedule("Hamsa Madhira");
    invitees.add(tanmay);
    invitees.add(hamsa);

    Event e = new Event("Study Session", "ISEC", false,
            new DayTime(5, 0, Day.SUNDAY),
            new DayTime(7, 0, Day.SUNDAY), invitees, tanmay.userID());


    centralSystemWith3User.scheduleEvent("Tanmay Shah", e.name(), e.location(), e.online(),
            e.startTime(),
            e.endTime(), e.listOfInvitees());
    EventCommand command = new ModifyEventEndTimeCommand(e, newEndTime);
    CentralSystemTextView view = new CentralSystemTextView(centralSystemWith3User);
    centralSystemWith3User.modifyEvent(e, command, tanmay.userID());
    String actual = view.displayScheduleAsString();
    String expected = "Sunday:\n" +
            "  name: Study Session\n" +
            "  time: Sunday : 05:00 -> Sunday : 09:30\n" +
            "  location: ISEC\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "Monday:\n" +
            "  name: Cognition\n" +
            "  time: Monday : 10:00 -> Monday : 11:00\n" +
            "  location: Dodge Hall\n" +
            "  online: true\n" +
            "  invitees: Hamsa Madhira\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 12:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" +
            "User: Prof. Nunez\n" +
            "Sunday:\n" +
            "  No events scheduled.\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  No events scheduled.\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" +
            "User: Tanmay Shah\n" +
            "Sunday:\n" +
            "  name: Study Session\n" +
            "  time: Sunday : 05:00 -> Sunday : 09:30\n" +
            "  location: ISEC\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "Monday:\n" +
            "  No events scheduled.\n" +
            "Tuesday:\n" +
            "  No events scheduled.\n" +
            "Wednesday:\n" +
            "  name: OOD Class\n" +
            "  time: Wednesday : 09:50 -> Wednesday : 11:45\n" +
            "  location: Churchill\n" +
            "  online: true\n" +
            "  invitees: Prof. Nunez, Tanmay Shah, Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  name: OOD Grind\n" +
            "  time: Friday : 12:00 -> Friday : 17:00\n" +
            "  location: WVH Lab\n" +
            "  online: false\n" +
            "  invitees: Tanmay Shah, Hamsa Madhira\n" +
            "Saturday:\n" +
            "  No events scheduled.\n";
    assertEquals(expected, actual);
  }
  @Test (expected = IllegalArgumentException.class)
  public void testModifyEventNullEndTime(){
    ArrayList<IUsers> invitees = new ArrayList<>();
    invitees.add(new UserSchedule(
            "Tanmay Shah"));
    invitees.add(new UserSchedule(
            "Hamsa Madhira"));

    Event e = new Event("OOD Grind", "WVH Lab", false,
            new DayTime(12, 0, Day.FRIDAY),
            new DayTime(17, 0, Day.FRIDAY), invitees, "Tanmay Shah");

    EventCommand command = new ModifyEventEndTimeCommand(e, null);
    centralSystemWith3User.modifyEvent(e, command, "Tanmay Shah");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModifyEndTimeWithConflict() {
    DayTime newEndTime = new DayTime(12, 30, Day.FRIDAY);
    ArrayList<IUsers> invitees = new ArrayList<>();
    IUsers tanmay = new UserSchedule("Tanmay Shah");
    Event e = new Event("Office Hours", "Meserve Hall", false,
            new DayTime(10, 30, Day.FRIDAY),
            new DayTime(12, 0, Day.FRIDAY), invitees, tanmay.userID());
    e.addInvitee(new UserSchedule(
            "Tanmay Shah"));
    e.addInvitee(new UserSchedule(
            "Hamsa Madhira"));

    centralSystemWith3User.scheduleEvent("Tanmay Shah", e.name(), e.location(), e.online(),
            e.startTime(),
            e.endTime(), e.listOfInvitees());
    EventCommand command = new ModifyEventEndTimeCommand(e, newEndTime);
    centralSystemWith3User.modifyEvent(e, command, tanmay.userID());
  }

}