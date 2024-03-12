package model;

public class ModifyEventStartTimeCommand implements EventCommand {
  private final Event event;
  private final DayTime newStartTime;

  public ModifyEventStartTimeCommand(Event event, DayTime newStartTime) {
    this.event = event;
    this.newStartTime = newStartTime;
  }

  @Override
  public void execute() {
    event.changeStartTime(newStartTime);
  }

}


