package model;

public class DayTime {
  //fields

  int hours;
  int minutes;

  Day day;

  // constructor
  DayTime(int hours, int minutes,Day day){
    if (23 < hours || hours < 0){
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



}
