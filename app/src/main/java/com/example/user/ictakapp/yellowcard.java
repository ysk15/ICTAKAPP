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
         ad = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arr);
        df= FirebaseDatabase.getInstance().getReference().child("registerdet");
        pg.show();
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    registerdet em = ds.getValue(registerdet.class);
                    String details = "EMPLOYEE NAME: "+em.getName()
                            +"\nEMAIL:"+em.getEmail()
                            +"\nCONTACT NO: "+em.getNum();
                    if(em.getType().equals("employee")){
                        arr.add(details);
                        arr1.add(em.getNum());
                    }

                }
                lv.setAdapter(ad);
                pg.cancel();
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
                pg.cancel();
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
