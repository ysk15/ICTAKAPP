package com.example.user.ictakapp;


import android.app.ProgressDialog;
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
public class viewapplicants extends Fragment {
    ListView lv;
    ProgressDialog pg;
    DatabaseReference df;
    ArrayList<String> arr;
    ArrayAdapter<String> ad;


    public viewapplicants() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_viewapplicants, container, false);
        pg = new ProgressDialog(getActivity());
        arr =new ArrayList<>();
        lv=(ListView)v.findViewById(R.id.lv);
        ad = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arr);
        df= FirebaseDatabase.getInstance().getReference().child("courseapply");
        pg.show();
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    pg.dismiss();
                    courseapplydet ca =ds.getValue(courseapplydet.class);
                    String data = "STUDENT NAME: "+ca.getName()
                            +"\n"+ca.getCname()
                            +"\nDURATION: "+ca.getCduration()
                            +"\nCOLLEGE NAME: "+ca.getColleage()
                            +"\nDEPARTMENT : "+ca.getBranch()
                            +"\nSEMESTER: "+ca.getSemester();
                    arr.add(data);

                }
                lv.setAdapter(ad);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                pg.dismiss();
                Toast.makeText(getActivity(), "ERROR LOADING LIST", Toast.LENGTH_SHORT).show();

            }
        });

        return  v;
    }

}
