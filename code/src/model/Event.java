package model;

import java.util.ArrayList;
import java.util.List;

public class Event implements IEvent{
  private String name;
  private boolean online;

  private String location;
  private DayTime startTime;
  private DayTime endTime;

  private TimeSlot duration;
  private UserSchedule host;


  private List<IUsers> invitedUsers;




  // constructor:
  public Event(String name, String location, boolean online, DayTime startTime, DayTime endTime,
               UserSchedule host,  List<IUsers> invitedUsers) {
    if (name == null || location == null || startTime == null
            || endTime == null || host == null || invitedUsers == null) {
      throw new IllegalArgumentException("fields can't be null");
    }
    this.name = name;
    this.location = location;
    this.online = online;
    this.startTime = startTime;
    this.endTime = endTime;
    this.host = host;
    this.duration = new TimeSlot(startTime, endTime);
    this.invitedUsers = invitedUsers;

  }

  public Event(String name, String location, boolean online, DayTime startTime, DayTime endTime,
               UserSchedule host) {
    if (name == null || location == null || startTime == null
            || endTime == null || host == null || invitedUsers == null) {
      throw new IllegalArgumentException("fields can't be null");
    }
    this.name = name;
    this.location = location;
    this.online = online;
    this.startTime = startTime;
    this.endTime = endTime;
    this.host = host;
    this.duration = new TimeSlot(startTime, endTime);
    this.invitedUsers = new ArrayList<IUsers>();
  }





  @Override
  public void addInvitee(IUsers u) {
    if (u == null) {
      throw new IllegalArgumentException("User can't be null");
    }

    if(invitedUsers.contains(u)){
      throw new IllegalArgumentException("User is already invited");
    }

    invitedUsers.add(u);
  }



  @Override
  public void deleteEvent() {
    //delete event
    while(invitedUsers.size() > 0){
      invitedUsers.get(0).removeEvent(this);
    }

  }
  //




  @Override
  public boolean isHost(UserSchedule u){
    return u.userID().equals(this.host.userID());
  }

  @Override
  public boolean isEventAtGivenTime(DayTime d){
    return duration.timeInDuration();
  }


  @Override
  public boolean eventConflict(IEvent e){
    return e.eventConflict(this.duration);
  }

  @Override
  public boolean eventConflict(TimeSlot ts){
    return ts.conflict(this.duration);
  }

  @Override
  public ArrayList<String> getInvitees(){
    ArrayList<String> invitees = new ArrayList<String>();
    for(IUsers i: invitedUsers){
      invitees.add(i.userID());
    }
    return invitees;
  }

  @Override
  public void removeInvitee(IUsers u){
    if(u == null){
      throw new IllegalArgumentException("User can't be null");
    }
    if(!invitedUsers.contains(u)){
      throw new IllegalArgumentException("User is not invited");
    }
    invitedUsers.remove(u);
  }


}