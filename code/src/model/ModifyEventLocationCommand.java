package model;

/**
 * Command to modify the location of an event.
 */
public class ModifyEventLocationCommand implements EventCommand {
  private IEvent event;

  private final String newLocation;

  /**
   * Constructs a command to modify the location of an event.
   * @param event The event to modify.
   * @param newLocation The new location for the event.
   * @throws IllegalArgumentException if newLocation or event is null.
   */
  public ModifyEventLocationCommand(IEvent event, String newLocation) {
    if (newLocation == null || event == null){
      throw new IllegalArgumentException("Can not be null ");
    }
    this.event = event;
    this.newLocation = newLocation;
  }

  /**
   * Executes the command to modify the location of the event.
   */
  @Override
  public void execute() {
    event.changeLocation(newLocation);
  }


  /**
   * Sets the event for the user.
   *
   * @param e The event to set for the user.
   */
  @Override
  public void giveEvent(IEvent e){
    this.event = e;
  }

}
