package model;

import java.util.ArrayList;
import java.util.HashMap;

public class CentralSystem {
//

  private ArrayList<UserSchedule> users;

  private HashMap<String,UserSchedule> activeUserMap;

  private HashMap<String,UserSchedule> inactiveUserMap;

  public CentralSystem(ArrayList<UserSchedule> users){
    this.users = users;
    this.activeUserMap = new HashMap<String,UserSchedule>();
    for(UserSchedule user: users){
      activeUserMap.put(user.userID(), user);
    }
  }

  public void addUser(UserSchedule u) {
    if (activeUserMap.containsKey(u.userID())) {
      throw new IllegalArgumentException("Active user cannot be added using this method.");
    }

    if (inactiveUserMap.containsKey(u.userID())) {
      UserSchedule inactiveUser = inactiveUserMap.remove(u.userID());
      inactiveUser.activate();
      activeUserMap.put(inactiveUser.userID(), inactiveUser);
    }
    else {
      activeUserMap.put(u.userID(), u);
    }
  }

  public void addUser(String uid) {
    UserSchedule u = new UserSchedule(uid, this);

    if (activeUserMap.containsKey(uid)) {
      throw new IllegalArgumentException("User with the given ID already exists and is active.");
    } else if (inactiveUserMap.containsKey(uid)) {
      UserSchedule inactiveUser = inactiveUserMap.remove(uid);
      inactiveUser.activate();
      activeUserMap.put(inactiveUser.userID(), inactiveUser);
    }
    else {
      // User is neither active nor inactive, add as a new user
      activeUserMap.put(u.userID(), u);
    }
  }

  public UserSchedule schedule(String uid){
    return activeUserMap.get(uid);
  }

  public UserSchedule inactiveUser(String uid){
    return activeUserMap.get(uid);
  }

  //
}
