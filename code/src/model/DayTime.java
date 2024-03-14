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

  public Day day(){
    return this.day;
  }

  public String toString(){
    return this.day.toString() + ": " + formatTime(this.hours) + ":" + formatTime(this.minutes);
  }

  public String timeAsString(){
    return formatTime(this.hours) + ":" + formatTime(this.minutes);
  }

  private String formatTime(int timeValue) {
    return timeValue < 10 ? "0" + timeValue : String.valueOf(timeValue);
  }



}
