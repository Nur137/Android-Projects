package com.example.nur.monglaport.UI;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.Toast;
import com.example.nur.monglaport.Class.ShipArrayAdapter;
import com.example.nur.monglaport.Class.Shp;
import com.example.nur.monglaport.Local_Database.Shipdb;
import com.example.nur.monglaport.R;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Ship extends AppCompatActivity {

    String res;
    ArrayList<Shp> shps;
    ArrayList<String> str;
    int i;
    ListView lis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship);


        lis=(ListView)findViewById(R.id.listv);

        shps = new ArrayList<Shp>();
        str=new ArrayList<String>();
        res=new String();


        Shipdb shipdb=new Shipdb(Ship.this);
        try {
            shipdb.open();
            shps=shipdb.getData();
            shipdb.close();
            set_list();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(isInternetConnected(Ship.this)==false)
        {
            Toast.makeText(Ship.this,"You are viewing offline data",Toast.LENGTH_SHORT).show();

        }

        else
            new doit().execute();
      }

    public class doit extends AsyncTask<Void, Void, Void> {
        String words = new String();

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect("http://www.mpa.gov.bd/site/page/af0d5189-2b61-4562-b575-92c43b26d05c/%E0%A6%AC%E0%A6%A8%E0%A7%8D%E0%A6%A6%E0%A6%B0%E0%A7%87-%E0%A6%9C%E0%A6%BE%E0%A6%B9%E0%A6%BE%E0%A6%9C-%E0%A6%B8%E0%A6%82%E0%A6%96%E0%A7%8D%E0%A6%AF%E0%A6%BE").get();

                Elements elements=doc.select("div[id*=printable_area");


                for (Element x : elements) {
                    words += x.getElementsByTag("tbody");
                  //  Toast.makeText(Ship.this,words,Toast.LENGTH_SHORT).show();
                }
                words = words.replace("&amp;", "&");

                words = words.replace("&nbsp;", "");
                for (i = 0; i < words.length(); i++) {
                    String x = new String();

                    if (words.charAt(i) == '<' && words.charAt(i + 1) == 't' && words.charAt(i + 2) == 'd') {
                        //res+= Integer.toString(i);
                        while (words.charAt(i) != '>')
                            i++;
                        i++;
                        if(words.charAt(i) == '<')
                        {
                            x =" ";

                        }
                        while (words.charAt(i) != '<') {
                            res += words.charAt(i);
                            x += words.charAt(i);
                            i++;
                        }
                         str.add(x);
                    }

                }
                Shipdb shpdb =new Shipdb(Ship.this);

                try {
                    shpdb.open();
                    shpdb.delete_all();
                    shpdb.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


                int val=0;

                for (i = 7; i < str.size(); i += 7) {
                    Shp shp = new Shp();
                    shp.setSlno(Integer.toString(val++));
                    shp.setName(str.get(i + 1));
                    shp.setLocation(str.get(i + 2));
                    shp.setNature(str.get(i + 3));
                    shp.setArrival(str.get(i + 4));
                    shp.setEtd(str.get(i + 5));
                    shp.setAgent(str.get(i + 6));

                    Shipdb shipdb =new Shipdb(Ship.this);
                    try {
                        shipdb.open();
                        shipdb.createEntry(Integer.toString(val),str.get(i+1),str.get(i+2),str.get(i+3),str.get(i+4),str.get(i+5),str.get(i+6));
                        shipdb.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                    shps.add(shp);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
            Toast.makeText(Ship.this,"Data Updated",Toast.LENGTH_SHORT).show();

            Shipdb shipdb=new Shipdb(Ship.this);
            try {
                shipdb.open();
                shps=shipdb.getData();
                shipdb.close();
                set_list();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }


    public void set_list()
    {
        ShipArrayAdapter eq=new ShipArrayAdapter(this,shps);
        lis.setAdapter(eq);

    }


    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            finish();
//            Intent in = new Intent(Ship.this, Front.class);
//            startActivity(in);
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }
}