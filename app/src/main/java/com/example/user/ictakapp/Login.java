package com.example.user.ictakapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText uname,pass;
    Button log,reg;
    String usr,pwd;
    DatabaseReference df;
    Sqlite sq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uname=(EditText)findViewById(R.id.login);
        pass=(EditText)findViewById(R.id.password);
        log= (Button)findViewById(R.id.button5);
        reg= (Button)findViewById(R.id.button6);
        sq = new Sqlite(Login.this);
        df = FirebaseDatabase.getInstance().getReference().child("registerdet");
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, sturegister.class));
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr = uname.getText().toString();
                pwd = pass.getText().toString();
                if(usr.equals("")||pwd.equals("")){
                    Toast.makeText(Login.this, "Fields Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    df.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(usr)){
                                registerdet emp = dataSnapshot.child(usr).getValue(registerdet.class);
                                if(emp.getPass().equals(pwd)) {
                                    if (emp.getType().equals("employee")) {
                                        if(sq.mtd(sq.getWritableDatabase(),"tbuser")){
                                            sq.delete();
                                            sq.userinsert(usr);
                                        }
                                        startActivity(new Intent(Login.this, Employeehome.class));
                                    }
                                    else if (emp.getType().equals("student")) {
                                        if(sq.mtd(sq.getWritableDatabase(),"tbuser")){
                                            sq.delete();
                                            sq.userinsert(usr);
                                        }
                                        startActivity(new Intent(Login.this, Studenthome.class));
                                    }
                                    else if (emp.getType().equals("member")) {
                                        if(sq.mtd(sq.getWritableDatabase(),"tbuser")){
                                            sq.delete();
                                            sq.userinsert(usr);
                                        }
                                        startActivity(new Intent(Login.this, Memberhome.class));
                                    }
                                    else if (emp.getType().equals("admin")) {
                                        if(sq.mtd(sq.getWritableDatabase(),"tbuser")){
                                            sq.delete();
                                            sq.userinsert(usr);
                                        }
                                        startActivity(new Intent(Login.this, Adminhome.class));
                                    }
                                }
                                else {
                                    Toast.makeText(Login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(Login.this, "Incorrect Username", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        });

    }
}
