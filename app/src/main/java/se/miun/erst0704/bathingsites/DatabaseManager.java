package se.miun.erst0704.bathingsites;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.*;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Erik on 31/5 031.
 */
public class DatabaseManager {
    private static DatabaseManager managerInstance = null;
    private SQLiteHelper dbHelper;

    private DatabaseManager(Context context) {
        context.deleteDatabase(DB_DETAILS.DB_NAME);

        // used in debug; drops table
//        myDatabase.delete("BathingSites",null,null);

        dbHelper = new SQLiteHelper(context);
    }

    private SQLiteDatabase openWritableDB() { return dbHelper.getWritableDatabase(); }
    private SQLiteDatabase openReadableDB() { return dbHelper.getReadableDatabase(); }
    private void closeDB() { dbHelper.close(); }

    private boolean doesExist(BathingSite site) {
        Cursor cursor = null;
        try {
            SQLiteDatabase myDatabase = openReadableDB();

            String query =
                    DB_DETAILS.COL_LONGITUDE + " = '" + site.getLongitude() + "'"
                            + " AND " + DB_DETAILS.COL_LATITUDE + " = '" + site.getLatitude() + "'";

            cursor = myDatabase.query(DB_DETAILS.TABLE_NAME, DB_DETAILS.ALL_COLUMNS, query,
                    null, null, null, null);

            return (cursor.getCount() > 0) ? true : false;

        } finally {
            if(cursor != null)
                cursor.close();
            closeDB();
        }

    }

    public boolean addBathingSite(BathingSite site) {
        if(doesExist(site))
            return false;

        ContentValues values = new ContentValues();
        values.put(DB_DETAILS.COL_NAME, site.getName());
        values.put(DB_DETAILS.COL_DESCRIPTION, site.getDescription());
        values.put(DB_DETAILS.COL_ADDRESS, site.getAddress());
        values.put(DB_DETAILS.COL_LONGITUDE, site.getLongitude());
        values.put(DB_DETAILS.COL_LATITUDE, site.getLatitude());
        values.put(DB_DETAILS.COL_GRADE, site.getGrade());
        values.put(DB_DETAILS.COL_WATER_TEMP, site.getTemp());
        values.put(DB_DETAILS.COL_TEMP_DATE, site.getDate());

        SQLiteDatabase myDatabase = openWritableDB();
        myDatabase.insert(DB_DETAILS.TABLE_NAME, null, values);
        closeDB();

        return true;
    }

    public int getAmountOfSites() {
        SQLiteDatabase myDatabase = openReadableDB();
        Cursor resultSet = myDatabase.rawQuery("SELECT * FROM " + DB_DETAILS.TABLE_NAME, null);
        int value = resultSet.getCount();
        resultSet.close();
        closeDB();
        return value;
    }

    public static DatabaseManager getInstance(Context context) {
        if(managerInstance == null)
            managerInstance = new DatabaseManager(context);
        return managerInstance;
    }
}

class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context) {
        super(context, DB_DETAILS.DB_NAME, null, DB_DETAILS.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // create table, if not present
        database.execSQL(DB_DETAILS.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL(DB_DETAILS.UPGRADE_TABLE);
        onCreate(database);
    }
}

// Data structure with detail values for database
class DB_DETAILS {
    public static final int DB_VERSION = 2;
    public static final String
            DB_NAME = "BathingSites.db",
            TABLE_NAME = "BathingSites",
            COL_ID = "_id",
            COL_NAME = "Name",
            COL_DESCRIPTION = "Description",
            COL_ADDRESS = "Address",
            COL_LONGITUDE = "Longitude",
            COL_LATITUDE = "Latitude",
            COL_GRADE = "Grade",
            COL_WATER_TEMP = "Water_Temp",
            COL_TEMP_DATE = "Date";

    public static final String[] ALL_COLUMNS = { COL_ID, COL_NAME, COL_DESCRIPTION, COL_ADDRESS,
            COL_LONGITUDE, COL_LATITUDE, COL_GRADE, COL_WATER_TEMP, COL_TEMP_DATE };

    // Queries
    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + "("
            + COL_ID + " INTEGER primary key autoincrement,"
            + COL_NAME + " TEXT,"
            + COL_DESCRIPTION + " TEXT,"
            + COL_ADDRESS + " TEXT,"
            + COL_LONGITUDE + " TEXT,"
            + COL_LATITUDE + " TEXT,"
            + COL_GRADE + " TEXT,"
            + COL_WATER_TEMP + " TEXT,"
            + COL_TEMP_DATE + " TEXT"
            +");";

    public static final String UPGRADE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
