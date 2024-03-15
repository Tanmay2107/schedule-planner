package model;

import java.util.ArrayList;

/**
 * Interface representing users.
 */
public interface IUsers extends ReadOnlyUsers{

  /**
   * Retrieves the user ID.
   * @return The user ID.
   */
  public String userID();


  /**
   * Removes the given event from the user's schedule.
   * @param e The event to remove.
   */
  public void removeEvent(IEvent e);

  /**
   * Invites the user to a given event.
   * @param e The event to invite the user to.
   */
  public void inviteUser(IEvent e);

  /**
   * Activates the user.
   * @return The activated user schedule.
   */
  public UserSchedule activate();

  /**
   * Retrieves a list of read-only events scheduled for the user.
   * @return The list of read-only events.
   */
  public ArrayList<ReadOnlyEvent> scheduledEvents();

  /**
   * Generates an XML string representing the user and their events.
   * @return The XML string representation of the user.
   */
  public String giveXMLString();

  /**
   * Checks if there is an overlapping event in the user's schedule with the given event.
   * @param e The event to check for overlap.
   * @return true if an overlapping event exists, false otherwise.
   */
  public boolean overlappingEventExists(IEvent e);

  public void modifyEvent(ReadOnlyEvent event, EventCommand command);

  public void inviteAUserToAnEvent(IUsers invitee, IEvent e);
}
