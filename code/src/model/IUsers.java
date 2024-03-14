package model;

import java.io.File;
import java.util.ArrayList;

public interface IUsers extends ReadOnlyUsers{

  public String userID();


  public void removeEvent(IEvent e);

  public Event eventAtGiveTime(DayTime dt0);

  public void inviteUser(IEvent e);

  public UserSchedule activate();

  public ArrayList<ReadOnlyEvent> scheduledEvents();

  public String giveXMLString();

  // public modify ();

}
