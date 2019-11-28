import java.lang.object.*;

Struct U1 {
    String pseudo;
    String passeword;
}

Struct U2 {
    String pseudo;
    String passeword;
    int id;
}

// Doc GETDATE
// https://docs.oracle.com/javase/7/docs/api/java/util/Date.html
Struct DateMsg {
    int year;
    int month;
    int day;
    int hour;
    int min;
    int sec;
}

Struct Conv {
    String content:
    Struct DateMsg date;
    Struct Conv * next;
}