package com.example.nur.monglaport.Local_Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nur.monglaport.Class.Teleph;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by nur on 11/28/16.
 */public class Telephonedb {

    public static final String KEY_SLNO="SLNO";
    public static final String KEY_DESIG="DESIGNATION";
    public static final String KEY_TEL="TELEPHONE_NO";
    public static final String KEY_EM="EMAIL";


    private static final String DATABASE_NAME="mongla_teledb12";
    private static final String DATABASE_TABLE="r4telephoneuiuias098754";
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
                    KEY_SLNO+" TEXT, "+
                    KEY_DESIG+" TEXT, "+
                    KEY_EM+" TEXT, "+
                    KEY_TEL+" TEXT);"
            );


        }

        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);

        }
    }

    public Telephonedb(Context c)
    {
        ourContext=c;
    }


    public Telephonedb open() throws SQLException
    {
        ourHelper=new DbHelper(ourContext);
        ourDatabase=ourHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        ourHelper.close();
    }


    public void createEntry(String slno, String name,String tel,String em)
    {
        ContentValues cv=new ContentValues();

        cv.put(KEY_SLNO,slno);
        cv.put(KEY_DESIG,name);
        cv.put(KEY_TEL,tel);
        cv.put(KEY_EM,em);


        if(!match(name))
        ourDatabase.insert(DATABASE_TABLE, null, cv);
    }


    public boolean match(String name) {
        String[] columns = new String[]{KEY_DESIG};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);

        int iName = c.getColumnIndex(KEY_DESIG);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (c.getString(iName).equalsIgnoreCase(name))
            {
                return true;
            }

        }
        return false;
    }


    public ArrayList<Teleph> getData()
    {

        String[] columns=new String[]{ KEY_SLNO,KEY_DESIG,KEY_TEL,KEY_EM};
        Cursor c=ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);
        ArrayList<Teleph> telephs=new ArrayList<Teleph>();
        int isl=c.getColumnIndex(KEY_SLNO);
        int iname=c.getColumnIndex(KEY_DESIG);
        int itel=c.getColumnIndex(KEY_TEL);
        int iem=c.getColumnIndex(KEY_EM);



        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            Teleph teleph=new Teleph();
            teleph.setSlno(c.getString(isl));
            teleph.setDesignation(c.getString(iname));
            teleph.setTel(c.getString(itel));
            teleph.setEmail(c.getString(iem));

            telephs.add(teleph);
        }

        return telephs;
    }


    public ArrayList<String> getDesig()
    {
        String[] columns=new String[]{ KEY_DESIG};
        Cursor c=ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);
        ArrayList<String> desigs=new ArrayList<String>();
        int iname=c.getColumnIndex(KEY_DESIG);



        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            desigs.add(c.getString(iname));
        }

        return desigs;
    }



    public ArrayList<Teleph> getDatabyDesing(String x)
    {
        String[] columns=new String[]{KEY_SLNO, KEY_DESIG,KEY_TEL,KEY_EM};
        Cursor c=ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);
        ArrayList<Teleph> telephs=new ArrayList<Teleph>();
        int isl=c.getColumnIndex(KEY_SLNO);
        int iname=c.getColumnIndex(KEY_DESIG);
        int itel=c.getColumnIndex(KEY_TEL);
        int iem=c.getColumnIndex(KEY_EM);


        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(c.getString(iname).equalsIgnoreCase(x))
            {
                Teleph teleph=new Teleph();
                teleph.setSlno(c.getString(isl));
                teleph.setDesignation(c.getString(iname));
                teleph.setTel(c.getString(itel));
                teleph.setEmail(c.getString(iem));

                telephs.add(teleph);
            }
        }

        return telephs;
    }

    public void delete_all(){
        ourDatabase.delete(DATABASE_TABLE, null, null);
    }

}