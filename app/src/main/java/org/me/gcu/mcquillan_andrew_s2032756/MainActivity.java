package org.me.gcu.mcquillan_andrew_s2032756;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
// Andrew Mcquillan
// s2032756


public class MainActivity extends AppCompatActivity implements OnClickListener
{
    private TextView error1,error2,error3,error4;
    private Button startButton, incidentsButton, roadButton, dateSearchButton,roadNameSearchButton,dateSearchButtonIncidents,roadNameSearchButtonIncidents,dateSearchButtonPlanned,roadNameSearchButtonPlanned,journeyButton;
    private String result = "";
   private TableLayout table_road,table_incidents, table_planned,table_journey,table_journey2;
   private  TableRow Row;
   private EditText dateSearchRoad,roadSearch,datesearchincident,roadsearchincident,datesearchplanned,roadsearchplanned,journeySearch;
    public ArrayList<Incidents> roadworks = new ArrayList<>();
    public ArrayList<Incidents> currentIncidents = new ArrayList<>();
    public ArrayList<Incidents> plannedWorks = new ArrayList<>();
    // Traffic Scotland Planned Roadworks XML link
    private String plannedRoadworks="https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private  String Roadworks = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private String Incidents = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag","in onCreate");
        // Set up the to the graphical components
        error1 = (TextView)findViewById(R.id.error1);
        error2 = (TextView)findViewById(R.id.error2);
        error3 = (TextView)findViewById(R.id.error3);
        error4 = (TextView)findViewById(R.id.error4);
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
        incidentsButton = (Button)findViewById(R.id.IncidentsButton);
        incidentsButton.setOnClickListener(this);
        roadButton = (Button)findViewById(R.id.roadworksButton);
        roadButton.setOnClickListener(this);
        dateSearchRoad = (EditText) findViewById(R.id.dateSearch);
        dateSearchButton = (Button)findViewById(R.id.dateSearchButton);
        dateSearchButton.setOnClickListener(this);
        roadSearch = (EditText) findViewById(R.id.roadSearch);
        roadNameSearchButton = (Button)findViewById(R.id.roadNameSearchButton);
        roadNameSearchButton.setOnClickListener(this);
        datesearchincident = (EditText) findViewById(R.id.dateSearchincident);
        dateSearchButtonIncidents = (Button)findViewById(R.id.dateSearchButtonIncident);
        dateSearchButtonIncidents.setOnClickListener(this);
        roadsearchincident = (EditText) findViewById(R.id.roadSearchincident);
        roadNameSearchButtonIncidents = (Button)findViewById(R.id.roadNameSearchButtonIncident);
        roadNameSearchButtonIncidents.setOnClickListener(this);
        datesearchplanned = (EditText) findViewById(R.id.dateSearchplanned);
        dateSearchButtonPlanned = (Button)findViewById(R.id.dateSearchButtonPlanned);
        dateSearchButtonPlanned.setOnClickListener(this);
        roadsearchplanned = (EditText) findViewById(R.id.roadSearchplanned);
        roadNameSearchButtonPlanned = (Button)findViewById(R.id.roadNameSearchButtonPlanned);
        roadNameSearchButtonPlanned.setOnClickListener(this);
        table_road = (TableLayout)findViewById(R.id.RoadworksTable);
        table_incidents = (TableLayout)findViewById(R.id.IncidentsTable);
        table_planned = (TableLayout)findViewById(R.id.PlannedRoadworksTable);
        table_journey = (TableLayout)findViewById(R.id.journeyTable);
        table_journey2 = (TableLayout)findViewById(R.id.journeyTable2);
        journeyButton = (Button)findViewById(R.id.journeyButton);
        journeyButton.setOnClickListener(this);
        journeySearch = (EditText)findViewById(R.id.journeySearch);
    }

    public void startProgress(int url)
    {

        switch (url){
            case 1:
                // Run network access on a separate thread;
                new Thread(new Task(Roadworks,1)).start();
                break;
            case 2:
                // Run network access on a separate thread;
                new Thread(new Task(Incidents,2)).start();
                break;
            case 3:
                // Run network access on a separate thread;
                new Thread(new Task(plannedRoadworks,3)).start();
                break;
        }

    } //

    @Override
    public void onClick(View aview)
    {
        if (aview == startButton){

            startProgress(1);
        }
        else if(aview == incidentsButton){
            startProgress(2);
        }
        else if(aview == roadButton){
            startProgress(3);
        }
       if(aview == dateSearchButton){
           String query = dateSearchRoad.getText().toString();

           if (query.isEmpty() ==true) {
               error1.setText("error please enter text in the field");
           } else {
               DateSearchRoad(query);
           }
       }
        if(aview == roadNameSearchButton){
            String query = roadSearch.getText().toString();

            if (query.isEmpty() ==true) {
                error1.setText("error please enter text in the field");
            } else {
                RoadNameSearch(query);
            }
        }
        if(aview == dateSearchButtonIncidents){
            String query = datesearchincident.getText().toString();

            if (query.isEmpty() ==true) {
                error2.setText("error please enter text in the field");
            } else {
                DateSearchIncident(query);
            }
        }
        if(aview == roadNameSearchButtonIncidents){
            String query = roadsearchincident.getText().toString();

            if (query.isEmpty() ==true) {
                error2.setText("error please enter text in the field");
            } else {
                RoadNameSearchIncident(query);
            }
        }
        if(aview == dateSearchButtonPlanned){
            String query = datesearchplanned.getText().toString();

            if (query.isEmpty() ==true) {
                error3.setText("error please enter text in the field");
            } else {
                DateSearchPlanned(query);
            }
        }
        if(aview == roadNameSearchButtonPlanned){
            String query = roadsearchplanned.getText().toString();

            if (query.isEmpty() ==true) {
                error3.setText("error please enter text in the field");
            } else {
                RoadNameSearchPlanned(query);
            }
        }
        if(aview == journeyButton){
            String query = journeySearch.getText().toString();

            if (query.isEmpty() ==true) {
                error4.setText("error please enter text in the field");
            } else {
                JourneySearch(query);
                JourneySearch2(query);
            }
        }
    }
    public void DateSearchRoad(String query){
        table_road.removeAllViews();
        for(Incidents i : roadworks){
            boolean date = i.shortenDescription(i.getDescription()).contains(query);
             if (date==true){

                TextView t = new TextView(getApplicationContext());
                String output = i.getTitle()+" "+ i.shortenDescription(i.getDescription());
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                 t.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                t.setTextSize(20);
                 int length = i.RoadworksLength();
                 if (length > 30 && length < 60) {
                     t.setBackgroundColor(Color.parseColor("#e6d53c"));
                 } else if (length < 30) {
                     t.setBackgroundColor(Color.parseColor("#4bde53"));
                 } else {
                     t.setBackgroundColor(Color.parseColor("#fa3f3c"));
                 }
                table_road.addView(t);
            }
            else if (roadworks.indexOf(i) == roadworks.size()-1)
            {

                TextView t = new TextView(getApplicationContext());
                String output = "no current roadworks match the search";
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                t.setTextSize(25);
                t.setTextColor(Color.parseColor("#fa3f3c"));
                table_road.addView(t);

            }
        }
    }
    public void RoadNameSearch(String query){
        table_road.removeAllViews();
        for(Incidents i : roadworks){
            boolean title = i.getTitle().contains(query.toUpperCase());
            if (title==true){

                TextView t = new TextView(getApplicationContext());
                String output = i.getTitle()+" "+ i.shortenDescription(i.getDescription());
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                t.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                t.setTextSize(20);
                table_road.addView(t);
            }
            else if (roadworks.indexOf(i) == roadworks.size()-1)
            {

                TextView t = new TextView(getApplicationContext());
                String output = "no current roadworks match the search";
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                t.setTextSize(25);
                t.setTextColor(Color.parseColor("#fa3f3c"));
                table_road.addView(t);

            }
        }
    }
    public void DateSearchIncident(String query){
        table_incidents.removeAllViews();
        for(Incidents i : currentIncidents){
            boolean date = i.getPubDate().contains(query);
            Log.d("UI thread", String.valueOf(date));
            if (date==true){

                TextView t = new TextView(getApplicationContext());
                String output = i.getTitle()+" "+ i.getDescription();
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                t.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                t.setTextSize(20);
                table_incidents.addView(t);
            }
            else if (currentIncidents.indexOf(i) == currentIncidents.size()-1)
            {

                TextView t = new TextView(getApplicationContext());
                String output = "no current Incidents match the search";
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                t.setTextSize(25);
                t.setTextColor(Color.parseColor("#fa3f3c"));
                table_incidents.addView(t);
               // startProgress(2);

            }
        }
    }
    public void RoadNameSearchIncident(String query){
        table_incidents.removeAllViews();
        for(Incidents i : currentIncidents){
            boolean title = i.getTitle().contains(query.toUpperCase());
            if (title==true){

                TextView t = new TextView(getApplicationContext());
                String output = i.getTitle()+" "+ i.getDescription();
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                t.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                t.setTextSize(20);
                table_incidents.addView(t);
            }
            else if (currentIncidents.indexOf(i) == currentIncidents.size()-1)
            {

                TextView t = new TextView(getApplicationContext());
                String output = "no current Incidents match the search";
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                t.setTextSize(25);
                t.setTextColor(Color.parseColor("#fa3f3c"));
                table_incidents.addView(t);

            }
        }
    }
    public void DateSearchPlanned(String query){
        table_planned.removeAllViews();
        for(Incidents i : plannedWorks){
            boolean date = i.shortenDescription(i.getDescription()).contains(query);
            if (date==true){

                TextView t = new TextView(getApplicationContext());
                String output = i.getTitle()+" "+ i.shortenDescription(i.getDescription());
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                t.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                t.setTextSize(20);
                int length = i.RoadworksLength();
                if (length > 30 && length < 60) {
                    t.setBackgroundColor(Color.parseColor("#e6d53c"));
                } else if (length < 30) {
                    t.setBackgroundColor(Color.parseColor("#4bde53"));
                } else {
                    t.setBackgroundColor(Color.parseColor("#fa3f3c"));
                }
                table_planned.addView(t);
            }
            else if (plannedWorks.indexOf(i) == plannedWorks.size()-1)
            {

                TextView t = new TextView(getApplicationContext());
                String output = "no Planned roadworks match the search";
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                t.setTextSize(25);
                t.setTextColor(Color.parseColor("#fa3f3c"));
                table_planned.addView(t);

            }
        }
    }
    public void RoadNameSearchPlanned(String query){
        table_planned.removeAllViews();
        for(Incidents i : plannedWorks){
            boolean title = i.getTitle().contains(query.toUpperCase());
            if (title==true){

                TextView t = new TextView(getApplicationContext());
                String output = i.getTitle()+" "+ i.shortenDescription(i.getDescription());
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                t.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                t.setTextSize(20);
                table_planned.addView(t);
            }
            else if (plannedWorks.indexOf(i) == plannedWorks.size()-1)
            {

                TextView t = new TextView(getApplicationContext());
                String output = "no Planned roadworks match the search";
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                t.setTextSize(25);
                t.setTextColor(Color.parseColor("#fa3f3c"));
                table_planned.addView(t);

            }
        }
    }
    public void JourneySearch(String query){
        table_journey.removeAllViews();
        for(Incidents i : roadworks){
            boolean date = i.shortenDescription(i.getDescription()).contains(query);
            if (date==true){

                TextView t = new TextView(getApplicationContext());
                String output = i.getTitle()+" "+ i.shortenDescription(i.getDescription());
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                t.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                t.setTextSize(20);
                table_journey.addView(t);
            }
            else if (roadworks.indexOf(i) == roadworks.size()-1)
            {

                TextView t = new TextView(getApplicationContext());
                String output = "no roadworks match the search";
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                t.setTextSize(25);
                t.setTextColor(Color.parseColor("#fa3f3c"));
                table_journey.addView(t);

            }
        }
    }
    public void JourneySearch2(String query){
        table_journey2.removeAllViews();
        for(Incidents i : plannedWorks){
            boolean date = i.shortenDescription(i.getDescription()).contains(query);
            if (date==true){

                TextView t = new TextView(getApplicationContext());
                String output = i.getTitle()+" "+ i.shortenDescription(i.getDescription());
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                t.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                t.setTextSize(20);
                table_journey2.addView(t);
            }
            else if (plannedWorks.indexOf(i) == plannedWorks.size()-1)
            {

                TextView t = new TextView(getApplicationContext());
                String output = "no roadworks match the search";
                t.setText(output);
                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                t.setTextSize(25);
                t.setTextColor(Color.parseColor("#fa3f3c"));
                table_journey2.addView(t);

            }
        }
    }
    private class Task implements Runnable
    {
        private String url;
        private int c;

        public Task(String aurl,int urlChoice )
        {
            url = aurl;
            c = urlChoice;
        }
        @Override
        public void run()
        {
            final int choice = c;
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";
            result ="";

            Log.e("MyTag","in run");

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

                while ((inputLine = in.readLine()) != null)
                {

                    result = result + inputLine;
                    Log.e("MyTag",inputLine);

                }
                in.close();
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception");
            }


            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    Parser2 p = new Parser2();
                    switch (choice){
                        case 1:


                            roadworks.clear();

                            roadworks = p.XMLParse(result);
                            if(roadworks.size()==0){
                                TextView t = new TextView(getApplicationContext());
                                String output = "No roadworks taking place currently";
                                t.setText(output);
                                t.setTextSize(20);
                                t.setTypeface(Typeface.create("times-new-roman", Typeface.NORMAL));
                                table_road.addView(t);
                            }
                            else {

                                table_road.removeAllViews();
                                TextView j = new TextView(getApplicationContext());
                                int joutput = roadworks.size();
                                String jtext = "There are currently ";
                                String jtext2 = " roadworks taking place";
                                j.setText(jtext+joutput+jtext2);
                                j.setTextSize(25);
                                j.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                                table_road.addView(j,0);
                                int id =0;
                                for (int i = 0; i < roadworks.size(); i++) {

                                  TableRow tr = new TableRow(getApplicationContext());
                                    tr.setClickable(true);
                                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT,0.2f));
                                    tr.setId(id++);
                                   TextView t = new TextView(getApplicationContext());
                                    String output = roadworks.get(i).getTitle()  + roadworks.get(i).shortenDescription(roadworks.get(i).getDescription());
                                    t.setText(output);
                                    t.setTextSize(20);
                                    t.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                                    t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                                    tr.addView(t);
                                    table_road.addView(tr);
                                    tr.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            int index = tr.getId();
                                           int i = table_road.indexOfChild(tr);

                                            TableRow row = (TableRow)table_road.getChildAt(i);
                                            Log.e("mytag", String.valueOf(row));
                                            table_road.removeView(row);
                                            String upoutput = roadworks.get(index).getTitle()+ " - " + roadworks.get(index).shortenDescription(roadworks.get(index).getDescription())+ "link for more info" + roadworks.get(index).getLink()+ "Date Published"+roadworks.get(index).getPubDate();

                                            t.setText(upoutput);

                                            table_road.addView(row,i);



                                        }
                                    });
                                }
                            }

                            break;
                        case 2:


                            currentIncidents.clear();
                            currentIncidents = p.XMLParse(result);
                            if(currentIncidents.size()==0){
                                TextView t = new TextView(getApplicationContext());
                                String output = "NO CURRENT INCIDENTS";
                                t.setText(output);
                                t.setTextSize(20);
                                t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                                table_incidents.addView(t);
                            }
                            else {

                                table_incidents.removeAllViews();
                                TextView j = new TextView(getApplicationContext());
                                int joutput = currentIncidents.size();
                                String jtext = "There are currently ";
                                String jtext2 = " Incidents Happening Currently";
                                j.setText(jtext+joutput+jtext2);
                                j.setTextSize(25);
                                j.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                                table_incidents.addView(j,0);
                                int id =0;
                                for (int i = 0; i < currentIncidents.size(); i++) {

                                    TableRow tr = new TableRow(getApplicationContext());
                                    tr.setClickable(true);
                                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT,0.2f));
                                    tr.setId(id++);
                                    TextView t = new TextView(getApplicationContext());
                                    String output = currentIncidents.get(i).getTitle()  + currentIncidents.get(i).getDescription();
                                    t.setText(output);
                                    t.setTextSize(20);
                                    t.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                                    t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                                    tr.addView(t);
                                    table_incidents.addView(tr);
                                    tr.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            int index = tr.getId();
                                            int i = table_incidents.indexOfChild(tr);

                                            TableRow row = (TableRow)table_incidents.getChildAt(i);
                                            Log.e("mytag", String.valueOf(row));
                                            table_incidents.removeView(row);
                                            String output = currentIncidents.get(index).getTitle()  + currentIncidents.get(index).getDescription()+ "link for more info"+ currentIncidents.get(index).getLink()+ "Date Published" + currentIncidents.get(index).getPubDate();

                                            t.setText(output);

                                            table_incidents.addView(row,i);



                                        }
                                    });
                                }
                            }
                            break;
                        case 3:


                            plannedWorks.clear();

                            plannedWorks = p.XMLParse(result);
                            if(plannedWorks.size()==0){
                                TextView t = new TextView(getApplicationContext());
                                String output = "No roadworks taking place currently";
                                t.setText(output);
                                t.setTextSize(20);
                                t.setTypeface(Typeface.create("times-new-roman", Typeface.NORMAL));
                                table_planned.addView(t);
                            }
                            else {

                                table_planned.removeAllViews();
                                TextView j = new TextView(getApplicationContext());
                                int joutput = plannedWorks.size();
                                String jtext = "There are currently ";
                                String jtext2 = "  Planned roadworks taking place";
                                j.setText(jtext+joutput+jtext2);
                                j.setTextSize(25);
                                j.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                                table_planned.addView(j,0);
                                int id =0;
                                for (int i = 0; i < plannedWorks.size(); i++) {

                                    TableRow tr = new TableRow(getApplicationContext());
                                    tr.setClickable(true);
                                    tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT,0.2f));
                                    tr.setId(id++);
                                    TextView t = new TextView(getApplicationContext());
                                    String output = plannedWorks.get(i).getTitle()  + plannedWorks.get(i).shortenDescription(plannedWorks.get(i).getDescription());
                                    t.setText(output);
                                    t.setTextSize(20);
                                    t.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                                    t.setTypeface(Typeface.create("sans-serif-condensed-medium", Typeface.NORMAL));
                                    tr.addView(t);
                                    table_planned.addView(tr);
                                    tr.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            int index = tr.getId();
                                            int i = table_planned.indexOfChild(tr);

                                            TableRow row = (TableRow)table_planned.getChildAt(i);
                                            Log.e("mytag", String.valueOf(row));
                                            table_planned.removeView(row);
                                            String upoutput = plannedWorks.get(index).getTitle()+ " - " + plannedWorks.get(index).shortenDescription(plannedWorks.get(index).getDescription())+ "link for more info" + plannedWorks.get(index).getLink()+ "Date Published"+plannedWorks.get(index).getPubDate();

                                            t.setText(upoutput);

                                            table_planned.addView(row,i);



                                        }
                                    });
                                }
                            }
                            break;
                    }

                }
            });
        }

    }


}