package model;


import java.util.ArrayList;
import java.util.List;

/**
 * Represents an event in the system.
 */
public class Event implements IEvent {
  private String name;
  //INVARIANT: name != null

  private boolean online;
  private String location;

  private DayTime startTime;

  private DayTime endTime;

  private TimeSlot duration;
  //INVARIANT: duration represents the time slot between startTime and endTime

  private List<IUsers> invitedUsers;
  //INVARIANT: invitedUsers contains all the users that are invited to the event
  //INVARIANT: No two users in the list of invitedUsers are the same
  //IVARIANT: All the users in the list have this event as a part of their schedule

  private String hostId;


  /**
   * Constructs an event with the specified attributes.
   *
   * @param name         The name of the event.
   * @param location     The location of the event.
   * @param online       A boolean indicating if the event is online.
   * @param startTime    The start time of the event.
   * @param endTime      The end time of the event.
   * @param invitedUsers The list of users invited to the event.
   * @param hostId       The ID of the host user.
   * @throws IllegalArgumentException if any of the parameters (except invitedUsers) is null.
   */

  public Event(String name, String location, boolean online, DayTime startTime, DayTime endTime,
               List<IUsers> invitedUsers, String hostId) {
    if (name == null || location == null || startTime == null
            || endTime == null || hostId == null || invitedUsers == null) {
      throw new IllegalArgumentException("fields can't be null");
    }
    this.name = name;
    this.location = location;
    this.online = online;
    this.startTime = startTime;
    this.endTime = endTime;
    this.duration = new TimeSlot(startTime, endTime);
    this.invitedUsers = invitedUsers;
    this.hostId = hostId;
  }

  /**
   * Constructs an event with the specified attributes.
   *
   * @param name      The name of the event.
   * @param location  The location of the event.
   * @param online    A boolean indicating if the event is online.
   * @param startTime The start time of the event.
   * @param endTime   The end time of the event.
   * @param hostId    The ID of the host user.
   * @throws IllegalArgumentException if any of the parameters (except invitedUsers) is null.
   */
  public Event(String name, String location, boolean online, DayTime startTime, DayTime endTime,
               String hostId) {
    if (name == null || location == null || startTime == null
            || endTime == null || hostId == null) {
      throw new IllegalArgumentException("fields can't be null");
    }
    this.name = name;
    this.location = location;
    this.online = online;
    this.startTime = startTime;
    this.endTime = endTime;
    this.duration = new TimeSlot(startTime, endTime);
    this.invitedUsers = new ArrayList<IUsers>();
    this.hostId = hostId;
  }

  /**
   * Adds a user to the list of invitees for this event.
   *
   * @param u The user to add as an invitee.
   * @throws IllegalArgumentException if the user is already invited or null.
   */
  @Override
  public void addInvitee(IUsers u) {
    if (u == null) {
      throw new IllegalArgumentException("User can't be null");
    }

    if (invitedUsers.contains(u)) {
      throw new IllegalArgumentException("User is already invited");
    }
    invitedUsers.add(u);
  }


  /**
   * Deletes the event from all users' schedules.
   */
  @Override
  public void deleteEvent() {
    //delete event
    while (invitedUsers.size() > 0) {
      invitedUsers.get(0).removeEvent(this);
    }
  }


  /**
   * Checks if a user is the host of the event.
   *
   * @param u The user to check.
   * @return true if the user is the host, otherwise false.
   */
  @Override
  public boolean isHost(IUsers u) {
    return u.userID().equals(this.hostId);
  }

  /**
   * Checks if the event conflicts with another event.
   *
   * @param e The event to check for conflicts with.
   * @return true if there is a conflict, otherwise false.
   */
  @Override
  public boolean eventConflict(ReadOnlyEvent e) {
    return this.eventConflict(e.duration());
  }

  /**
   * Checks if the event conflicts with a time slot.
   *
   * @param ts The time slot to check for conflicts with.
   * @return true if there is a conflict, otherwise false.
   */
  @Override
  public boolean eventConflict(TimeSlot ts) {
    return ts.conflict(this.duration);
  }

  /**
   * Retrieves a list of invitees' IDs.
   *
   * @return A list of invitees' IDs.
   */
  @Override
  public ArrayList<String> listOfInvitees() {
    ArrayList<String> invitees = new ArrayList<String>();
    for (IUsers i : invitedUsers) {
      invitees.add(i.userID());
    }
    return invitees;
  }

