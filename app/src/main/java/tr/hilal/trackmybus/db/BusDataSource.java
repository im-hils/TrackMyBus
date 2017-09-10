package tr.hilal.trackmybus.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import tr.hilal.trackmybus.model.Bus;
import tr.hilal.trackmybus.model.Schedule;
import tr.hilal.trackmybus.model.Stop;

/**
 * Created by TOSHIBA on 6/30/2015.
 */
public class BusDataSource {
    public static final String LOGTAG="HILSTAG";
    Context c;
    SQLiteOpenHelper dbhelper;
    SQLiteDatabase db;
    String sqlquery;
    public Boolean moveNext=false;
    public BusDataSource(Context context) {
        dbhelper = new BusDBopenHelper(context);
        c=context;
    }

    //OPENING DATABASE CONNECTION
    public void open() throws SQLException
    {
        Log.d(LOGTAG, "Checking sqliteDBInstance...");
        if(this.db == null)
        {
            Log.i(LOGTAG, "Opening Connection");
            this.db = dbhelper.getWritableDatabase();
        }
    }

    //CLOSING DATABASE CONNECTION
    public void close() {
        Log.i(LOGTAG, "Database closed");
        dbhelper.close();
    }

    //GET ALL STOPS FROM STOP TABLE
    public Stop[] getAllStops(String searchTerm) {
        // select query
        String sqlquery = "";
        sqlquery += "SELECT * FROM " + BusDBopenHelper.TABLE_STOP;
        sqlquery += " WHERE " + BusDBopenHelper.STOP_NAME + " LIKE '%" + searchTerm + "%'";
        sqlquery += " OR " + BusDBopenHelper.STOP_CODE + " LIKE '%" + searchTerm + "%'";
        Cursor cursor = db.rawQuery(sqlquery, null);
//        Log.d(LOGTAG, "GetAllStops Worked From BusDataSource");
//        Log.d(LOGTAG,sqlquery);
        int recCount = cursor.getCount();
        Stop[] ObjectItemData = new Stop[recCount];
        int x = 0;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String stopName = cursor.getString(cursor.getColumnIndex(BusDBopenHelper.STOP_NAME));
                Stop myObject = new Stop(stopName);
                ObjectItemData[x] = myObject;
                x++;
            }
            while (cursor.moveToNext());
        }
        return ObjectItemData;
    }
    //GETTING ALL BUSES
    public ArrayList<Schedule> getBetweenList(String source,String destination,String busClass) {
        ArrayList<Schedule> schedules = new ArrayList<Schedule>();
        switch (busClass){
            case "All":
                sqlquery = "";
                sqlquery +="SELECT "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.BUS_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME ;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.TIME;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
                sqlquery+=" FROM "+BusDBopenHelper.TABLE_SCHEDULE ;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_BUS +" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_STOP+" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.STOP_ID;
                sqlquery+=" WHERE "+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME +"='" ;
                sqlquery +=source;
                sqlquery+="' OR "+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE +"='" ;
                sqlquery +=source;
                sqlquery +="' ORDER BY "+BusDBopenHelper.TIME;
                Log.d("HILSTAG", sqlquery);
                break;
            case "Ordinary":
                sqlquery = "";
                sqlquery +="SELECT "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.BUS_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME ;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.TIME;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
                sqlquery+=" FROM "+BusDBopenHelper.TABLE_SCHEDULE ;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_BUS +" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_STOP+" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.STOP_ID;
                sqlquery+=" WHERE ("+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME +"='" ;
                sqlquery +=source;
                sqlquery+="' OR "+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE +"='" ;
                sqlquery +=source;
                sqlquery+="') AND "+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME +"='" ;
                sqlquery +=busClass;
                sqlquery +="' ORDER BY "+BusDBopenHelper.TIME;
                Log.d("HILSTAG", sqlquery);
                break;
            case "Fast Passenger":
                sqlquery = "";
                sqlquery +="SELECT "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.BUS_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME ;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.TIME;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
                sqlquery+=" FROM "+BusDBopenHelper.TABLE_SCHEDULE ;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_BUS +" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_STOP+" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.STOP_ID;
                sqlquery+=" WHERE ("+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME +"='" ;
                sqlquery +=source;
                sqlquery+="' OR "+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE +"='" ;
                sqlquery +=source;
                sqlquery+="') AND "+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME +" GLOB '*" ;
                sqlquery +=busClass;
                sqlquery +="*' ORDER BY "+BusDBopenHelper.TIME;
                Log.d("HILSTAG", sqlquery);
                break;
            case "Town To Town Ordinary":
                sqlquery = "";
                sqlquery +="SELECT "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.BUS_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME ;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.TIME;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
                sqlquery+=" FROM "+BusDBopenHelper.TABLE_SCHEDULE ;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_BUS +" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_STOP+" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.STOP_ID;
                sqlquery+=" WHERE ("+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME +"='" ;
                sqlquery +=source;
                sqlquery+="' OR "+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE +"='" ;
                sqlquery +=source;
                sqlquery+="') AND "+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME;
                sqlquery +="='TT Ordinary' ORDER BY "+BusDBopenHelper.TIME;
                Log.d("HILSTAG", sqlquery);
                break;
            case "Limited Stop Ordinary":
                sqlquery = "";
                sqlquery +="SELECT "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.BUS_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME ;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.TIME;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
                sqlquery+=" FROM "+BusDBopenHelper.TABLE_SCHEDULE ;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_BUS +" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_STOP+" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.STOP_ID;
                sqlquery+=" WHERE ("+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME +"='" ;
                sqlquery +=source;
                sqlquery+="' OR "+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE +"='" ;
                sqlquery +=source;
                sqlquery+="') AND ("+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME;
                sqlquery +="='LS Ordinary' OR "+ BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME;
                sqlquery +="='Fare Stage LS') ORDER BY "+BusDBopenHelper.TIME;
                Log.d("HILSTAG", sqlquery);
                break;
            case "Super Fast Passenger":
                sqlquery = "";
                sqlquery +="SELECT "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.BUS_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME ;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.TIME;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
                sqlquery+=" FROM "+BusDBopenHelper.TABLE_SCHEDULE ;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_BUS +" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_STOP+" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.STOP_ID;
                sqlquery+=" WHERE ("+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME +"='" ;
                sqlquery +=source;
                sqlquery+="' OR "+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE +"='" ;
                sqlquery +=source;
                sqlquery+="') AND "+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME;
                sqlquery +="='Super Fast' ORDER BY "+BusDBopenHelper.TIME;
                Log.d("HILSTAG", sqlquery);
                break;
            case "Super Express Air Bus":
                sqlquery = "";
                sqlquery +="SELECT "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.BUS_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME ;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.TIME;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
                sqlquery+=" FROM "+BusDBopenHelper.TABLE_SCHEDULE ;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_BUS +" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_STOP+" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.STOP_ID;
                sqlquery+=" WHERE ("+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME +"='" ;
                sqlquery +=source;
                sqlquery+="' OR "+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE +"='" ;
                sqlquery +=source;
                sqlquery+="') AND "+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME;
                sqlquery +="='Super Express' ORDER BY "+BusDBopenHelper.TIME;
                Log.d("HILSTAG", sqlquery);
                break;
            case "Super Deluxe Air Bus":
                sqlquery = "";
                sqlquery +="SELECT "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.BUS_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME ;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.TIME;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
                sqlquery+=" FROM "+BusDBopenHelper.TABLE_SCHEDULE ;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_BUS +" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_STOP+" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.STOP_ID;
                sqlquery+=" WHERE ("+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME +"='" ;
                sqlquery +=source;
                sqlquery+="' OR "+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE +"='" ;
                sqlquery +=source;
                sqlquery+="') AND "+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME;
                sqlquery +="='Super Deluxe' ORDER BY "+BusDBopenHelper.TIME;
                Log.d("HILSTAG", sqlquery);
                break;
            case "Low Floor AC Volvo":
                sqlquery = "";
                sqlquery +="SELECT "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.BUS_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME ;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.TIME;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
                sqlquery+=" FROM "+BusDBopenHelper.TABLE_SCHEDULE ;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_BUS +" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_STOP+" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.STOP_ID;
                sqlquery+=" WHERE ("+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME +"='" ;
                sqlquery +=source;
                sqlquery+="' OR "+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE +"='" ;
                sqlquery +=source;
                sqlquery+="') AND "+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME;
                sqlquery +="='AC Low Floor' ORDER BY "+BusDBopenHelper.TIME;
                Log.d("HILSTAG", sqlquery);
                break;
            case "Low Floor Non AC":
                sqlquery = "";
                sqlquery +="SELECT "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.BUS_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME ;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.TIME;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
                sqlquery+=" FROM "+BusDBopenHelper.TABLE_SCHEDULE ;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_BUS +" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_STOP+" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.STOP_ID;
                sqlquery+=" WHERE ("+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME +"='" ;
                sqlquery +=source;
                sqlquery+="' OR "+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE +"='" ;
                sqlquery +=source;
                sqlquery+="') AND "+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME;
                sqlquery +="='Non AC Low Floor' ORDER BY "+BusDBopenHelper.TIME;
                Log.d("HILSTAG", sqlquery);
                break;
            case "AC Multi Axile":
                sqlquery = "";
                sqlquery +="SELECT "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.BUS_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME ;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.TIME;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
                sqlquery+=" FROM "+BusDBopenHelper.TABLE_SCHEDULE ;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_BUS +" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_STOP+" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.STOP_ID;
                sqlquery+=" WHERE ("+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME +"='" ;
                sqlquery +=source;
                sqlquery+="' OR "+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE +"='" ;
                sqlquery +=source;
                sqlquery+="') AND "+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME +"='" ;
                sqlquery +=busClass;
                sqlquery +="' ORDER BY "+BusDBopenHelper.TIME;
                Log.d("HILSTAG", sqlquery);
                break;
            case "AC Air Bus":
                sqlquery = "";
                sqlquery +="SELECT "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.BUS_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME ;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.TIME;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
                sqlquery+=" FROM "+BusDBopenHelper.TABLE_SCHEDULE ;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_BUS +" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_STOP+" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.STOP_ID;
                sqlquery+=" WHERE ("+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME +"='" ;
                sqlquery +=source;
                sqlquery+="' OR "+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE +"='" ;
                sqlquery +=source;
                sqlquery+="') AND "+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME +"='" ;
                sqlquery +=busClass;
                sqlquery +="' ORDER BY "+BusDBopenHelper.TIME;
                Log.d("HILSTAG", sqlquery);
                break;
            case "Silver Line Jet":
                sqlquery = "";
                sqlquery +="SELECT "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.BUS_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME ;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.TIME;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
                sqlquery+=" FROM "+BusDBopenHelper.TABLE_SCHEDULE ;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_BUS +" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_STOP+" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.STOP_ID;
                sqlquery+=" WHERE ("+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME +"='" ;
                sqlquery +=source;
                sqlquery+="' OR "+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE +"='" ;
                sqlquery +=source;
                sqlquery+="') AND "+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME +"='" ;
                sqlquery +=busClass;
                sqlquery +="' ORDER BY "+BusDBopenHelper.TIME;
                Log.d("HILSTAG", sqlquery);
                break;
            case "Point to Point":
                sqlquery = "";
                sqlquery +="SELECT "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.BUS_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME ;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME;
                sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.TIME;
                sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
                sqlquery+=" FROM "+BusDBopenHelper.TABLE_SCHEDULE ;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_BUS +" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
                sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_STOP+" ON " ;
                sqlquery+=BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.KEY_ID+"=" ;
                sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.STOP_ID;
                sqlquery+=" WHERE ("+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME +"='" ;
                sqlquery +=source;
                sqlquery+="' OR "+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE +"='" ;
                sqlquery +=source;
                sqlquery+="') AND "+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME +"='" ;
                sqlquery +=busClass;
                sqlquery +="' ORDER BY "+BusDBopenHelper.TIME;
                Log.d("HILSTAG", sqlquery);
                break;

        }

        Cursor cursor =db.rawQuery(sqlquery, null);
        Log.d("HILSTAG", sqlquery);
        while (cursor.moveToNext()) {
            if(hasDestination((cursor.getInt(cursor.getColumnIndex(BusDBopenHelper.BUS_ID))), source,destination)) {
                String arrTime=findArrivalTime((cursor.getInt(cursor.getColumnIndex(BusDBopenHelper.BUS_ID))),
                        source,destination);
                Schedule schedule = new Schedule();
                schedule.setTime(cursor.getString(cursor.getColumnIndex(BusDBopenHelper.TIME)));
//                Log.d("HILSTAG", "Arrival Time :SET");
                Bus bus = new Bus();
                bus.setBusName(cursor.getString(cursor.getColumnIndex(BusDBopenHelper.BUS_NAME)));
//                Log.d("HILSTAG", "Bus Name :SET");
                bus.setClassName(cursor.getString(cursor.getColumnIndex(BusDBopenHelper.CLASS_NAME)));
                bus.setId(cursor.getInt(cursor.getColumnIndex(BusDBopenHelper.BUS_ID)));
//                Log.d("HILSTAG", "Class Name :SET");
                Stop stop = new Stop();
                stop.setStopName(cursor.getString(cursor.getColumnIndex(BusDBopenHelper.STOP_NAME)));
                schedule.setArrival(arrTime);
                schedule.setStop(stop);
                schedule.setBus(bus);
                schedules.add(schedule);
            }

        }
//        Log.d("HILSTAG","EVERYTHING FINE TILL DATASOURCE");
        return schedules;

    }

    private String findArrivalTime(int BusId, String source, String destination) {
        String sqlquery = "";
        sqlquery += "SELECT " + BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME;;
        sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.TIME;
        sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
        sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
        sqlquery+=" FROM "+BusDBopenHelper.TABLE_SCHEDULE ;
        sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_BUS +" ON " ;
        sqlquery+=BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID+"=" ;
        sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
        sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_STOP+" ON " ;
        sqlquery+=BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.KEY_ID+"=" ;
        sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.STOP_ID;
        sqlquery+=" WHERE "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID +"='" ;
        sqlquery +=BusId + "' ORDER BY "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;

        Cursor boolCursorr =db.rawQuery(sqlquery,null);
//        Log.d("HILSTAG", sqlquery+"After");
        while (boolCursorr.moveToNext()) {
            if ((boolCursorr.getString(boolCursorr.getColumnIndex(BusDBopenHelper.STOP_NAME))).contains(source)) {
                while (boolCursorr.moveToNext())
                {
                    if ((boolCursorr.getString(boolCursorr.getColumnIndex(BusDBopenHelper.STOP_NAME))).contains(destination)){
                        String arrTime=boolCursorr.getString(boolCursorr.getColumnIndex(BusDBopenHelper.TABLE_SCHEDULE + "." + BusDBopenHelper.TIME));
                        return arrTime;
                    }
                }


            }

        }
        return null;
    }

    private boolean hasDestination(int BusId, String source, String destination) {
//        Log.d("HILSTAG","hasNext Function Executed");
        String sqlquery = "";
        sqlquery += "SELECT " + BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME;
        sqlquery+=","+ BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE;
        sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.TIME;
        sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
        sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
        sqlquery+=" FROM "+BusDBopenHelper.TABLE_SCHEDULE ;
        sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_BUS +" ON " ;
        sqlquery+=BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID+"=" ;
        sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
        sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_STOP+" ON " ;
        sqlquery+=BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.KEY_ID+"=" ;
        sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.STOP_ID;
        sqlquery+=" WHERE "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID +"='" ;
        sqlquery +=BusId + "' ORDER BY "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
//        Log.d("HILSTAG", sqlquery+"before");
        Cursor boolCursorr =db.rawQuery(sqlquery,null);
//        Log.d("HILSTAG", sqlquery+"After");
        while (boolCursorr.moveToNext()) {
            if (((boolCursorr.getString(boolCursorr.getColumnIndex(BusDBopenHelper.STOP_NAME))).equals(source)) || ((boolCursorr.getString(boolCursorr.getColumnIndex(BusDBopenHelper.STOP_CODE))).equals(source))) {
                while (boolCursorr.moveToNext())
                {
                    if (((boolCursorr.getString(boolCursorr.getColumnIndex(BusDBopenHelper.STOP_NAME))).equals(destination)) || ((boolCursorr.getString(boolCursorr.getColumnIndex(BusDBopenHelper.STOP_CODE))).equals(destination))){
                        return true;
                    }
                }


            }

        }
        return false;
    }


    //GETTING DETAILS FOR PASSING BY
    public ArrayList<Schedule> getPassingList(String stopField){
        ArrayList<Schedule> schedules = new ArrayList<Schedule>();
        String sqlquery = "";
        sqlquery +="SELECT DISTINCT "+ BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID;
        sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.BUS_NAME;
        sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME ;
        sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME;
        sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.TIME;
        sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
        sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
        sqlquery+=" FROM "+BusDBopenHelper.TABLE_SCHEDULE ;
        sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_BUS +" ON " ;
        sqlquery+=BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID+"=" ;
        sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
        sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_STOP+" ON " ;
        sqlquery+=BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.KEY_ID+"=" ;
        sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.STOP_ID;
        sqlquery+=" WHERE "+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME +"='"+stopField ;
        sqlquery += "' OR "+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE +"='"+stopField ;
        sqlquery +="' ORDER BY "+BusDBopenHelper.TIME;
//        Log.d("HILSTAG", sqlquery);
        Cursor cursor =db.rawQuery(sqlquery, null);
//        Log.d("HILSTAG", sqlquery);
        while (cursor.moveToNext()) {
//            Log.d("HILSTAG","ENTERDED into List While Loop");
            String towards=findNextStop((cursor.getInt(cursor.getColumnIndex(BusDBopenHelper.BUS_ID))),stopField);
            if(moveNext!=false) {
                Schedule schedule = new Schedule();
                schedule.setTime(cursor.getString(cursor.getColumnIndex(BusDBopenHelper.TIME)));
//                Log.d("HILSTAG", "Arrival Time :SET");

                Bus bus = new Bus();
                bus.setBusName(cursor.getString(cursor.getColumnIndex(BusDBopenHelper.BUS_NAME)));
//                Log.d("HILSTAG", "Bus Name :SET");
                bus.setClassName(cursor.getString(cursor.getColumnIndex(BusDBopenHelper.CLASS_NAME)));
                bus.setId(cursor.getInt(cursor.getColumnIndex(BusDBopenHelper.BUS_ID)));
//                Log.d("HILSTAG", "Class Name :SET");
                Stop stop = new Stop();
                stop.setStopName(cursor.getString(cursor.getColumnIndex(BusDBopenHelper.STOP_NAME)));
                stop.setTowards(towards);
//                Log.d("HILSTAG", "STOP NAME:SET");
                schedule.setStop(stop);
                schedule.setBus(bus);
                schedules.add(schedule);
            }
        }

//        Log.d("HILSTAG","Returned From List Detail Functuion");
        return schedules;

    }

    private String findNextStop(int BusId, String stopField) {
        String sqlquery = "";
        sqlquery += "SELECT DISTINCT "+ BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID;
        sqlquery +=","+ BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME;
        sqlquery +=","+ BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_CODE;
        sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.TIME;
        sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
        sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
        sqlquery+=" FROM "+BusDBopenHelper.TABLE_SCHEDULE ;
        sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_BUS +" ON " ;
        sqlquery+=BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID+"=" ;
        sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
        sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_STOP+" ON " ;
        sqlquery+=BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.KEY_ID+"=" ;
        sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.STOP_ID;
        sqlquery+=" WHERE "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID +"='" ;
        sqlquery +=BusId + "' ORDER BY "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
        Cursor findNext=db.rawQuery(sqlquery,null);
//        Log.d("HILSTAG", "CHECKNG SQL EXECTED");
        while (findNext.moveToNext()){
            if (((findNext.getString(findNext.getColumnIndex(BusDBopenHelper.STOP_NAME))).contains(stopField)) || ((findNext.getString(findNext.getColumnIndex(BusDBopenHelper.STOP_CODE))).contains(stopField)))
                    {
                if (findNext.moveToNext()) {
                    String towards=findNext.getString(findNext.getColumnIndex(BusDBopenHelper.STOP_NAME));
                    moveNext=true;
                    return towards;

                }
                else{
                    moveNext=false;
                }

            }

        }
        return null;
    }

