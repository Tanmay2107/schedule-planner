package model;



import java.util.ArrayList;

/**
 * Interface for read-only user data.
 * Provides methods to retrieve user ID and scheduled events.
 */
public interface ReadOnlyUsers {

  /**
   * Retrieves the user ID.
   *
   * @return The user ID.
   */
  public String userID();

  /**
   * Retrieves a list of read-only events scheduled for the user.
   *
   * @return The list of read-only events.
   */
  public ArrayList<ReadOnlyEvent> scheduledEvents();

}
