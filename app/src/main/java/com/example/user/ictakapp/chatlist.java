package com.example.user.ictakapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class chatlist extends Fragment {
    ListView lv;
    ArrayList<String> arr,arr1;
    ArrayAdapter<String> ad;
    DatabaseReference df;
    public static String empname;


    public chatlist() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_chatlist, container, false);
        lv=(ListView)v.findViewById(R.id.lv);
        arr = new ArrayList<>();
        arr1 = new ArrayList<>();
         ad = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arr);
        df = FirebaseDatabase.getInstance().getReference().child("registerdet");
        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    registerdet ld = ds.getValue(registerdet.class);
                    if(ld.getType().equals("employee")){
                        arr1.add(ld.getEmail());
                        arr.add(ld.getName());

                    }
                }
                lv.setAdapter(ad);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                empname=arr1.get(position);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.adminframe,new chatadmin()).commit();
            }
        });


        return  v;
    }

}
