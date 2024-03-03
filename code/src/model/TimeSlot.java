package model;

public class Duration {

  private DayTime startTime;
  private DayTime endTime;

  // constructor
  public Duration(DayTime startTime, DayTime endTime){

    if (startTime == null || endTime == null){
      throw new IllegalArgumentException("startTime and endTime of a duration cannot be null");
    }

    this.endTime = endTime;
    this.startTime = startTime;
  }

  public boolean timeInDuration(){
    return true;

  }


  //return true if there is conflict
  public boolean conflict(TimeSlot d) {
    return d.conflictHelper(this) || this.conflictHelper(d);
  }

  private boolean conflictHelper(TimeSlot d) {

    if (this.endTime.toInt() < this.startTime.toInt()) {
      return this.overFlowConflict(d);
    }
    else {
      return this.sameWeekConflict(d);
    }

  }

  private boolean overFlowConflict(TimeSlot d) {
    return !(d.startTime.toInt() >= this.endTime.toInt() &&
            d.startTime.toInt() < this.startTime.toInt() &&
            d.endTime.toInt() <= this.startTime.toInt() &&
            d.endTime.toInt() > this.endTime.toInt());
  }


  // returns true if there is a conflict for events that last within the same week.
  private boolean sameWeekConflict(TimeSlot d) {
    return (d.startTime.toInt() < this.endTime.toInt());
  }



}