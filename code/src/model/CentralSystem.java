package model;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Represents the central system that manages all the users and their schedules.
 */
public class CentralSystem implements CentralSystemModel{

  private ArrayList<UserSchedule> users;
  //INVARIANT: users != null
  //INVARIANT: No two users in the list of users have the same user ID

  private HashMap<String,UserSchedule> activeUserMap;
  //INVARIANT: activeUserMap != null
  //INVARIANT: No two users in the activeUserMap have the same user ID
  //INVARIANT: All users are stored as values with their user ID as the key


  private HashMap<String,InactiveUser> inactiveUserMap;
  //INVARIANT: activeUserMap != null
  //INVARIANT: No two users in the inactiveUserMap have the same user ID
  //INVARIANT: All users are stored as values with their user ID as the key

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
    if (uid == null) {
      throw new IllegalArgumentException("User ID can't be null");
    }
    UserSchedule u = new UserSchedule(uid);
    users.add(u);

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


  /**
   * Schedules an event with the specified details.
   *
   * @param host_uid   The user ID of the event host.
   * @param name       The name of the event.
   * @param location   The location of the event.
   * @param online     Indicates if the event is online or not.
   * @param startTime  The start time of the event.
   * @param endTime    The end time of the event.
   * @param invitees   The list of user IDs of invitees to the event.
   */
  @Override
  public void createEvent(String host_uid, String name, String location, boolean online,
                          DayTime startTime, DayTime endTime, ArrayList<String> invitees) {
    if(name == null || location == null || startTime == null || endTime == null || invitees == null){
      throw new IllegalArgumentException("fields can't be null");
    }

    if(invitees.size() == 0){
      throw new IllegalArgumentException("There must be at least one invitee");
    }

    ArrayList<IUsers> invitedUsers = getInvitees(invitees);

    Event e = new Event(name, location, online, startTime, endTime,host_uid);

    for(IUsers u: invitedUsers){
      u.inviteUser(e);
    }

  }

