package model;

import java.util.ArrayList;



/**
 * Represents an event that is input by the user.
 */
public class InputEvent implements ReadOnlyEvent {

  private String name;

  private boolean online;
  private String location;

  private DayTime startTime;

  private DayTime endTime;

  private TimeSlot duration;
  //INVARIANT: duration represents the time slot between startTime and endTime

  private ArrayList<String> invitedUsers;


  private String hostId;

  /**
   * Constructs an InputEvent with the given name, online status, location, start time, end time,
   * host ID, and list of invited users.
   *
   * @param name         The name of the event.
   * @param online       The online status of the event.
   * @param location     The location of the event.
   * @param startTime    The start time of the event.
   * @param endTime      The end time of the event.
   * @param hostId       The host ID of the event.
   * @param invitedUsers The list of invited users to the event.
   */
  public InputEvent(String name, boolean online, String location, DayTime startTime,
                    DayTime endTime, String hostId, ArrayList<String> invitedUsers) {
    this.name = name;
    this.online = online;
    this.location = location;
    this.startTime = startTime;
    this.endTime = endTime;
    this.hostId = hostId;
    this.invitedUsers = invitedUsers;
    this.duration = new TimeSlot(startTime, endTime);
  }

  @Override
  public DayTime startTime() {
    return this.startTime;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public boolean online() {
    return this.online;
  }

  @Override
  public String location() {
    return this.location;
  }

  @Override
  public DayTime endTime() {
    return this.endTime;
  }

  @Override
  public String hostID() {
    return this.hostId;
  }

  @Override
  public ArrayList<String> listOfInvitees() {
    return this.invitedUsers;
  }

  @Override
  public TimeSlot duration() {
    return this.duration;
  }

  @Override
  public String toString() {
    String result = "";
    result += "name: " + this.name + "\n";
    //for time
    result += "time: " + this.startTime.toString() + " -> " + this.endTime.toString() + "\n";
    result += "location: " + this.location + "\n";
    result += "online: " + this.online + "\n";

    String stringOfInvitees = "";
    for (String str : invitedUsers) {
      stringOfInvitees += str + "\n";
    }
    result += "invitees: " + stringOfInvitees;
    return result;
  }
}
