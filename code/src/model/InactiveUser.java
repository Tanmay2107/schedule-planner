package model;

import java.io.File;
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

  @Override
  public String giveXMLString() {
    throw new IllegalStateException("Inactive user can not give XML string");
  }


}
