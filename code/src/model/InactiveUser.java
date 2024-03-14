package model;

import java.util.ArrayList;

public class InactiveUser extends AUsers{

  public InactiveUser(String uid) {
    super(uid);
  }

  @Override
  public void hostEvent(String name, String location, boolean online, DayTime startTime, DayTime endTime, ArrayList<IUsers> invitedUsers) {
    throw new IllegalStateException("Inactive user can not host");
  }


  @Override
  public void removeEvent(IEvent e) {
    events.remove(e);
    e.removeInvitee(this);
  }

  @Override
  public UserSchedule activate() {
    return new UserSchedule(uid, events);
  }

}
