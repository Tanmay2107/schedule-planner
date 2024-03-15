import org.junit.Test;

import java.util.ArrayList;

import model.CentralSystem;
import model.Day;
import model.DayTime;
import view.CentralSystemTextView;

import static org.junit.Assert.assertEquals;

/**
 * Tests the CentralSystemTextView class.
 */
public class TestCentralSystemView {

  /**
   * Tests the display schedule method with one event.
   */
  @Test
  public void testDisplayScheduleOneEvent() {
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

    centralSystem.createEvent(userId, eventName, location, online, startTime, endTime, invitees);
    CentralSystemTextView view = new CentralSystemTextView(centralSystem);

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
            "  No events scheduled.\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  No events scheduled.\n" +
            "Saturday:\n" +
            "  No events scheduled.\n";

    assertEquals(expected, actual);
  }


  /**
   * Tests the display schedule method with multiple events on the same day.
   */
  @Test
  public void testDisplayScheduleMultipleEventsSameDay() {
    CentralSystem centralSystem = new CentralSystem();
    String hamsa = "Hamsa Madhira";
    centralSystem.addUser(hamsa);

    String eventName1 = "Cognition";
    String location1 = "Dodge Hall";
    boolean online = true;
    DayTime startTime1 = new DayTime(10, 0, Day.MONDAY);
    DayTime endTime1 = new DayTime(11, 0, Day.MONDAY);
    ArrayList<String> invitees1 = new ArrayList<>();
    invitees1.add(hamsa);

    centralSystem.createEvent(hamsa, eventName1, location1, online, startTime1, endTime1,
            invitees1);

    String eventName2 = "Neurobiology";
    String location2 = "Shillman Hall";
    boolean online2 = false;
    DayTime startTime2 = new DayTime(1, 35, Day.MONDAY);
    DayTime endTime2 = new DayTime(2, 40, Day.MONDAY);
    ArrayList<String> invitees2 = new ArrayList<>();
    invitees2.add(hamsa);

    centralSystem.createEvent(hamsa, eventName2, location2, online, startTime2,
            endTime2, invitees2);

    CentralSystemTextView view = new CentralSystemTextView(centralSystem);
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
              "  name: Neurobiology\n" +
              "  time: Monday : 01:35 -> Monday : 02:40\n" +
              "  location: Shillman Hall\n" +
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
              "  No events scheduled.\n";

      assertEquals(expected, actual);
  }


  /**
   * Tests the display schedule method with multiple events on different days.
   */
  @Test
  public void testDisplayScheduleMultipleEventsDiffDay() {
    CentralSystem centralSystem = new CentralSystem();
    String hamsa = "Hamsa Madhira";
    centralSystem.addUser(hamsa);

    String eventName1 = "Cognition";
    String location1 = "Dodge Hall";
    boolean online = true;
    DayTime startTime1 = new DayTime(10, 0, Day.MONDAY);
    DayTime endTime1 = new DayTime(11, 0, Day.MONDAY);
    ArrayList<String> invitees1 = new ArrayList<>();
    invitees1.add(hamsa);

    centralSystem.createEvent(hamsa, eventName1, location1, online, startTime1, endTime1,
            invitees1);

    String eventName2 = "Neurobiology";
    String location2 = "Shillman Hall";
    boolean online2 = false;
    DayTime startTime2 = new DayTime(1, 35, Day.WEDNESDAY);
    DayTime endTime2 = new DayTime(2, 40, Day.WEDNESDAY);
    ArrayList<String> invitees = new ArrayList<>();
    invitees.add(hamsa);

    centralSystem.createEvent(hamsa, eventName2, location2, online2, startTime2,
            endTime2, invitees);

    CentralSystemTextView view = new CentralSystemTextView(centralSystem);
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
            "  name: Neurobiology\n" +
            "  time: Wednesday : 01:35 -> Wednesday : 02:40\n" +
            "  location: Shillman Hall\n" +
            "  online: false\n" +
            "  invitees: Hamsa Madhira\n" +
            "Thursday:\n" +
            "  No events scheduled.\n" +
            "Friday:\n" +
            "  No events scheduled.\n" +
            "Saturday:\n" +
            "  No events scheduled.\n" ;
      assertEquals(expected, actual);
    }

  /**
   * Tests the display schedule method with multiple users.
   */
  @Test
  public void testAddEventToMultipleUsers() {
    CentralSystem centralSystem = new CentralSystem();
    String hamsa = "Hamsa Madhira";
    String tanmay = "Tanmay Shah";
    centralSystem.addUser(hamsa);
    centralSystem.addUser(tanmay);

    String eventName = "Group Study";
    String location = "Library";
    boolean online = false;
    DayTime startTime = new DayTime(14, 0, Day.SATURDAY);
    DayTime endTime = new DayTime(16, 0, Day.SATURDAY);
    ArrayList<String> invitees = new ArrayList<>();
    invitees.add(hamsa);
    invitees.add(tanmay);

    centralSystem.createEvent(hamsa, eventName, location, online, startTime, endTime, invitees);

    CentralSystemTextView view = new CentralSystemTextView(centralSystem);
    String actual = view.displayScheduleAsString();

    String expected = "User: Hamsa Madhira\n" +
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
            "  name: Group Study\n" +
            "  time: Saturday : 14:00 -> Saturday : 16:00\n" +
            "  location: Library\n" +
            "  online: false\n" +
            "  invitees: Hamsa Madhira, Tanmay Shah\n" +
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
            "  name: Group Study\n" +
            "  time: Saturday : 14:00 -> Saturday : 16:00\n" +
            "  location: Library\n" +
            "  online: false\n" +
            "  invitees: Hamsa Madhira, Tanmay Shah\n";

    assertEquals(expected, actual);
  }

