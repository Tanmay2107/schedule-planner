package model;

import java.util.ArrayList;

public abstract class AUsers implements IUsers{
  protected String uid;
  protected ArrayList<IEvent> events;


  public AUsers(String uid){
    this.uid = uid;
    this.events = new ArrayList<IEvent>();
  }

  public String userID(){
    return this.uid;
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

  public Event eventAtGiveTime(DayTime dt0) {
    return null;
  }

  // invites a user to a given event
  public void inviteUser(IEvent e){
    if (overlappingEventExists(e)) {
      throw new IllegalArgumentException("Event overlaps with existing event");
    }
    events.add(e);
    e.addInvitee(this);
  }

  protected boolean overlappingEventExists(IEvent e) {

    int overLappingEvents = 0;
    for (IEvent event : events) {
      if (event.eventConflict(e)) {
        overLappingEvents++;
      }
    }

    return overLappingEvents > 0;
  }

  public abstract UserSchedule activate();


  public ArrayList<ReadOnlyEvent> scheduledEvents() {
    return new ArrayList<ReadOnlyEvent>(this.events);
  }
}
