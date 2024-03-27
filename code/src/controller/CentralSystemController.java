package controller;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.CentralSystemModel;
import model.ReadOnlyEvent;
import view.CentralSystemView;
import view.ScheduleFrame;

/**
 * The controller class for managing the central system of the schedule planner.
 * This class implements the SchedulePlannerController interface.
 */
public class CentralSystemController implements SchedulePlannerController {
  private final CentralSystemModel model;
  private final CentralSystemView view;

  /**
   * Constructs a CentralSystemController with the given model.
   * Initializes the view with the model and adds features.
   *
   * @param model The CentralSystemModel to be used.
   */
  public CentralSystemController(CentralSystemModel model) {

    this.model = model;
    this.view = new ScheduleFrame(model);
    this.view.addFeatures(this);


  }

  /**
   * Starts the controller.
   */
  @Override
  public void start() {
    this.view.makeVisible();

  }

  /**
   * Loads user data from an XML file.
   *
   * @param filepath The path to the XML file.
   * @throws IllegalArgumentException If filepath is null.
   * @throws IllegalStateException    If an error occurs in parsing or opening the file.
   */
  @Override
  public void loadXML(String filepath) {

    if (filepath == null) {
      throw new IllegalArgumentException("Filepath cannot be null");
    }

    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document xmlDoc = builder.parse(new File(filepath));
      model.loadUserFromXML(xmlDoc);
    } catch (ParserConfigurationException e) {
      throw new IllegalStateException("Error in creating the builder");
    } catch (IOException e) {
      throw new IllegalStateException("Error in opening file");
    } catch (SAXException e) {
      throw new IllegalStateException("Error in opening file");
    }


  }

  /**
   * Saves data.
   */
  @Override
  public void save(String uid, String path) {
    try {
      Writer file = new FileWriter(path + ".xml");

      file.write("<?xml version=\"1.0\"?>\n");
      file.write("<schedule id=\"" + uid + "\">");
      file.write(this.model.giveXMLString(uid));
      file.write("</schedule>");
      file.close();
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }

  }


  /**
   * Loads user data from an XML file.
   *
   * @param path The path to the XML file.
   */
  @Override
  public void loadXMLFile(String path) {
    System.out.println("Loading file:" + path);
    /*
    if(path == null) throw new IllegalArgumentException("Filepath cannot be null");
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document xmlDoc = builder.parse(new File(path));
      model.loadUserFromXML(xmlDoc);
    } catch (ParserConfigurationException e) {
      throw new IllegalStateException("Error in creating the builder");
    } catch (IOException e) {
      throw new IllegalStateException("Error in opening file");
    } catch (SAXException e) {
      throw new IllegalStateException("Error in opening file");
    }

     */
  }

  /**
   * Saves calender of the given user to their XML file.
   */
  @Override
  public void saveXMLFile(String path) {
    System.out.println("Saving files");
    System.out.println("To path:" + path);
  }

  /**
   * Adds an invitee to an event.
   *
   * @param inviter The user inviting.
   * @param invitee The user being invited.
   * @param event   The event to which the invitee is added.
   */
  @Override
  public void addInvitee(String inviter, String invitee, ReadOnlyEvent event) {
    System.out.println("Adding invitee" + "\n" + invitee + "\n" + "by :" + inviter + "\n" +
            "to event" + "\n" + event.toString());
  }


  /**
   * Removes an event from a user's schedule.
   *
   * @param user  The user from whose schedule the event is removed.
   * @param event The event to be removed.
   */
  @Override
  public void removeEvent(String user, ReadOnlyEvent event) {
    System.out.println("Removing event" + "\n" + event.toString() + "\n" + "from :" + user);
  }

  /**
   * Creates a new event.
   *
   * @param event The event to be created.
   */
  @Override
  public void createEvent(ReadOnlyEvent event) {
    System.out.println("Creating Event");
    System.out.println(event.toString());
  }

  /**
   * Modifies an existing event.
   *
   * @param uid      The unique identifier of the event.
   * @param oldEvent The original event.
   * @param newEvent The modified event.
   */
  @Override
  public void modifyEvent(String uid, ReadOnlyEvent oldEvent, ReadOnlyEvent newEvent) {

    System.out.println(uid + " is modifying Event" + "\n" + "from" + "\n" +
            oldEvent.toString() + "\n" + "to" + "\n" + newEvent.toString());
  }


}
