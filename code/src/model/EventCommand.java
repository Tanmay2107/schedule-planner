package model;

/**
 * Interface for commands that can be executed on events.
 */
public interface EventCommand {

  /**
   * Executes the command on an event.
   */
  void execute();
}

