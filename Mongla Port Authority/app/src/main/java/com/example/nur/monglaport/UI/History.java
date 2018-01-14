package com.example.nur.monglaport.UI;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nur.monglaport.Local_Database.Historydb;
import com.example.nur.monglaport.R;
import com.firebase.client.Firebase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLException;

public class History extends AppCompatActivity {


    TextView tx;
    Firebase firebase;
    String res;
    private static final String HTML_TAG_PATTERN = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
    //SharedPreferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Firebase.setAndroidContext(this);

        firebase=new Firebase(com.example.nur.monglaport.Class.Config.FIREBASE_URL);

        tx=(TextView)findViewById(R.id.txt);
        res=new String();
        Historydb historydb=new Historydb(History.this);
        try {
            historydb.open();
            res=historydb.getData();
            tx.setText(res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(isInternetConnected(History.this)==false)
        {
            Toast.makeText(History.this,"You are seeing offline data",Toast.LENGTH_SHORT).show();

        }
        else

        new doit().execute();


    }


    public class doit extends AsyncTask<Void,Void,Void> {
        String words =new String();

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc= Jsoup.connect("http://www.mpa.gov.bd/site/page/2fa934e2-3601-4fa5-b877-bd9bd4f04aa4/history").get();
                Elements elements=doc.select("div[id*=printable_area");

                for (Element x : elements) {
                    words+= x.getElementsByTag("p");
                }

                words=words.replaceAll(HTML_TAG_PATTERN,"");
                Historydb historydb =new Historydb(History.this);
                try {
                    historydb.open();
                    historydb.createEntry(words);
                    historydb.close();
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
           // tx.setText(words);
            //firebase.child("History").setValue(words);
            Toast.makeText(History.this,"Data Updated",Toast.LENGTH_SHORT).show();
            Historydb historydb=new Historydb(History.this);
            try {
                historydb.open();
                res=historydb.getData();
                tx.setText(res);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }
    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
    //@Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            finish();
//            Intent in = new Intent(History.this, Front.class);
//            startActivity(in);
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }
}