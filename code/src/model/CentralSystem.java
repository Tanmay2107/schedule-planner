package model;

import java.util.ArrayList;
import java.util.HashMap;

public class CentralSystem {
//

  private ArrayList<UserSchedule> users;

  private HashMap<String,UserSchedule> userMap;

  private HashMap<String,UserSchedule> inactiveUserMap;

  public CentralSystem(ArrayList<UserSchedule> users){
    this.users = users;
    this.userMap = new HashMap<String,UserSchedule>();
    for(UserSchedule user: users){
      userMap.put(user.userID(), user);
    }
  }

  public void addUser(UserSchedule u){
    userMap.put(u.userID(), u);
  }

  public void addUser(String uid){
    UserSchedule u = new UserSchedule(uid,this);
    userMap.put(u.userID(), u);
  }

  public UserSchedule schedule(String uid){
    return userMap.get(uid);
  }

  public UserSchedule inactiveUser(String uid){
    return userMap.get(uid);
  }

  //
}
