package model;

import java.util.ArrayList;
import java.util.List;

public class InputEvent implements ReadOnlyEvent{

  private String name;

  private boolean online;
  private String location;

  private DayTime startTime;

  private DayTime endTime;

  private TimeSlot duration;
  //INVARIANT: duration represents the time slot between startTime and endTime

  private List<String> invitedUsers;


  private String hostId;

  public InputEvent(String name, boolean online, String location, DayTime startTime,
                    DayTime endTime, String hostId,List<String> invitedUsers) {
    this.name = name;
    this.online = online;
    this.location = location;
    this.startTime = startTime;
    this.endTime = endTime;
    this.hostId = hostId;
    this.invitedUsers = invitedUsers;
    this.duration = new TimeSlot(startTime, endTime);
  }
  @Override
  public DayTime startTime() {
    return null;
  }

  @Override
  public String name() {
    return null;
  }

  @Override
  public boolean online() {
    return false;
  }

  @Override
  public String location() {
    return null;
  }

  @Override
  public DayTime endTime() {
    return null;
  }

  @Override
  public String hostID() {
    return null;
  }

  @Override
  public ArrayList<String> listOfInvitees() {
    return null;
  }

  @Override
  public TimeSlot duration() {
    return null;
  }

  @Override
  public String toString() {
    String result = "";
    result += "name: " + this.name + "\n";
    //for time
    result += "time: " + this.startTime.toString() + " -> " + this.endTime.toString() + "\n";
    result += "location: " + this.location + "\n";
    result += "online: " + this.online + "\n";

    String stringOfInvitees = "";
    for (String str : invitedUsers) {
      stringOfInvitees += str + "\n";
    }
    result += "invitees: " + stringOfInvitees;
    return result;
  }
}