  /**
   * Removes a user from the list of invitees for this event.
   *
   * @param u The user to remove as an invitee.
   * @throws IllegalArgumentException if the user is not invited or null.
   */
  @Override
  public void removeInvitee(IUsers u) {
    if (u == null) {
      throw new IllegalArgumentException("User can't be null");
    }
    if (!invitedUsers.contains(u)) {
      throw new IllegalArgumentException("User is not invited");
    }
    invitedUsers.remove(u);
  }


  /**
   * Changes the name of the event.
   *
   * @param newName The new name to set for the event.
   */
  public void changeName(String newName) {
    this.name = newName;
  }

  /**
   * Changes the location of the event.
   *
   * @param newLocation The new location to set for the event.
   */
  public void changeLocation(String newLocation) {
    this.location = newLocation;
  }

  /**
   * Changes the StartTime of the event.
   *
   * @param newStartTime The new StartTime to set for the event.
   */
  public void changeStartTime(DayTime newStartTime) {
    for (IUsers u : this.invitedUsers) {
      // Check for overlapping events with the new start time
      for (ReadOnlyEvent e : u.scheduledEvents()) {
        if (this.eventEquals(e)) {
          continue;
        } else if ((new Event(this.name, this.location,
                this.online, newStartTime, this.endTime, invitedUsers, hostId)).eventConflict(e)) {
          throw new IllegalArgumentException("Can not modify this time");
        }
      }
    }

    // Update the start time after ensuring no overlap
    this.startTime = newStartTime;
    this.duration = new TimeSlot(this.startTime, this.endTime);
  }

  /**
   * Changes the newEndTime of the event.
   *
   * @param newEndTime The new EndTime to set for the event.
   */
  public void changeEndTime(DayTime newEndTime) {
    for (IUsers u : this.invitedUsers) {
      // Check for overlapping events with the new start time
      for (ReadOnlyEvent e : u.scheduledEvents()) {
        if (this.eventEquals(e)) {
          continue;
        } else if ((new Event(this.name, this.location,
                this.online, this.startTime, newEndTime, invitedUsers, hostId)).eventConflict(e)) {
          throw new IllegalArgumentException("Can not modify this time");
        }
      }
    }
    this.endTime = newEndTime;
    this.duration = new TimeSlot(this.startTime, this.endTime);
  }


  /**
   * Changes the online status of the event.
   */
  public void changeOnlineStatus() {
    this.online = !this.online;
  }

  /**
   * Modifies the online status of the event using a command pattern.
   */
  public void modifyOnline() {
    EventCommand command = new ModifyEventOnlineStatus(this);
    EventModifier modifier = new EventModifier(command);
    modifier.executeModification();
  }

  /**
   * Modifies the name of the event using a command pattern.
   *
   * @param newName The new name for the event.
   * @throws IllegalArgumentException if the new name is null.
   */
  public void modifyName(String newName) {
    if (newName == null) {
      throw new IllegalArgumentException("Name can't be null");
    }
    EventCommand command = new ModifyEventNameCommand(this, newName);
    EventModifier modifier = new EventModifier(command);
    modifier.executeModification();
  }

  /**
   * Modifies the location of the event using a command pattern.
   *
   * @param newLocation The new location for the event.
   * @throws IllegalArgumentException if the new location is null.
   */
  public void modifyLocation(String newLocation) {
    if (newLocation == null) {
      throw new IllegalArgumentException("Location can't be null");
    }
    EventCommand command = new ModifyEventLocationCommand(this, newLocation);
    EventModifier modifier = new EventModifier(command);
    modifier.executeModification();
  }

  /**
   * Modifies the start time of the event using a command pattern.
   *
   * @param newStartTime The new start time for the event.
   * @throws IllegalArgumentException if the new start time is null.
   */
  public void modifyStartTime(DayTime newStartTime) {
    if (newStartTime == null) {
      throw new IllegalArgumentException("Start Time can't be null");
    }
    EventCommand command = new ModifyEventStartTimeCommand(this, newStartTime);
    EventModifier modifier = new EventModifier(command);
    modifier.executeModification();
  }

  /**
   * Modifies the end time of the event using a command pattern.
   *
   * @param newEndTime The new end time for the event.
   * @throws IllegalArgumentException if the new end time is null.
   */
  public void modifyEndTime(DayTime newEndTime) {
    if (newEndTime == null) {
      throw new IllegalArgumentException("End Time can't be null");
    }
    EventCommand command = new ModifyEventEndTimeCommand(this, newEndTime);
    EventModifier modifier = new EventModifier(command);
    modifier.executeModification();
  }

