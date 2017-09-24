package com.example.user.ictakapp;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class yellowcard extends Fragment {
    ArrayList<String> arr;
    ArrayAdapter<String> ad;
    DatabaseReference df;
    ArrayList<String> arr1;
    ListView lv;
    ProgressDialog pg;
    AutoCompleteTextView atv;
    Button search;
    ArrayList<String> sarr;
    ArrayAdapter<String> sad;
    DatabaseReference ds;


    public yellowcard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_yellowcard, container, false);
        arr = new ArrayList<>();
        arr1 = new ArrayList<>();
        lv =(ListView)v.findViewById(R.id.lvemp);
        pg = new ProgressDialog(getActivity());
        atv=(AutoCompleteTextView)v.findViewById(R.id.emptv);
        search=(Button)v.findViewById(R.id.empsearch);
        sarr = new ArrayList<>();
        sad = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,sarr);
         ad = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arr);
        atv.setThreshold(1);
        ds = FirebaseDatabase.getInstance().getReference().child("registerdet");
        df= FirebaseDatabase.getInstance().getReference().child("registerdet");
        pg.show();
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arr.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    registerdet em = ds.getValue(registerdet.class);
                    String details = "EMPLOYEE NAME: "+em.getName()
                            +"\nEMAIL:"+em.getEmail()
                            +"\nCONTACT NO: "+em.getNum();
                    if(em.getType().equals("employee")){
                        arr.add(details);
                        arr1.add(em.getNum());
                        sarr.add(em.getName());
                    }

                }
                lv.setAdapter(ad);
                atv.setAdapter(sad);
                pg.cancel();
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
                pg.cancel();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s= atv.getText().toString();
                if(!s.equals("")){
                    ds.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            arr.clear();
                            for (DataSnapshot ds:dataSnapshot.getChildren()) {
                                registerdet em = ds.getValue(registerdet.class);
                                String details = "EMPLOYEE NAME: "+em.getName()
                                        +"\nEMAIL:"+em.getEmail()
                                        +"\nCONTACT NO: "+em.getNum();
                                if(em.getType().equals("employee")&&em.getName().equals(atv.getText().toString())){
                                    arr.add(details);
                                    arr1.add(em.getNum());
                                }

                            }
                            lv.setAdapter(ad);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                else if(s.equals("")){
                    pg.show();
                    df.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            arr.clear();
                            for (DataSnapshot ds:dataSnapshot.getChildren()) {
                                registerdet em = ds.getValue(registerdet.class);
                                String details = "EMPLOYEE NAME: "+em.getName()
                                        +"\nEMAIL:"+em.getEmail()
                                        +"\nCONTACT NO: "+em.getNum();
                                if(em.getType().equals("employee")){
                                    arr.add(details);
                                    arr1.add(em.getNum());
                                    sarr.add(em.getName());
                                }

                            }
                            lv.setAdapter(ad);
                            atv.setAdapter(sad);
                            pg.cancel();
                        }



                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
                            pg.cancel();
                        }
                    });
                }
                atv.setText("");
            }
        });
        search.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                pg.show();
                df.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        arr.clear();
                        for (DataSnapshot ds:dataSnapshot.getChildren()) {
                            registerdet em = ds.getValue(registerdet.class);
                            String details = "EMPLOYEE NAME: "+em.getName()
                                    +"\nEMAIL:"+em.getEmail()
                                    +"\nCONTACT NO: "+em.getNum();
                            if(em.getType().equals("employee")){
                                arr.add(details);
                                arr1.add(em.getNum());
                                sarr.add(em.getName());
                            }

                        }
                        lv.setAdapter(ad);
                        atv.setAdapter(sad);
                        pg.cancel();
                    }



                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
                        pg.cancel();
                    }
                });
                atv.setText("");
                return true;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final  String number = arr1.get(position);
                AlertDialog.Builder bu = new AlertDialog.Builder(getActivity());
                bu.setTitle("CALL?");
                bu.setPositiveButton("CALL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri call = Uri.parse("tel:" + number);
                        Intent surf = new Intent(Intent.ACTION_CALL, call);
                        startActivity(surf);

                    }
                });

                bu.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                bu.show();
            }
        });


        return  v;
    }

}
