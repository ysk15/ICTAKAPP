package com.example.user.ictakapp;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vistrav.ask.Ask;

public class Splash extends AppCompatActivity {
    DatabaseReference df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Ask.on(Splash.this)
                .id(1234567)
                .forPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , android.Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.CALL_PHONE)
                .go();
        df = FirebaseDatabase.getInstance().getReference().child("registerdet");
        registerdet rdet = new registerdet();
        rdet.setName("admin");
        rdet.setEmail("a");
        rdet.setPass("a");
        rdet.setType("admin");
        df.child("a").setValue(rdet).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(Splash.this,Login.class));
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }
}
