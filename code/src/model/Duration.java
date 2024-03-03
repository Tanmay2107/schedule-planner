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

}
