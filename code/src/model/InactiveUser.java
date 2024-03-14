package model;

import java.util.ArrayList;

public class InactiveUser extends AUsers{

  public InactiveUser(String uid) {
    super(uid);
  }






  @Override
  public UserSchedule activate() {
    return new UserSchedule(uid, events);
  }

  @Override
  public ArrayList<ReadOnlyEvent> scheduledEvents() {
    throw new IllegalStateException("Inactive user can not check scheduled events");
  }
}
