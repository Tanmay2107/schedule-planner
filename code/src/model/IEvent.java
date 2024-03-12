package model;

import java.util.ArrayList;

public interface IEvent {


  /**
   * Adds the given user to the list of invitees for this event.
   * @param u
   */
  public void addInvitee(IUsers u);




  /**
   * Deletes the event from the system by removing it from every invitee's schedule.
   */
  public void deleteEvent();



  /**
   * Checking if the given user is the host.
   * @param u
   * @return true if given userschedule is the host of this event.
   */
  public boolean isHost(UserSchedule u);

  /**
   * Returns is the event occurs at the given time.
   * @return true if this event occurs at the given time.
   */
  public boolean isEventAtGivenTime(DayTime d);


  /**
   * Checks if the given event conflicts with this event.
   * @param e
   * @return true if the given event conflicts with this event.
   */
  public boolean eventConflict(IEvent e);

  /**
   * Checks if the given time slot conflicts with this event.
   * @param ts
   * @return true if the given time slot conflicts with this event.
   */
  public boolean eventConflict(TimeSlot ts);

  /**
   * Returns a list of the uids of the invitees for this event.
   * @return a list of uids of the invitees for this event.
   */
  public ArrayList<String> getInvitees();

  /**
   * Removes the given invitee from the event
   * @return removes the given invitee from the event
   */
  public void removeInvitee(IUsers u);
}
