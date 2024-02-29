package model;

import java.util.ArrayList;


public class UserSchedule {
  private String uid;
  private ArrayList<Event> events;

  /**
   * Creates a new user schedule with the given uid.
   * @param uid
   */
  public UserSchedule(String uid){
    this.uid = uid;
    this.events = new ArrayList<Event>();
  }

  //only for testing

  /**
   * Creates a new user with the a given uid and a list of events. Helpful while testing.
   * @param uid
   * @param events
   */
  public UserSchedule(String uid, ArrayList<Event> events){
    if (events == null){
      throw new IllegalArgumentException("List of events cannot be null");
    }
    this.uid = uid;
    this.events = events;
  }

  // getter method for the uid
  public String getUid(){
    return this.uid;
  }

  //same fields as event constructor

  public void hostEvent(String name, String location, boolean online, DayTime startTime,
                        DayTime endTime, ArrayList<String> invitees){
    Event currentEvent = new Event(name, location, online,startTime,endTime, this,invitees);
    if (this.overlappingEventExists(currentEvent)){
      throw new IllegalStateException("Event is overlapping with another event");
    }


  }

  //

  /**
   * Removes the given event from the users schedule.
   * @param e
   */
  public void removeEvent(Event e){
    events.remove(e);
    //if host change functionality
  }

  /**
   * Checks if any event exists that is overlapping the time period of the given event.
   * @param e
   * @return if there is an overlapping event in this user's schedule.
   */
  private boolean overlappingEventExists(Event e){
    return false;
  }

  /**
   * Gives the event occuring at the given time.
   * @param dt
   * @return the event that is occuring during the given time
   */
  public Event eventAtGiveTime(DayTime dt){
    return null;
  }



  // invites a user to a given event
  public void inviteUser(Event e, String u){


  }






}
