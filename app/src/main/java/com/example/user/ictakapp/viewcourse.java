package com.example.user.ictakapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class viewcourse extends Fragment {
    ListView lv;
    DatabaseReference df;
    ArrayList<String> arr;
    ArrayAdapter<String> ad;


    public viewcourse() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_viewcourse, container, false);
        df = FirebaseDatabase.getInstance().getReference().child("coursedet");
        lv = (ListView)v.findViewById(R.id.listView);
        arr = new ArrayList<>();
        ad = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arr);
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              for(DataSnapshot ds:dataSnapshot.getChildren()){
                  coursedet cd = ds.getValue(coursedet.class) ;
                  String details = "COURSE NAME:"+cd.getName()
                          +"\nDESCRIPTION"+cd.getDesc()
                          +"DURATION"+cd.getDuration();
                  arr.add(details);
              }
                lv.setAdapter(ad);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Network Problem", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

}
