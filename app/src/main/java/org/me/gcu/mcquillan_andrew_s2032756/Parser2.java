package org.me.gcu.mcquillan_andrew_s2032756;
// Andrew Mcquillan
// s2032756
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class Parser2 {
    public ArrayList<Incidents> XMLParse(String xml){
        ArrayList<Incidents> incidentsList = new ArrayList<>();
        try{
            boolean parsingComplete = false;
            Incidents incident = null;
            XmlPullParser xmlParser = XmlPullParserFactory.newInstance().newPullParser();
            xmlParser.setInput(new StringReader(xml));
            int eventType = xmlParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT && !parsingComplete) {
                String name = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        name = xmlParser.getName();
                        if (name.equalsIgnoreCase("Item")) {
                            incident = new Incidents();
                        } else if (incident != null) {
                            if (name.equalsIgnoreCase("Title")) {
                                incident.setTitle(xmlParser.nextText());
                            } else if (name.equalsIgnoreCase("Description")) {
                                incident.setDescription(xmlParser.nextText());
                            }
                            else if (name.equalsIgnoreCase("link")) {
                                incident.setLink(xmlParser.nextText());
                            }
                            else if (name.equalsIgnoreCase("pubDate")) {
                                incident.setPubDate(xmlParser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = xmlParser.getName();
                        if (name.equalsIgnoreCase("Item") && incident != null) {
                            incidentsList.add(incident);
                        } else if (name.equalsIgnoreCase("Channel")) {
                            parsingComplete = true;
                        }
                        break;
                }
                eventType = xmlParser.next();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return incidentsList;
    }
}
