import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.Day;
import model.DayTime;


/**
 * Tests the DayTime class.
 */
public class DayTimeTest {
  DayTime mon1000;
  DayTime mon1000_1;

  DayTime mon1000_2;
  DayTime tue1200;
  DayTime thur1200;
  DayTime thur1300;

  /**
   * Initializes up the variables for the tests.
   */
  @Before
  public void setUp() {
    mon1000 = new DayTime(10, 00, Day.MONDAY);
    mon1000_1 = new DayTime(10, 00, Day.MONDAY);
    mon1000_2 = new DayTime(10, 00, Day.MONDAY);
    tue1200 = new DayTime(12, 00, Day.TUESDAY);
    thur1200 = new DayTime(12, 00, Day.THURSDAY);
    thur1300 = new DayTime(13, 00, Day.THURSDAY);
  }


  /**
   * Tests the constructor with invalid hours.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidHours() {
    mon1000 = new DayTime(-1, 00, Day.MONDAY);
  }

  /**
   * Tests the constructor with invalid hours.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidHours1() {
    mon1000 = new DayTime(25, 00, Day.MONDAY);
  }

  /**
   * Tests the constructor with invalid minutes.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMinutes() {
    mon1000 = new DayTime(12, -1, Day.MONDAY);
  }

  /**
   * Tests the constructor with invalid minutes.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMinutes1() {
    mon1000 = new DayTime(12, 98, Day.MONDAY);
  }

  /**
   * Tests the constructor with null day.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullDay() {
    mon1000 = new DayTime(12, 98, null);
  }


  /**
   * Tests the toInt method.
   */
  @Test
  public void toInt() {
    Assert.assertEquals(21000, mon1000.toInt());
    Assert.assertEquals(31200, tue1200.toInt());
    Assert.assertEquals(51200, thur1200.toInt());

  }

  /**
   * Tests the dayEquals method.
   */
  @Test
  public void dayEquals() {
    Assert.assertTrue(mon1000.dayEquals(Day.MONDAY));
    Assert.assertFalse(mon1000.dayEquals(Day.TUESDAY));
  }

  /**
   * Tests the day method.
   */
  @Test
  public void day() {
    Assert.assertEquals(Day.MONDAY, mon1000.day());
    Assert.assertEquals(Day.TUESDAY, tue1200.day());
    Assert.assertEquals(Day.THURSDAY, thur1200.day());
  }

  /**
   * Tests the toString method.
   */
  @Test
  public void testToString() {
    Assert.assertEquals("Monday: 10:00", mon1000.toString());
    Assert.assertEquals("Tuesday: 12:00", tue1200.toString());
    Assert.assertEquals("Thursday: 12:00", thur1200.toString());
  }

  /**
   * Tests the timeAsString method.
   */
  @Test
  public void timeAsString() {
    Assert.assertEquals("10:00", mon1000.timeAsString());
    Assert.assertEquals("12:00", tue1200.timeAsString());
    Assert.assertEquals("12:00", thur1200.timeAsString());
  }

  /**
   * Tests the timeAsXMLString method.
   */
  @Test
  public void timeAsXMLString() {
    Assert.assertEquals("1000", mon1000.timeAsXMLString());
    Assert.assertEquals("1200", tue1200.timeAsXMLString());
    Assert.assertEquals("1200", thur1200.timeAsXMLString());
  }

  /**
   * Tests the if the equals override works correctly.
   */
  @Test
  public void testEquals() {
    Assert.assertTrue(mon1000.equals(mon1000_1));
    Assert.assertTrue(mon1000.equals(mon1000_2));
    Assert.assertTrue(mon1000_1.equals(mon1000_2));
    Assert.assertFalse(mon1000.equals(tue1200));
    Assert.assertFalse(mon1000_1.equals(tue1200));
    Assert.assertFalse(mon1000_2.equals(tue1200));
    Assert.assertFalse(thur1200.equals(thur1300));
  }
}