package controller;

import model.ReadOnlyEvent;

public interface SchedulePlannerFeatures {


  public void  loadXMLFile(String path);

  public void saveXMLFile(String path);

  public void addInvitee(String inviter, String invitee, ReadOnlyEvent event);

  public void removeEvent(String user, ReadOnlyEvent event);

  public void createEvent(ReadOnlyEvent event);

  public void modifyEvent(String uid,ReadOnlyEvent oldEvent, ReadOnlyEvent newEvent);







}