//    public boolean hasNext(int BusId, String stopField) {
////        Log.d("HILSTAG","hasNext Function Executed");
//        String sqlquery = "";
//        sqlquery += "SELECT DISTINCT " + BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
//        sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.TIME;
//        sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
//        sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME;
//        sqlquery+=" FROM "+BusDBopenHelper.TABLE_SCHEDULE ;
//        sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_BUS +" ON " ;
//        sqlquery+=BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID+"=" ;
//        sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
//        sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_STOP+" ON " ;
//        sqlquery+=BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.KEY_ID+"=" ;
//        sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.STOP_ID;
//        sqlquery+=" WHERE "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID +"='" ;
//        sqlquery +=BusId + "' ORDER BY "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
////        Log.d("HILSTAG", sqlquery+"before");
//        Cursor boolCursorr =db.rawQuery(sqlquery,null);
////        Log.d("HILSTAG", sqlquery+"After");
//        while (boolCursorr.moveToNext())
//        {
//            if ((boolCursorr.getString(boolCursorr.getColumnIndex(BusDBopenHelper.STOP_NAME))).contains(stopField)) {
//                if (boolCursorr.moveToNext()) {
//                    return true;
//                }
//            }
//
//        }
//        return false;
//    }
    public ArrayList<Schedule> getScheduleDetails(int busid) {
        ArrayList<Schedule> scheduleDetails = new ArrayList<Schedule>();
//        Log.d("HLSTAG","Getschele Detals Exected");
        String sqlquery = "";
        sqlquery += "SELECT " + BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.BUS_NAME;
        sqlquery+=","+BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.CLASS_NAME ;
        sqlquery+=","+BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.STOP_NAME;
        sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.TIME;
        sqlquery+=","+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
        sqlquery+=" FROM "+BusDBopenHelper.TABLE_SCHEDULE ;
        sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_BUS +" ON " ;
        sqlquery+=BusDBopenHelper.TABLE_BUS+"."+BusDBopenHelper.KEY_ID+"=" ;
        sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID;
        sqlquery+=" INNER JOIN "+BusDBopenHelper.TABLE_STOP+" ON " ;
        sqlquery+=BusDBopenHelper.TABLE_STOP+"."+BusDBopenHelper.KEY_ID+"=" ;
        sqlquery+=BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.STOP_ID;
        sqlquery+=" WHERE "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.BUS_ID +"='" ;
        sqlquery +=busid + "' ORDER BY "+BusDBopenHelper.TABLE_SCHEDULE+"."+BusDBopenHelper.KEY_ID;
        Cursor cursor =db.rawQuery(sqlquery, null);
//        Log.d("HILSTAG", sqlquery);


        while (cursor.moveToNext()) {
//            Log.d("HILSTAG","ENTERDED into List While Loop");

                Schedule schedule = new Schedule();
                schedule.setTime(cursor.getString(cursor.getColumnIndex(BusDBopenHelper.TIME)));
//                Log.d("HILSTAG", "Arrival Time :SET");
                Bus bus = new Bus();
                bus.setBusName(cursor.getString(cursor.getColumnIndex(BusDBopenHelper.BUS_NAME)));
//                Log.d("HILSTAG", "Bus Name :SET");
                bus.setClassName(cursor.getString(cursor.getColumnIndex(BusDBopenHelper.CLASS_NAME)));
//                Log.d("HILSTAG", "Class Name :SET");
                Stop stop = new Stop();
                stop.setStopName(cursor.getString(cursor.getColumnIndex(BusDBopenHelper.STOP_NAME)));
//                Log.d("HILSTAG", "STOP NAME:SET");
                schedule.setStop(stop);
                schedule.setBus(bus);
                scheduleDetails.add(schedule);

        }

//        Log.d("HILSTAG","Returned From List Detail Functuion");
        return scheduleDetails;

    }

}
