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