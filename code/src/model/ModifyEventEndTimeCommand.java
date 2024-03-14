package model;

/**
 * Command to modify the end time of an event.
 */
public class ModifyEventEndTimeCommand implements EventCommand {
  private IEvent event;
  private final DayTime newEndTime;

  /**
   * Constructs a command to modify the end time of an event.
   * @param event The event to modify.
   * @param newEndTime The new end time for the event.
   * @throws IllegalArgumentException if newEndTime or event is null.
   */
  public ModifyEventEndTimeCommand(IEvent event, DayTime newEndTime) {
    if (newEndTime == null || event == null){
      throw new IllegalArgumentException("Can not be null ");
    }
    this.event = event;
    this.newEndTime = newEndTime;
  }

  /**
   * Executes the command to modify the end time of the event.
   * @throws IllegalStateException if modifying the end time would result in a conflicting event.
   */
  @Override
  public void execute() {
    if (event.eventConflict(new TimeSlot(event.startTime(), newEndTime))) {
      event.changeEndTime(newEndTime);
    } else {
      throw new IllegalStateException("Cannot modify end time because of a conflicting event");
    }
  }

  @Override
  public void giveEvent(IEvent e){
    this.event = e;
  }

}
