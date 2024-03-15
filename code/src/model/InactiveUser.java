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

  /**
   * Modifies the event based on the provided command.
   *
   * @param event   The event to modify.
   * @param command The command for modifying the event.
   * @throws IllegalStateException If an inactive user attempts to modify the event.
   */
  public void modifyEvent(ReadOnlyEvent event, EventCommand command) {
    throw new IllegalStateException("Inactive user cannot modify events.");
  }

  /**
   * Invites a user to the event.
   *
   * @param invitee The user to invite.
   * @param e       The event to invite the user to.
   * @throws IllegalStateException If an inactive user attempts to invite another user to the event.
   */
  @Override
  public void inviteAUserToAnEvent(IUsers invitee, IEvent e) {
    throw new IllegalStateException("Inactive user cannot invite users to events.");
  }


}