  /**
   * Retrieves the start time of the event.
   *
   * @return The start time of the event.
   */
  @Override
  public DayTime startTime() {
    return this.startTime;
  }


  /**
   * Retrieves the name of the event.
   *
   * @return The name of the event.
   */
  @Override
  public String name() {
    return this.name;
  }

  /**
   * Retrieves the online status of the event.
   *
   * @return {@code true} if the event is online, otherwise {@code false}.
   */
  @Override
  public boolean online() {
    return this.online;
  }


  /**
   * Retrieves the location of the event.
   *
   * @return The location of the event.
   */
  @Override
  public String location() {
    return this.location;
  }

  /**
   * Retrieves the end time of the event.
   *
   * @return The end time of the event.
   */
  @Override
  public DayTime endTime() {
    return this.endTime;
  }

  /**
   * Retrieves the duration (time slot) of the event.
   *
   * @return The duration of the event.
   */
  @Override
  public TimeSlot duration() {
    return this.duration;
  }

  /**
   * Retrieves the ID of the host user for the event.
   *
   * @return The ID of the host user.
   */
  @Override
  public String hostID() {
    return this.hostId;
  }

  /**
   * Generates an XML string representation of the event.
   *
   * @return An XML string representation of the event.
   */
  @Override
  public String giveXMLString() {
    String result = "";
    result += "<event>\n";
    result += "<name>" + this.name + "</name>\n";
    result += "<time>\n" + "<start-day>" + this.startTime.day().toString() + "</start-day>\n" +
            "<start>" + this.startTime.timeAsXMLString() + "</start>\n" +
            "<end-day>" + this.endTime.day().toString() + "</end-day>\n" +
            "<end>" + this.endTime.timeAsXMLString() + "</end>\n" + "</time>\n";
    String online;
    if (this.online) {
      online = "true";
    } else {
      online = "false";
    }

    result += "<location>\n";
    result += "<online>" + online + "</online>\n";
    result += "<place>" + this.location + "</place>\n";
    result += "</location>\n";

    result += "<users>\n";

    for (IUsers i : invitedUsers) {
      result += "<uid>" + i.userID() + "</uid>\n";
    }

    result += "</users>\n";
    result += "</event>\n";
    return result;
  }

  /**
   * Returns a string representation of the event.
   *
   * @return A string representation of the event.
   */
  public String toString() {
    String result = "";
    result += "name: " + this.name + "\n";
    //for time
    result += "time: " + this.startTime.toString() + " -> " + this.endTime.toString() + "\n";
    result += "location: " + this.location + "\n";
    result += "online: " + this.online + "\n";

    String stringOfInvitees = "";
    for (IUsers i : invitedUsers) {
      stringOfInvitees += i.userID() + "\n";
    }
    result += "invitees: " + stringOfInvitees;
    return result;
  }


  /**
   * Checks if the event is equal to the provided event.
   *
   * @param e The event to compare with.
   * @return true if the events are equal, false otherwise.
   */
  public boolean eventEquals(ReadOnlyEvent e) {
    boolean nameEquals = this.name.equals(e.name());
    boolean locationEquals = this.location.equals(e.location());
    boolean onlineEquals = this.online == e.online();
    boolean startTimeEquals = this.startTime.equals(e.startTime());
    boolean endTimeEquals = this.endTime.equals(e.endTime());
    boolean hostIdEquals = this.hostId.equals(e.hostID());

    return this.name.equals(e.name()) && this.location.equals(e.location()) &&
            this.online == e.online() && this.startTime.equals(e.startTime()) &&
            this.endTime.equals(e.endTime()) && this.hostId.equals(e.hostID());

  }


  /**
   * Replaces an inactive user with an activated user in the list of invited users for this event.
   *
   * @param activatedUser   The activated user to replace the inactive user.
   * @param inactivatedUser The inactive user to be replaced.
   */
  public void replaceInactivatedWithActivatedUser(UserSchedule activatedUser,
                                                  InactiveUser inactivatedUser) {
    List<IUsers> modifiedInvitedUsers = new ArrayList<IUsers>();
    for (IUsers i : this.invitedUsers) {
      if (i.userID().equals(inactivatedUser.userID())) {
        modifiedInvitedUsers.add(activatedUser);
      } else {
        modifiedInvitedUsers.add(i);
      }
    }

    this.invitedUsers = modifiedInvitedUsers;

  }

}