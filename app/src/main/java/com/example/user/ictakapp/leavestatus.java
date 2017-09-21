package com.example.user.ictakapp;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class leavestatus extends Fragment {
    TextView tv;
    DatabaseReference df;
    Sqlite sq;
    ProgressDialog pg;


    public leavestatus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_leavestatus, container, false);
        tv=(TextView)v.findViewById(R.id.tvstatus);
        df= FirebaseDatabase.getInstance().getReference().child("leavedet");
        sq = new Sqlite(getActivity());
        pg =new ProgressDialog(getActivity());
        pg.show();
        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pg.dismiss();
                if(dataSnapshot.hasChild(sq.CheckLogin()[0])){
                    leavedet ld = dataSnapshot.child(sq.CheckLogin()[0]).getValue(leavedet.class);
                    String det = "EMPLOYEE ID: "+ld.getEmpl()
                            +"\nFROM DATE: "+ld.getFdate()
                            +"\nTO DATE: "+ld.getTodate()
                            +"\n NO OF DAYS: "+ld.getNoofdays()
                            +"\n APPROOVAL STATUS: "+ld.getStatus();
                    if(ld.getStatus().equals("ON REQUEST")){
                        tv.setTextColor(Color.parseColor("#ffe500"));
                    tv.setText(det);
                    }
                  else  if(ld.getStatus().equals("APPROOVED")){
                        tv.setTextColor(Color.GREEN);
                        tv.setText(det);
                    }
                  else  if(ld.getStatus().equals("REJECTED")){
                        tv.setTextColor(Color.RED);
                        tv.setText(det);
                    }
                }
                else {
                    Toast.makeText(getActivity(), "NO LEAVE APPLIED", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                pg.dismiss();

            }
        });
        return  v;
    }

}
