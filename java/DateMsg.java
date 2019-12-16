import java.lang.Object.*;

// https://docs.oracle.com/javase/7/docs/api/java/util/Date.html

public class DateMsg {

    //attributs
    int year;
    int month;
    int day;
    int hour;
    int min;
    int sec;

    // constructeurs
    public DateMsg (int year, int month, int day, int hour, int min, int sec) {
        this.year=year;
        this.month=month;
        this.day=day;
        this.hour=hour;
        this.min=min;
        this.sec=sec;
    }

    //getters
    public int getYear() { return this.year; }
    public int getMonth() { return this.month; }
    public int getDay() { return this.day; }
    public int getMin() { return this.min; }
    public int getSec() { return this.sec; }

    //setter
    public void setYear(int y) { this.year=y; }
    public void setMonth(int m) { this.year=m; }
    public void setDay(int d) { this.year=d; }
    public void setMin(int m) { this.year=m; }
    public void setSec(int s) { this.year=s; }

    //methodes
    
}