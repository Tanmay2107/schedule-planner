package model;

import java.io.File;
import java.util.ArrayList;


/**
 * Represents a user's schedule. In our system, this is considered an active user.
 */
public class UserSchedule extends AUsers{

  /**
   * Creates a new user schedule with the given uid.
   * @param uid
   */
  public UserSchedule(String uid){
    super(uid);

  }

  /**
   * Creates a new user with the a given uid and a list of events. Helpful while testing.
   * @param uid
   * @param events
   */
  public UserSchedule(String uid, ArrayList<IEvent> events){
    super(uid);
    this.events = events;
  }

  /**
   * Activates the user schedule.
   * This method is overridden to throw an exception as the user schedule is already active.
   * @return The activated user schedule.
   * @throws IllegalStateException if the user schedule is already active.
   */
  @Override
  public UserSchedule activate() {
    throw new IllegalStateException("User is already active");
  }


  /**
   * Generates an XML string representing the user schedule and its events.
   * @return The XML string representation of the user schedule.
   */
  @Override
  public String giveXMLString() {
    String result  = "" + "\n";
    for(IEvent e: events){
      result += e.giveXMLString();
    }
    return result;
  }

  public void modifyEvent(ReadOnlyEvent e, EventCommand command){
    IEvent eventForThisUser = null;
    for (IEvent event : events) {
      if (event.eventEquals(e)) {
        eventForThisUser = event;
      }
    }

    if(eventForThisUser == null){
      throw new IllegalArgumentException("Event does not exist in user's schedule");
    }
    command.giveEvent(eventForThisUser);
    EventModifier modifier = new EventModifier(command);
    modifier.executeModification();
  }

}