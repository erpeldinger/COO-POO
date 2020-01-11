package format;

import java.lang.Object.*;
import java.util.Calendar;
import java.util.Date;
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
    
    public DateMsg() {
    	Calendar now = Calendar.getInstance();
    	this.year = now.get(Calendar.YEAR);
    	this.month = now.get(Calendar.MONTH) + 1;
    	this.day = now.get(Calendar.DAY_OF_MONTH);
    	this.hour = now.get(Calendar.HOUR_OF_DAY);
    	this.min = now.get(Calendar.MINUTE);
    	this.sec = now.get(Calendar.SECOND);    
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
    public static String toString(DateMsg date){
        return new String(Integer.toString(date.year) + ":" + Integer.toString(date.month) + ":" + Integer.toString(date.day) + ":" + Integer.toString(date.hour) + ":" + Integer.toString(date.min) + ":" + Integer.toString(date.sec));
    }
    
}