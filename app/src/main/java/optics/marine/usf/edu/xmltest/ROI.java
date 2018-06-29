package optics.marine.usf.edu.xmltest;

public class ROI{
    private final String titleROI;
    private final String link;

    public ROI(String titleROI, String link){
        this.titleROI = titleROI;
        this.link  = link;
    }

    public String getLink() {
        return link;
    }

    public String getTitleROI() {
        return titleROI;
    }
}
