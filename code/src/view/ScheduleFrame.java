package view;


import java.awt.BorderLayout;

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
public class ScheduleFrame extends JFrame implements CentralSystemView {
  private ReadOnlyCentralSystem model;

  private SchedulePanel schedulePanel;

  private ScheduleScreenMenuBar menuBar;
  private JButton createButton;

  private JButton scheduleButton;
  private JComboBox<String> userBox;
  private String currentUser;
  private String[] users;

  /**
   * Constructs a ScheduleFrame with the given model.
   *
   * @param model The ReadOnlyCentralSystem model providing schedule data.
   */
  public ScheduleFrame(ReadOnlyCentralSystem model) {
    super();
    setSize(800, 800);
    setDefaultCloseOperation(EXIT_ON_CLOSE);


    this.setLayout(new BorderLayout());
    this.model = model;


    JPanel buttonPanel = this.setUpButtonPanel();
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
   *
   * @return The JPanel representing the button panel.
   */
  private JPanel setUpButtonPanel() {
    JPanel buttonPanel = new JPanel();
    this.createButton = new JButton("Create Event");

    this.scheduleButton = new JButton("Schedule Event");

    String[] users = new String[model.getUserIds().size() + 1];
    for (int i = 0; i < model.getUserIds().size(); i++) {
      users[i] = model.getUserIds().get(i);
    }
    users[model.getUserIds().size()] = "none";
    this.users = users;
    this.userBox = new JComboBox<String>(users);
    this.userBox.setSelectedIndex(model.getUserIds().size());


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
   * Adds features to the frame.
   *
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
      EventWithAddFrame eventWithAddFrame = new EventWithAddFrame(this.currentUser, model);
      eventWithAddFrame.addFeatures(features);
      eventWithAddFrame.makeVisible();
    });


  }

  @Override
  public String displayScheduleAsString() {
    CentralSystemTextView textView = new CentralSystemTextView(this.model);
    return this.model.toString();
  }
}
