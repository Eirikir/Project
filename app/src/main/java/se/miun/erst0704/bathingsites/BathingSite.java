package se.miun.erst0704.bathingsites;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Erik on 31/5 031.
 */
public class BathingSite {
    private Map<String, String> details = new HashMap<String, String>();

    public BathingSite() {}
    public BathingSite(String name, String address, String longitude, String latitude) {
        setName(name);
        setAddress(address);
        setLongitude(longitude);
        setLatitude(latitude);

    }

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

        StringBuilder tmp = new StringBuilder();
        tmp.append("Name: " + getName());
        if(!getDescription().isEmpty())
            tmp.append("\nDescription: " + getDescription());
        if(!getAddress().isEmpty())
            tmp.append("\nAddress: " + getAddress());
        if(!getLongitude().isEmpty())
            tmp.append("\nLongitude: " + getLongitude());
        if(!getLatitude().isEmpty())
            tmp.append("\nLatitude: " + getLatitude());
        if(!getGrade().equals("0.0"))
            tmp.append("\nGrade: " + getGrade());
        if(!getTemp().isEmpty())
            tmp.append("\nWater Temp: " + getTemp());
        if(!getDate().isEmpty())
            tmp.append("\nTemp Date: " + getDate());

        return tmp.toString();
    }
}
