package model;

/**
 * Command to modify the end time of an event.
 */
public class ModifyEventEndTimeCommand implements EventCommand {
  private IEvent event;
  private final DayTime newEndTime;

  /**
   * Constructs a command to modify the end time of an event.
   *
   * @param event      The event to modify.
   * @param newEndTime The new end time for the event.
   * @throws IllegalArgumentException if newEndTime or event is null.
   */
  public ModifyEventEndTimeCommand(IEvent event, DayTime newEndTime) {
    if (newEndTime == null || event == null) {
      throw new IllegalArgumentException("Can not be null ");
    }
    this.event = event;
    this.newEndTime = newEndTime;
  }

  /**
   * Executes the command to modify the end time of the event.
   *
   * @throws IllegalStateException if modifying the end time would result in a conflicting event.
   */
  @Override
  public void execute() {
    event.changeEndTime(newEndTime);
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
