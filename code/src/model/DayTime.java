package model;

/**
 * Represents a specific time of the day, associated with a particular day of the week.
 */
public class DayTime {
  //fields

  private int hours;
  private int minutes;
  private Day day;

  /**
   * Constructs a DayTime object with the specified hours, minutes, and day.
   *
   * @param hours   The hours component of the time (0-23).
   * @param minutes The minutes component of the time (0-59).
   * @param day     The day of the week associated with the time.
   * @throws IllegalArgumentException if hours, minutes are out of range, or if day is null.
   */
  public DayTime(int hours, int minutes, Day day){
    if (23 < hours || hours < 0){
      throw new IllegalArgumentException("Hours need to be between");
    }

    if (59 < minutes || minutes < 0){
      throw new IllegalArgumentException("Hours need to be between");
    }

    if (day == null){
      throw new IllegalArgumentException("Day cannot be null");
    }
    this.hours = hours;
    this.minutes = minutes;
    this.day = day;
  }

  /**
   * Returns the time as an integer value representing hours and minutes.
   *
   * @return The time as an integer value.
   */
  public int toInt(){
    return this.minutes + (this.hours * 100) + (this.day.dayToInt() * 10000);
  }

  /**
   * Checks if the DayTime object's day is equal to the specified Day object.
   *
   * @param d The Day object to compare with.
   * @return true if the days are equal, false otherwise.
   */
  public boolean dayEquals(Day d){
    return this.day.dayToInt() == d.dayToInt();
  }

  /**
   * Returns the day associated with this DayTime object.
   *
   * @return The day associated with this DayTime object.
   */
  public Day day(){
    return this.day;
  }

  /**
   * Returns a string representation of the DayTime object, including the day and time.
   *
   * @return A string representation of the DayTime object.
   */
  public String toString(){
    return this.day.toString() + ": " + formatTime(this.hours) + ":" + formatTime(this.minutes);
  }

  /**
   * Returns the time formatted as a string (HH:MM).
   *
   * @return The time formatted as a string.
   */
  public String timeAsString(){
    return formatTime(this.hours) + ":" + formatTime(this.minutes);
  }

  /**
   * Returns the time formatted as a string (HHMM).
   *
   * @return The time formatted as a string for the XML file.
   */
  public String timeAsXMLString(){
    return formatTime(this.hours) + formatTime(this.minutes);
  }

  /**
   * Formats the given time value (hours or minutes) to ensure it has two digits.
   *
   * @param timeValue The time value to format.
   * @return The formatted time value as a string.
   */
  private String formatTime(int timeValue) {
    return timeValue < 10 ? "0" + timeValue : String.valueOf(timeValue);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof DayTime) {
      DayTime that = (DayTime) o;
      return this.hours == that.hours && this.minutes == that.minutes && this.day.equals(that.day);
    }
    else {
      return false;
    }
  }

}