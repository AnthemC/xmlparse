package optics.marine.usf.edu.xmltest;

import java.util.List;

public class ProcessedData {
    private List menuEntries;
    private StartCal CalStart;
    private EndCal CalEnd;
    private List<Pass> image;

    public ProcessedData(List menuEntries, StartCal CalStart, EndCal CalEnd, List<Pass> image){
        this.CalEnd = CalEnd;
        this.CalStart = CalStart;
        this.menuEntries = menuEntries;
        this.image = image;
    }

    public List getMenuEntries() {
        return menuEntries;
    }

    public List<Pass> getImage() {
        return image;
    }

    public EndCal getCalEnd() {
        return CalEnd;
    }

    public StartCal getCalStart() {
        return CalStart;
    }
}
