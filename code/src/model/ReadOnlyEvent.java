package model;


import java.util.ArrayList;

/**
 * Interface for read-only event data.
 * Provides methods to retrieve information about an event without allowing modification.
 */
public interface ReadOnlyEvent {
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
   * @return true if the event is online, false otherwise.
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
   * Retrieves the ID of the host of the event.
   *
   * @return The ID of the host of the event.
   */
  public String hostID();

  /**
   * Retrieves a list of IDs of invitees to the event.
   *
   * @return The list of IDs of invitees to the event.
   */
  public ArrayList<String> listOfInvitees();

  /**
   * Retrieves the duration of an event.
   */
  public TimeSlot duration();
}
