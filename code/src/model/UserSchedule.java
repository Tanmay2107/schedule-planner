package model;

import java.util.ArrayList;
//


public class UserSchedule extends AUsers{

  /**
   * Creates a new user schedule with the given uid.
   * @param uid
   */
  public UserSchedule(String uid, CentralSystem cs){
    super(uid, cs);

  }

  /**
   * Creates a new user with the a given uid and a list of events. Helpful while testing.
   * @param uid
   * @param events
   */
  public UserSchedule(String uid, ArrayList<Event> events, CentralSystem cs){
    super(uid, cs);
    this.events = events;
  }



  //same fields as event constructor

  // user can host an event.
  public void hostEvent(String name, String location, boolean online, DayTime startTime,
                        DayTime endTime, ArrayList<String> invitees){
    Event currentEvent = new Event(name, location, online,startTime,endTime, this, invitees,
            this.cs);
    if (this.overlappingEventExists(currentEvent)) {
      throw new IllegalStateException("Event is overlapping with another event");
    }
  }

  //host event helper
  private void hostHelper(String invitee) {
  //
  }
  /**
   * Removes the given event from the users schedule.
   * @param e
   */
  public void removeEvent(Event e){
    //remove this event from my events
    this.events.remove(e);

    //remove me as an invitee
    e.removeInvitee(this);

    //if host then  delete the event
    if(e.isHost(this)){

      //delete this event for every user who is invited to it
      e.deleteEvent();
    }

  }
  ///
  /**
   * Gives the event occuring at the given time.
   * @param dt
   * @return the event that is occuring during the given time
   */
  public Event eventAtGiveTime(DayTime dt){
    return null;
  }

  @Override
  public IUsers activate() {
    throw new IllegalStateException("User is already active");
  }

}