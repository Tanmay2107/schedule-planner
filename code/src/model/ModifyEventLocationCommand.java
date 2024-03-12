package model;

public class ModifyEventLocationCommand implements EventCommand {
  private final Event event;
  private final String newLocation;

  public ModifyEventLocationCommand(Event event, String newLocation) {
    this.event = event;
    this.newLocation = newLocation;
  }

  @Override
  public void execute() {
    event.changeLocation(newLocation);
  }

}
