import org.junit.Before;
import org.junit.Test;

// Checks methods with Central System specifically.
public class TestCentralSystem {

  @Before
  public void setUp(){
    // Set up a central system

  }

  @Test
  public void testAddNewUser(){}

  @Test
  public void testAddExistingActiveUser(){}

  @Test
  public void testAddInactiveUser(){}

  @Test
  public void testAddNullUser(){}


  @Test
  public void testAddValidEvent(){}

  @Test
  public void testAddEventInactiveHost(){}

  @Test
  public void testAddEventActiveHost(){}

  @Test
  public void testAddEventNonExistentHost(){}
  @Test
  public void testAddEventNullName(){}

  @Test
  public void testAddEventNullLocation(){}

  @Test
  public void testAddEventNullStartTime(){}

  @Test
  public void testAddEventNullEndTime(){}

  @Test
  public void testAddEventNullUsers(){}

  /*
  public void removeEvent(String uid, IEvent e){
    this.checkForActiveUser(uid);
    UserSchedule user = activeUserMap.get(uid);
    user.removeEvent(e);
  }



  public void modifyEvent(Event event, EventCommand command) {
    EventModifier modifier = new EventModifier(command);
    modifier.executeModification();
  }
   */


}
