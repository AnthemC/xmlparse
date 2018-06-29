package optics.marine.usf.edu.xmltest;

// from video https://www.youtube.com/watch?v=kJY4SJ3IgGk


import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "xmltest";

    private static final String APP_ID = "";
    private static final String SERVER_URL = "http://optics.marine.usf.edu/";
    private static final String QUERY_FILE = "cgi-bin/md";
    private static final String QUERY_OPTIONS = "";
    private static final String QUERY_URL = SERVER_URL + QUERY_FILE + QUERY_OPTIONS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //QueryServer();
    }



    /*public void QueryServer(){
        Log.i(TAG, "Query server...");
        AsyncDownloader downloader = new AsyncDownloader();
        downloader.execute();
    }



    //inner class for doing background download
    private class AsyncDownloader extends AsyncTask<Object, String, Integer>{

        @Override
        protected Integer doInBackground(Object... objects) {
            XmlPullParser receivedData = tryDownloadingXmlData();
            int recordsFound = tryParsingXmlData(receivedData);
            return recordsFound;
        }

        private XmlPullParser tryDownloadingXmlData() {
            try {
                Log.i(TAG, "Now downloading...");
                        URL xmlUrl = new URL(QUERY_URL);
                XmlPullParser receivedData = XmlPullParserFactory.newInstance().newPullParser();
                receivedData.setInput(xmlUrl.openStream(), null);
                return receivedData;
            }catch (XmlPullParserException e){
                Log.e(TAG, "XmlPullParserException", e);
            }catch (IOException e){
                Log.e(TAG, "IOException", e);
            }
            return null;
        }

        private int tryParsingXmlData(XmlPullParser receivedData) {
            if (receivedData != null){
                try {
                    return processReceivedData(receivedData);
                }catch (XmlPullParserException e){
                    Log.e(TAG, "Pull parser failure", e);
                }catch (IOException e){
                    Log.e(TAG, "IO Exception parsing XML", e);
                }
            }
            return 0;
        }

        private List processReceivedData(XmlPullParser xmlData) throws IOException, XmlPullParserException {
            List entries = new ArrayList();

            xmlData.require(XmlPullParser.START_TAG, ns, "menu");
            while (xmlData.next() != XmlPullParser.END_TAG) {
                if (xmlData.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = xmlData.getName();
                // Starts by looking for the entry tag
                if (name.equals("entry")) {
                    entries.add(readEntry(xmlData));
                } else {
                    skip(xmlData);
                }
            }
            return entries;





            /*int eventType = -1;
            int recordsFound = 0;

            String appId = "";
            String itemId = "";
            String timeStamp = "";
            String data = "";

            while (eventType != XmlResourceParser.END_DOCUMENT) {
                String tagName = xmlData.getName();

                switch (eventType) {
                    case XmlResourceParser.START_TAG:
                        if (tagName.equals("record")) {
                            appId = xmlData.getAttributeValue(null, "appid");
                            itemId = xmlData.getAttributeValue(null, "itemid");
                            timeStamp = xmlData.getAttributeValue(null, "timestamp");
                            data = "";
                        }
                        break;


                    case XmlResourceParser.TEXT:
                        data += xmlData.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName.equals("record")) {
                            recordsFound++;
                            publishProgress(appId, itemId, data, timeStamp);
                        }
                        break;
                }
                eventType = xmlData.next();
            }
            if (recordsFound == 0){
                publishProgress();
            }
            Log.i(TAG,"finished processing " + recordsFound + " records");
            return recordsFound;
        }
        @Override
        protected void onProgressUpdate(String... values){
            if(values.length == 0){
                Log.i(TAG, "no data downloaded");
            }
            if (values.length == 4){
                String appId = values[0];
                String itemId = values[1];
                String data = values[2];
                String timeStamp = values[3];

                Log.i(TAG,"AppID: " + appId + ", Timestamp " + timeStamp);
                Log.i(TAG, "    ItemID: " + itemId + ", Data: " + data);
            }
            super.onProgressUpdate(values);

        }

    }*/

}
