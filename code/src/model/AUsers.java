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


   public abstract void hostEvent(String name, String location, boolean online, DayTime startTime,
                                  DayTime endTime, ArrayList<IUsers> invitedUsers);

  public abstract void removeEvent(IEvent e) ;

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



}
