package com.example.nur.monglaport.Local_Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nur.monglaport.Class.Tide;

import java.sql.SQLException;
import java.util.ArrayList;

public class Tidedb {

    public static final String KEY_DATE="Date";
    public static final String KEY_RIVER="River";
    public static final String KEY_TIME="Time";
    public static final String KEY_HEIGHT="Height";


    private static final String DATABASE_NAME="ghtsayui34";
    private static final String DATABASE_TABLE="jaskfjsjkhjkbj987";
    private static final int DATABASE_VERSION=1;

    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    private static class DbHelper extends SQLiteOpenHelper{

        public DbHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }
        @Override

        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + "(" +
                    KEY_DATE + " TEXT, "+
                    KEY_RIVER + " TEXT, "+
                    KEY_TIME + " TEXT, "+
                    KEY_HEIGHT+" TEXT);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);

        }
    }

    public Tidedb(Context c)
    {
        ourContext=c;
    }

    public Tidedb open() throws SQLException
    {
        ourHelper=new DbHelper(ourContext);
        ourDatabase=ourHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        ourHelper.close();
    }

    public void createEntry(String date, String river, String time,String height)
    {
        ContentValues cv=new ContentValues();
        cv.put(KEY_DATE,date);
        cv.put(KEY_RIVER,river);
        cv.put(KEY_TIME, time);
        cv.put(KEY_HEIGHT,height);

        if(!match(date,river,time,height) || isTableExists(ourDatabase,DATABASE_TABLE)==false )
            ourDatabase.insert(DATABASE_TABLE, null, cv);
    }




    public boolean match(String date, String river, String time,String height) {

        String[] columns = new String[]{KEY_DATE, KEY_RIVER,KEY_TIME,KEY_HEIGHT};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);

        int idate = c.getColumnIndex(KEY_DATE);
        int iriv = c.getColumnIndex(KEY_RIVER);
        int itim= c.getColumnIndex(KEY_TIME);
        int iheight= c.getColumnIndex(KEY_HEIGHT);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (c.getString(idate).equalsIgnoreCase(date) && c.getString(iriv).equalsIgnoreCase(river)  && c.getString(itim).equalsIgnoreCase(time) && c.getString(iheight).equalsIgnoreCase(height) )
            {
                return true;
            }


        }
        return false;
    }



    public ArrayList<Tide> getData()
    {

        String[] columns = new String[]{KEY_DATE, KEY_RIVER,KEY_TIME,KEY_HEIGHT};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);

        ArrayList<Tide> tides=new ArrayList<Tide>();

        int idate = c.getColumnIndex(KEY_DATE);
        int iriv = c.getColumnIndex(KEY_RIVER);
        int itim= c.getColumnIndex(KEY_TIME);
        int iheight= c.getColumnIndex(KEY_HEIGHT);;

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
//            Tide tide=new Tide();
//            tide.setDate(c.getString(idate));
//            tide.setRiver(c.getString(iriv));
//            tide.setTime(c.getString(itim));
//            tide.setHeight(c.getString(iheight));
//
//            tides.add(tide);
        }

        return tides;
    }


    public ArrayList<Tide> getbyData(String x)
    {
        String[] columns = new String[]{KEY_DATE, KEY_RIVER,KEY_TIME,KEY_HEIGHT};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);

        ArrayList<Tide> tides=new ArrayList<Tide>();

        int idate = c.getColumnIndex(KEY_DATE);
        int iriv = c.getColumnIndex(KEY_RIVER);
        int itim= c.getColumnIndex(KEY_TIME);
        int iheight= c.getColumnIndex(KEY_HEIGHT);;

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(idate).equalsIgnoreCase(x))
            {
//                Tide tide=new Tide();
//                tide.setDate(c.getString(idate));
//                tide.setRiver(c.getString(iriv));
//                tide.setTime(c.getString(itim));
//                tide.setHeight(c.getString(iheight));
//                tides.add(tide);
            }
        }
        return tides;
    }


    public ArrayList<String> getDate()
    {

        String[] columns = new String[]{KEY_DATE};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);

        ArrayList<String> tides=new ArrayList<String>();

        int idate = c.getColumnIndex(KEY_DATE);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            tides.add(c.getString(idate));
        }

        return tides;
    }

    boolean isTableExists(SQLiteDatabase db, String tableName)
    {
        if (tableName == null || db == null || !db.isOpen())
        {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
        if (!cursor.moveToFirst())
        {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    public void delete_all(){
        ourDatabase.delete(DATABASE_TABLE, null, null);

    }
}