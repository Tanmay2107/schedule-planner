package model;

import java.time.Duration;
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

  public String eventName(){
    return this.name;
  }

  public String eventLocation(){
    return this.location;
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
  public ArrayList<String> listOfInvitees(){
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

  @Override
  public boolean eventStartsOnDay(Day d) {
    return this.startTime.dayEquals(d);
  }


  void changeName(String newName){
    this.name = newName;
  }

  void changeLocation(String newLocation){
    this.location = newLocation;
  }

  void changeStartTime(DayTime newStartTime){
    this.startTime = newStartTime;
  }

  void changeEndTime(DayTime newEndTime){
    this.endTime = newEndTime;
  }


  public void modifyName(String newName) {
    if (newName== null){
      throw new IllegalArgumentException("Name can't be null");
    }
    EventCommand command = new ModifyEventNameCommand(this, newName);
    EventModifier modifier = new EventModifier(command);
    modifier.executeModification();
  }

  public void modifyLocation(String newLocation) {
    if (newLocation== null){
      throw new IllegalArgumentException("Location can't be null");
    }
    EventCommand command = new ModifyEventLocationCommand(this, newLocation);
    EventModifier modifier = new EventModifier(command);
    modifier.executeModification();
  }

  public void modifyStartTime(DayTime newStartTime) {
    if (newStartTime== null){
      throw new IllegalArgumentException("Start Time can't be null");
    }
    EventCommand command = new ModifyEventStartTimeCommand(this, newStartTime);
    EventModifier modifier = new EventModifier(command);
    modifier.executeModification();
  }

  public void modifyEndTime(DayTime newEndTime) {
    if (newEndTime== null){
      throw new IllegalArgumentException("End Time can't be null");
    }
    EventCommand command = new ModifyEventEndTimeCommand(this, newEndTime);
    EventModifier modifier = new EventModifier(command);
    modifier.executeModification();
  }

  @Override
  public DayTime startTime() {
    return this.startTime;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public boolean online() {
    return this.online;
  }

  @Override
  public String location() {
    return this.location;
  }

  @Override
  public DayTime endTime() {
    return this.endTime;
  }

  @Override
  public TimeSlot duration() {
    return this.duration;
  }

  @Override
  public String hostID() {
    return this.host.userID();
  }


  //Move to textview
  public String toString(){
    String result = "";
    result += "name: " + this.name + "\n";
    //for time
    result += "time: " + this.startTime.toString() + " -> " + this.endTime.toString() + "\n";
    result += "location: " + this.location + "\n";
    result += "online: " + this.online + "\n";

    String stringOfInvitees = "";
    for(IUsers i: invitedUsers){
      stringOfInvitees += i.userID() + "\n";
    }
    result += "invitees: " + stringOfInvitees;
    return result;
  }

}