public class Time {
  private int day = 32;
  private int month = 13;
  private int year = 9999;
  private int hour = 13;
  private int minute = 60;
  private String inputBuffer;
  
  public Time() {
    
  }
  
  // Temporary constructor for making the premade VOLs
  public Time(int day, int month, int year, int hour, int minute) {
    this.day = day;
    this.month = month;
    this.year = year;
    this.hour = hour;
    this.minute = minute;
  }
  
  public int GetDay() {
    return day;
  }
  
  public void SetDay(int day) {
    this.day = day;
  }
  
  public int GetMonth() {
    return month;
  }
  
  public void SetMonth(int month) {
    this.month = month;
  }
  
  public int GetYear() {
    return year;
  }
  
  public void SetYear(int year) {
    this.year = year;
  }
  
  public int GetHour() {
    return hour;
  }
  
  public void SetHour(int hour) {
    this.hour = hour;
  }
  
  public int GetMinute() {
    return minute;
  }
  
  public void SetMinute(int minute) {
    this.minute = minute;
  }
  
  @Override
  public String toString() {
    String output = "";
    output = "Occuring on " + month + "/" + day + "/" + year + " at " + hour + ":" + minute;
    return output;
  }
}