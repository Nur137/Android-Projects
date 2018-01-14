package com.example.nur.monglaport.Local_Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by nur on 11/28/16.
 */
public class Navigationdb {
    public static final String KEY_NAV="NAVIGATION";

    private static final String DATABASE_NAME="monknglaport";
    private static final String DATABASE_TABLE="navbjk";
    private static final int DATABASE_VERSION=1;

    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    private static class DbHelper extends SQLiteOpenHelper {


        public DbHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + "(" +
                    KEY_NAV+" TEXT NOT NULL);"
            );


        }

        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);

        }
    }

    public Navigationdb(Context c)
    {
        ourContext=c;
    }


    public Navigationdb open() throws SQLException
    {
        ourHelper=new DbHelper(ourContext);
        ourDatabase=ourHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        ourHelper.close();
    }


    public void createEntry(String nav)
    {
        ContentValues cv=new ContentValues();
        cv.put(KEY_NAV,nav);
        if(!match(nav))
            ourDatabase.insert(DATABASE_TABLE, null, cv);
    }


    public boolean match(String desc) {

        String[] columns = new String[]{KEY_NAV};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);


        int iNav = c.getColumnIndex(KEY_NAV);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (c.getString(iNav).equalsIgnoreCase(desc))
            {
                return true;
            }

        }
        return false;
    }

    public String getData()
    {

        String[] columns=new String[]{ KEY_NAV};
        Cursor c=ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);
        String  result=new String ();
        int nv=c.getColumnIndex(KEY_NAV);



        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            result+=c.getString(nv);
        }

        return result;
    }

    public void delete_all(){
        ourDatabase.delete(DATABASE_TABLE, null, null);
    }
}