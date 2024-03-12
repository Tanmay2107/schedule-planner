package model;

public class ModifyEventNameCommand implements EventCommand {
  private final Event event;
  private final String newName;

  public ModifyEventNameCommand(Event event, String newName) {
    this.event = event;
    this.newName = newName;
  }

  @Override
  public void execute() {
    event.changeName(newName);
  }
}

