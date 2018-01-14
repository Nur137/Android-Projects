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

import com.example.nur.monglaport.Class.Config;
import com.example.nur.monglaport.Class.Container;
import com.example.nur.monglaport.Class.ContainerArrayAdapter;
import com.example.nur.monglaport.Local_Database.Containersdb;
import com.example.nur.monglaport.R;
import com.firebase.client.Firebase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Containers extends AppCompatActivity {
    int i,t_d,t_r,f_d,f_r;
    ListView lis;
    Firebase firebase;
    ArrayList<Container> containers;
    ArrayList<String> str;
    private static final String HTML_TAG_PATTERN = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";

    String res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_containers);


        lis=(ListView)findViewById(R.id.lst);
        firebase = new Firebase(Config.FIREBASE_URL);

        containers = new ArrayList<Container>();
        str=new ArrayList<String>();
        res=new String();

        Containersdb containersdb=new Containersdb(Containers.this);
        try {
            containersdb.open();
            containers=containersdb.getData();
            containersdb.close();
            set_list();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(isInternetConnected(Containers.this)==false)
        {
            Toast.makeText(Containers.this,"You are seeing offline data",Toast.LENGTH_SHORT).show();
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
                Document doc = Jsoup.connect("http://www.mpa.gov.bd/site/page/df5cde85-f5dd-4623-b445-7a0d044d1dba/container").get();

                Elements elements=doc.select("div[id*=printable_area");

                for (Element x : elements) {
                    words += x.getElementsByTag("table");
                }

                words = words.replace("&amp;", "&");

                words = words.replace("&nbsp;", "");


                for (i = 0; i < words.length(); i++) {
                    String x = new String();

                    if (words.charAt(i) == '<' && words.charAt(i + 1) == 't' && words.charAt(i + 2) == 'd') {

                        while(words.charAt(i)!='>')i++;

                        while (!(words.charAt(i) == '<' && words.charAt(i+1) == '/' && words.charAt(i+2) == 't')) {
                            res += words.charAt(i);
                            x += words.charAt(i);
                            i++;
                        }

                        String d=new String();

                        int mc=0,k,ln=x.length();
                        for(k=0;k<ln;k++)
                        {
                            if(mc==2)d+=x.charAt(k);
                            if(x.charAt(k)=='>')mc++;
                            if(x.charAt(k)=='<' && mc==2)mc=0;
                        }
                        d=d.replaceAll("<","");
                        str.add(d);
                    }
            }

                Containersdb containersdb=new Containersdb(Containers.this);
                try {
                    containersdb.open();
                    containersdb.delete_all();
                    containersdb.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                t_d=t_r=f_d=f_r=0;
                for (i = 11; i < str.size()-21; i += 8) {
                    Container container=new Container();

                        container.setSlno(str.get(i));
                        container.setA_name(str.get(i+1));
                        container.setTwd(str.get(i+2));
                        container.setTwr(str.get(i+3));
                        container.setFord(str.get(i+4));
                        container.setForr(str.get(i+5));

                        t_d+=Integer.parseInt(str.get(i+2).trim());
                        t_r+=Integer.parseInt(str.get(i+3).trim());
                        f_d+=Integer.parseInt(str.get(i+4).trim());
                        f_r+=Integer.parseInt(str.get(i+5).trim());

                        Containersdb contdb=new Containersdb(Containers.this);
                        try {
                            contdb.open();
                            contdb.createEntry(str.get(i),str.get(i+1),str.get(i+2),str.get(i+3),str.get(i+4),str.get(i+5));
                            contdb.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    containers.add(container);

                }


                Container cont=new Container();

                cont.setSlno("Total");
                cont.setA_name(" ");
                cont.setTwd(Integer.toString(t_d));
                cont.setTwr(Integer.toString(t_r));
                cont.setFord(Integer.toString(f_d));
                cont.setForr(Integer.toString(f_r));
                containers.add(cont);

                Containersdb contdb2=new Containersdb(Containers.this);
                try {
                    contdb2.open();
                    contdb2.createEntry("Total"," ",Integer.toString(t_d),Integer.toString(t_r),Integer.toString(f_d),Integer.toString(f_r));
                    contdb2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
                return null;
            }


        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);

            res=Integer.toString(words.length());

            Toast.makeText(Containers.this, "Data updated", Toast.LENGTH_SHORT).show();

            Containersdb containersdb=new Containersdb(Containers.this);
            try {
                containersdb.open();
                containers=containersdb.getData();
                containersdb.close();
                set_list();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }


    public void set_list()
    {
        ContainerArrayAdapter eq=new ContainerArrayAdapter(this,containers);
        lis.setAdapter(eq);
    }


    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
