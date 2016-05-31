package se.miun.erst0704.bathingsites;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Erik on 31/5 031.
 */
public class BathingSite {
    private Map<String, String> details = new HashMap<String, String>();

    public BathingSite() {}

    public void setName(String name)                { details.put("Name", name); }
    public void setDescription(String description)  { details.put("Description", description); }
    public void setAddress(String address)          { details.put("Address", address); }
    public void setLongitude(String longitude)      { details.put("Longitude", longitude); }
    public void setLatitude(String latitude)        { details.put("Latitude", latitude); }
    public void setTemp(String temp)                { details.put("Temp", temp); }
    public void setDate(String date)                { details.put("Date", date); }
    public void setGrade(String grade)              { details.put("Grade", grade); }

    public String getName()         { return details.get("Name"); }
    public String getDescription()  { return details.get("Description"); }
    public String getAddress()      { return details.get("Address"); }
    public String getLongitude()    { return details.get("Longitude"); }
    public String getLatitude()     { return details.get("Latitude"); }
    public String getTemp()         { return details.get("Temp"); }
    public String getDate()         { return details.get("Date"); }
    public String getGrade()        { return details.get("Grade"); }

    @Override
    public String toString() {
        String tmp = "BATHING SITE SAVED" + '\n'
                + "********************" + '\n'
                + "Name        :" + getName() + '\n'
                + "Description :" + getDescription() + '\n'
                + "Address     :" + getAddress() + '\n'
                + "Longitude   :" + getLongitude() + '\n'
                + "Latitude    :" + getLatitude() + '\n'
                + "Temp        :" + getTemp() + '\n'
                + "Date        :" + getDate() + '\n'
                + "Grade       :" + getGrade();

        return tmp;
    }
}
