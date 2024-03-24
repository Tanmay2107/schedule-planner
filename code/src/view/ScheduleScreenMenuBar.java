package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ScheduleScreenMenuBar extends JMenuBar implements ActionListener {
  JMenu menu;

  JMenuItem menuItemLoadXML;

  JMenuItem menuItemSaveXML;

  public ScheduleScreenMenuBar() {
    super();
    menu = new JMenu("File");
    menuItemLoadXML = new JMenuItem("Add calender");
    menuItemLoadXML.addActionListener(this);
    menu.add(menuItemLoadXML);
    menuItemSaveXML = new JMenuItem("Save calender");
    menu.add(menuItemSaveXML);
    menuItemSaveXML.addActionListener(this);
    this.add(menu);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == menuItemLoadXML) {
      System.out.println("Load XML");
      JFileChooser fileChooser = new JFileChooser();
      int i = fileChooser.showOpenDialog(this);
      if(i == JFileChooser.APPROVE_OPTION) {
        System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
      }
    } else if(e.getSource() == menuItemSaveXML) {
      System.out.println("Save XML");

    }
  }
}

