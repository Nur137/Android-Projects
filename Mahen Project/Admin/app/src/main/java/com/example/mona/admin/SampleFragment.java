package com.example.mona.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class SampleFragment extends Fragment {
    Firebase firebase;
    ArrayList<User> custs,workers;
    String Id_no;
    ListView lvc,lvw;

    private static final String ARG_POSITION = "position";

    private int position;

    private android.widget.Button b1;

    public static SampleFragment newInstance(int position) {
        SampleFragment f = new SampleFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        position = getArguments().getInt(ARG_POSITION);
        View rootView = inflater.inflate(R.layout.page, container, false);
        custs=new ArrayList<User>();
        workers=new ArrayList<User>();
        firebase=new Firebase(Config.FIREBASE_URL);







        switch (position) {
            case 0:
                rootView = inflater.inflate(R.layout.customer_appr, container, false);
                 firebase=new Firebase(Config.FIREBASE_URL);

               lvc=(ListView) rootView.findViewById(R.id.lv_cust);
                final ProgressBar pb=(ProgressBar) rootView.findViewById(R.id.pb);


                firebase.child("Admin").child("Not Approved").child("customer").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                        Map<String,Object> newPost=(Map<String,Object>)snapshot.getValue();
                        String name = String.valueOf(newPost.get("full_name"));
                        String un = String.valueOf(newPost.get("un"));
                        String email = String.valueOf(newPost.get("email"));
                        String pass = String.valueOf(newPost.get("pass"));
                        String acttp = String.valueOf(newPost.get("type"));
                        String b_day = String.valueOf(newPost.get("birth_date"));
                        String sex = String.valueOf(newPost.get("sex"));
                        String idno= String.valueOf(newPost.get("id"));
                        String pp = String.valueOf(newPost.get("propic"));
                        String address= String.valueOf(newPost.get("address"));
                        String contact= String.valueOf(newPost.get("contact"));
                        String nationality= String.valueOf(newPost.get("nationality"));


                        User user=new User();
                        user.setFull_name(name);
                        user.setUn(un);
                        user.setEmail(email);
                        user.setPass(pass);
                        user.setType(acttp);
                        user.setBirth_date(b_day);
                        user.setSex(sex);
                        user.setID(idno);
                        user.setPropic(pp);
                        user.setNationality(nationality);
                        user.setContact(contact);
                        user.setAddress(address);
                        custs.add(user);
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
                firebase.child("Admin").child("Not Approved").child("customer").addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        pb.setVisibility(View.INVISIBLE);
                        try{
                            if(custs.size()==0) Toast.makeText(getActivity(),"No customers to approve",Toast.LENGTH_SHORT).show();
                            else
                                set_list_cust();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    public void onCancelled(FirebaseError firebaseError) { }
                });




                lvc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final User user=(User)parent.getItemAtPosition(position);


                        firebase.child("Admin").child("Not Approved").child("customer").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                                Map<String,Object> newPost=(Map<String,Object>)snapshot.getValue();
                                String fn = String.valueOf(newPost.get("full_name"));
                                if(fn.equalsIgnoreCase(user.getFull_name()))
                                {

                                    Id_no=snapshot.getKey();
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
                        firebase.child("Admin").child("Not Approved").child("customer").addListenerForSingleValueEvent(new ValueEventListener() {
                            public void onDataChange(DataSnapshot dataSnapshot) {



                                //Toast.makeText(getApplicationContext(), "List Item Value: "+patient, Toast.LENGTH_LONG).show();
                                Intent clnt = new Intent(getActivity(),Details.class);
                                clnt.putExtra("full_name_user", user.getFull_name());
                                clnt.putExtra("user_name_user", user.getUn());
                                clnt.putExtra("email_user", user.getEmail());
                                clnt.putExtra("pass_user", user.getPass());
                                clnt.putExtra("type_user", "customer");
                                clnt.putExtra("bday_user", user.getBirth_date());
                                clnt.putExtra("sex_user", user.getSex());
                                clnt.putExtra("idno_user", user.getID());
                                clnt.putExtra("address",user.getAddress());
                                clnt.putExtra("contact",user.getContact());
                                clnt.putExtra("nationality",user.getNationality());
                                clnt.putExtra("acttype_user", user.getType());
                                //   clnt.putExtra("prop", user.getPropic());
                                clnt.putExtra("id", Id_no);
                                startActivity(clnt);



                            }
                            public void onCancelled(FirebaseError firebaseError) { }
                        });

                    }
                });








                break;
            case 1:
                rootView = inflater.inflate(R.layout.worker_appr, container, false);
                firebase=new Firebase(Config.FIREBASE_URL);
                    lvw=(ListView) rootView.findViewById(R.id.lv_worker);
                final ProgressBar pb1=(ProgressBar) rootView.findViewById(R.id.pb);


                firebase.child("Admin").child("Not Approved").child("worker").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                        Map<String,Object> newPost=(Map<String,Object>)snapshot.getValue();
                        String name = String.valueOf(newPost.get("full_name"));
                        String un = String.valueOf(newPost.get("un"));
                        String email = String.valueOf(newPost.get("email"));
                        String pass = String.valueOf(newPost.get("pass"));
                        String acttp = String.valueOf(newPost.get("type"));
                        String b_day = String.valueOf(newPost.get("birth_date"));
                        String sex = String.valueOf(newPost.get("sex"));
                        String idno= String.valueOf(newPost.get("id"));
                        String pp = String.valueOf(newPost.get("propic"));
                        String address= String.valueOf(newPost.get("address"));
                        String contact= String.valueOf(newPost.get("contact"));
                        String nationality= String.valueOf(newPost.get("nationality"));
                        String idpic=String.valueOf(newPost.get("idpic"));

                        User user=new User();
                        user.setFull_name(name);
                        user.setUn(un);
                        user.setEmail(email);
                        user.setPass(pass);
                        user.setType(acttp);
                        user.setBirth_date(b_day);
                        user.setSex(sex);
                        user.setID(idno);
                        user.setPropic(pp);
                        user.setIdpic(idpic);
                        user.setNationality(nationality);
                        user.setContact(contact);
                        user.setAddress(address);
                        workers.add(user);
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
                firebase.child("Admin").child("Not Approved").child("worker").addListenerForSingleValueEvent(new ValueEventListener() {
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        pb1.setVisibility(View.INVISIBLE);
                        try{

                            if(workers.size()==0) Toast.makeText(getActivity(),"No workers to approve",Toast.LENGTH_SHORT).show();
                            else
                                set_list_worker();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    public void onCancelled(FirebaseError firebaseError) { }
                });

                lvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        final User user=(User)parent.getItemAtPosition(position);


                        firebase.child("Admin").child("Not Approved").child("worker").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                                Map<String,Object> newPost=(Map<String,Object>)snapshot.getValue();
                                String fn = String.valueOf(newPost.get("full_name"));
                                if(fn.equalsIgnoreCase(user.getFull_name()))
                                {

                                    Id_no=snapshot.getKey();
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
                        firebase.child("Admin").child("Not Approved").child("worker").addListenerForSingleValueEvent(new ValueEventListener() {
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Intent wrkr = new Intent(getActivity(),Details_Worker.class);
                                wrkr.putExtra("full_name_user", user.getFull_name());
                                wrkr.putExtra("user_name_user", user.getUn());
                                wrkr.putExtra("email_user", user.getEmail());
                                wrkr.putExtra("pass_user", user.getPass());
                                wrkr.putExtra("type_user", "worker");
                                wrkr.putExtra("bday_user", user.getBirth_date());
                                wrkr.putExtra("sex_user", user.getSex());
                                wrkr.putExtra("idno_user", user.getID());
                                wrkr.putExtra("address",user.getAddress());
                                wrkr.putExtra("contact",user.getContact());
                               // wrkr.putExtra("idpic",user.getIdpic());
                                wrkr.putExtra("nationality",user.getNationality());
                                wrkr.putExtra("acttype_user", user.getType());
                                //     wrkr.putExtra("prop", user.getPropic());
                                wrkr.putExtra("id", Id_no);
                                startActivity(wrkr);



                            }
                            public void onCancelled(FirebaseError firebaseError) { }
                        });

                        //Toast.makeText(getApplicationContext(), "List Item Value: "+patient, Toast.LENGTH_LONG).show();

                    }
                });



                break;

        }




        return rootView;
    }

    public void set_list_cust(){
        Pending_list pl = new Pending_list(getActivity(), custs);
        lvc.setAdapter(pl);
    }

    public void set_list_worker(){
        Pending_list wr = new Pending_list(getActivity(), workers);
        lvw.setAdapter(wr);

    }
}

