package view;

/**
 * The CentralSystemView interface defines the contract for views in the central system.
 * Views implementing this interface should provide a string representation of the system.
 */
public interface CentralSystemView extends ScheduleView{



  /**
   * Displays the schedule for a given user as a string.
   *
   * @return A string representation of the user's schedule.
   */
  public String displayScheduleAsString();


}
