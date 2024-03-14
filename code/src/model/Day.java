package model;

/**
 * Represents the days of the week.
 */
public enum Day {
  SUNDAY(1, "Sunday"),
  MONDAY(2, "Monday"),
  TUESDAY(3, "Tuesday"),
  WEDNESDAY(4, "Wednesday"),
  THURSDAY(5, "Thursday"),
  FRIDAY(6, "Friday"),
  SATURDAY(7, "Saturday");

  private final int value;

  private final String stringValue;

  /**
   * Constructs a Day enum with the specified value and string representation.
   *
   * @param value        The numerical value associated with the day.
   * @param stringValue The string representation of the day.
   */
  Day(int value, String stringValue) {
    this.value = value;
    this.stringValue = stringValue;
  }

  /**
   * Returns the numerical value associated with the day.
   *
   * @return The numerical value of the day.
   */
  private int getValue() {
    return value;
  }

  /**
   * Returns the numerical value representing the day.
   *
   * @return The numerical value representing the day.
   */
  public int dayToInt() {
    return this.getValue();
  }

  /**
   * Returns the string representation of the day.
   *
   * @return The string representation of the day.
   */
  public String toString() {
    return this.stringValue;
  }

}
