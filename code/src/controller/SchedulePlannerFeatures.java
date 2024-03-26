package controller;

import model.ReadOnlyEvent;

public interface SchedulePlannerFeatures {


  public void  loadXMLFile(String path);

  public void saveXMLFile(String path);

  public void addInvitee(String inviter, String invitee, ReadOnlyEvent event);


}
