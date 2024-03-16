package view;

/**
 * The CentralSystemView interface defines the contract for views in the central system.
 * Views implementing this interface should provide a string representation of the system.
 */
public interface CentralSystemView {

  /**
   * Returns a string representation of the central system.
   *
   * @return A string representation of the central system.
   */
  public String toString();

  /**
   * Displays the schedule for a given user as a string.
   *
   * @return A string representation of the user's schedule.
   */
  public String displayScheduleAsString();
}
