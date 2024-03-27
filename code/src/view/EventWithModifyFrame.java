package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import controller.SchedulePlannerFeatures;
import model.CentralSystem;
import model.Day;
import model.DayTime;
import model.IUsers;
import model.ReadOnlyCentralSystem;
import model.ReadOnlyEvent;
import model.UserSchedule;

public class EventWithModifyFrame extends JFrame implements ScheduleView {

  private ReadOnlyCentralSystem model;
  private String uid;
  private ReadOnlyEvent event;
  private JPanel buttonPanel;
  private JButton removeEventButton;
  private JButton modifyEventButton;
  private EventDetailsPanel eventDetailsPanel;

  public EventWithModifyFrame(String uid, ReadOnlyCentralSystem model, ReadOnlyEvent event) {
    this.model = model;
    this.uid = uid;
    this.event = event;
    setUp();
  }

  public EventWithModifyFrame() {
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

    // Create modify button
    this.modifyEventButton = new JButton("Modify Event");
    this.buttonPanel.add(modifyEventButton);

    // Create remove button
    this.removeEventButton = new JButton("Remove Event");
    this.buttonPanel.add(this.removeEventButton);


    // Create an empty panel
    this.eventDetailsPanel = new EventDetailsPanel(uid, model, event);

    // Create a container to hold both panels
    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    // Add panels to the content pane
    contentPane.add(this.buttonPanel, BorderLayout.SOUTH);
    contentPane.add(this.eventDetailsPanel, BorderLayout.CENTER);

    // Center the frame on the screen
    setLocationRelativeTo(null);

    setVisible(false);

  }


  @Override
  public void addFeatures(SchedulePlannerFeatures features) {
    this.modifyEventButton.addActionListener(evt -> {
    features.modifyEvent(uid, this.event, eventDetailsPanel.giveEvent()); });
    this.removeEventButton.addActionListener(evt -> {
      features.removeEvent(uid, this.event);
    });

  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }
}
