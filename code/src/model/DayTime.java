package model;

public class DayTime {
  //fields

  private int hours;
  private int minutes;
  private Day day;

  // constructor
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

  public int toInt(){
    return this.minutes + (this.hours * 100) + (this.day.dayToInt() * 10000);
  }

  public boolean dayEquals(Day d){
    return this.day.dayToInt() == d.dayToInt();
  }

  public String toString(){
    return this.day.toString() + ": " + this.hours + "" + this.minutes;
  }

  public Day day(){
    return this.day;
  }

  public String timeAsString(){
    return this.hours + "" + this.minutes;
  }




}
