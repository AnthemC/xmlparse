package optics.marine.usf.edu.xmltest;

public class StartCal {
    private String day;
    private String month;
    private String year;

    public StartCal(String day, String month, String year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getDayStart() {
        return day;
    }

    public String getMonthStart() {
        return month;
    }

    public String getYearStart() {
        return year;
    }

}
