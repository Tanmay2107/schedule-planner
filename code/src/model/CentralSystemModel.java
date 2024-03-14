package model;

import java.util.ArrayList;
import java.util.List;

public interface CentralSystemModel extends ReadOnlyCentralSystem {
  public void addUser(String uid) ;



  public void scheduleEvent(String host_uid, String name, String location, boolean online,
                            DayTime startTime, DayTime endTime, ArrayList<String> invitees);
  public void scheduleEvent(String name, String location, boolean online,
                            DayTime startTime, DayTime endTime, ArrayList<String> invitees);


  public void removeEvent(String uid, IEvent e);

  public void modifyEvent(Event event, EventCommand command);



  public List<ReadOnlyUsers> getUsers(String uid);

  public void writeUserToXMLFile(String uid, String path);

  public void loadUserFromXML(String xmlPath);
}
