package optics.marine.usf.edu.xmltest;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



public class TestXmlParser {
    // We don't use namespaces
    private static final String ns = null;

    public static ProcessedData parse(InputStream in) throws XmlPullParserException, IOException {
        Log.i("parse", "in read parse");
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();

            return processReceivedData(parser);
        }finally {
            in.close();
        }

        /*catch (IOException e) {
            e.printStackTrace();
        }*/
        //return null;
    }


    /*Build a way to paste together the URL for the images based on the basic url, the <this_request> tag and the
      <image> tag.
     */

    private static ProcessedData processReceivedData(XmlPullParser xmlData) throws IOException, XmlPullParserException {
        Log.i("processRecievedData", "in processedRevievedData");

        List<MenuItem> menuEntries = new ArrayList();
        StartCal startDate = new StartCal("","","");
        EndCal endDate = new EndCal("","","");
        List<Pass> image = new ArrayList<>();

        //int eventType = xmlData.getEventType();

        xmlData.require(XmlPullParser.START_TAG, ns, "response");
        Log.i("processRecievedData", "after .require");
        while (xmlData.next() != XmlPullParser.END_TAG) {
            //Log.i("while loop", "in while loop in processData");
            if (xmlData.getEventType() != XmlPullParser.START_TAG) {
                Log.i("continue", "in continue");
                continue;
            }
            String name = xmlData.getName();
            Log.i("name", name);
            // Starts by looking for the entry tag
            if(name.equals("menu_top")) {
                Log.i("processIf", "in read the readEntry call in processRecievedData");
                menuEntries.add(readEntry(xmlData));
            }else if (name.equals("calendar")) {
                startDate = readCalendarStart(xmlData);
                endDate = readCalendarEnd(xmlData);
            }else if (name.equals("images")) {
                image = readImages(xmlData);
            }else {
                skip(xmlData);
            }
        }
        /*ProcessedData processedData = new ProcessedData(){{
           setCalEnd(endDate);
           setCalStart(startDate);
           setImage(image);
           setMenuEntries(menuEntries);
        }};*/
        return new ProcessedData(menuEntries, startDate, endDate, image);
    }

    /*
    ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    Lower Level Reading Methods

    Each of these methods is used to read the text that is in the tags or the attributes into variables that
    are returned and stored into the custom data types. Those will be returned to the higher data type in a
    structure that will be easy to get all of the variables out of.
     */

    // Processes title tags in the feed.
