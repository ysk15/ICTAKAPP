package com.example.user.ictakapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class viewleaverequest extends Fragment {
    ListView lv;
    DatabaseReference df;
    ArrayAdapter<String> ad;
    ArrayList<String> arr;

    public viewleaverequest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_viewleaverequest, container, false);
         lv = (ListView)v.findViewById(R.id.lvleave);
        df = FirebaseDatabase.getInstance().getReference().child("leavedet");
        arr = new ArrayList<>();
         ad = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arr);
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    leavedet ld = ds.getValue(leavedet.class);
                    String det = "EMPLOYEE ID:"+ld.getEmpl()
                            +"\nFROM DATE: "+ld.getFdate()
                            +"\nTO DATE: "+ld.getTodate();
                    arr.add(det);




                }
                lv.setAdapter(ad);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


         return  v;
    }

}