  /**
   * Schedules an event with the specified details.
   *
   * @param name      The name of the event.
   * @param location  The location of the event.
   * @param online    Indicates if the event is online or not.
   * @param startTime The start time of the event.
   * @param endTime   The end time of the event.
   * @param invitees  The list of user IDs of invitees to the event.
   */
  @Override
  public void createEvent(String name, String location, boolean online,
                          DayTime startTime, DayTime endTime, ArrayList<String> invitees) {
    if(name == null || location == null || startTime == null || endTime == null || invitees == null){
      throw new IllegalArgumentException("fields can't be null");
    }

    if(invitees.size() == 0){
      throw new IllegalArgumentException("There must be at least one invitee");
    }

    ArrayList<IUsers> invitedUsers = getInvitees(invitees);

    Event e = new Event(name, location, online, startTime, endTime,invitees.get(0));

    for(IUsers u: invitedUsers){
      u.inviteUser(e);
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


  /**
   * Removes the specified event associated with the given user ID from the central system.
   *
   * @param uid The user ID of the user who owns the event.
   * @param e   The event to remove.
   */
  public void removeEvent(String uid, IEvent e){
    if(uid == null || e == null){
      throw new IllegalArgumentException("fields can't be null");
    }
    this.checkForActiveUser(uid);
    UserSchedule user = activeUserMap.get(uid);
    user.removeEvent(e);
  }

  /**
   * Modifies the specified event using the provided command.
   *
   * @param event   The event to modify.
   * @param command The command to execute for modifying the event.
   */
  public void modifyEvent(Event event, EventCommand command, String uid) {
    if(!activeUserMap.containsKey(uid)){
      throw new IllegalStateException("User not active ");
    }

    IUsers currentuser = activeUserMap.get(uid);
    currentuser.modifyEvent(event,command);
  }

  /**
   * Retrieves a list of user IDs registered in the central system.
   *
   * @return An ArrayList containing the user IDs.
   */
  @Override
  public ArrayList<String> getUserIds(){
    ArrayList<String> userIds = new ArrayList<>();

    for (UserSchedule user : activeUserMap.values()) {
      userIds.add(user.userID());
    }
    return userIds;
  }


  /**
   * Retrieves the user with the specified user ID.
   *
   * @param uid The ID of the user to retrieve.
   * @return The user with the specified user ID.
   * @throws IllegalArgumentException If the user ID is null or if no user exists with the given ID.
   */
  @Override
  public ReadOnlyUsers getUser(String uid) {
    if (uid == null) {
      throw new IllegalArgumentException("User ID can't be null");
    }

    if (activeUserMap.containsKey(uid)) {
      return activeUserMap.get(uid);
    } else {
      throw new IllegalArgumentException("User with the given ID does not exist");
    }
  }

  /**
   * Writes the user's data to an XML file specified by the given path.
   *
   * @param uid  The user ID of the user whose data is to be written.
   * @param path The file path where the XML file will be written.
   */
  @Override
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

  /**
   * Returns a string representation of the central system, including the schedules of all users.
   *
   * @return A string representation of the central system.
   */
  public String toString(){
    String result = "";

    for(UserSchedule u: users){
      result += u.toString() + "\n";
    }
    return result;
  }

  /**
   * Loads user data from an XML file located at the specified path.
   *
   * @param xmlPath The path to the XML file containing user data.
   * @throws IllegalArgumentException If the XML path is null.
   * @throws IllegalStateException    If an error occurs during parsing or processing of the XML file.
   */
  public void loadUserFromXML(String xmlPath) {
    if (xmlPath == null) {
      throw new IllegalArgumentException("Path can't be null");
    }
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document xmlDoc = builder.parse(new File(xmlPath));
      xmlDoc.getDocumentElement().normalize();
      NodeList nodeList = xmlDoc.getElementsByTagName("schedule");
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

        this.createEvent(users.get(0), eventName, locationPlace, locationOnline.equals("true"),
                stringToDayTime(startDay,startTime), stringToDayTime(endDay,endTime), users);

      }

    } catch (ParserConfigurationException ex) {
      throw new IllegalStateException("Error in creating the builder");
    } catch (IOException ioEx) {
      throw new IllegalStateException("Error in opening the file");
    } catch (SAXException saxEx) {
      throw new IllegalStateException("Error in parsing the file");
    }

  }

  /**
   * Retrieves a list of inactive users in the central system.
   *
   * @return An ArrayList containing read-only representations of inactive users.
   */
  @Override
  public ArrayList<ReadOnlyUsers> giveInactiveUsers() {
    ArrayList<ReadOnlyUsers> result = new ArrayList<ReadOnlyUsers>();
    for(String uid: inactiveUserMap.keySet()){
      result.add(inactiveUserMap.get(uid));
    }
    return result;
  }

  private DayTime stringToDayTime(String day,String time){
    if(day == null || time == null){
      throw new IllegalArgumentException("fields can't be null");
    }
    if(time.length() != 4){
      throw new IllegalArgumentException("Invalid time");
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

  /**
   * Invites a user to an event.
   *
   * @param inviter_uid The user ID of the inviter.
   * @param invitee_uid The user ID of the invitee.
   * @param event       The event to which the user is invited.
   * @throws IllegalArgumentException if any of the input fields are null.
   */
  public void inviteUserToEvent(String inviter_uid, String invitee_uid, IEvent event){
    if(inviter_uid == null || invitee_uid == null || event == null){
      throw new IllegalArgumentException("fields can't be null");
    }

    ArrayList<String> mockInvitees = new ArrayList<String>();
    mockInvitees.add(inviter_uid);
    mockInvitees.add(invitee_uid);
    ArrayList<IUsers> mockInviteesList = getInvitees(mockInvitees);

    IUsers inviter = mockInviteesList.get(0);
    IUsers invitee = mockInviteesList.get(1);

    inviter.inviteAUserToAnEvent(invitee, event);
  }

}
