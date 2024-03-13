package model;

public interface ReadOnlyEvent {
  public DayTime startTime();
  public String name();
  public boolean online();
  public String location();
  public DayTime endTime();
  public TimeSlot duration();
  public String hostID();
}
