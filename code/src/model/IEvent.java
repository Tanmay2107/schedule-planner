package model;


import java.util.ArrayList;

/**
 * Interface representing an event.
 */
public interface IEvent extends ReadOnlyEvent {


  /**
   * Adds the given user to the list of invitees for this event.
   *
   * @param u The user to add to the list of invitees.
   */
  public void addInvitee(IUsers u);


  /**
   * Deletes the event from the system by removing it from every invitee's schedule.
   */
  public void deleteEvent();


  /**
   * Checking if the given user is the host.
   *
   * @param u The user to check if they are the host.
   * @return true if given user is the host of this event.
   */
  public boolean isHost(IUsers u);

  /**
   * Checks if the given event conflicts with this event.
   *
   * @param e The event to check for conflicts.
   * @return true if the given event conflicts with this event.
   */
  public boolean eventConflict(ReadOnlyEvent e);

  /**
   * Checks if the given time slot conflicts with this event.
   *
   * @param ts The time slot to check for conflicts.
   * @return true if the given time slot conflicts with this event.
   */
  public boolean eventConflict(TimeSlot ts);

  /**
   * Returns a list of the uids of the invitees for this event.
   *
   * @return a list of uids of the invitees for this event.
   */
  public ArrayList<String> listOfInvitees();

  /**
   * Removes the given invitee from the event.
   *
   * @param u The user to remove from the event.
   * @throws IllegalArgumentException if the user is not invited or null.
   */
  public void removeInvitee(IUsers u);

  /**
   * Returns a string representation of the event.
   *
   * @return A string representation of the event.
   */
  public String toString();

  /**
   * Retrieves the start time of the event.
   *
   * @return The start time of the event.
   */
  public DayTime startTime();

  /**
   * Retrieves the name of the event.
   *
   * @return The name of the event.
   */
  public String name();

  /**
   * Checks if the event is online.
   *
   * @return True if the event is online, false otherwise.
   */
  public boolean online();

  /**
   * Retrieves the location of the event.
   *
   * @return The location of the event.
   */
  public String location();

  /**
   * Retrieves the end time of the event.
   *
   * @return The end time of the event.
   */
  public DayTime endTime();

  /**
   * Retrieves the duration of the event.
   *
   * @return The duration of the event.
   */
  public TimeSlot duration();

  /**
   * Retrieves the host ID of the event.
   *
   * @return The host ID of the event.
   */
  public String hostID();

  /**
   * Generates an XML string representing the event.
   *
   * @return The XML string representation of the event.
   */
  String giveXMLString();

  /**
   * Changes the name of the event.
   *
   * @param newName The new name for the event.
   */
  public void changeName(String newName);

  /**
   * Changes the location of the event.
   *
   * @param newLocation The new location for the event.
   */
  public void changeLocation(String newLocation);

  /**
   * Changes the start time of the event.
   *
   * @param newStartTime The new start time for the event.
   */
  public void changeStartTime(DayTime newStartTime);

  /**
   * Changes the end time of the event.
   *
   * @param newEndTime The new end time for the event.
   */
  public void changeEndTime(DayTime newEndTime);

  /**
   * Changes the online status of the event.
   */
  public void changeOnlineStatus();

  /**
   * Checks if this event is equal to another event.
   *
   * @param e The event to compare with.
   * @return True if the events are equal, false otherwise.
   */
  public boolean eventEquals(ReadOnlyEvent e);

  /**
   * Replaces an inactive user with an activated user in the list of invited users for this event.
   *
   * @param activatedUser   The activated user to replace the inactive user.
   * @param inactivatedUser The inactive user to be replaced.
   */
  public void replaceInactivatedWithActivatedUser(UserSchedule activatedUser, InactiveUser
          inactivatedUser);

}
