package optics.marine.usf.edu.xmltest;

// from video https://www.youtube.com/watch?v=kJY4SJ3IgGk


import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static optics.marine.usf.edu.xmltest.TestXmlParser.parse;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "xmltest";

    private static final String APP_ID = "";
    private static final String SERVER_URL = "http://optics.marine.usf.edu/";
    private static final String QUERY_FILE = "cgi-bin/md";
    private static final String QUERY_OPTIONS = "";
    private static final String QUERY_URL = SERVER_URL + QUERY_FILE + QUERY_OPTIONS;

    TextView display;
    private Resources resources;
    private String output;

    //private TextView menuTitle = null;
    //private TextView



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = (TextView)findViewById(R.id.info);


        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = getApplicationContext().getAssets().open("opticsmenu");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            parseXML(parser);

        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        /*LinearLayout lView = new LinearLayout(this);

        menuTitle = new TextView(this);
        menuTitle.setText("put text here");

        lView.addView(menuTitle);
        setContentView(lView);*/

        //QueryServer();
    }

    private void parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {
        Log.i("inMethod", "in parseXML");
        ProcessedData products = null;
        int eventType = parser.getEventType();
        ProcessedData currentProduct = null;

        products = parse(parser);

        Log.i("end", "ending of parseXML");
        printProducts(products);
    }

    private void printProducts(ProcessedData products)
    {
        Log.i("start printProducts", "in printProducts");
        String content = "";
        //Iterator<ProcessedData> it = products.iterator();
        //while(it.hasNext())
        //{
            //ProcessedData currProduct  = it.next();
            content = content + "nnnProduct: " +  currProduct.name + "\n";
            content = content + "Quantity: " +  currProduct.quantity + "\n";
            content = content + "Color: " +  currProduct.color + "\n";

        //}


        Log.i("content", content);

        Log.i("end", "end of printProducts");
        display.setText(content);
    }





    /*public void QueryServer(){
        Log.i(TAG, "Query server...");
        AsyncDownloader downloader = new AsyncDownloader();
        downloader.execute();
    }



    //inner class for doing background download
    private class AsyncDownloader extends AsyncTask<Object, String, ProcessedData>{

        @Override
        protected ProcessedData doInBackground(Object... objects) {
            XmlPullParser receivedData = tryDownloadingXmlData();
            ProcessedData recordsFound = tryParsingXmlData(receivedData);
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

        private ProcessedData tryParsingXmlData(XmlPullParser receivedData) {
            if (receivedData != null){
                try {
                    return processReceivedData(receivedData);
                }catch (XmlPullParserException e){
                    Log.e(TAG, "Pull parser failure", e);
                }catch (IOException e){
                    Log.e(TAG, "IO Exception parsing XML", e);
                }
            }
            return null;
        }

        private ProcessedData processReceivedData(XmlPullParser xmlData) throws IOException, XmlPullParserException {

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
