package org.me.gcu.mcquillan_andrew_s2032756;
// Andrew Mcquillan
// s2032756
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Incidents {
    private String title;
    private String description;
    private String dates;
    private String link;
    private String pubDate;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    public String getPubDate() {
        return pubDate;
    }


    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }


    public String shortenDescription(String description) {
        String[] arrOfStr = description.split("<br />", 3);
         String output="";

        for (int i=0;i<arrOfStr.length;i++){
             output = output+ " " +arrOfStr[i]+ " ";
        }
        dates = arrOfStr[0]+""+arrOfStr[1];
        return output;
    }

    public int RoadworksLength(){
        String[] arrOfStr = dates.split(",", 3);
        String end = arrOfStr[2];
        int r = arrOfStr[1].indexOf("End Date");
        String initial = arrOfStr[1].substring(r);
        String initialDate = arrOfStr[1].replace(initial,"");

        DateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        Date endDate = null;
        Date start = null;
        try {
            endDate = format.parse(end);
            start = format.parse(initialDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

            int diff = (int) (endDate.getTime() - start.getTime());
            int diffDays = diff / (24 * 60 * 60 * 1000);

        return diffDays;
    }
}
