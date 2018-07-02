package optics.marine.usf.edu.xmltest;

import java.util.List;

public class Pass {
    private String sensor;
    private String hour;
    private String minute;
    private List<MyImages> images;

    public Pass(String sensor, String hour, String minute, List<MyImages> images){
        this.sensor = sensor;
        this.hour = hour;
        this.minute = minute;
        this.images = images;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getSensor() {
        return sensor;
    }

    public List<MyImages> getImages() {
        return images;
    }
}
