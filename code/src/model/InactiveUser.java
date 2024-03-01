package model;

import java.util.ArrayList;

public class InactiveUser extends AUsers{

  public InactiveUser(String uid, CentralSystem cs) {
    super(uid, cs);
  }

  @Override
  public void hostEvent(String name, String location, boolean online, DayTime startTime,
                        DayTime endTime, ArrayList<String> invitees) {
    throw new IllegalStateException("Inactive user can not host");
  }

  @Override
  public void removeEvent(Event e) {
    throw new IllegalStateException("Inactive user can not delete event");
  }

  @Override
  public IUsers activate() {
    return new UserSchedule(uid, events, cs);
  }
}
