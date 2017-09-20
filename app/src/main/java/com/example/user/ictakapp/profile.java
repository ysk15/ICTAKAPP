package com.example.user.ictakapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class profile extends Fragment {
    DatabaseReference df;
    ImageView img;
    TextView tv;
    Sqlite sq;


    public profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);
        df = FirebaseDatabase.getInstance().getReference().child("registerdet");
        img=(ImageView)v.findViewById(R.id.imageView3);
        tv = (TextView)v.findViewById(R.id.tvprofile);
        sq = new Sqlite(getActivity());
        if(sq.mtd(sq.getReadableDatabase(),"tbuser")){
            if(!sq.CheckLogin()[0].equals("")){
                df.child(sq.CheckLogin()[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                     registerdet rd = dataSnapshot.getValue(registerdet.class);
                        String det = "NAME: "+rd.getName()
                                +"\nEMAIL: "+rd.getEmail()
                                +"\nPHONE: "+rd.getNum()
                                +"\nDESIGNATION: "+rd.getDesig()
                                +"DEPARTMENT: "+rd.getDept();
                        tv.setText(det);
                        Picasso.with(getActivity())
                                .load(rd.getImg())
                                .placeholder(R.drawable.user)
                                .error(R.drawable.user)
                                .into(img);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
        return  v;
    }

}
