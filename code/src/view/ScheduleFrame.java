package view;

import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import controller.SchedulePlannerFeatures;
import model.ReadOnlyCentralSystem;

/**
 * Frame for scheduling events and managing the schedule.
 * This frame provides the main interface for scheduling events and managing the schedule.
 */
public class ScheduleFrame extends JFrame implements ScheduleView, ActionListener, MouseListener {
  private ReadOnlyCentralSystem model;

  private SchedulePanel schedulePanel;
  private JPanel buttonPanel;
  private ScheduleScreenMenuBar menuBar;
  private JButton createButton, scheduleButton;
  private JComboBox<String> userBox;
  private String currentUser;
  private String[] users;

  /**
   * Constructs a ScheduleFrame with the given model.
   * @param model The ReadOnlyCentralSystem model providing schedule data.
   */
  public ScheduleFrame(ReadOnlyCentralSystem model) {
    super();
    setSize(800, 800);
    setDefaultCloseOperation(EXIT_ON_CLOSE);


    this.setLayout(new BorderLayout());
    this.model = model;


    this.buttonPanel = this.setUpButtonPanel();
    this.currentUser = "none";
    this.schedulePanel = new SchedulePanel(model, this.currentUser);
    this.schedulePanel.setSize(800, 600);
    this.menuBar = new ScheduleScreenMenuBar();
    this.setJMenuBar(menuBar);
    this.add(schedulePanel, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.SOUTH);
    this.currentUser = "none";
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(false);

  }


  /**
   * Sets up the button panel.
   * @return The JPanel representing the button panel.
   */
  private JPanel setUpButtonPanel(){
    JPanel buttonPanel = new JPanel();
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

    buttonPanel.add(userBox);
    buttonPanel.add(createButton);
    buttonPanel.add(scheduleButton);
    return buttonPanel;

  }

  /**
   * Refreshes the frame.
   */
  public void refresh() {
    this.repaint();
  }

  /**
   * Makes the frame visible.
   */
  public void makeVisible() {
    setVisible(true);
  }

  /**
   * Updates the user ID for the schedule panel.
   */
  public void updateUserID() {
    this.schedulePanel.setUserID(users[userBox.getSelectedIndex()]);
  }

  /**
   * Handles button click events.
   * @param e The ActionEvent object representing the action performed.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == createButton) {
      System.out.println("Create Event");
      EventWithAddFrame eventWithAddFrame = new EventWithAddFrame(this.currentUser, model);

    } else if (e.getSource() == scheduleButton) {
      System.out.println("Schedule Event");
    } else if (e.getSource() == userBox) {
      System.out.println("User changed to " + users[userBox.getSelectedIndex()]);
      this.currentUser = users[userBox.getSelectedIndex()];
      this.updateUserID();

    }
    this.refresh();
  }


  /**
   * Handles mouse click events.
   * @param e The MouseEvent object representing the mouse event.
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    System.out.println("Mouse clicked");
  }


  /**
   * Handles mouse press events.
   * @param e The MouseEvent object representing the mouse event.
   */
  @Override
  public void mousePressed(MouseEvent e) {
    System.out.println("Mouse clicked");

  }

  /**
   * Handles mouse release events.
   * @param e The MouseEvent object representing the mouse event.
   */
  @Override
  public void mouseReleased(MouseEvent e) {

  }

  /**
   * Handles mouse entered events.
   * @param e The MouseEvent object representing the mouse event.
   */
  @Override
  public void mouseEntered(MouseEvent e) {

  }

  /**
   * Handles mouse exits.
   * @param e The MouseEvent object representing the mouse event.
   */
  @Override
  public void mouseExited(MouseEvent e) {

  }

  /**
   * Adds features to the frame.
   * @param features The SchedulePlannerFeatures object providing features to be added.
   */
  @Override
  public void addFeatures(SchedulePlannerFeatures features) {
    System.out.println("Adding features");
    this.menuBar.addFeatures(features);
    this.schedulePanel.addFeatures(features);
    this.createButton.addActionListener(evt -> {
      System.out.println("Create Event");
      EventWithAddFrame eventWithAddFrame = new EventWithAddFrame(this.currentUser, model);
      eventWithAddFrame.addFeatures(features);
      eventWithAddFrame.makeVisible();
    });

    this.userBox.addActionListener(evt -> {
      System.out.println("User changed to " + users[userBox.getSelectedIndex()]);
      this.currentUser = users[userBox.getSelectedIndex()];
      this.updateUserID();
    });

    this.scheduleButton.addActionListener(evt -> {
      System.out.println("Schedule Event");
    });

  }
}
