package optics.marine.usf.edu.xmltest;

import java.util.List;

public class MenuRegion{
    private final String titleMR;
    private final List<ROI> subItems;

    public MenuRegion(String titleMR, List<ROI> subItems){
        this.titleMR = titleMR;
        this.subItems = subItems;
    }

    public List<ROI> getSubItems() {
        return subItems;
    }

    public String getTitleMR() {
        return titleMR;
    }
}
