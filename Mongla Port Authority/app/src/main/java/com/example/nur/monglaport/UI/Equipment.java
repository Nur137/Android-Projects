package com.example.nur.monglaport.UI;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nur.monglaport.Class.EquipArrayAdapter;
import com.example.nur.monglaport.Class.Equips;
import com.example.nur.monglaport.Local_Database.Equipmentdb;
import com.example.nur.monglaport.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.StreamHandler;

public class Equipment extends AppCompatActivity {

    String res;
    ArrayList<Equips> equipmentlist;
    ArrayList<String> str;
    int i,count=0;
    ListView lis;
    private static final String HTML_TAG_PATTERN = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);

        lis=(ListView)findViewById(R.id.listv);
        equipmentlist = new ArrayList<Equips>();
        str=new ArrayList<String>();
        res=new String();
        count=0;

        Equipmentdb equipmentdb=new Equipmentdb(Equipment.this);
        try {
            equipmentdb.open();
            equipmentlist=equipmentdb.getData();
            equipmentdb.close();
            set_list();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(isInternetConnected(Equipment.this)==false)
        {
            Toast.makeText(Equipment.this,"You are seeing offline data",Toast.LENGTH_SHORT).show();

        }

        else
            new doit().execute();

    }

    public class doit extends AsyncTask<Void, Void, Void> {
        String words = new String();

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect("http://www.mpa.gov.bd/site/page/891258b2-3e13-41e4-a661-4e3cea1d7da2/%E0%A6%A6%E0%A7%88%E0%A6%A8%E0%A6%BF%E0%A6%95-%E0%A6%B8%E0%A6%B0%E0%A6%9E%E0%A7%8D%E0%A6%9C%E0%A6%BE%E0%A6%AE-%E0%A6%85%E0%A6%AC%E0%A6%B8%E0%A7%8D%E0%A6%A5%E0%A6%BE").get();
                Elements elements=doc.select("div[id*=printable_area");

                for (Element x : elements) {
                    words += x.getElementsByTag("tbody");
                }
                words = words.replace("&amp;", "&");
                words = words.replace("&nbsp;", "N/A");


                for (i = 0; i < words.length(); i++) {
                    String x = new String();

                    if (words.charAt(i) == '<' && words.charAt(i + 1) == 't' && words.charAt(i + 2) == 'd') {
                        while (words.charAt(i) != '>')
                            i++;

                        i++;
                        while (!(words.charAt(i) == '<' && words.charAt(i+1) == '/' && words.charAt(i+2) == 't')) {
                            res += words.charAt(i);
                            x += words.charAt(i);
                            i++;
                        }
                        if (x.equalsIgnoreCase("SL. No.")) {
                            break;
                        }
                        //words = words.replace("<p>", "");

                        //words=words.replace(HTML_TAG_PATTERN,"");
                        //words = words.replace("</p>", "");

                        String y=new String();
                        int flg=0;
                        for(int l=0;l<x.length();l++)
                        {
                            if(x.charAt(l)=='>')flg=1;
                            if(x.charAt(l)=='<')flg=0;
                            else if(flg==1)y+=x.charAt(l);
                        }
                        y=y.replace(">","");
                        y=y.replace("\n","");

                        str.add(y);
                    }

                }

                Equipmentdb equipdb =new Equipmentdb(Equipment.this);
                try {
                    equipdb.open();
                    equipdb.delete_all();
                    equipdb.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


                for (i = 10; i < str.size()-10; i += 9) {
                    Equips equips = new Equips();
                    equips.setSlno(str.get(i));
                    equips.setDesc(str.get(i + 1));
                    equips.setCapacity(str.get(i + 2));
                    equips.setQuantity(str.get(i + 3));
                    equips.setYear(str.get(i + 8));
                    String countable=str.get(i + 8).replaceAll("\\s+","");
                    count+=Integer.parseInt(countable);

                    Equipmentdb equipmentdb =new Equipmentdb(Equipment.this);
                    try {
                        equipmentdb.open();
                        equipmentdb.createEntry(str.get(i),str.get(i+1),str.get(i+2),str.get(i+3),str.get(i+8));
                        equipmentdb.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    equipmentlist.add(equips);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
            Equips equips = new Equips();
            equips.setSlno(" ");
            equips.setDesc("Total");
            equips.setCapacity(" ");
            equips.setQuantity(" ");
            equips.setYear(Integer.toString(count));
            equipmentlist.add(equips);

            for(i=0;i<str.size();i++)
            {
                Log.d("cvcv",str.get(i));
            }

            Equipmentdb equipmentdb =new Equipmentdb(Equipment.this);
            try {
                equipmentdb.open();
                equipmentdb.createEntry("","Total","","",Integer.toString(count));
                equipmentdb.close();
            } catch (SQLException e) {
                e.printStackTrace();

            }

            Equipmentdb equipmentdb1=new Equipmentdb(Equipment.this);
            try {
                equipmentdb1.open();
                equipmentlist=equipmentdb1.getData();
                equipmentdb1.close();
                set_list();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Toast.makeText(Equipment.this,"Data Updated",Toast.LENGTH_SHORT).show();

        }
    }


public void set_list()
{ 
    EquipArrayAdapter eq=new EquipArrayAdapter(this,equipmentlist);
    lis.setAdapter(eq);

}


    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            finish();
//            Intent in = new Intent(Equipment.this, Front.class);
//            startActivity(in);
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }
}