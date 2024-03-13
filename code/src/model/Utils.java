package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * An example code file to show how to write to files
 * and read XML files using the built in XML parser.
 *
 * The writing example is simple since not much is needed
 * for it. The reading example shows not only how to read
 * XML and extract information, but also WHY we need to dive
 * really deep: text content at higher levels requires
 * more parsing than it's worth.
 *
 * Written by Lucia A. Nunez, using the tutorial.xml file and based
 * on the tutorial written by Baeldung
 * Source: https://www.baeldung.com/java-xerces-dom-parsing
 *
 * Do NOT simply copy-paste this code into your projects. It's
 * useless in its current form to you. Instead, figure out what it
 * is doing and how, lookup any related Javadocs, and finally write your
 * own wherever you need it.
 */
public class Utils {
  /**
   * Creates an XML file in the directory where this code is run
   * For IntelliJ, that is the project's folder.
   *
   * SIDE-EFFECT: Calling this method twice will OVERWRITE the file.
   * If you want to add to an existing file, use append instead.
   */
  public static void writeToFile() {
    try {
      Writer file = new FileWriter("sample-written.xml");
      file.write("<?xml version=\"1.0\"?>\n");
      file.write("<schedule id=\"You\">");
      file.write("</schedule>");
      file.close();
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  /**
   * Reads the specific tutorial.xml file, assuming it's right next to the program,
   * and prints useful information from the file.
   * For IntelliJ, that is the project's folder.
   */
  public static void readXML() {
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document xmlDoc = builder.parse(new File("tutorial.xml"));
      xmlDoc.getDocumentElement().normalize();

      Node tutorialsNode = xmlDoc.getElementsByTagName("tutorials").item(0);
      //This result isn't as nice...
      System.out.println("Investigating the textContent straight from the outermost element:");
      System.out.println(tutorialsNode.getTextContent());

      //So let's dig deeper into the other elements!
      NodeList nodeList = tutorialsNode.getChildNodes();
      for (int item = 0; item < nodeList.getLength(); item++) {
        Node current = nodeList.item(item);
        //We need to search for Elements (actual tags) and there
        //is only one: the tutorial tag
        if(current.getNodeType() == Node.ELEMENT_NODE) {
          Element elem = (Element) current;
          //Print out the attributes of this element
          System.out.println("Investigating the attributes:");
          System.out.println(elem.getTagName() + " : " + elem.getAttribute("tutId") + " " + elem.getAttribute("type"));

          //Print out the text that exists inside of this element: it doesn't look pretty...
          //and it erases the other elements
          System.out.println("Investigating the text content inside this element:");
          System.out.println(elem.getTagName() + " : " + elem.getTextContent());

          //... so let's dig even deeper!
          NodeList elemChildren = elem.getChildNodes();
          for(int childIdx = 0; childIdx < elemChildren.getLength(); childIdx++ ) {
            Node childNode = elemChildren.item(childIdx);
            if(childNode.getNodeType() == Node.ELEMENT_NODE) {
              Element child = (Element) childNode;
              //Now we're getting something more meaningful from each element!
              System.out.println("Investigating the text content inside the innermost tags");
              System.out.println(child.getTagName() + " : " + child.getTextContent());
            }
          }
        }
      }
    } catch (ParserConfigurationException ex) {
      throw new IllegalStateException("Error in creating the builder");
    } catch (IOException ioEx) {
      throw new IllegalStateException("Error in opening the file");
    } catch (SAXException saxEx) {
      throw new IllegalStateException("Error in parsing the file");
    }
  }

  public static void readUserXML(){
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document xmlDoc = builder.parse(new File("prof.xml"));
      xmlDoc.getDocumentElement().normalize();
      NodeList nodeList = xmlDoc.getElementsByTagName("calendar");
      Node first = nodeList.item(0);
      NamedNodeMap attrList = first.getAttributes();
      System.out.println("User Details");

      System.out.println( attrList.item(0).getNodeName() + " : " +
              attrList.item(0).getNodeValue());

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


      }



    } catch (ParserConfigurationException ex) {
      throw new IllegalStateException("Error in creating the builder");
    } catch (IOException ioEx) {
      throw new IllegalStateException("Error in opening the file");
    } catch (SAXException saxEx) {
      throw new IllegalStateException("Error in parsing the file");
    }
  }
}
