package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.ReadOnlyCentralSystem;

public class ScheduleButtonPanel extends JPanel implements ActionListener {
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

  public String currentUser() {
    return users[userBox.getSelectedIndex()];
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == createButton) {
      System.out.println("Create Event");
    } else if (e.getSource() == scheduleButton) {
      System.out.println("Schedule Event");
    } else if (e.getSource() == userBox) {
      System.out.println("User changed to " + users[userBox.getSelectedIndex()]);
    }


  }
}
