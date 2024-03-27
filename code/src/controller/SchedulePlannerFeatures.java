package controller;

import model.ReadOnlyEvent;

public interface SchedulePlannerFeatures {


  public void  loadXMLFile(String path);

  public void saveXMLFile(String path);

  public void addInvitee(String inviter, String invitee, ReadOnlyEvent event);

  public void removeEvent(String user, ReadOnlyEvent event);

  public void createEvent(String hostUid, String name, String location, boolean online,
                          String startTime, String endTime, String invitees);

  public void modifyEvent(String hostUid, String name, String location, boolean online,
                          String startTime, String endTime, String invitees, ReadOnlyEvent event);

  public void removeInvitee(String inviter, String invitee, ReadOnlyEvent event);





}
