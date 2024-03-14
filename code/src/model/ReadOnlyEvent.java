package model;

import java.util.ArrayList;
import java.util.List;

public interface ReadOnlyEvent {
  public DayTime startTime();
  public String name();
  public boolean online();
  public String location();
  public DayTime endTime();
  public TimeSlot duration();
  public String hostID();
  public ArrayList<String> listOfInvitees();
}
