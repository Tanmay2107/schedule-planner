package model;

/**
 * Command to modify the name of an event.
 */
public class ModifyEventNameCommand implements EventCommand {
  private IEvent event;
  private final String newName;

  /**
   * Constructs a command to modify the name of an event.
   *
   * @param event   The event to modify.
   * @param newName The new name for the event.
   * @throws IllegalArgumentException if newName or event is null.
   */
  public ModifyEventNameCommand(IEvent event, String newName) {
    if (newName == null || event == null) {
      throw new IllegalArgumentException("Can not be null ");
    }
    this.event = event;
    this.newName = newName;
  }

  /**
   * Executes the command to modify the name of the event.
   */
  @Override
  public void execute() {
    event.changeName(newName);
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

