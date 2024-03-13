package model;

import java.util.ArrayList;

public interface ReadOnlyUsers {
  public String userID();

  public ArrayList<ReadOnlyEvent> scheduledEvents();


}
