package com.example.nur.monglaport.UI;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nur.monglaport.Class.Config;
import com.example.nur.monglaport.Class.FpArrayAdapter;
import com.example.nur.monglaport.Class.Fpofficer;
import com.example.nur.monglaport.Class.Teleph;
import com.example.nur.monglaport.Local_Database.Fofficerdb;
import com.example.nur.monglaport.Local_Database.Telephonedb;
import com.example.nur.monglaport.R;
import com.firebase.client.Firebase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Focal_point_off extends AppCompatActivity {
    ListView listView;
    Firebase firebase;
    ArrayList<Fpofficer> fpofficers;
    ArrayList<String> str,work,off,email,mob;
    int i,spflg=0;

    String res;
    private static final String HTML_TAG_PATTERN = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focal_point_off);
        listView=(ListView)findViewById(R.id.listv);
        firebase=new Firebase(Config.FIREBASE_URL);
        str=new ArrayList<String>();
        work=new ArrayList<String>();
        off=new ArrayList<String>();
        email=new ArrayList<String>();
        mob=new ArrayList<String>();

        fpofficers=new ArrayList<Fpofficer>();
        res=new String();


        Fofficerdb fofficerdb=new Fofficerdb(Focal_point_off.this);
        try {
            fofficerdb.open();
            fpofficers=fofficerdb.getData();
            fofficerdb.close();
            set_list();

        } catch (SQLException e) {
            e.printStackTrace();
        }



        if(!isInternetConnected(Focal_point_off.this))
        {
            Toast.makeText(Focal_point_off.this,"You are seeing offline data",Toast.LENGTH_SHORT).show();
        }
        else
        {
            new doit().execute();
        }


    }

    public class doit extends AsyncTask<Void, Void, Void> {
        String words = new String();

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect("http://www.mpa.gov.bd/site/page/9b5ffd5d-4d3a-40df-9a7f-c92f63d4f7a3/%E0%A6%AB%E0%A7%8B%E0%A6%95%E0%A6%BE%E0%A6%B2-%E0%A6%AA%E0%A7%9F%E0%A7%87%E0%A6%A8%E0%A7%8D%E0%A6%9F-%E0%A6%95%E0%A6%B0%E0%A7%8D%E0%A6%AE%E0%A6%95%E0%A6%B0%E0%A7%8D%E0%A6%A4%E0%A6%BE").get();
                Elements elements=doc.select("div[id*=printable_area");

                for (Element x : elements) {
                    words += x.getElementsByTag("tbody");
                }
                words = words.replace("&amp;", "&");
                words = words.replace("&nbsp;", "N/A");
                for (i = 0; i < words.length(); i++) {
                    String x = new String();

                    if (words.charAt(i) == '<' && words.charAt(i + 1) == 'p' && words.charAt(i + 2) == '>') {

                        while(words.charAt(i)!='>')i++;

                        while (!(words.charAt(i) == '<' && words.charAt(i+1) == '/' && words.charAt(i+2) == 'p')) {
                            res += words.charAt(i);
                            x += words.charAt(i);
                            i++;
                        }
                        x.trim();
                        if(!x.contains("N/A"))
                        str.add(x);



                        if(x.contains("১)") || x.contains("২)")||x.contains("৩)")||x.contains("৪)")||x.contains("৫)")||x.contains("৬)")||x.contains("৭)")||x.contains("৮)")||x.contains("৯)")||x.contains("০)"))spflg=1;
                        else if(spflg==1){spflg=0;x=x.replace(">","");work.add(x);}


                        else if(x.contains("(ক)") || x.contains("(খ)") || x.contains("(গ)")|| x.contains("(ঘ)")|| x.contains("(ঙ)")  || x.contains("(চ)") || x.contains("(ছ)")|| x.contains("(জ)")|| x.contains("(ঝ)")|| x.contains("(ঞ)")){x=x.replace(">","");String y;work.add(x);}


                        if(x.contains("নাম:") || x.contains("জনাব")){x=x.replace(">","");x=x.replace("নাম:","");off.add(x);}
                        if(x.contains("ইমেইল:")){
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
                            email.add(y);
                        }
                        if(x.contains("মোবাইল:")){x=x.replace(">","");mob.add(x);}



                    }

                }


                Fofficerdb fofficerdb=new Fofficerdb(Focal_point_off.this);
                try {
                    fofficerdb.open();
                    fofficerdb.delete_all();
                    fofficerdb.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                for (i = 0; i < work.size(); i++) {


                    Fofficerdb fofficerdb1=new Fofficerdb(Focal_point_off.this);
                    try {
                        fofficerdb1.open();
                        String cont=mob.get(i)+'\n'+email.get(i);
                        fofficerdb1.createEntry(work.get(i), off.get(i),cont);
                        fofficerdb1.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


//                    fpofficers.add(teleph);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);


            for(i=0;i<off.size();i++)
            {
                Log.d("valueue",off.get(i));
            }


            Toast.makeText(Focal_point_off.this, "Data updated", Toast.LENGTH_SHORT).show();

            Fofficerdb fofficerdb=new Fofficerdb(Focal_point_off.this);
            try {
                fofficerdb.open();
                fpofficers=fofficerdb.getData();
                fofficerdb.close();
                set_list();

            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

    }


    public void set_list()
    {
        FpArrayAdapter fpArrayAdapter =new FpArrayAdapter(this,fpofficers);
        listView.setAdapter(fpArrayAdapter);

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
//            Intent in = new Intent(Focal_point_off.this, Front.class);
//            startActivity(in);
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }

}
