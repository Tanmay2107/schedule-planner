package model;

/**
 * Command to modify the online status of an event.
 */
public class ModifyEventOnlineStatus implements EventCommand {
  private IEvent event;
  /**
   * Constructs a command to modify the online status of an event.
   * @param event The event to modify.
   * @throws IllegalArgumentException if event is null.
   */
  public ModifyEventOnlineStatus(IEvent event){
    if (event == null){
      throw new IllegalArgumentException("Can not be null ");
    }
    this.event = event;
  }

  /**
   * Executes the command to modify the online status of the event.
   */
  @Override
  public void execute() {
    event.changeOnlineStatus();
  }


  @Override
  public void giveEvent(IEvent e){
    this.event = e;
  }
}
