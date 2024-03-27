package view;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;

import controller.SchedulePlannerFeatures;

/**
 * A menu bar for the schedule screen, providing options for loading and saving calendar data.
 */
public class ScheduleScreenMenuBar extends JMenuBar implements ScheduleView {
  JMenu menu;

  JMenuItem menuItemLoadXML;

  JMenuItem menuItemSaveXML;

  /**
   * Constructs a new ScheduleScreenMenuBar with default menu items.
   */
  public ScheduleScreenMenuBar() {
    super();
    menu = new JMenu("File");
    menuItemLoadXML = new JMenuItem("Add calender");

    menu.add(menuItemLoadXML);
    menuItemSaveXML = new JMenuItem("Save calender");
    menu.add(menuItemSaveXML);

    this.add(menu);
  }


  /**
   * Adds the provided features to this menu bar.
   *
   * @param features The SchedulePlannerFeatures object containing the functionality to be
   *                added to the menu items.
   */
  @Override
  public void addFeatures(SchedulePlannerFeatures features) {
    System.out.println("Adding features");
    menuItemLoadXML.addActionListener(evt -> {
      System.out.println("Load XML");
      JFileChooser fileChooser = new JFileChooser();
      int i = fileChooser.showOpenDialog(this);
      String path = "";
      if (i == JFileChooser.APPROVE_OPTION) {
        path = fileChooser.getSelectedFile().getAbsolutePath();
      }
      features.loadXMLFile(path);
    });

    menuItemSaveXML.addActionListener(evt -> {
      System.out.println("Save XML");
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setCurrentDirectory(new java.io.File("."));
      fileChooser.setDialogTitle("SelectDirectory");
      fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      int i = fileChooser.showOpenDialog(this);
      String path = "";
      if (i == JFileChooser.APPROVE_OPTION) {
        path = fileChooser.getSelectedFile().getAbsolutePath();
      }
      features.saveXMLFile(path);
    });
  }

  /**
   * Makes this menu bar visible.
   */
  @Override
  public void makeVisible() {
    setVisible(true);
  }
}

