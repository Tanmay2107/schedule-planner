package view;

import controller.SchedulePlannerFeatures;

import model.ReadOnlyCentralSystem;
import model.ReadOnlyUsers;
import model.Day;
import model.DayTime;

import java.util.ArrayList;
import java.util.List;

import model.ReadOnlyEvent;

/**
 * CentralSystemTextView provides a textual representation of the central system's data
 * and functionality. It allows displaying schedules and event details in a
 * formatted text representation.
 */
public class CentralSystemTextView implements CentralSystemView {

  private ReadOnlyCentralSystem model;

  /**
   * Constructs a CentralSystemTextView with the specified model.
   *
   * @param model The CentralSystemModel to associate with this view.
   */
  public CentralSystemTextView(ReadOnlyCentralSystem model) {
    this.model = model;
  }


  /**
   * Returns a string representation of the CentralSystemModel associated with this view.
   *
   * @return A string representation of the CentralSystemModel.
   */
  @Override
  public String toString() {
    return this.model.toString();
  }

  /**
   * Displays the schedule for a given user as a string.
   *
   * @param userid The ID of the user whose schedule to display.
   * @return A string representation of the user's schedule.
   */
  private String displaySchedule(String userid) {
    ReadOnlyUsers user = this.model.getUser(userid);
    String result = "User: " + user.userID() + "\n";

    for (Day day : Day.values()) {

      result += day + ":\n";

      ArrayList<ReadOnlyEvent> events = filterEventsByDay(user.scheduledEvents(), day);

      if (events.isEmpty()) {
        result += "  No events scheduled.\n";
      } else {
        for (ReadOnlyEvent event : events) {
          displayEventDetails(event);
          result += displayEventDetailsAsString(event);
        }
      }
    }
    return result;
  }


  /**
   * Displays the schedules for all users as a single string.
   *
   * @return A string representation of the schedules for all users.
   */
  @Override
  public String displayScheduleAsString() {
    StringBuilder scheduleString = new StringBuilder();
    ArrayList<String> userIds = this.model.getUserIds();
    for (String userId : userIds) {
      scheduleString.append(displaySchedule(userId));
    }
    return scheduleString.toString();
  }

  /**
   * Filters events by a given day.
   *
   * @param events The list of events to filter.
   * @param day    The day to filter events by.
   * @return The list of events that occur on the specified day.
   */
  private static ArrayList<ReadOnlyEvent> filterEventsByDay(ArrayList<ReadOnlyEvent> events,
                                                            Day day) {
    ArrayList<ReadOnlyEvent> eventsForDay = new ArrayList<>();
    for (ReadOnlyEvent event : events) {
      if (event.startTime().day().dayToInt() == day.dayToInt()) {
        eventsForDay.add(event);
      }
    }
    return eventsForDay;
  }

  /**
   * Displays the details of an event to the standard output.
   *
   * @param event The event to display details for.
   */
  private static void displayEventDetails(ReadOnlyEvent event) {
    System.out.println("  name: " + event.name());
    System.out.println("  time: " + formatEventTime(event.startTime(), event.endTime()));
    System.out.println("  location: " + event.location());
    System.out.println("  online: " + event.online());
    System.out.print("  invitees: " + event.listOfInvitees());
    if (!event.listOfInvitees().isEmpty()) {
      System.out.print(String.join(", ", event.listOfInvitees()));
    } else {
      System.out.print("None");
    }
    System.out.println();
  }

  /**
   * Displays the details of an event as a string.
   *
   * @param event The event to display details for.
   * @return A string representation of the event details.
   */
  private String displayEventDetailsAsString(ReadOnlyEvent event) {
    StringBuilder resultBuilder = new StringBuilder();
    resultBuilder.append("  name: ").append(event.name()).append("\n");
    resultBuilder.append("  time: ").append(formatEventTime(event.startTime(),
            event.endTime())).append("\n");
    resultBuilder.append("  location: ").append(event.location()).append("\n");
    resultBuilder.append("  online: ").append(event.online()).append("\n");

    List<String> invitees = event.listOfInvitees();
    resultBuilder.append("  invitees: ");
    if (!invitees.isEmpty()) {
      resultBuilder.append(String.join(", ", invitees));
    } else {
      resultBuilder.append("None");
    }
    resultBuilder.append("\n");

    return resultBuilder.toString();
  }

  /**
   * Formats the start and end time of an event.
   *
   * @param startTime The start time of the event.
   * @param endTime   The end time of the event.
   * @return A string representation of the formatted event time.
   */
  private static String formatEventTime(DayTime startTime, DayTime endTime) {
    String startTimeString = startTime.toString();
    String endTimeString = endTime.toString();

    startTimeString = startTimeString.substring(0, startTimeString.indexOf(":")) + " " +
            startTimeString.substring(startTimeString.indexOf(":"));
    endTimeString = endTimeString.substring(0, endTimeString.indexOf(":")) + " " +
            endTimeString.substring(endTimeString.indexOf(":"));

    if (startTime.dayEquals(endTime.day())) {
      return startTimeString + " -> " + endTimeString;

    } else {
      return startTimeString + " -> " + endTimeString;
    }
  }

  @Override
  public void addFeatures(SchedulePlannerFeatures features) {
    //DO NOTHING
    // This view does not support adding features

  }

  @Override
  public void makeVisible() {
    //DO NOTHING

    //This view is not a GUI
  }
}