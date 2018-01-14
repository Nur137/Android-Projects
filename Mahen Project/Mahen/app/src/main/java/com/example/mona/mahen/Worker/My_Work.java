package com.example.mona.mahen.Worker;


        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.preference.PreferenceManager;
        import android.support.v7.app.AppCompatActivity;
        import android.view.KeyEvent;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ListView;

        import com.example.mona.mahen.Class.Config;
        import com.example.mona.mahen.Class.WorkUnderAdapter;
        import com.example.mona.mahen.Database.Work_Under;
        import com.example.mona.mahen.R;
        import com.firebase.client.ChildEventListener;
        import com.firebase.client.DataSnapshot;
        import com.firebase.client.Firebase;
        import com.firebase.client.FirebaseError;
        import com.firebase.client.ValueEventListener;

        import java.util.ArrayList;
        import java.util.Map;

        import butterknife.Bind;
        import butterknife.ButterKnife;

public class My_Work extends AppCompatActivity {
    SharedPreferences preferences;
    ArrayList<Work_Under> wu;
    Firebase firebase;
    String active;
    @Bind(R.id.list)
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__work);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        active = preferences.getString("wrkr_id", "");

        wu = new ArrayList<Work_Under>();
        firebase = new Firebase(Config.FIREBASE_URL);
        firebase.child("work_under").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                Map<String, Object> newPost = (Map<String, Object>) snapshot.getValue();
                String wrkr_id = String.valueOf(newPost.get("worker_id"));
                if (wrkr_id.equalsIgnoreCase(active)) {
                    Work_Under work_under = new Work_Under();
                    work_under.setU_id(String.valueOf(newPost.get("u_id")));
                    work_under.setWork_title(String.valueOf(newPost.get("work_title")));
                    work_under.setCust_id(String.valueOf(newPost.get("cust_id")));
                    work_under.setWorker_id(String.valueOf(newPost.get("worker_id")));
                    work_under.setWage(String.valueOf(newPost.get("wage")));
                    wu.add(work_under);
                }

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //Checking authorization when loading is done
        firebase.child("work_under").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                set_list_work();
            }

            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Work_Under wu=(Work_Under) parent.getItemAtPosition(position);

                Intent in= new Intent(My_Work.this,Work_Done.class);
                in.putExtra("w_id",wu.getWork_title());
                in.putExtra("u_id",wu.getU_id());
                in.putExtra("c_id",wu.getCust_id());
                in.putExtra("wage",wu.getWage());
                startActivity(in);
            }
        });



    }



    public void set_list_work() {
        WorkUnderAdapter woru = new WorkUnderAdapter(this, wu);
        list.setAdapter(woru);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
            Intent intent = new Intent(getApplicationContext(),Main.class);
            startActivity(intent);
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

}