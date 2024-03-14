package model;

import java.util.ArrayList;

public interface IUsers extends ReadOnlyUsers{

  public String userID();

  public void hostEvent(String name, String location, boolean online, DayTime startTime,
                        DayTime endTime, ArrayList<IUsers> invitedUsers);

  public void removeEvent(IEvent e);

  public Event eventAtGiveTime(DayTime dt0);

  public void inviteUser(IEvent e);

  public UserSchedule activate();

  public ArrayList<ReadOnlyEvent> scheduledEvents();
  // public modify ();

}
