package controller;

import model.ReadOnlyEvent;

/**
 * Interface defining features for a schedule planner.
 * Implementing classes should provide functionality to load and save data,
 * manage events, and modify event details.
 */
public interface SchedulePlannerFeatures {

  /**
   * Loads user data from an XML file.
   * @param path The path to the XML file.
   */
  public void  loadXMLFile(String path);

  /**
   * Saves data to an XML file.
   * @param path The path to save the XML file.
   */
  public void saveXMLFile(String path);

  /**
   * Adds an invitee to an event.
   * @param inviter The user inviting.
   * @param invitee The user being invited.
   * @param event The event to which the invitee is added.
   */
  public void addInvitee(String inviter, String invitee, ReadOnlyEvent event);

  /**
   * Removes an event from a user's schedule.
   * @param user The user from whose schedule the event is removed.
   * @param event The event to be removed.
   */
  public void removeEvent(String user, ReadOnlyEvent event);

  /**
   * Creates a new event.
   * @param event The event to be created.
   */
  public void createEvent(ReadOnlyEvent event);

  /**
   * Modifies an existing event.
   * @param uid The unique identifier of the event.
   * @param oldEvent The original event.
   * @param newEvent The modified event.
   */
  public void modifyEvent(String uid,ReadOnlyEvent oldEvent, ReadOnlyEvent newEvent);


}
