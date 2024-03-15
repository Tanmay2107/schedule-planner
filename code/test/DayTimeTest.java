import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.Day;
import model.DayTime;

import static org.junit.Assert.*;

public class DayTimeTest {
  DayTime mon1000 ;
  DayTime mon1000_1;
  DayTime tue1200 ;
  DayTime thur1200 ;
  DayTime thur1300;

  @Before
  public void setUp() {
     mon1000 = new DayTime(10, 00, Day.MONDAY);
     mon1000_1 = new DayTime(10, 00, Day.MONDAY);
     tue1200 = new DayTime(12, 00, Day.TUESDAY);
     thur1200 = new DayTime(12, 00, Day.THURSDAY);
     thur1300 = new DayTime(13, 00, Day.THURSDAY);
  }

  @Test
  public void toInt() {
    Assert.assertEquals(21000, mon1000.toInt());
    Assert.assertEquals(31200, tue1200.toInt());
    Assert.assertEquals(51200, thur1200.toInt());

  }

  @Test
  public void dayEquals() {
    Assert.assertTrue(mon1000.dayEquals(Day.MONDAY));
    Assert.assertFalse(mon1000.dayEquals(Day.TUESDAY));
  }

  @Test
  public void day() {
    Assert.assertEquals(Day.MONDAY, mon1000.day());
    Assert.assertEquals(Day.TUESDAY, tue1200.day());
    Assert.assertEquals(Day.THURSDAY, thur1200.day());
  }

  @Test
  public void testToString() {
    Assert.assertEquals("Monday: 10:00", mon1000.toString());
    Assert.assertEquals("Tuesday: 12:00", tue1200.toString());
    Assert.assertEquals("Thursday: 12:00", thur1200.toString());
  }

  @Test
  public void timeAsString() {
    Assert.assertEquals("10:00", mon1000.timeAsString());
    Assert.assertEquals("12:00", tue1200.timeAsString());
    Assert.assertEquals("12:00", thur1200.timeAsString());
  }

  @Test
  public void timeAsXMLString() {
    Assert.assertEquals("1000", mon1000.timeAsXMLString());
    Assert.assertEquals("1200", tue1200.timeAsXMLString());
    Assert.assertEquals("1200", thur1200.timeAsXMLString());
  }

  @Test
  public void testEquals() {
    Assert.assertTrue(mon1000.equals(mon1000_1));
    Assert.assertFalse(mon1000.equals(tue1200));
    Assert.assertFalse(thur1200.equals(thur1300));
  }
}