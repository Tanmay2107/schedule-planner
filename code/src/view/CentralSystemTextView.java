package view;

import model.CentralSystemModel;
import model.ReadOnlyCentralSystem;
import model.ReadOnlyUsers;
import model.Day;
import model.DayTime;
import java.util.ArrayList;
import model.ReadOnlyEvent;

public class CentralSystemTextView implements CentralSystemView{

  CentralSystemModel model;

  public CentralSystemTextView(CentralSystemModel model) {
    this.model = model;
  }

  @Override
  public String toString() {
    return this.model.toString();
  }

  public static void displaySchedule(ReadOnlyUsers user) {
    System.out.println("User: " + user.userID());

    for (Day day : Day.values()) {
      System.out.println(day + ":");

      ArrayList<ReadOnlyEvent> events = filterEventsByDay(user.scheduledEvents(), day);

      if (events.isEmpty()) {
        System.out.println("  No events scheduled.");
      } else {
        for (ReadOnlyEvent event : events) {
          displayEventDetails(event);
        }
      }
    }
  }

  private static ArrayList<ReadOnlyEvent> filterEventsByDay(ArrayList<ReadOnlyEvent> events, Day day) {
    ArrayList<ReadOnlyEvent> eventsForDay = new ArrayList<>();
    for (ReadOnlyEvent event : events) {
      if (event.startTime().day().dayToInt() == day.dayToInt()) {
        eventsForDay.add(event);
      }
    }
    return eventsForDay;
  }

  private static void displayEventDetails(ReadOnlyEvent event) {
    System.out.println("  name: " + event.name());
    System.out.println("  time: " + formatEventTime(event.startTime(), event.endTime()));
    System.out.println("  location: " + event.location());
    System.out.println("  online: " + event.online());
    System.out.println("  invitees: " + event.hostID());
    System.out.println();
  }

  private static String formatEventTime(DayTime startTime, DayTime endTime) {
    String startTimeString = startTime.toString();
    String endTimeString = endTime.toString();

    startTimeString = startTimeString.substring(0, startTimeString.indexOf(":")) + " " +
            startTimeString.substring(startTimeString.indexOf(":"));
    endTimeString = endTimeString.substring(0, endTimeString.indexOf(":")) + " " +
            endTimeString.substring(endTimeString.indexOf(":"));

    if (startTime.dayEquals(endTime.day())) {
      return startTimeString + " -> " + endTimeString;

    }
    else{
      return startTimeString + " -> " + endTime.day() + ": " + endTimeString;
    }
  }
}
