package model;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;




/**
 * Represents the central system that manages all the users and their schedules.
 */
public class CentralSystem implements CentralSystemModel{
//

  private ArrayList<UserSchedule> users;

  private HashMap<String,UserSchedule> activeUserMap;

  private HashMap<String,InactiveUser> inactiveUserMap;

  /**
   * Creates a new central system with the given list of users.
   * @param users
   */
  public CentralSystem(ArrayList<UserSchedule> users){
    this.users = users;
    this.activeUserMap = new HashMap<String,UserSchedule>();
    for(UserSchedule user: users){
      activeUserMap.put(user.userID(), user);
    }
  }

  /**
   * Creates a new central system from scratch.
   */
  public CentralSystem(){
    this.users = new ArrayList<UserSchedule>();
    this.activeUserMap = new HashMap<String,UserSchedule>();
    this.inactiveUserMap = new HashMap<String,InactiveUser>();
  }

  /**
   * Adds a new user to the system with the given uid.
   * @param uid
   */
  public void addUser(String uid) {
    UserSchedule u = new UserSchedule(uid);

    if (activeUserMap.containsKey(uid)) {
      throw new IllegalArgumentException("User with the given ID already exists and is active.");
    } else if (inactiveUserMap.containsKey(uid)) {
      InactiveUser inactiveUser = inactiveUserMap.remove(uid);
      UserSchedule activedUser = inactiveUser.activate();
      activeUserMap.put(inactiveUser.userID(), activedUser);
    }
    else {
      // User is neither active nor inactive, add as a new user
      activeUserMap.put(u.userID(), u);
    }
  }



  public void scheduleEvent(String host_uid, String name, String location, boolean online,
                            DayTime startTime, DayTime endTime, ArrayList<String> invitees){
    if(host_uid == null || name == null || location == null || startTime == null || endTime == null || invitees == null){
      throw new IllegalArgumentException("fields can't be null");
    }

    if(invitees.size() == 0){
      throw new IllegalArgumentException("There should be at least one invitee");
    }

    ArrayList<IUsers> invitedUsers = getInvitees(invitees);
    Event e = new Event(name, location, online, startTime, endTime, host_uid);

    for(IUsers i: invitedUsers){
      i.inviteUser(e);
    }

  }

  public void scheduleEvent(String name, String location, boolean online,
                            DayTime startTime, DayTime endTime, ArrayList<String> invitees){
    if(name == null || location == null || startTime == null || endTime == null || invitees == null){
      throw new IllegalArgumentException("fields can't be null");
    }

    if(invitees.size() == 0){
      throw new IllegalArgumentException("There should be at least one invitee");
    }

    ArrayList<IUsers> invitedUsers = getInvitees(invitees);
    Event e = new Event(name, location, online, startTime, endTime, invitees.get(0));

    for(IUsers i: invitedUsers){
      i.inviteUser(e);
    }

  }

  private void checkForActiveUser(String uid){
    if(!activeUserMap.containsKey(uid)){
      throw new IllegalArgumentException("There is no active user with the given ID.");
    }
  }

  private ArrayList<IUsers> getInvitees(ArrayList<String> invitees){
    ArrayList<IUsers> invitedUsers = new ArrayList<IUsers>();
    for(String invitee: invitees){
      if(activeUserMap.containsKey(invitee)){
        invitedUsers.add(activeUserMap.get(invitee));
      } else if(inactiveUserMap.containsKey(invitee)){
        invitedUsers.add(inactiveUserMap.get(invitee));
      } else {
        InactiveUser newUser = new InactiveUser(invitee);
        this.inactiveUserMap.put(invitee,newUser);
        invitedUsers.add(newUser);
      }
    }
    return invitedUsers;
  }


  public void removeEvent(String uid, IEvent e){
    this.checkForActiveUser(uid);
    UserSchedule user = activeUserMap.get(uid);
    user.removeEvent(e);
  }

  public void modifyEvent(Event event, EventCommand command) {
    EventModifier modifier = new EventModifier(command);
    modifier.executeModification();
  }



  @Override
  public List<ReadOnlyUsers> getUsers(String uid) {
    List<ReadOnlyUsers> userList = new ArrayList<>();

    for (UserSchedule user : activeUserMap.values()) {
      if (user.userID().equals(uid)) {
        userList.add(user);
      }
    }

    return userList;
  }

  public String toString(){
    String result = "";
    for(UserSchedule u: users){
      result += u.toString() + "\n";
    }
    return result;
  }

