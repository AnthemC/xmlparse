package optics.marine.usf.edu.xmltest;

import java.util.List;

public class MenuItem {
    private final String titleMI;
    private final List<MenuRegion> subItems;

    public MenuItem(String titleMI, List<MenuRegion> subItems) {
        this.titleMI = titleMI;
        this.subItems = subItems;
    }

    public List<MenuRegion> getSubItems() {
        return subItems;
    }

    public String getTitleMI() {
        return titleMI;
    }
}