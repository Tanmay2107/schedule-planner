package model;

import java.util.ArrayList;


/**
 * Represents a user's schedule. In our system, this is considered an active user.
 */
public class UserSchedule extends AUsers {

  /**
   * Creates a new user schedule with the given uid.
   *
   * @param uid Unique identifier for the user.
   */
  public UserSchedule(String uid) {
    super(uid);

  }

  /**
   * Creates a new user with the a given uid and a list of events. Helpful while testing.
   *
   * @param uid   Unique identifier for the user.
   * @param events List of events for the user.
   */
  public UserSchedule(String uid, ArrayList<IEvent> events) {
    super(uid);
    this.events = events;
  }

  /**
   * Activates the user schedule.
   * This method is overridden to throw an exception as the user schedule is already active.
   *
   * @return The activated user schedule.
   * @throws IllegalStateException if the user schedule is already active.
   */
  @Override
  public UserSchedule activate() {
    throw new IllegalStateException("User is already active");
  }


  /**
   * Generates an XML string representing the user schedule and its events.
   *
   * @return The XML string representation of the user schedule.
   */
  @Override
  public String giveXMLString() {
    String result = "" + "\n";
    for (IEvent e : events) {
      result += e.giveXMLString();
    }
    return result;
  }

  /**
   * Modifies the event based on the provided command.
   *
   * @param e       The event to modify.
   * @param command The command for modifying the event.
   * @throws IllegalArgumentException If the event does not exist in the user's schedule.
   */
  public void modifyEvent(ReadOnlyEvent e, EventCommand command) {
    IEvent eventForThisUser = null;
    for (IEvent event : events) {
      if (event.eventEquals(e)) {
        eventForThisUser = event;
      }
    }

    if (eventForThisUser == null) {
      throw new IllegalArgumentException("Event does not exist in user's schedule");
    }
    command.giveEvent(eventForThisUser);
    EventModifier modifier = new EventModifier(command);
    modifier.executeModification();
  }

}