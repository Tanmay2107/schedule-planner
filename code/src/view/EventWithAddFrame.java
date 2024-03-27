package view;

import java.awt.Container;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import controller.SchedulePlannerFeatures;
import model.ReadOnlyCentralSystem;
import model.ReadOnlyEvent;

/**
 * Frame for adding new events.
 * This frame provides a user interface for adding new events to the schedule planner.
 */
public class EventWithAddFrame extends JFrame implements ScheduleView {

  private ReadOnlyCentralSystem model;
  private String uid;

  private JButton addEventButton;
  private EventDetailsPanel eventDetailsPanel;

  /**
   * Constructs an EventWithAddFrame with the given unique identifier and model.
   *
   * @param uid   The unique identifier associated with the user.
   * @param model The ReadOnlyCentralSystem model to interact with.
   */
  public EventWithAddFrame(String uid, ReadOnlyCentralSystem model) {
    this.uid = uid;
    this.model = model;
    setUp();
  }

  protected void setUp() {
    // Setting the title of the frame
    setTitle("Event");

    // Setting the size of the frame
    setSize(500, 600);

    // When the frame is closed, exit.
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    // Centering the frame on the screen
    setLocationRelativeTo(null);



    // Create a panel to hold buttons
    JPanel buttonPanel = new JPanel();
    // Create add button
    this.addEventButton = new JButton("Add Event");
    buttonPanel.add(addEventButton);

    // Create an empty panel
    this.eventDetailsPanel = new EventDetailsPanel(this.uid, this.model);

    // Create a container to hold both panels
    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    // Add panels to the content pane
    contentPane.add(buttonPanel, BorderLayout.SOUTH);
    contentPane.add(this.eventDetailsPanel, BorderLayout.CENTER);

    // Center the frame on the screen
    setLocationRelativeTo(null);

    setVisible(false);

  }


  /**
   * Adds features to the frame.
   *
   * @param features The SchedulePlannerFeatures object providing features to be added.
   */
  @Override
  public void addFeatures(SchedulePlannerFeatures features) {
    this.eventDetailsPanel.addFeatures(features);

    this.addEventButton.addActionListener(evt -> {
      System.out.println("Adding Event");
      //features.addEvent();
      try {
        ReadOnlyEvent event = this.eventDetailsPanel.giveEvent();
        features.createEvent(event);
      } catch (IllegalArgumentException e) {
        JOptionPane.showMessageDialog(this, "Enter a valid time");
      }

    });
  }

  /**
   * Makes the frame visible.
   */
  public void makeVisible() {
    setVisible(true);
  }


}