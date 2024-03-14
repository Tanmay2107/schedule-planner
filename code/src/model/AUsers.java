package model;

import java.util.ArrayList;

/**
 * Abstract class representing users. This class represents active and inactive users.
 */
public abstract class AUsers implements IUsers{
  protected String uid;
  protected ArrayList<IEvent> events;


  /**
   * Constructs a new user with the given user ID.
   * Initializes the list of events.
   * @param uid The user ID.
   */
  public AUsers(String uid){
    this.uid = uid;
    this.events = new ArrayList<IEvent>();
  }

  /**
   * Retrieves the user ID.
   * @return The user ID.
   */
  public String userID(){
    return this.uid;
  }


  /**
   * Removes the given event from the users schedule.
   * @param e
   */
  public void removeEvent(IEvent e){
    IEvent eventForThisUser = null;
    for (IEvent event : events) {
      if (event.eventEquals(e)) {
        eventForThisUser = event;
      }
    }

    if(eventForThisUser == null){
      throw new IllegalArgumentException("Event does not exist in user's schedule");
    }


    if(eventForThisUser.isHost(this)){
      events.remove(eventForThisUser);
      eventForThisUser.removeInvitee(this);
      eventForThisUser.deleteEvent();
    } else {
      events.remove(eventForThisUser);
      eventForThisUser.removeInvitee(this);

    }
  }

  /**
   * Invites the user to a given event.
   * @param e The event to invite the user to.
   * @throws IllegalArgumentException if an event overlaps with an existing event in the user's schedule.
   */
  public void inviteUser(IEvent e) {
    if (this.eventAlreadyExists(e)) {
      if (overlappingEventExists(e)) {
        throw new IllegalArgumentException("Event overlaps with existing event");
      }
      events.add(e);
      e.addInvitee(this);
    }
  }

  private boolean eventAlreadyExists(IEvent e) {
    for (IEvent event : events) {
      if (event.eventEquals(e)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if there is an overlapping event in the user's schedule with the given event.
   * @param e The event to check for overlap.
   * @return true if an overlapping event exists, false otherwise.
   */
  public boolean overlappingEventExists(IEvent e) {

    int overLappingEvents = 0;
    for (IEvent event : events) {
      if (event.eventConflict(e)) {
        overLappingEvents++;
      }
    }
    return overLappingEvents > 0;
  }

  /**
   * Activates the user.
   * Subclasses must implement this method to return an instance of UserSchedule.
   * @return The activated user.
   */
  public abstract UserSchedule activate();

  /**
   * Retrieves a list of read-only events scheduled for the user.
   * @return The list of read-only events.
   */
  public ArrayList<ReadOnlyEvent> scheduledEvents() {
    return new ArrayList<ReadOnlyEvent>(this.events);
  }
}
