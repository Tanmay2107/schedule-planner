package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import controller.SchedulePlannerFeatures;
import model.ReadOnlyCentralSystem;

/**
 * Panel containing buttons for creating and scheduling events.
 * This panel allows users to select a user from a dropdown box and then create or
 * schedule events accordingly.
 */
public class ScheduleButtonPanel extends JPanel implements ActionListener, ScheduleView {
  private JButton createButton, scheduleButton;
  private JComboBox<String> userBox;

  private ReadOnlyCentralSystem model;

  private String[] users;

  public ScheduleButtonPanel(ReadOnlyCentralSystem model) {
    super();
    this.model = model;
    this.createButton = new JButton("Create Event");
    this.createButton.addActionListener(this);
    this.scheduleButton = new JButton("Schedule Event");
    this.scheduleButton.addActionListener(this);
    String[] users = new String[model.getUserIds().size() + 1];
    for (int i = 0; i < model.getUserIds().size(); i++) {
      users[i] = model.getUserIds().get(i);
    }
    users[model.getUserIds().size()] = "none";
    this.users = users;
    this.userBox = new JComboBox<String>(users);
    this.userBox.setSelectedIndex(model.getUserIds().size());
    this.userBox.addActionListener(this);


    this.add(userBox);
    this.add(createButton);
    this.add(scheduleButton);
  }

  /**
   * Retrieves the currently selected user.
   * @return The username of the currently selected user.
   */
  public String currentUser() {
    return users[userBox.getSelectedIndex()];
  }


  /**
   * Handles button click events.
   * @param e The ActionEvent object representing the action performed.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == createButton) {
      System.out.println("Create Event");
      EventWithAddFrame eventWithAddFrame = new EventWithAddFrame(this.currentUser(), model);
    } else if (e.getSource() == scheduleButton) {
      System.out.println("Schedule Event");
    } else if (e.getSource() == userBox) {
      System.out.println("User changed to " + users[userBox.getSelectedIndex()]);
    }


  }


  /**
   * Adds features to the panel.
   * @param features The SchedulePlannerFeatures object providing features to be added.
   */
  @Override
  public void addFeatures(SchedulePlannerFeatures features) {

  }

  /**
   * Makes the panel visible.
   */
  @Override
  public void makeVisible() {
    setVisible(true);
  }
}
