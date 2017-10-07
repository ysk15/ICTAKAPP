package com.example.user.ictakapp;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
public class leavestatus extends Fragment {
    ListView tv;
    DatabaseReference df;
    Sqlite sq;
    ProgressDialog pg;
    ArrayAdapter<String>ad;
    ArrayList<String> arr;


    public leavestatus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_leavestatus, container, false);
        tv=(ListView)v.findViewById(R.id.tvstatus);
        arr = new ArrayList<>();
        ad = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arr);
        df= FirebaseDatabase.getInstance().getReference().child("leavedet");
        sq = new Sqlite(getActivity());
        pg =new ProgressDialog(getActivity());
        pg.show();
        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    leavedet ld = ds.getValue(leavedet.class);
                    Log.e("leaveview",ld.getEmpl());
                    if(ld.getEmpl().equals(sq.CheckLogin()[0])){
                        String det = "EMPLOYEE ID: "+ld.getEmpl()
                                +"\nFROM DATE: "+ld.getFdate()
                                +"\nTO DATE: "+ld.getTodate()
                                +"\n NO OF DAYS: "+ld.getNoofdays()
                                +"\n APPROOVAL STATUS: "+ld.getStatus();
                        arr.add(det);
                    }


                }
                pg.dismiss();
                tv.setAdapter(ad);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                pg.dismiss();

            }
        });
        return  v;
    }

}
