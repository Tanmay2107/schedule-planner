package model;

import java.util.List;

public class Event {
  private String name;
  private boolean online;

  private String location;
  private DayTime startTime;
  private DayTime endTime;

  private Duration duration;
  private UserSchedule host;
  private List<String> invitees;


  // constructor:
  public Event(String name, String location, boolean online, DayTime startTime, DayTime endTime,
               UserSchedule host, List<String> invitees) {
    if (name == null || location == null || startTime == null
            || endTime == null || host == null || invitees == null) {
      throw new IllegalArgumentException("fields can't be null");
    }
    this.name = name;
    this.location = location;
    this.online = online;
    this.startTime = startTime;
    this.endTime = endTime;
    this.host = host;
    invitees.add(0, host.getUid());
    this.invitees = invitees;
    this.duration = new Duration(startTime,endTime);
  }

  public boolean isEventOverlapping(){
    return false; // handle in duration
  }

  // adding invitee to event
  public void inviteUser(String uid){

  }

  public void deleteEvent(){

  }
}