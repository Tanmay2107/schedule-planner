package model;

public class EventModifier {
  private final EventCommand command;

  public EventModifier(EventCommand command) {
    this.command = command;
  }
  public void executeModification() {
    command.execute();
  }
}