  /**
   * Tests the display schedule method with multiple users and multiple events.
   */
  @Test
  public void testDisplayScheduleMultiDayEvent() {
    CentralSystem centralSystem = new CentralSystem();
    String userId = "Tanmay Shah";
    centralSystem.addUser(userId);

    String eventName = "Hackathon";
    String location = "ISEC";
    boolean online = true;
    DayTime startTime = new DayTime(10, 0, Day.MONDAY);
    DayTime endTime = new DayTime(11, 0, Day.THURSDAY);
    ArrayList<String> invitees = new ArrayList<>();
    invitees.add(userId);

    centralSystem.createEvent(userId, eventName, location, online, startTime, endTime, invitees);

    CentralSystemTextView view = new CentralSystemTextView(centralSystem);
    String actual = view.displayScheduleAsString();

      String expected = "User: Tanmay Shah\n" +
              "Sunday:\n" +
              "  No events scheduled.\n" +
              "Monday:\n" +
              "  name: Hackathon\n" +
              "  time: Monday : 10:00 -> Thursday : 11:00\n" +
              "  location: ISEC\n" +
              "  online: true\n" +
              "  invitees: Tanmay Shah\n" +
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

  /**
   * Tests the display schedule method with overflowing events.
   */
  @Test
  public void testDisplayScheduleOverflowEvent() {
    CentralSystem centralSystem = new CentralSystem();
    String userId = "Tanmay Shah";
    centralSystem.addUser(userId);

    String eventName = "Hackathon";
    String location = "ISEC";
    boolean online = true;
    DayTime startTime = new DayTime(10, 0, Day.SATURDAY);
    DayTime endTime = new DayTime(11, 0, Day.TUESDAY);
    ArrayList<String> invitees = new ArrayList<>();
    invitees.add(userId);

    centralSystem.createEvent(userId, eventName, location, online, startTime, endTime, invitees);

    CentralSystemTextView view = new CentralSystemTextView(centralSystem);
    String actual = view.displayScheduleAsString();

      String expected = "User: Tanmay Shah\n" +
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
              "  name: Hackathon\n" +
              "  time: Saturday : 10:00 -> Tuesday : 11:00\n" +
              "  location: ISEC\n" +
              "  online: true\n" +
              "  invitees: Tanmay Shah\n";

      assertEquals(expected, actual);
  }


  /**
   * Tests the display schedule method with multiple users and multiple events.
   */
  @Test
  public void testAddEventToMultipleUsersMultipleEvents() {
    CentralSystem centralSystem = new CentralSystem();
    String hamsa = "Hamsa Madhira";
    String tanmay = "Tanmay Shah";
    centralSystem.addUser(hamsa);
    centralSystem.addUser(tanmay);

    String eventName1 = "Group Study";
    String location1 = "Library";
    boolean online1 = false;
    DayTime startTime1 = new DayTime(14, 0, Day.SATURDAY);
    DayTime endTime1 = new DayTime(16, 0, Day.SATURDAY);
    ArrayList<String> invitees1 = new ArrayList<>();
    invitees1.add(hamsa);
    invitees1.add(tanmay);


    centralSystem.createEvent(hamsa, eventName1, location1, online1, startTime1, endTime1, invitees1);

    CentralSystemTextView view = new CentralSystemTextView(centralSystem);
    String actual = view.displayScheduleAsString();

    String eventName2 = "Hackathon";
    String location2 = "ISEC";
    boolean online2 = true;
    DayTime startTime2 = new DayTime(10, 0, Day.MONDAY);
    DayTime endTime2 = new DayTime(11, 0, Day.THURSDAY);
    ArrayList<String> invitees2 = new ArrayList<>();
    invitees2.add(tanmay);

    centralSystem.createEvent(tanmay, eventName2, location2, online2, startTime2,
            endTime2, invitees2);

    String eventName3 = "Neurobiology";
    String location3 = "Shillman Hall";
    boolean online3 = false;
    DayTime startTime3 = new DayTime(1, 35, Day.WEDNESDAY);
    DayTime endTime3 = new DayTime(2, 40, Day.WEDNESDAY);
    ArrayList<String> invitees3 = new ArrayList<>();
    invitees3.add(hamsa);

    centralSystem.createEvent(hamsa, eventName3, location3, online3, startTime3,
            endTime3, invitees3);

    String expected = "User: Hamsa Madhira\n" +
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
            "  name: Group Study\n" +
            "  time: Saturday : 14:00 -> Saturday : 16:00\n" +
            "  location: Library\n" +
            "  online: false\n" +
            "  invitees: Hamsa Madhira, Tanmay Shah\n" +
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
            "  name: Group Study\n" +
            "  time: Saturday : 14:00 -> Saturday : 16:00\n" +
            "  location: Library\n" +
            "  online: false\n" +
            "  invitees: Hamsa Madhira, Tanmay Shah\n";

    assertEquals(expected, actual);
  }



}