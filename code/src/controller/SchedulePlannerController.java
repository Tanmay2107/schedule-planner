package controller;

public interface SchedulePlannerController extends  SchedulePlannerFeatures{

  /**
   * Start the schedule planner application.
   */
  void start();

  /**
   * Load a schedule to the central system from a file.
   * @param filepath the path to the file to load the schedule from
   */
  void loadXML(String filepath);

  /**
   * Save the current schedule to the file it is supposed to be stored to.
   */
  void save();



}
