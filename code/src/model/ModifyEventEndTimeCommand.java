package model;

public class ModifyEventEndTimeCommand implements EventCommand {
  private final Event event;
  private final DayTime newEndTime;

  public ModifyEventEndTimeCommand(Event event, DayTime newEndTime) {
    this.event = event;
    this.newEndTime = newEndTime;
  }

  @Override
  public void execute() {
    event.changeEndTime(newEndTime);
  }

}
