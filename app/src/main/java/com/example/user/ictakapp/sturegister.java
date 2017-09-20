package com.example.user.ictakapp;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class sturegister extends AppCompatActivity {
    EditText name,dob,email,pass,college,branch,sem,num;
    String sname,sdob,semail,spass,scollege,sbranch,ssem,snum;
    Button bu;
    DatabaseReference df;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.fragment_sturegister);
        name=(EditText)findViewById(R.id.sname);
        dob=(EditText)findViewById(R.id.sdob);
        email=(EditText)findViewById(R.id.semail);
        pass=(EditText)findViewById(R.id.spass);
        college=(EditText)findViewById(R.id.scoll);
        branch=(EditText)findViewById(R.id.sbranch);
        sem=(EditText)findViewById(R.id.ssem);
        num=(EditText)findViewById(R.id.snum);
        bu=(Button)findViewById(R.id.button4);
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
                    Toast.makeText(sturegister.this, "Fields Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerdet sd = new registerdet();
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
                            Toast.makeText(sturegister.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(sturegister.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
    }




}
