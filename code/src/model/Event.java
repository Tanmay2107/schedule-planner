package model;

import java.util.ArrayList;
import java.util.List;

public class Event {
  private String name;
  private boolean online;

  private String location;
  private DayTime startTime;
  private DayTime endTime;

  private Duration duration;
  private UserSchedule host;
  private List<String> invitees;

  private CentralSystem cs;


  // constructor:
  public Event(String name, String location, boolean online, DayTime startTime, DayTime endTime,
               UserSchedule host, List<String> invitees,CentralSystem cs) {
    if (name == null || location == null || startTime == null
            || endTime == null || host == null || invitees == null) {
      throw new IllegalArgumentException("fields can't be null");
    }
    this.name = name;
    this.location = location;
    this.online = online;
    this.startTime = startTime;
    this.endTime = endTime;
    this.host = host;
    invitees.add(0, host.getUid());
    this.invitees = invitees;
    this.duration = new Duration(startTime, endTime);
    this.cs = cs;
  }


  public boolean isEventOverlapping() {
    return false; // handle in duration
  }


  // adding invitee to event
  public void inviteUser(String uid) {

    // user already an invitee
    if (invitees.contains(uid)) {
      throw new IllegalArgumentException("User " + uid + " is already invited to the event.");
    }

    // Add the user to the list of invitees
    invitees.add(uid);
  }



  public void deleteEvent() {
    //make a list of users from uids
    ArrayList<UserSchedule> inviteesList = new ArrayList<>();
    for (String uid: this.invitees){

      //use the map from the central system
      inviteesList.add(cs.getUserSchedule(uid));
    }

    //remove this event for every user
    for (UserSchedule u: inviteesList){
      u.removeEvent(this);
    }

  }
  //


  public void removeInvitee(UserSchedule u){

    //removes the event but if the given user is not an invitee then throws exception
      if(!this.invitees.remove(u.getUid())){
        throw new IllegalStateException("This user is not in the invitee list");
      }

  }

  /**
   * Checking if the given user is the host.
   * @param u
   * @return true if given userschedule is the host of this event.
   */
  public boolean isHost(UserSchedule u){
    return u.getUid().equals(this.host.getUid());
  }


}