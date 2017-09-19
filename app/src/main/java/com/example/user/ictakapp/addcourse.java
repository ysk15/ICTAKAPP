package com.example.user.ictakapp;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class addcourse extends Fragment {
    EditText cname,cduration,cdesp;
    String name,duration,desp;
    Button bu;
    DatabaseReference df;
    ProgressDialog pg;
    public addcourse() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_addcourse, container, false);
        cname=(EditText)v.findViewById(R.id.cname);
        cduration=(EditText)v.findViewById(R.id.duration);
        cdesp=(EditText)v.findViewById(R.id.cdesc);
        bu =(Button)v.findViewById(R.id.button2);
        pg = new ProgressDialog(getActivity());
        df = FirebaseDatabase.getInstance().getReference().child("coursedet");
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = cname.getText().toString();
                duration=cduration.getText().toString();
                desp=cdesp.getText().toString();
                if(name.equals("")||duration.equals("")||cdesp.equals("")) {
                    Toast.makeText(getActivity(), "Fields Empty", Toast.LENGTH_SHORT).show();

                }
                else {
                    pg.show();
                    coursedet cd = new coursedet();
                    cd.setName(name);
                    cd.setDesc(desp);
                    cd.setDuration(duration);
                    df.child(name).setValue(cd).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pg.dismiss();
                            Toast.makeText(getActivity(), "Course Added", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pg.dismiss();
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return  v;
    }

}
