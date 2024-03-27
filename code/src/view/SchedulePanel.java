package view;

import javax.swing.JPanel;

import controller.SchedulePlannerFeatures;
import model.Day;
import model.DayTime;
import model.ReadOnlyCentralSystem;
import model.ReadOnlyEvent;
import model.ReadOnlyUsers;
import model.Event;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Panel for displaying the schedule of events.
 * This panel displays the schedule of events for a particular user on different days of the week.
 */
public class SchedulePanel extends JPanel implements ScheduleView {
  private final ReadOnlyCentralSystem model;
  private String userID;
  private HashMap<Rectangle, ReadOnlyEvent> eventMap;

  /**
   * Constructs a SchedulePanel with the given model and user ID.
   * @param model The ReadOnlyCentralSystem model providing schedule data.
   * @param userID The ID of the user whose schedule is to be displayed.
   */
  public SchedulePanel(ReadOnlyCentralSystem model, String userID) {
    super();
    this.model = model;
    this.userID = userID;
    this.eventMap = new HashMap<>();

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    System.out.println("Painting");
    Graphics2D g2d = (Graphics2D)g;
    this.drawSkeleton(g2d);
    if (!this.userID.equals("none")) {
      this.drawEvents(g2d);
    }

  }

  private void drawSkeleton(Graphics2D g2d) {

    int hours = 1;
    while (hours <=  24) {
      g2d.drawLine(0, hours * this.getHeight() / 24, this.getWidth(),
              hours * this.getHeight() / 24);
      hours++;

    }

    int days = 1;
    while (days < 7) {
      g2d.drawLine(days * this.getWidth() / 7, 0, days * this.getWidth() / 7,
              this.getHeight());
      days++;

    }

  }

  private void drawEvents(Graphics2D g2d) {
    ReadOnlyUsers user = this.model.getUser(userID);

    Day[] days = new Day[]{
    Day.SUNDAY, Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY, Day.SATURDAY};
    ReadOnlyEvent carryForwardEvent = null;
    ReadOnlyEvent carryForwardEventToDraw = null;
    for (int i = 0; i < days.length; i++) {
      ArrayList<ReadOnlyEvent> events = filterEventsByDay(user.scheduledEvents(), days[i]);

      if(!(carryForwardEvent == null)){
        events.add(carryForwardEventToDraw);
      }

      for (int j = 0; j < events.size(); j++) {
        ReadOnlyEvent event = events.get(j);
        int x = dayXcoord(event.startTime().day());
        int y = timeYcoord(event.startTime());
        int width = this.getWidth() / 7;
        int height;

        if(!event.startTime().dayEquals(event.endTime().day())){
          height = timeHeight(event.startTime(), new DayTime(23, 59,
                  event.startTime().day()));
          carryForwardEventToDraw =
                  new Event(event.name(), event.location(), event.online(),
                          new DayTime(0, 0, days[i+1]),
                  event.endTime(), event.hostID());
          carryForwardEvent = event;
        } else {
          height = timeHeight(event.startTime(), event.endTime());
        }

        Rectangle rect = new Rectangle(x, y, width, height);
        this.eventMap.put(rect, event);
        g2d.drawRect(x, y, width, height);
        g2d.setColor(Color.CYAN);
        g2d.fillRect(x, y, width, height);
        g2d.setColor(Color.BLACK);
        g2d.drawString(event.name(), x , y + height/2);
      }
    }
  }

  private int timeYcoord(DayTime time) {
    int hour = time.hours();
    int minute = time.minutes();
    int totalminutes = (hour * 60) + minute;
    return totalminutes * this.getHeight() / 1440;
  }

  private int dayXcoord(Day day) {
    return (day.dayToInt() - 1) * this.getWidth() / 7;
  }

  private int timeHeight(DayTime startTime, DayTime endTime) {
    int start = timeYcoord(startTime);
    int end = timeYcoord(endTime);
    return end - start;
  }

  /**
   * Filters events by a given day.
   *
   * @param events The list of events to filter.
   * @param day    The day to filter events by.
   * @return The list of events that occur on the specified day.
   */
  private static ArrayList<ReadOnlyEvent> filterEventsByDay(ArrayList<ReadOnlyEvent> events,
                                                            Day day) {
    ArrayList<ReadOnlyEvent> eventsForDay = new ArrayList<>();
    for (ReadOnlyEvent event : events) {
      if (event.startTime().day().dayToInt() == day.dayToInt()) {
        eventsForDay.add(event);
      }
    }
    return eventsForDay;
  }


  /**
   * Sets the user ID for the schedule panel.
   * @param currentUser The ID of the current user.
   */
  public void setUserID(String currentUser) {
    this.userID = currentUser;
    System.out.println("User ID set to " + currentUser);
    this.repaint();
  }


  /**
   * Adds features to the schedule panel.
   * @param features The SchedulePlannerFeatures object providing features to be added.
   */
  @Override
  public void addFeatures(SchedulePlannerFeatures features) {
    this.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse clicked in Panel");
        for (Rectangle rect : eventMap.keySet()) {
          if (rect.contains(e.getPoint().x, e.getPoint().y)) {
            System.out.println(userID);
            System.out.println("Event clicked: " + eventMap.get(rect).toString());
            EventWithModifyFrame modifyframe = new EventWithModifyFrame(userID,
                    model, eventMap.get(rect));
            modifyframe.addFeatures(features);
            modifyframe.makeVisible();
          }
        }
      }

      /**
       * Invoked when a mouse button has been pressed on a component.
       *
       * @param e The MouseEvent describing the event source and details.
       */
      @Override
      public void mousePressed(MouseEvent e) {

      }

      /**
       * Invoked when a mouse button has been released on a component.
       *
       * @param e The MouseEvent describing the event source and details.
       */
      @Override
      public void mouseReleased(MouseEvent e) {

      }

      /**
       * Invoked when the mouse enters a component.
       *
       * @param e The MouseEvent describing the event source and details.
       */
      @Override
      public void mouseEntered(MouseEvent e) {

      }

      /**
       * Invoked when the mouse exits a component.
       *
       * @param e The MouseEvent describing the event source and details.
       */
      @Override
      public void mouseExited(MouseEvent e) {

      }
    });

  }

  /**
   * Makes the schedule panel visible.
   */
  @Override
  public void makeVisible() {
    setVisible(true);
  }
}
