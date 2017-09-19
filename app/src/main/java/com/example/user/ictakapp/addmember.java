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


/**
 * A simple {@link Fragment} subclass.
 */
public class addmember extends Fragment {
    EditText name,email,pass,num;
    Button bu;
    String mname,memail,mpass,mnum;
    DatabaseReference df;


    public addmember() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addmember, container, false);
        name=(EditText)v.findViewById(R.id.mname);
        email=(EditText)v.findViewById(R.id.email);
        pass=(EditText)v.findViewById(R.id.mpass);
        bu =(Button)v.findViewById(R.id.button3);
        df = FirebaseDatabase.getInstance().getReference().child("registerdet");
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mname = name.getText().toString();
                memail=email.getText().toString();
                mpass=pass.getText().toString();
                mnum=num.getText().toString();
                if(name.equals("")||memail.equals("")||mpass.equals("")||mnum.equals("")){
                   memeberdet md = new memeberdet();
                    md.setName(mname);
                    md.setEmail(memail);
                    md.setPassword(mpass);
                    md.setNumber(mnum);
                    md.setType("member");
                    df.child(memail).setValue(md).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), "Member Added", Toast.LENGTH_SHORT).show();
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
