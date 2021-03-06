package com.example.user.ictakapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.vistrav.ask.Ask;
import com.vistrav.ask.annotations.AskGranted;

public class Splash extends AppCompatActivity {
    int ok= PackageManager.PERMISSION_GRANTED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int storage = ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int call = ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CALL_PHONE);
        if(storage==ok&&call==ok){
            startActivity(new Intent(Splash.this,Login.class));
        }
        else {
            Ask.on(this)
                    .id(1234567)
                    .forPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                            , android.Manifest.permission.READ_EXTERNAL_STORAGE
                            , Manifest.permission.CALL_PHONE)
                    .go();
        }


       /* df = FirebaseDatabase.getInstance().getReference().child("registerdet");
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
        });*/


    }

    @AskGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void storageGranted(int id) {


    }

    @AskGranted(Manifest.permission.CALL_PHONE)
    public void callGranted(int id) {
        startActivity(new Intent(Splash.this,Login.class));

    }

}