  public void loadUserFromXML(String xmlPath) {
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document xmlDoc = builder.parse(new File(xmlPath));
      xmlDoc.getDocumentElement().normalize();
      NodeList nodeList = xmlDoc.getElementsByTagName("calendar");
      Node first = nodeList.item(0);
      NamedNodeMap attrList = first.getAttributes();
      System.out.println("User Details");

      System.out.println( attrList.item(0).getNodeName() + " : " +
              attrList.item(0).getNodeValue());
      String uid = attrList.item(0).getNodeValue();
      this.addUser(uid);

      NodeList eventNodeList = xmlDoc.getElementsByTagName("event");

      for(int j=0; j<eventNodeList.getLength(); j++){
        Node firstEvent = eventNodeList.item(j);

        NodeList eventChildNodeList = firstEvent.getChildNodes();

        int n =  eventChildNodeList.getLength();
        Node current;
        System.out.println("Event " + j + " Details");

        xmlDoc.getElementsByTagName("name").item(j).getTextContent();

        String eventName = xmlDoc.getElementsByTagName("name").item(j).getTextContent();
        System.out.println("Event Name: " + eventName);

        String startDay = xmlDoc.getElementsByTagName("start-day").item(j).getTextContent();

        String startTime = xmlDoc.getElementsByTagName("start").item(j).getTextContent();

        String endDay = xmlDoc.getElementsByTagName("end-day").item(j).getTextContent();

        String endTime = xmlDoc.getElementsByTagName("end").item(j).getTextContent();
        System.out.println("Time-Start Day: " + startDay);
        System.out.println("Time-Start Time: " + startTime);
        System.out.println("Time-End Day: " + endDay);
        System.out.println("Time-End Time: " + endTime);

        String locationOnline = xmlDoc.getElementsByTagName("online").item(j).getTextContent();

        String locationPlace = xmlDoc.getElementsByTagName("place").item(j).getTextContent();
        System.out.println("Location-Online: " + locationOnline);
        System.out.println("Location-Place: " + locationPlace);

        Node inviteesNode = xmlDoc.getElementsByTagName("users").item(j);
        NodeList inviteesNodeList = inviteesNode.getChildNodes();

        ArrayList<String> users = new ArrayList<>();


        int numInvitees = inviteesNodeList.getLength();
        Node currentInvitee;
        for(int k=0; k<numInvitees; k++){
          currentInvitee = inviteesNodeList.item(k);
          if(currentInvitee.getNodeType() == Node.ELEMENT_NODE) {

            users.add(currentInvitee.getTextContent());
          }
        }
        System.out.println("Users: " + users);

        this.scheduleEvent(eventName, locationPlace, locationOnline.equals("true"),
                stringToDayTime(startDay,startTime), stringToDayTime(endDay,endTime), users);
      }

    } catch (ParserConfigurationException ex) {
      throw new IllegalStateException("Error in creating the builder");
    } catch (IOException ioEx) {
      throw new IllegalStateException("Error in opening the file");
    } catch (SAXException saxEx) {
      throw new IllegalStateException("Error in parsing the file");
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException("Conflicting events");
    }

  }

  private DayTime stringToDayTime(String day,String time){
    if(day == null || time == null){
      throw new IllegalArgumentException("fields can't be null");
    }
    if(day.length() != 4){
      throw new IllegalArgumentException("Invalid day");
    }
    String hours = time.substring(0,2);
    String minutes = time.substring(2,4);
    int h = Integer.parseInt(hours);
    int m = Integer.parseInt(minutes);
    switch (day){
      case "Monday":
        return new DayTime(h,m,Day.MONDAY);
      case "Tuesday":
        return new DayTime(h,m,Day.TUESDAY);
      case "Wednesday":
        return new DayTime(h,m,Day.WEDNESDAY);
      case "Thursday":
        return new DayTime(h,m,Day.THURSDAY);
      case "Friday":
        return new DayTime(h,m,Day.FRIDAY);
      case "Saturday":
        return new DayTime(h,m,Day.SATURDAY);
      case "Sunday":
        return new DayTime(h,m,Day.SUNDAY);
      default:
        throw new IllegalArgumentException("Invalid day");
    }

  }

  public void writeUserToXMLFile(String uid, String path) {
    if(uid == null || path == null){
      throw new IllegalArgumentException("fields can't be null");
    }
    if(!activeUserMap.containsKey(uid)){
      throw new IllegalArgumentException("There is no active user with the given ID.");
    }
    try {
      Writer file = new FileWriter(path);
      file.write("<?xml version=\"1.0\"?>\n");
      file.write("<schedule id=\"" + uid +"\">");
      UserSchedule user = activeUserMap.get(uid);
      file.write(user.giveXMLString());
      file.write("</schedule>");
      file.close();
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

}
