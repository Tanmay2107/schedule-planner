package model;

import java.util.ArrayList;

public abstract class AUsers implements IUsers{
  protected String uid;
  protected ArrayList<Event> events;
  protected CentralSystem cs;

  public AUsers(String uid, CentralSystem cs){
    this.uid = uid;
    this.events = new ArrayList<Event>();
    this.cs = cs;
  }

  public String userID(){
    return this.uid;
  }


   public abstract void hostEvent(String name, String location, boolean online, DayTime startTime,
                        DayTime endTime, ArrayList<String> invitees);

  public abstract void removeEvent(Event e) ;

  public Event eventAtGiveTime(DayTime dt0) {
    return null;
  }

  // invites a user to a given event
  public void inviteUser(Event e){
    // if the user already has the event in their schedule
    if (events.contains(e)) {
      throw new IllegalStateException("User already has the event in their schedule.");
    }
    if (this.overlappingEventExists(e)) {
      throw new IllegalStateException("This event overlaps with an existing event in the schedule.");
    }

    // adds this event to their schedule.
    events.add(e);
    // user gets added to the event as an invitee
    e.inviteUser(uid);

  }

  /**
   * Checks if any event exists that is overlapping the time period of the given event.
   * @param e
   * @return if there is an overlapping event in this user's schedule.
   */
  protected boolean overlappingEventExists(Event e){
    return false;
  }


  public abstract IUsers activate();



}
