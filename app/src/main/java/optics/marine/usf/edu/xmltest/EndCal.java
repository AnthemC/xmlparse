package optics.marine.usf.edu.xmltest;

public class EndCal {
    private String day;
    private String month;
    private String year;

    public EndCal(String day, String month, String year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getDayEnd() {
        return day;
    }

    public String getMonthEnd() {
        return month;
    }

    public String getYearEnd() {
        return year;
    }
}
