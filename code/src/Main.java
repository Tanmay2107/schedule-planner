import java.util.ArrayList;
import java.util.List;

import model.CentralSystem;
import model.Day;
import model.DayTime;
import model.Event;
import model.IEvent;
import model.IUsers;
import model.UserSchedule;

public class Main {
    public static void main(String[] args) {
      // temp examples to see whether displaySchedulePlanner and displayEventDetails work
      DayTime startTime = new DayTime(9, 50, Day.TUESDAY);
      DayTime endTime = new DayTime(11, 35, Day.TUESDAY);
      IUsers user1 = new UserSchedule("Hamsa");
      IUsers user2 = new UserSchedule("Tanmay");
      UserSchedule host = new UserSchedule("Nunez");

      ArrayList<IUsers> invitees = new ArrayList<IUsers>();
      invitees.add(user1);
      invitees.add(user2);
      invitees.add(host);
//

      IEvent event1 = new Event("CS 3500", "Churchill 101", true,
              startTime, endTime, host, invitees);
      List<IUsers> users = List.of(user1, user2);
      List<IEvent> events = List.of(event1);
      displaySchedulePlanner(users, events);
    }

    // Display schedule planner text view
    private static void displaySchedulePlanner(List<IUsers> users, List<IEvent> events) {
      System.out.println("Schedule Planner:");

      for (IUsers user : users) {
        System.out.println("User: " + user.userID());

        List<IEvent> userEvents = user.scheduledEvents();
        if (!userEvents.isEmpty()) {
          System.out.println("  Events:");
          for (IEvent event : userEvents) {
            displayEventDetails(event);
          }
        }
        else {
          System.out.println("  No events scheduled.");
        }
        System.out.println();
      }
    }

    // Display event details
    private static void displayEventDetails(IEvent event) {
      System.out.println("    Event: " + event.name());
      System.out.println("      Location: " + event.location());
      System.out.println("      Time: " + event.startTime() + " to " + event.endTime());
      System.out.println("      Host: " + event.hostID());

      // Displays invitees
      List<String> invitees = event.listOfInvitees();
      if (!invitees.isEmpty()) {
        System.out.println("      Invitees: " + String.join(", ", invitees));
      }
      System.out.println();
    }
}