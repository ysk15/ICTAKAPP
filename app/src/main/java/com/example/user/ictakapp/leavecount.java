package com.example.user.ictakapp;


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
public class leavecount extends Fragment {
    DatabaseReference db;
    ArrayAdapter<String> ad;
    ArrayList<String> arr,arremp;
    ArrayList<leavedet> arrdet;
    int days=0;
    String leavedates="";
    ListView lv;


    public leavecount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_leavecount, container, false);
        arr = new ArrayList<>();
        arremp = new ArrayList<>();
        arrdet = new ArrayList<>();
        lv=(ListView)v.findViewById(R.id.lv);
        ad = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arr);
        db=FirebaseDatabase.getInstance().getReference();
        DatabaseReference ds = db.child("registerdet");
        ds.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    registerdet ld = ds.getValue(registerdet.class);
                    if(ld.getType().equals("employee")){
                      arremp.add(ld.getName());
                        Log.e("employee",ld.getName());
                    }
                }

                DatabaseReference df = db.child("leavedet");
                df.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds:dataSnapshot.getChildren()) {
                            leavedet ld = ds.getValue(leavedet.class);
                            Log.e("leavedet",ld.getEmpl());
                            arrdet.add(ld);
                        }

                        arr.clear();
                        for (int i = 0; i < arremp.size(); i++) {
                            for (int i1 = 0; i1 < arrdet.size(); i1++) {
                                if(arremp.get(i).equals(arrdet.get(i1).getEmpl())){
                                    days = Integer.valueOf(arrdet.get(i1).getNoofdays())+days;
                                    leavedates = arrdet.get(i1).getFdate()+" - "+arrdet.get(i1).getTodate()+" , "+leavedates;
                                    arr.add("Employee Name: "+arremp.get(i)+
                                            "\nTotal No of Days: "+""+days+
                                            "\nLeave Dates: "+leavedates);
                                    Log.e("leavedetinif",arremp.get(i));
                                }
                                Log.e("leavedetin",arremp.get(i));
                            }
                            Log.e("leavedetout",arremp.get(i));
                        }
                        lv.setAdapter(ad);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return  v;
    }

}
