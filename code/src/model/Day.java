package model;

public enum Day {

  MONDAY(1, "Monday"),
  TUESDAY(2, "Tuesday"),
  WEDNESDAY(3, "Wednesday"),
  THURSDAY(4, "Thursday"),
  FRIDAY(5, "Friday"),
  SATURDAY(6, "Saturday"),
  SUNDAY(7, "Sunday");

  private final int value;

  private final String stringValue;

  Day(int value, String stringValue) {
    this.value = value;
    this.stringValue = stringValue;
  }

  private int getValue() {
    return value;
  }

  public int dayToInt() {
    return this.getValue();
  }

  public String toString() {
    return this.stringValue;
  }

}
