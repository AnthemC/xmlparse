package optics.marine.usf.edu.xmltest;

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

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return processReceivedData(parser);
        } finally {
            in.close();
        }
    }


    /*Build a way to paste together the URL for the images based on the basic url, the <this_request> tag and the
      <image> tag.
     */

    private List processReceivedData(XmlPullParser xmlData) throws IOException, XmlPullParserException {
        List menuEntries = new ArrayList();
        StartCal startDate;
        EndCal endDate;

        xmlData.require(XmlPullParser.START_TAG, ns, "menu");
        while (xmlData.next() != XmlPullParser.END_TAG) {
            if (xmlData.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = xmlData.getName();
            // Starts by looking for the entry tag
            switch (name) {
                case "menu_top":
                    menuEntries.add(readEntry(xmlData));
                    break;
                case "calendar":
                    startDate = readCalendarStart(xmlData);
                    endDate = readCalendarEnd(xmlData);
                    break;
                case "images":

                    break;
                default:
                    skip(xmlData);
            }
        }
        return menuEntries;
    }

    // Processes title tags in the feed.
    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    // Processes link tags in the feed.
    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
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
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
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

    private MenuItem readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "menu");
        String title = null;
        List subItems = new ArrayList<>();
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "title":
                    title = readTitle(parser);
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

    private MenuRegion readRegion(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "menu_region");
        String title = null;
        List subItems = new ArrayList<>();
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "title":
                    title = readTitle(parser);
                    break;
                case "menu_region":
                    subItems.add(readROI(parser));
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return new MenuRegion(title, subItems);
    }

    private ROI readROI(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "roi");
        String title = null;
        String link = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "title":
                    title = readTitle(parser);
                    break;
                case "link":
                    link = readLink(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return new ROI(title, link);
    }

    private StartCal readCalendarStart(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "calendar_start");
        String day = null;
        String month = null;
        String year = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "day":
                    day = readTitle(parser);
                    break;
                case "month":
                    month = readLink(parser);
                    break;
                case "year":
                    year = readLink(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return new StartCal(day, month, year);
    }

    private EndCal readCalendarEnd(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "calendar_end");
        String day = null;
        String month = null;
        String year = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "day":
                    day = readTitle(parser);
                    break;
                case "month":
                    month = readLink(parser);
                    break;
                case "year":
                    year = readLink(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return new EndCal(day, month, year);
    }
}
