import java.lang.object.*;
import constantes.java;

// https://docs.oracle.com/javase/7/docs/api/java/util/Date.html

public class U1 {

    //attributs
    int year;
    int month;
    int day;
    int hour;
    int min;
    int sec;

    // constructeurs
    public void U1 (int year, int month, int day, int hour, int min, int sec) {
        this.year=year;
        this.month=month;
        this.day=day;
        this.hour=hour;
        this.min=min;
        this.sec=sec;
    }

    //getters
    public String getYear() { return this.year; }
    public String getMonth() { return this.month; }
    public String getDay() { return this.day; }
    public String getMin() { return this.min; }
    public String getSec() { return this.sec; }

    //setter
    public void setYear(int y) { this.year=y; }
    public void setMonth(int m) { this.year=m; }
    public void setDay(int d) { this.year=d; }
    public void setMin(int m) { this.year=m; }
    public void setSec(int s) { this.year=s; }

    //methodes
    
}