/*    private static String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    // Processes link tags in the feed.
    private static String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        String link = "";
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String tag = parser.getName();
        String relType = parser.getAttributeValue(null, "rel");
        if (tag.equals("link")) {
            if (relType.equals("alternate")){
                link = parser.getAttributeValue(null, "href");
                parser.nextTag();
            }
        }
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }*/

    // For the tags title and summary, extracts their text values.
    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private static List<String> readAttribute(XmlPullParser parser) throws IOException, XmlPullParserException{
        List<String> attribute = new ArrayList<>();
        for(int i = 0; i <= parser.getAttributeCount(); i++) {
            if (parser.next() == XmlPullParser.TEXT) {
                attribute.add(parser.getAttributeValue(i));
                parser.nextTag();
            }
        }
        return attribute;
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    /*
    this may be something that needs to be reworked with if() trees and not switch cases. I am not sure if they will get
    to the other cases if one is triggered and then returned. Perhaps it will be possible to do so with loops in front of
    each of the switch cases, if not if() trees will have to be used. Maybe it will work if there are no breaks, see
    documentation of this here: https://docs.oracle.com/javase/tutorial/java/nutsandbolts/switch.html

    I think this actually should work without change because i had forgotten about the while loop that was already there
    ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    Menu Item Reading

    The menu is read and returned as a single MenuItem object. This object however is only that of Satellite Data Products,
    if there were the other parts of the menu there would have to be multiple(List) of these objects returned. We store the
    title and the subItems of this menu in the object. The subItems happens to be a list of the MenuRegion objects. The
    MenuRegion object stores a title of the region that it is and the sub areas of that called Region of Interest(ROI).
    The ROI method returns an ROI object for each MenuRegion that there is. the ROI contains the name of the region and the
    link that it goes to.


    (may have to edit this for it to make more sense and the code to make sure it works. It probably doesn't right now.)
    */


    private static MenuItem readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        Log.i("readEntry", "in read entry");
        //parser.require(XmlPullParser.START_TAG, ns, "menu");
        String title = null;
        List subItems = new ArrayList<>();
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "title":
                    title = readText(parser);
                    break;
                case "menu_region":
                    subItems.add(readRegion(parser));
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return new MenuItem(title, subItems);
    }

    private static MenuRegion readRegion(XmlPullParser parser) throws XmlPullParserException, IOException {
        //parser.require(XmlPullParser.START_TAG, ns, "menu_region");
        String title = null;
        List subItems = new ArrayList<>();
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if(name.equals("title")) {
                title = readText(parser);
            }else if (name.equals("menu_region")) {
                subItems.add(readROI(parser));
            }else {
                skip(parser);
            }
        }
        return new MenuRegion(title, subItems);
    }

    private static ROI readROI(XmlPullParser parser) throws XmlPullParserException, IOException {
        Log.i("ROI", "in readROI");
        //parser.require(XmlPullParser.START_TAG, ns, "roi");
        String title = null;
        String link = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                title = readText(parser);
            }else if (name.equals("link")) {
                link = readText(parser);
            }else{
                skip(parser);
            }
        }
        return new ROI(title, link);
    }

    /*
    ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    Calendar Date Reading

    In the calendar tag there is a calendar_start subtag and a calendar_end subtag. Each of these designates the day that we started collecting
    data and the last day that we have data for. We have a start and end object for each of these that has the day month and year for easier
    processing and parsing. It finds the day tag  the month tag and the year tag in the calendar_start and calendar_end tags. From there it stores
    them to be used in the calendar setting later on.
     */

    private static StartCal readCalendarStart(XmlPullParser parser) throws XmlPullParserException, IOException {
        //parser.require(XmlPullParser.START_TAG, ns, "calendar_start");
        String day = null;
        String month = null;
        String year = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("day")) {
                day = readText(parser);
            }else if (name.equals("month")) {
                month = readText(parser);
            }else if (name.equals("year")) {
                year = readText(parser);
            }else {
                skip(parser);
            }
        }
        return new StartCal(day, month, year);
    }

    private static EndCal readCalendarEnd(XmlPullParser parser) throws XmlPullParserException, IOException {
        //parser.require(XmlPullParser.START_TAG, ns, "calendar_end");
        String day = null;
        String month = null;
        String year = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("day")) {
                day = readText(parser);
            }else if (name.equals("month")) {
                month = readText(parser);
            }else if (name.equals("year")) {
                year = readText(parser);
            }else {
                skip(parser);
            }
        }
        return new EndCal(day, month, year);
    }

    /*
    ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    Image Tag Reading

    what happens is that the parser starts reading from the image tag then it moves through and sees if the tag is equal to "pass" then it
    calls readPass() which then finds the attributes of the Pass tag and it stores them in the Pass object along with a List of image items.
    The image items also have their attributes read and stored in their instantiated MyImages object and the text that is inside the tag.
    The attributes in the pass tag is sensor, hour, and minute respectively. In the image tag it is the type, png400, and pngfull respectively.
     */


    private static List<Pass> readImages(XmlPullParser parser) throws XmlPullParserException, IOException{
        //parser.require(XmlPullParser.START_TAG, ns, "images");
        List<Pass> images = new ArrayList<>();

        while (parser.next() != XmlPullParser.START_TAG){
            if (parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            if(name.equals("pass")) {
                images.add(readPass(parser));
            }else {
                skip(parser);
            }
        }
        return images;
    }

    private static Pass readPass(XmlPullParser parser) throws XmlPullParserException, IOException{
        //parser.require(XmlPullParser.START_TAG, ns, "pass");

        List<String> attributes = new ArrayList<>();
        String sensor = "";
        String hour = "";
        String minute = "";
        List<MyImages> images = new ArrayList<>();


        while (parser.next() != XmlPullParser.START_TAG){
            if (parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            if(name.equals("pass")) {
                attributes = readAttribute(parser);
            }else if (name.equals("images")) {
                images.add(readImage(parser));
            }else {
                skip(parser);
            }
        }

        sensor = attributes.get(0);
        hour = attributes.get(1);
        minute = attributes.get(2);

        return new Pass(sensor, hour, minute, images);
    }

    private static MyImages readImage(XmlPullParser parser) throws XmlPullParserException, IOException{
        //parser.require(XmlPullParser.START_TAG, ns, "images");

        List<String> attributes = new ArrayList<>();
        String contents = "";

        while (parser.next() != XmlPullParser.START_TAG){
            if (parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            if (name.equals("images")) {
                attributes = readAttribute(parser);
                contents = readText(parser);
            }else {
                skip(parser);
            }
        }
        return new MyImages(attributes, contents);
    }

}
