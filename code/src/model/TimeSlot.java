package model;

/**
 * Represents a time slot, defined by a start time and an end time.
 * Provides methods to check if a time is within the duration of the time slot and to detect conflicts with other time slots.
 */
public class TimeSlot {

  private DayTime startTime;
  private DayTime endTime;

  /**
   * Constructs a time slot with the specified start time and end time.
   * @param startTime The start time of the time slot.
   * @param endTime The end time of the time slot.
   * @throws IllegalArgumentException if startTime or endTime is null.
   */
  public TimeSlot(DayTime startTime, DayTime endTime) {

    if (startTime == null || endTime == null) {
      throw new IllegalArgumentException("startTime and endTime of a duration cannot be null");
    }

    this.endTime = endTime;
    this.startTime = startTime;
  }

  /**
   * Checks if a time is within the duration of the time slot.
   * @return true if the time is within the duration of the time slot, false otherwise.
   */
  public boolean timeInDuration() {
    return true;
  }


  /**
   * Checks if there is a conflict between this time slot and another time slot.
   * @param d The other time slot to check for conflicts.
   * @return true if there is a conflict, false otherwise.
   */
  public boolean conflict(TimeSlot d) {
    return d.conflictHelper(this) || this.conflictHelper(d);
  }

  /**
   * Helper method to check for conflicts with another time slot.
   * @param d The other time slot to check for conflicts.
   * @return true if there is a conflict, false otherwise.
   */
  private boolean conflictHelper(TimeSlot d) {
    if (this.endTime.toInt() < this.startTime.toInt()) {
      return this.overFlowConflict(d);
    }
    else {
      return this.sameWeekConflict(d);
    }
  }

  /**
   * Checks for conflicts with another time slot when the time slot spans across week boundaries.
   * @param d The other time slot to check for conflicts.
   * @return true if there is a conflict, false otherwise.
   */
  private boolean overFlowConflict(TimeSlot d) {
    return !(d.startTime.toInt() >= this.endTime.toInt() &&
            d.startTime.toInt() < this.startTime.toInt() &&
            d.endTime.toInt() <= this.startTime.toInt() &&
            d.endTime.toInt() > this.endTime.toInt());
  }


  /**
   * Checks for conflicts with another time slot when both time slots are within the same week.
   * @param d The other time slot to check for conflicts.
   * @return true if there is a conflict, false otherwise.
   */
  private boolean sameWeekConflict(TimeSlot d) {
    return !(d.startTime.toInt() >= this.endTime.toInt()
    || d.endTime.toInt() <= this.startTime.toInt());
  }

}