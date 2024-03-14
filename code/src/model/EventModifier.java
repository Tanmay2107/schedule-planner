package model;

/**
 * A class responsible for executing modifications on events based on provided commands.
 */
public class EventModifier {
  private final EventCommand command;

  /**
   * Constructs an EventModifier with the specified command.
   * @param command The command representing the modification to be executed.
   */
  public EventModifier(EventCommand command) {
    this.command = command;
  }

  /**
   * Executes the modification specified by the command.
   */
  public void executeModification() {
    command.execute();
  }
}
