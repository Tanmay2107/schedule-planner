package model;

/**
 * Interface for commands that can be executed on events.
 */
public interface EventCommand {

  /**
   * Executes the command on an event.
   */
  void execute();



  /*
  * Takes in the event to execute the command on
   */
  public void giveEvent(IEvent e);
}

