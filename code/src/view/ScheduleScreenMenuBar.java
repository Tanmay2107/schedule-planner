package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.SchedulePlannerFeatures;

public class ScheduleScreenMenuBar extends JMenuBar implements ScheduleView {
  JMenu menu;

  JMenuItem menuItemLoadXML;

  JMenuItem menuItemSaveXML;

  public ScheduleScreenMenuBar() {
    super();
    menu = new JMenu("File");
    menuItemLoadXML = new JMenuItem("Add calender");

    menu.add(menuItemLoadXML);
    menuItemSaveXML = new JMenuItem("Save calender");
    menu.add(menuItemSaveXML);

    this.add(menu);
  }




  @Override
  public void addFeatures(SchedulePlannerFeatures features) {
    System.out.println("Adding features");
    menuItemLoadXML.addActionListener(evt -> {
      System.out.println("Load XML");
      JFileChooser fileChooser = new JFileChooser();
      int i = fileChooser.showOpenDialog(this);
      String path ="";
      if(i == JFileChooser.APPROVE_OPTION) {
        path = fileChooser.getSelectedFile().getAbsolutePath();
      }
      features.loadXMLFile(path);
    });

    menuItemSaveXML.addActionListener(evt -> {
      System.out.println("Save XML");
    });
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }
}

