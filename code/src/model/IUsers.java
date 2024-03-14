package model;

import java.util.ArrayList;

public interface IUsers extends ReadOnlyUsers{

  public String userID();


  public void removeEvent(IEvent e);

  public Event eventAtGiveTime(DayTime dt0);

  public void inviteUser(IEvent e);

  public UserSchedule activate();

  public ArrayList<ReadOnlyEvent> scheduledEvents();

  // public modify ();

}
