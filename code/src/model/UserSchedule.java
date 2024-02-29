package model;

import java.util.ArrayList;
import java.util.List;

public class UserSchedule {
  String uid;
  List<Event> events;

  public UserSchedule(String uid){
    this.uid = uid;
    this.events = new ArrayList<Event>();
  }

  //only for testing
  public UserSchedule(String uid,List<Event> events){
    if (events == null){
      throw new IllegalArgumentException("List of events cannot be null");
    }
    this.uid = uid;
    this.events = events;
  }

  //same fields as event constructor
  public void hostEvent(){}

  //
  public void removeEvent(Event e){
    events.remove(e);
    //if host change functionality
  }

  private boolean overlappingEventExists(Event e){
    return false;
  }

  public Event eventAtGiveTime(DayTime dt){
    return null;
  }

  public void inviteUser(Event e, UserSchedule u){

  }

  public void userInvitedToEvent(Event e){}




}
