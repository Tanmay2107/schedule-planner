package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents the central system that manages all the users and their schedules.
 */
public class CentralSystem {
//

  private ArrayList<UserSchedule> users;

  private HashMap<String,UserSchedule> activeUserMap;

  private HashMap<String,InactiveUser> inactiveUserMap;

  /**
   * Creates a new central system with the given list of users.
   * @param users
   */
  public CentralSystem(ArrayList<UserSchedule> users){
    this.users = users;
    this.activeUserMap = new HashMap<String,UserSchedule>();
    for(UserSchedule user: users){
      activeUserMap.put(user.userID(), user);
    }
  }

  /**
   * Creates a new central system from scratch.
   */
  public CentralSystem(){
    this.users = new ArrayList<UserSchedule>();
    this.activeUserMap = new HashMap<String,UserSchedule>();
    this.inactiveUserMap = new HashMap<String,InactiveUser>();
  }



  /**
   * Adds a new user to the system with the given uid.
   * @param uid
   */
  public void addUser(String uid) {
    UserSchedule u = new UserSchedule(uid);

    if (activeUserMap.containsKey(uid)) {
      throw new IllegalArgumentException("User with the given ID already exists and is active.");
    } else if (inactiveUserMap.containsKey(uid)) {
      InactiveUser inactiveUser = inactiveUserMap.remove(uid);
      UserSchedule activedUser = inactiveUser.activate();
      activeUserMap.put(inactiveUser.userID(), activedUser);
    }
    else {
      // User is neither active nor inactive, add as a new user
      activeUserMap.put(u.userID(), u);
    }
  }


  public void addEvent(String host_uid, String name, String location, boolean online,
                       DayTime startTime, DayTime endTime, ArrayList<String> invitees){
    this.checkForActiveUser(host_uid);

    ArrayList<IUsers> invitedUsers = getInvitees(invitees);



    IUsers hostUser = activeUserMap.get(host_uid);
    hostUser.hostEvent(name, location, online, startTime, endTime, invitedUsers);
  }

  private void checkForActiveUser(String uid){
    if(!activeUserMap.containsKey(uid)){
      throw new IllegalArgumentException("There is no active user with the given ID.");
    }
  }

  private ArrayList<IUsers> getInvitees(ArrayList<String> invitees){
    ArrayList<IUsers> invitedUsers = new ArrayList<IUsers>();
    for(String invitee: invitees){
      if(activeUserMap.containsKey(invitee)){
        invitedUsers.add(activeUserMap.get(invitee));
      } else if(inactiveUserMap.containsKey(invitee)){
        invitedUsers.add(inactiveUserMap.get(invitee));
      } else {
        InactiveUser newUser = new InactiveUser(invitee);
        this.inactiveUserMap.put(invitee,newUser);
        invitedUsers.add(newUser);
      }
    }
    return invitedUsers;
  }


  public void removeEvent(String uid, IEvent e){
    this.checkForActiveUser(uid);
    UserSchedule user = activeUserMap.get(uid);
    user.removeEvent(e);
  }


}
