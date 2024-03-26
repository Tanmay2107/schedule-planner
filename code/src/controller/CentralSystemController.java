package controller;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.CentralSystemModel;
import model.ReadOnlyEvent;
import view.ScheduleFrame;
import view.ScheduleView;



public class CentralSystemController implements SchedulePlannerController, SchedulePlannerFeatures  {
  private final CentralSystemModel model;
  private final ScheduleView view;

  public CentralSystemController(CentralSystemModel model) {

    this.model = model;
    this.view = new ScheduleFrame(model);

  }
  @Override
  public void start() {

  }

  @Override
  public void loadXML(String filepath) {

    if(filepath == null) throw new IllegalArgumentException("Filepath cannot be null");
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

  @Override
  public void save() {

  }


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

  @Override
  public void saveXMLFile(String path) {

  }

  @Override
  public void addInvitee(String inviter, String invitee, ReadOnlyEvent event) {

  }
}
