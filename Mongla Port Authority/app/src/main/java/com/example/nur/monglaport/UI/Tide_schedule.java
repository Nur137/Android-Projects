package com.example.nur.monglaport.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.nur.monglaport.Class.EquipArrayAdapter;
import com.example.nur.monglaport.Class.Equips;
import com.example.nur.monglaport.Class.Tide;
import com.example.nur.monglaport.Class.TideArrayAdapter;
import com.example.nur.monglaport.Local_Database.Equipmentdb;
import com.example.nur.monglaport.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Tide_schedule extends AppCompatActivity {

    ArrayList<String> str;
    ArrayList<Tide> tides;
    int i;
    ListView listView;
    private static final String HTML_TAG_PATTERN = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tide);
         listView=(ListView)findViewById(R.id.listv);
        str=new ArrayList<String>();
        tides=new ArrayList<Tide>();



        new doit().execute();

    }


    public class doit extends AsyncTask<Void, Void, Void> {
        String words = new String();

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect("http://www.mpa.gov.bd/site/view/commondoc/Tide Table/%E0%A6%9C%E0%A7%8B%E0%A7%9F%E0%A6%BE%E0%A6%B0-%E0%A6%AD%E0%A6%BE%E0%A6%9F%E0%A6%BE%E0%A6%B0-%E0%A6%A4%E0%A6%A5%E0%A7%8D%E0%A6%AF").get();
                Elements elements = doc.select("div[id*=printable_area");

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
                        while (!(words.charAt(i) == '<' && words.charAt(i + 1) == '/' && words.charAt(i + 2) == 't')) {
                            x += words.charAt(i);
                            i++;
                        }
                        if (x.equalsIgnoreCase("SL. No.")) {
                            break;
                        }

                        x=x.replaceAll("<a href=","");
                        //x=x.replaceAll(HTML_TAG_PATTERN,"");

                        str.add(x);
                    }

                }


                for (i = 0; i < str.size() - 1; i += 3) {
                    Tide tide = new Tide();
                    tide.setSlno(str.get(i));
                    tide.setInfo(str.get(i + 1));
                    String now=str.get(i+2);
                   String main="";
                    for(int k=2;k<now.length();k++)
                    {
                        if(now.charAt(k)=='"' )break;

                        //if(now.charAt(k)=='t' && now.charAt(k+1)=='i'&& now.charAt(k+2)=='t'&&now.charAt(k+3)=='l' )break;
                        main+=now.charAt(k);
                    }

                    tide.setLink(main);
                    tides.add(tide);

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
            set_list();


        }
    }

//        @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            finish();
//            Intent in = new Intent(this, Front.class);
//            startActivity(in);
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }


    public void set_list()
    {
        TideArrayAdapter tideArrayAdapter=new TideArrayAdapter(this,tides);
        listView.setAdapter(tideArrayAdapter);

    }
}