package model;

/**
 * Command to modify the start time of an event.
 */
public class ModifyEventStartTimeCommand implements EventCommand {
  private IEvent event;
  private final DayTime newStartTime;

  /**
   * Constructs a command to modify the start time of an event.
   *
   * @param event        The event to modify.
   * @param newStartTime The new start time for the event.
   * @throws IllegalArgumentException if either event or newStartTime is null.
   */
  public ModifyEventStartTimeCommand(IEvent event, DayTime newStartTime) {
    if (newStartTime == null || event == null) {
      throw new IllegalArgumentException("Can not be null ");
    }
    this.event = event;
    this.newStartTime = newStartTime;
  }

  /**
   * Executes the command to modify the start time of the event.
   */
  @Override
  public void execute() {
    event.changeStartTime(newStartTime);
  }

  /**
   * Sets the event for the user.
   *
   * @param e The event to set for the user.
   */
  @Override
  public void giveEvent(IEvent e) {
    this.event = e;
  }

}


