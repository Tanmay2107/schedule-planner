package view;

import controller.SchedulePlannerFeatures;

/**
 * The ScheduleView interface defines methods that must be implemented by any class
 * representing a view in the schedule planner application.
 */
public interface ScheduleView {

  /**
   * Adds the provided features to the view.
   *
   * @param features The SchedulePlannerFeatures object containing the functionality
   *                 to be added to the view.
   */
  public void addFeatures(SchedulePlannerFeatures features);

  /**
   * Makes the view visible.
   */
  public void makeVisible();

}
