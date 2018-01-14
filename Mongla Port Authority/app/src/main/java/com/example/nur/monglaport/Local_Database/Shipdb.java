package com.example.nur.monglaport.Local_Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nur.monglaport.Class.Shp;

import java.sql.SQLException;
import java.util.ArrayList;

public class Shipdb {

    public static final String KEY_ROWID="_SLNO";
    public static final String KEY_NAME="Name";
    public static final String KEY_LOCATION="Loc";
    public static final String KEY_NATURE="Nature";
    public static final String KEY_ARRIVAL="Arrival";
    public static final String KEY_ETD="Etd";
    public static final String KEY_AGENT="Agent";


    private static final String DATABASE_NAME="asjkdjkakjghajsdklNKJASNAKJSNKJANSkjjkhbjhasjkdhasjkdhsagdfgjkdhhbjnbnmbnjbnmbsh";
    private static final String DATABASE_TABLE="jaskfjsdiofhAMSKLuhjkhjhjjhjhjjkhjkhkjahsdifhsdjkfhjkwhsddsi";
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
                    KEY_ROWID + " TEXT, "+
                    KEY_NAME + " TEXT, "+
                    KEY_LOCATION + " TEXT, "+
                    KEY_NATURE+" TEXT, "+
                    KEY_ARRIVAL+ " TEXT, "+
                    KEY_ETD+" TEXT, "+
                    KEY_AGENT+" TEXT);"
            );

        }

        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);

        }
    }

    public Shipdb(Context c)
    {
        ourContext=c;
    }


    public Shipdb open() throws SQLException
    {
        ourHelper=new DbHelper(ourContext);
        ourDatabase=ourHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        ourHelper.close();
    }

    public void createEntry(String slno, String name, String location, String nature, String arrival, String etd, String agent)
    {
        ContentValues cv=new ContentValues();
        cv.put(KEY_ROWID,slno);
        cv.put(KEY_NAME,name);
        cv.put(KEY_LOCATION, location);
        cv.put(KEY_NATURE,nature);
        cv.put(KEY_ARRIVAL,arrival);
        cv.put(KEY_ETD,etd);
        cv.put(KEY_AGENT,agent);

        if(!match(slno,name,location,nature,arrival,etd,agent) || isTableExists(ourDatabase,DATABASE_TABLE)==false )
            ourDatabase.insert(DATABASE_TABLE, null, cv);
    }




    public boolean match(String slno, String name, String location, String nature, String arrival, String etd, String agent) {

        String[] columns = new String[]{KEY_ROWID, KEY_NAME,KEY_LOCATION,KEY_NATURE,KEY_ARRIVAL,KEY_ETD,KEY_AGENT};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);

        int iSL = c.getColumnIndex(KEY_ROWID);
        int iName = c.getColumnIndex(KEY_NAME);
        int iLoc= c.getColumnIndex(KEY_LOCATION);
        int iNat= c.getColumnIndex(KEY_NATURE);
        int iArriv= c.getColumnIndex(KEY_ARRIVAL);
        int ietd= c.getColumnIndex(KEY_ETD);
        int iAgent= c.getColumnIndex(KEY_AGENT);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (c.getString(iSL).equalsIgnoreCase(slno) && c.getString(iName).equalsIgnoreCase(name)  && c.getString(iLoc).equalsIgnoreCase(location) && c.getString(iNat).equalsIgnoreCase(nature)  && c.getString(iArriv).equalsIgnoreCase(arrival) && c.getString(ietd).equalsIgnoreCase(etd) && c.getString(iAgent).equalsIgnoreCase(agent)  )
            {
                return true;
            }


        }
        return false;
    }




    public ArrayList<Shp> getData()
    {

        String[] columns=new String[]{ KEY_ROWID,KEY_NAME,KEY_LOCATION,KEY_NATURE,KEY_ARRIVAL,KEY_ETD,KEY_AGENT};
        Cursor c=ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);
        ArrayList<Shp> shps=new ArrayList<Shp>();

        int iSL = c.getColumnIndex(KEY_ROWID);
        int iName = c.getColumnIndex(KEY_NAME);
        int iLoc= c.getColumnIndex(KEY_LOCATION);
        int iNat= c.getColumnIndex(KEY_NATURE);
        int iArriv= c.getColumnIndex(KEY_ARRIVAL);
        int ietd= c.getColumnIndex(KEY_ETD);
        int iAgent= c.getColumnIndex(KEY_AGENT);


        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            Shp shp=new Shp();
            shp.setSlno(c.getString(iSL));
            shp.setName(c.getString(iName));
            shp.setLocation(c.getString(iLoc));
            shp.setNature(c.getString(iNat));
            shp.setArrival(c.getString(iArriv));
            shp.setEtd(c.getString(ietd));
            shp.setAgent(c.getString(iAgent));
            shps.add(shp);
        }

        return shps;
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

