package com.example.nur.monglaport.Local_Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.nur.monglaport.Class.Equips;
import java.sql.SQLException;
import java.util.ArrayList;

public class Equipmentdb {

    public static final String KEY_ROWID="_SLNO";
    public static final String KEY_DESCRIP="Description";
    public static final String KEY_CAPACITY="Capacity";
    public static final String KEY_QUANTITY="Quantity";
    public static final String KEY_YEAR="Year";

    private static final String DATABASE_NAME="monglsportsahkjdhasjhkhkj";
    private static final String DATABASE_TABLE="eqhjuipajskdj";
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
                    KEY_ROWID + " TEXT NOT NULL, "+
                    KEY_DESCRIP + " TEXT NOT NULL, "+
                    KEY_CAPACITY + " TEXT NOT NULL, "+
                    KEY_QUANTITY+" TEXT NOT NULL, "+
                    KEY_YEAR+" TEXT NOT NULL);"
            );

        }

        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);

        }
    }

    public Equipmentdb(Context c)
    {
        ourContext=c;
    }


    public Equipmentdb open() throws SQLException
    {
        ourHelper=new DbHelper(ourContext);
        ourDatabase=ourHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        ourHelper.close();
    }

    public void createEntry(String slno, String desc, String cap, String quantity, String year)
    {
        ContentValues cv=new ContentValues();
        cv.put(KEY_ROWID,slno);
        cv.put(KEY_DESCRIP,desc);
        cv.put(KEY_CAPACITY, cap);
        cv.put(KEY_QUANTITY,quantity);
        cv.put(KEY_YEAR,year);
        if(!match(slno,desc,cap,quantity,year) || isTableExists(ourDatabase,DATABASE_TABLE)==false )
        ourDatabase.insert(DATABASE_TABLE, null, cv);
    }




    public boolean match(String slno, String desc, String cap, String quantity, String year) {

        String[] columns = new String[]{KEY_ROWID, KEY_DESCRIP,KEY_CAPACITY,KEY_QUANTITY,KEY_YEAR};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);

        int iSL = c.getColumnIndex(KEY_ROWID);
        int iDesc = c.getColumnIndex(KEY_DESCRIP);
        int iCap= c.getColumnIndex(KEY_CAPACITY);
        int iQuant= c.getColumnIndex(KEY_QUANTITY);
        int iYear= c.getColumnIndex(KEY_YEAR);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (c.getString(iSL).equalsIgnoreCase(slno) && c.getString(iDesc).equalsIgnoreCase(desc)  && c.getString(iCap).equalsIgnoreCase(cap) && c.getString(iQuant).equalsIgnoreCase(quantity)  && c.getString(iYear).equalsIgnoreCase(year)  )
            {
             return true;
            }


        }
        return false;
    }




    public ArrayList<Equips> getData()
    {

        String[] columns=new String[]{ KEY_ROWID,KEY_DESCRIP,KEY_CAPACITY,KEY_QUANTITY,KEY_YEAR};
        Cursor c=ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);
        ArrayList<Equips> equipses=new ArrayList<Equips>();
        int i=0;
        int slno=c.getColumnIndex(KEY_ROWID);
        int idesc=c.getColumnIndex(KEY_DESCRIP);
        int icap=c.getColumnIndex(KEY_CAPACITY);
        int iQuan=c.getColumnIndex(KEY_QUANTITY);
        int iYear=c.getColumnIndex(KEY_YEAR);


        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            Equips equips=new Equips();
            equips.setSlno(c.getString(slno));
            equips.setDesc(c.getString(idesc));
            equips.setCapacity(c.getString(icap));
            equips.setQuantity(c.getString(iQuan));
            equips.setYear(c.getString(iYear));
            //result[i]=c.getString(iName);
            i++;
            equipses.add(equips);
        }

        return equipses;
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

