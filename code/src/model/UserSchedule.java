package model;

import java.util.ArrayList;



public class UserSchedule extends AUsers{

  /**
   * Creates a new user schedule with the given uid.
   * @param uid
   */
  public UserSchedule(String uid){
    super(uid);

  }

  /**
   * Creates a new user with the a given uid and a list of events. Helpful while testing.
   * @param uid
   * @param events
   */
  public UserSchedule(String uid, ArrayList<IEvent> events){
    super(uid);
    this.events = events;
  }



  //same fields as event constructor

  // user can host an event.
  public void hostEvent(String name, String location, boolean online, DayTime startTime,
                        DayTime endTime, ArrayList<IUsers> invitedUsers){
    if (name == null || location == null || startTime == null || endTime == null || invitedUsers== null) {
      throw new IllegalArgumentException("fields can't be null");
    }

    Event e = new Event(name, location, online, startTime, endTime, this);

    //add the host as an invitee
    this.inviteUser(e);

    //invite the other users
    for(IUsers i: invitedUsers){
      i.inviteUser(e);
    }
  }

  /**
   * Removes the given event from the users schedule.
   * @param e
   */
  public void removeEvent(IEvent e){
    if(e.isHost(this)){
      events.remove(e);
      e.removeInvitee(this);
      e.deleteEvent();
    } else {
      events.remove(e);
      e.removeInvitee(this);

    }
  }


  /**
   * Gives the event occuring at the given time.
   * @param dt
   * @return the event that is occuring during the given time
   */
  public Event eventAtGiveTime(DayTime dt){
    return null;
  }

  @Override
  public UserSchedule activate() {
    throw new IllegalStateException("User is already active");
  }

  @Override
  public ArrayList<ReadOnlyEvent> scheduledEvents() {
    return new ArrayList<ReadOnlyEvent>(this.events);
  }

}