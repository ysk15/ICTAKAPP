package com.example.user.ictakapp;


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


public class sturegister extends Fragment {
    EditText name,dob,email,pass,college,branch,sem,num;
    String sname,sdob,semail,spass,scollege,sbranch,ssem,snum;
    Button bu;
    DatabaseReference df;


    public sturegister() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_sturegister, container, false);
        name=(EditText)v.findViewById(R.id.sname);
        dob=(EditText)v.findViewById(R.id.sdob);
        email=(EditText)v.findViewById(R.id.semail);
        pass=(EditText)v.findViewById(R.id.spass);
        college=(EditText)v.findViewById(R.id.scoll);
        branch=(EditText)v.findViewById(R.id.sbranch);
        sem=(EditText)v.findViewById(R.id.ssem);
        num=(EditText)v.findViewById(R.id.snum);
        bu=(Button)v.findViewById(R.id.button4);
        df = FirebaseDatabase.getInstance().getReference().child("registerdet");
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sname=name.getText().toString();
                sdob=dob.getText().toString();
                semail=email.getText().toString();
                spass=pass.getText().toString();
                scollege=college.getText().toString();
                sbranch=branch.getText().toString();
                ssem=sem.getText().toString();
                snum=num.getText().toString();
                if(sname.equals("")||sdob.equals("")||semail.equals("")||spass.equals("")||scollege.equals("")||sbranch.equals("")||ssem.equals("")||snum.equals("")){
                    Toast.makeText(getActivity(), "Fields Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    studentdet sd = new studentdet();
                    sd.setName(sname);
                    sd.setDob(sdob);
                    sd.setType("student");
                    sd.setBranch(sbranch);
                    sd.setSem(ssem);
                    sd.setName(snum);
                    sd.setCollege(scollege);
                    sd.setEmail(semail);
                    sd.setPass(spass);

                 df.child(semail).setValue(sd).addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                         Toast.makeText(getActivity(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                     }
                 });

                }

            }
        });




        return  v;
    }

}
