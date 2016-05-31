package se.miun.erst0704.bathingsites;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.*;

/**
 * Created by Erik on 31/5 031.
 */
public class DatabaseManager {
    private static DatabaseManager managerInstance = null;
    private SQLiteDatabase myDatabase;
    final String DATABASE_NAME = "Bathing Sites Database";

    private DatabaseManager(Context context) {
        context.deleteDatabase(DATABASE_NAME);
        myDatabase = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null);

        // used in debug; drops table
//        myDatabase.delete("BathingSites",null,null);

        // create table, if not present
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS BathingSites("
                + "Name VARCHAR,"
                + "Description VARCHAR,"
                + "Address VARCHAR,"
//                + "Coordinates VARCHAR,"
                + "Longitude VARCHAR,"
                + "Latitude VARCHAR,"
                + "Grade VARCHAR,"
                + "Temp VARCHAR,"
                + "Date VARCHAR"
//                + "UNIQUE(Coordinates)"
                + ");");
    }

    private boolean doesExist(BathingSite site) {

    Cursor cursor = null;
/*        try {
            String query = "SELECT COUNT(*) FROM BathingSites WHERE Longitude=? AND Latitude=?";
            cursor = myDatabase.rawQuery(query, new String[] {site.getLongitude(), site.getLatitude()});
            if(cursor.getCount() > 0)
                return true;

            return false;


        } finally {
            if (cursor != null)
                cursor.close();
        }
*/

 //       String query = "SELECT COUNT(*) FROM BathingSites WHERE Longitude=? AND Latitude=?";
 //       cursor = myDatabase.rawQuery(query, new String[] {site.getLongitude(), site.getLatitude()});
        String query = "SELECT COUNT(*) FROM BathingSites WHERE Longitude='"
                +site.getLongitude()
                +"' AND Latitude='"+site.getLatitude()+"'";
        cursor = myDatabase.rawQuery(query, null);
        boolean value = cursor.getCount() > 0;
        cursor.close();
        return value;

//        Cursor amount = myDatabase.rawQuery(query, null);

//        return (cursor.getCount() < 1) ? false : true;
    }

    public boolean addBathingSite(BathingSite site) {
        if(doesExist(site))
            return false;
        String name = site.getName(), description = site.getDescription(), address = site.getAddress(),
                longitude = site.getLongitude(), latitude = site.getLatitude(), grade = site.getGrade(),
                temp = site.getTemp(), date = site.getDate();

        myDatabase.execSQL("INSERT INTO BathingSites VALUES("
                + "'"+name+"',"
                + "'"+description+"',"
                + "'"+address+"',"
 //               + "'longitude'|'latitude',"
                + "'"+longitude+"',"
                + "'"+latitude+"',"
                + "'"+grade+"',"
                + "'"+temp+"',"
                + "'"+date+"',"
                + ");");
        return true;
    }

    public int getAmountOfSites() {
        Cursor resultSet = myDatabase.rawQuery("SELECT * FROM BathingSites", null);
        return resultSet.getCount();
    }

    public static DatabaseManager getInstance(Context context) {
        if(managerInstance == null)
            managerInstance = new DatabaseManager(context);
        return managerInstance;
    }
}
