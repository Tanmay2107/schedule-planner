package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import model.CentralSystem;
import model.Day;
import model.DayTime;
import model.ReadOnlyCentralSystem;

public class EventWithAddFrame extends JFrame implements ActionListener {

  private ReadOnlyCentralSystem model;
  private String uid;
  private  JPanel buttonPanel;
  private JButton addEventButton;
  private JPanel eventDetailsPanel;

  public EventWithAddFrame(String uid , ReadOnlyCentralSystem model) {
    this.uid = uid;
    this.model = model;
    setUp();
  }

  protected void setUp(){
    // Setting the title of the frame
    setTitle("Event");

    // Setting the size of the frame
    setSize(500, 600);

    // When the frame is closed, exit.
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // Centering the frame on the screen
    setLocationRelativeTo(null);

    // Create a panel to hold buttons
    this.buttonPanel = new JPanel();
    // Create add button
    this.addEventButton = new JButton("Add Event");
    buttonPanel.add(addEventButton);

    // Create an empty panel
    this.eventDetailsPanel = new EventDetailsPanel(this.uid, this.model);

    // Create a container to hold both panels
    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    // Add panels to the content pane
    contentPane.add(this.buttonPanel, BorderLayout.SOUTH);
    contentPane.add(this.eventDetailsPanel, BorderLayout.CENTER);

    // Center the frame on the screen
    setLocationRelativeTo(null);

    setVisible(true);


  }

  /**
   * Invoked when an action occurs.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == addEventButton) {
      System.out.println("Adding Event");
    }
  }
}