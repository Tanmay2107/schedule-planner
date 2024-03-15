import org.junit.Before;
import org.junit.Test;

import model.UserSchedule;
import model.AUsers;

import static org.junit.Assert.*;

public class AUsersTest {
  AUsers activeUser1;
  AUsers activeUser2 ;
  AUsers activeUser3 ;
  AUsers inactiveUser1;
  AUsers inactiveUser2 ;
  AUsers inactiveUser3;
  @Before
  public void setUp() {
    activeUser1 = new UserSchedule("activeUser1");
    activeUser2 = new UserSchedule("activeUser2");
    activeUser3 = new UserSchedule("activeUser3");
    inactiveUser1 = new UserSchedule("inactiveUser1");
    inactiveUser2 = new UserSchedule("inactiveUser2");
    inactiveUser3 = new UserSchedule("inactiveUser3");

  }

  @Test
  public void userID() {
    assertEquals("activeUser1", activeUser1.userID());
    assertEquals("activeUser2", activeUser2.userID());
    assertEquals("activeUser3", activeUser3.userID());
    assertEquals("inactiveUser1", inactiveUser1.userID());
    assertEquals("inactiveUser2", inactiveUser2.userID());
    assertEquals("inactiveUser3", inactiveUser3.userID());
  }

  @Test
  public void removeEvent() {
  }

  @Test
  public void inviteUser() {
  }

  @Test
  public void overlappingEventExists() {
  }

  @Test
  public void activateInactiveUser() {
  }

  @Test
  public void activateActiveUser() {
  }

  @Test
  public void scheduledEvents() {
  }
}