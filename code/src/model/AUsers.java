package model;

import java.util.ArrayList;

/**
 * Abstract class representing users. This class represents active and inactive users.
 */
public abstract class AUsers implements IUsers {
  protected final String uid;

  protected ArrayList<IEvent> events;
  //INVARIANT: This users is invited to and a part of all the events
  //INVARIANT: No two events in the list of events overlap with each other
  //INVARIANT: No two events in the list of events are the same


  /**
   * Constructs a new user with the given user ID.
   * Initializes the list of events.
   *
   * @param uid The user ID.
   */
  public AUsers(String uid) {
    this.uid = uid;
    this.events = new ArrayList<IEvent>();
  }

  /**
   * Retrieves the user ID.
   *
   * @return The user ID.
   */
  public String userID() {
    return this.uid;
  }

  private IEvent getEventForThisUser(IEvent e) {
    IEvent eventForThisUser = null;
    for (IEvent event : events) {
      if (event.eventEquals(e)) {
        eventForThisUser = event;
      }
    }

    if (eventForThisUser == null) {
      throw new IllegalStateException("Event does not exist in user's schedule");
    }

    return eventForThisUser;
  }


  /**
   * Removes the given event from the users schedule.
   *
   * @param e The event to remove.
   */
  public void removeEvent(IEvent e) {
    IEvent eventForThisUser = this.getEventForThisUser(e);


    if (eventForThisUser.isHost(this)) {
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
   *
   * @param e The event to invite the user to.
   * @throws IllegalArgumentException if an event overlaps with an existing event in the
   *     user's schedule.
   */
  public void inviteUser(IEvent e) {
    if (!this.eventAlreadyExists(e)) {
      if (overlappingEventExists(e)) {
        throw new IllegalStateException("Event overlaps with existing event");
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
   *
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
   *
   * @return The activated user.
   */
  public abstract UserSchedule activate();

  /**
   * Retrieves a list of read-only events scheduled for the user.
   *
   * @return The list of read-only events.
   */
  public ArrayList<ReadOnlyEvent> scheduledEvents() {
    return new ArrayList<ReadOnlyEvent>(this.events);
  }


  /**
   * Invites a user to an event.
   *
   * @param invitee The user to invite to the event.
   * @param e       The event to which the user will be invited.
   */
  public void inviteAUserToAnEvent(IUsers invitee, IEvent e) {
    IEvent actualevent = this.getEventForThisUser(e);
    invitee.inviteUser(actualevent);
  }
}
