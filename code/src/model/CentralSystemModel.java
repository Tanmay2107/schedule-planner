package model;

import java.util.ArrayList;

/**
 * The interface representing the central system model.
 */
public interface CentralSystemModel extends ReadOnlyCentralSystem {

  /**
   * Adds a new user with the specified user ID to the central system.
   *
   * @param uid The user ID of the user to add.
   */
  public void addUser(String uid) ;


  /**
   * Schedules an event with the specified details.
   *
   * @param hostUid   The user ID of the event host.
   * @param name      The name of the event.
   * @param location  The location of the event.
   * @param online    Indicates if the event is online or not.
   * @param startTime The start time of the event.
   * @param endTime   The end time of the event.
   * @param invitees  The list of user IDs of invitees to the event.
   */
  public void createEvent(String hostUid, String name, String location, boolean online,
                          DayTime startTime, DayTime endTime, ArrayList<String> invitees);


  /**
   * Schedules an event with the specified details.
   *
   * @param name      The name of the event.
   * @param location  The location of the event.
   * @param online    Indicates if the event is online or not.
   * @param startTime The start time of the event.
   * @param endTime   The end time of the event.
   * @param invitees  The list of user IDs of invitees to the event.
   */
  public void createEvent(String name, String location, boolean online,
                          DayTime startTime, DayTime endTime, ArrayList<String> invitees);


  /**
   * Removes the specified event associated with the given user ID from the central system.
   *
   * @param uid The user ID of the user who owns the event.
   * @param e   The event to remove.
   */
  public void removeEvent(String uid, IEvent e);

  /**
   * Modifies the specified event using the provided command.
   *
   * @param event   The event to modify.
   * @param command The command to execute for modifying the event.
   * @param uid   The user id represented as String
   */
  public void modifyEvent(Event event, EventCommand command, String uid);

  /**
   * Retrieves a list of user IDs registered in the central system.
   *
   * @return An ArrayList containing the user IDs.
   */
  public ArrayList<String> getUserIds();

  /**
   * Writes the user's data to an XML file specified by the given path.
   *
   * @param uid  The user ID of the user whose data is to be written.
   * @param path The file path where the XML file will be written.
   */
  public void writeUserToXMLFile(String uid, String path);

  /**
   * Loads user data from an XML file specified by the given path.
   *
   * @param xmlPath The file path of the XML file containing user data.
   */
  public void loadUserFromXML(String xmlPath);


  /**
   * Retrieves a list of inactive users in the system.
   *
   * @return An ArrayList containing read-only representations of inactive users.
   */
  public ArrayList<ReadOnlyUsers> giveInactiveUsers();

  /**
   * Invites a user to an event.
   *
   * @param inviterUid The user ID of the inviter.
   * @param inviteeUid The user ID of the invitee.
   * @param event      The event to which the user is invited.
   * @throws IllegalArgumentException if any of the input fields are null.
   */
  public void inviteUserToEvent(String inviterUid, String inviteeUid, IEvent event);
}
//...