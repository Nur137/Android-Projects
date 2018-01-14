package com.example.nur.monglaport.UI;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nur.monglaport.Class.Config;
import com.example.nur.monglaport.Class.Container;
import com.example.nur.monglaport.Class.Equips;
import com.example.nur.monglaport.Class.Teleph;
import com.example.nur.monglaport.Class.TelephoneArrayAdapter;
import com.example.nur.monglaport.Local_Database.Containersdb;
import com.example.nur.monglaport.Local_Database.Equipmentdb;
import com.example.nur.monglaport.Local_Database.Telephonedb;
import com.example.nur.monglaport.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Telephone_dir extends AppCompatActivity {
    ListView listView;
    Firebase firebase;
    private static final String HTML_TAG_PATTERN = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
    ArrayList<Teleph> telephs;
    Button sb;
    int i;
    String res;
    ArrayList<String> str,tel,em;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephone_dir);
        listView=(ListView)findViewById(R.id.listv);
        firebase=new Firebase(Config.FIREBASE_URL);
        telephs=new ArrayList<Teleph>();
        str=new ArrayList<String>();
        tel=new ArrayList<String>();
        em=new ArrayList<String>();
        Telephonedb telephonedb=new Telephonedb(Telephone_dir.this);
        try {
            telephonedb.open();
            telephs=telephonedb.getData();
            telephonedb.close();
            set_list();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        if(!isInternetConnected(Telephone_dir.this))
        {
            Toast.makeText(Telephone_dir.this,"You are seeing offline data",Toast.LENGTH_SHORT).show();
        }
        else
            new doit().execute();


    }


    public class doit extends AsyncTask<Void, Void, Void> {
        String words = new String();

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Document doc = Jsoup.connect("http://www.mpa.gov.bd/site/view/officer_list/%E0%A6%95%E0%A6%B0%E0%A7%8D%E0%A6%AE%E0%A6%95%E0%A6%B0%E0%A7%8D%E0%A6%A4%E0%A6%BE%E0%A6%AC%E0%A7%83%E0%A6%A8%E0%A7%8D%E0%A6%A6").get();
                Elements elements=doc.select("div[id*=without-pic");

                for (Element x : elements) {
                    words += x.getElementsByTag("tbody");
                }
                words = words.replace("&amp;", "&");
                words = words.replace("&nbsp;", "N/A");
                 for (i = 0; i < words.length(); i++) {
                    String x = new String();

                    if (words.charAt(i) == '<' && words.charAt(i + 1) == 't' && words.charAt(i + 2) == 'd') {

                        while(words.charAt(i)!='>')i++;

                        while (!(words.charAt(i) == '<' && words.charAt(i+1) == '/' && words.charAt(i+2) == 't')) {
                            res += words.charAt(i);
                            x += words.charAt(i);
                            i++;
                        }
                        x = x.replace("<p>", "");

                        x = x.replace("</p>", "");

                        x = x.replace(">", "");

                        x = x.replace(HTML_TAG_PATTERN, "");

                        str.add(x);
                    }


                }

                Telephonedb telephonedb=new Telephonedb(Telephone_dir.this);
                try {
                    telephonedb.open();
                    telephonedb.delete_all();
                    telephonedb.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                for (i = 0; i < str.size()-7; i+=10) {

                    Teleph teleph = new Teleph();
                    teleph.setSlno(str.get(i));
                    teleph.setDesignation(str.get(i+2));
                    teleph.setTel(str.get(i+4));
                    teleph.setEmail(str.get(i+8));

                    Telephonedb telephonedb1 = new Telephonedb(Telephone_dir.this);
                    try {
                        telephonedb1.open();
                        telephonedb1.createEntry(str.get(i), str.get(i + 2), str.get(i + 4), str.get(i + 8));
                        telephonedb1.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                    telephs.add(teleph);
                }

                } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
            Toast.makeText(Telephone_dir.this, "Data updated", Toast.LENGTH_SHORT).show();
            Telephonedb telephonedb=new Telephonedb(Telephone_dir.this);
            try {
                telephonedb.open();
                telephs=telephonedb.getData();
                telephonedb.close();
                set_list();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void set_list()
    {
        TelephoneArrayAdapter telephoneArrayAdapter=new TelephoneArrayAdapter(this,telephs);
        listView.setAdapter(telephoneArrayAdapter);

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
//            Intent in = new Intent(Telephone_dir.this, Front.class);
//            startActivity(in);
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }


}
