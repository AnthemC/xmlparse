package optics.marine.usf.edu.xmltest;

import java.util.List;

public class MyImages {

    private List<String> attributes;
    private String contents;

    public MyImages(List<String> attributes, String contents){
        this.attributes = attributes;
        this.contents = contents;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public String getContents() {
        return contents;
    }
    
}
