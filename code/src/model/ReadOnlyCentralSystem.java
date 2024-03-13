package model;

import java.util.List;

public interface ReadOnlyCentralSystem {

  public List<ReadOnlyUsers> getUsers(String uid);

  public List<ReadOnlyEvent> getEvents(String uid);

}
