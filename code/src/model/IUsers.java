package model;

import java.util.ArrayList;

public interface IUsers {

  public void hostEvent(String name, String location, boolean online, DayTime startTime,
                        DayTime endTime, ArrayList<String> invitees);

  public void removeEvent(Event e);

  public Event eventAtGiveTime(DayTime dt0);

  public void inviteUser(Event e);

  public IUsers activate();

  // public modify ();

}
