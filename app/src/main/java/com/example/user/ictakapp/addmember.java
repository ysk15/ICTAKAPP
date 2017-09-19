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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
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
    ProgressDialog pg;


    public addmember() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addmember, container, false);
        name=(EditText)v.findViewById(R.id.mname);
        email=(EditText)v.findViewById(R.id.email);
        pass=(EditText)v.findViewById(R.id.mpass);
        num=(EditText)v.findViewById(R.id.mnum);
        bu =(Button)v.findViewById(R.id.button3);
        pg=new ProgressDialog(getActivity());
        df = FirebaseDatabase.getInstance().getReference().child("registerdet");
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pg.show();
                mname = name.getText().toString();
                memail=email.getText().toString();
                mpass=pass.getText().toString();
                mnum=num.getText().toString();
                if(name.equals("")||memail.equals("")||mpass.equals("")||mnum.equals("")){
                    pg.dismiss();
                    Toast.makeText(getActivity(), "Fields Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    memeberdet md = new memeberdet();
                    md.setName(mname);
                    md.setEmail(memail);
                    md.setPassword(mpass);
                    md.setNumber(mnum);
                    md.setType("member");
                    df.child(memail).setValue(md).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            pg.dismiss();
                            if(task.isSuccessful()){
                                Toast.makeText(getActivity(), "Succesfully added", Toast.LENGTH_SHORT).show();
                            }
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
