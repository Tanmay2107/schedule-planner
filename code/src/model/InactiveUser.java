package model;

import java.util.ArrayList;


/**
 * Represents an inactive user.
 * An inactive user cannot access their scheduled events or generate XML strings.
 */
public class InactiveUser extends AUsers{

  /**
   * Constructs an inactive user with the given user ID.
   * @param uid The user ID.
   */
  public InactiveUser(String uid) {
    super(uid);
  }


  /**
   * Activates the inactive user, converting them to an active user with their current events.
   * @return The activated user schedule.
   */
  @Override
  public UserSchedule activate() {
    UserSchedule activatedUser = new UserSchedule(uid, events);

    for(IEvent event : events){
      event.replaceInactivatedWithActivatedUser(activatedUser,this);
    }

    return activatedUser;
  }

  /**
   * Retrieves a list of read-only events scheduled for the inactive user.
   * @return The list of read-only events.
   * @throws IllegalStateException as inactive users cannot check scheduled events.
   */


  /**
   * Generates an XML string representing the inactive user and their events.
   * @return The XML string representation of the inactive user.
   * @throws IllegalStateException as inactive users cannot give XML strings.
   */
  @Override
  public String giveXMLString() {
    throw new IllegalStateException("Inactive user can not give XML string");
  }

  public void modifyEvent(ReadOnlyEvent event, EventCommand command){
    throw new IllegalStateException("Inactive user can not modify");
  }

}
