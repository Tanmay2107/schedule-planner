package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import controller.SchedulePlannerFeatures;
import model.ReadOnlyCentralSystem;

public class ScheduleFrame extends JFrame implements ScheduleView,ActionListener, MouseListener {
  private ReadOnlyCentralSystem model;

  private SchedulePanel schedulePanel;

  private JPanel buttonPanel;

  private ScheduleScreenMenuBar menuBar;

  private JButton createButton, scheduleButton;
  private JComboBox<String> userBox;


  private String currentUser;



  private String[] users;

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


  public void refresh() {
    this.repaint();
  }

  public void makeVisible() {
    setVisible(true);
  }

  public void updateUserID() {
    this.schedulePanel.setUserID(users[userBox.getSelectedIndex()]);
  }

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


  @Override
  public void mouseClicked(MouseEvent e) {
    System.out.println("Mouse clicked");
  }

  @Override
  public void mousePressed(MouseEvent e) {
    System.out.println("Mouse clicked");

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

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
