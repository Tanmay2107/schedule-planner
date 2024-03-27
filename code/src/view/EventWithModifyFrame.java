package view;

import java.awt.Container;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import controller.SchedulePlannerFeatures;
import model.ReadOnlyCentralSystem;
import model.ReadOnlyEvent;

/**
 * Frame for modifying existing events.
 * This frame provides a user interface for modifying existing events in the schedule planner.
 */
public class EventWithModifyFrame extends JFrame implements ScheduleView {

  private ReadOnlyCentralSystem model;
  private String uid;
  private ReadOnlyEvent event;
  private JPanel buttonPanel;
  private JButton removeEventButton;
  private JButton modifyEventButton;
  private EventDetailsPanel eventDetailsPanel;


  /**
   * Constructs an EventWithModifyFrame with the given unique identifier, model, and event.
   * @param uid The unique identifier associated with the user.
   * @param model The ReadOnlyCentralSystem model to interact with.
   * @param event The event to be modified.
   */
  public EventWithModifyFrame(String uid, ReadOnlyCentralSystem model, ReadOnlyEvent event) {
    this.model = model;
    this.uid = uid;
    this.event = event;
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

  /**
   * Adds features to the frame.
   * @param features The SchedulePlannerFeatures object providing features to be added.
   */
  @Override
  public void addFeatures(SchedulePlannerFeatures features) {
    this.removeEventButton.addActionListener(evt -> {
      features.removeEvent(uid, this.event);
    });

    this.modifyEventButton.addActionListener(evt -> {

      try {
        ReadOnlyEvent event = this.eventDetailsPanel.giveEvent();
        features.modifyEvent(uid, this.event, eventDetailsPanel.giveEvent());    }
      catch (IllegalArgumentException e) {
        JOptionPane.showMessageDialog(this,"Enter a valid time");
      }

    });
  }

  /**
   * Makes the frame visible.
   */
  @Override
  public void makeVisible() {
    setVisible(true);
  }
}
