package com.example.user.ictakapp;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class addemployee extends Fragment {
   EditText ename,eemail,epass,enumb,edept,edesig;
    String sename,seemail,sepass,senumb,sedept,sedesig,sgender,simage;
    RadioGroup rg;
    RadioButton rb;
    Button bu;
    FirebaseAuth auth;
    DatabaseReference df;
    StorageReference sf;
    ImageView iv;
    ProgressDialog pg;
    public addemployee() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rv =  inflater.inflate(R.layout.fragment_addemployee, container, false);
        ename=(EditText)rv.findViewById(R.id.empname);
        eemail=(EditText)rv.findViewById(R.id.empemail);
        epass=(EditText)rv.findViewById(R.id.empass);
        enumb=(EditText)rv.findViewById(R.id.empnumb);
        edept=(EditText)rv.findViewById(R.id.empdept);
        edesig=(EditText)rv.findViewById(R.id.empdesig);
        rg=(RadioGroup)rv.findViewById(R.id.emprg);
        bu=(Button)rv.findViewById(R.id.button);
        iv=(ImageView)rv.findViewById(R.id.imageView2);
        pg = new ProgressDialog(getActivity());
        iv.setDrawingCacheEnabled(true);
        iv.buildDrawingCache();
        auth = FirebaseAuth.getInstance();
        df = FirebaseDatabase.getInstance().getReference().child("registerdet");
        sf= FirebaseStorage.getInstance().getReference();
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "Select Picture"), 0);

            }
        });
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sename=ename.getText().toString();
                seemail=eemail.getText().toString();
                sepass=epass.getText().toString();
                senumb=enumb.getText().toString();
                sedept=edept.getText().toString();
                sedesig=edesig.getText().toString();
                rb=(RadioButton)rv.findViewById(rg.getCheckedRadioButtonId());
                sgender = rb.getText().toString();
                if(sgender.equals("")||sename.equals("")||seemail.equals("")||sepass.equals("")||senumb.equals("")||sedept.equals("")||sedesig.equals("")){
                    Toast.makeText(getActivity(), "Fields are empty", Toast.LENGTH_SHORT).show();
                }
                else if(simage.equals("")){
                    Toast.makeText(getActivity(), "Please Choose Image", Toast.LENGTH_SHORT).show();
                }
                else {
                    pg.show();
                    registerdet rdet = new registerdet();
                    rdet.setName(sename);
                    rdet.setEmail(seemail);
                    rdet.setPass(sepass);
                    rdet.setGender(sgender);
                    rdet.setDept(sedept);
                    rdet.setImg(simage);
                    rdet.setDesig(sedesig);
                    rdet.setNum(senumb);
                    rdet.setType("employee");
                    df.child(seemail).setValue(rdet).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                         pg.dismiss();
                            if(task.isSuccessful()){
                                Toast.makeText(getActivity(), "Employee Added Succesfully", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getActivity(), "Employee Adding Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pg.dismiss();
                            Toast.makeText(getActivity(), "Employee Adding Failed", Toast.LENGTH_SHORT).show();
                        }
                    });



        }


            }
        });





        return  rv;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            pg.show();
            if (data != null) {
                Uri uri = data.getData();
                Bitmap b1 = null;
                try {
                    b1 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Bitmap b = Bitmap.createScaledBitmap(b1,200,200,true);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                b1.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                iv.setImageBitmap(b1);
                pg.show();
                Uri file = data.getData();
                StorageReference riversRef = sf.child("emppics/"+file.getLastPathSegment());
                 UploadTask     uploadTask = riversRef.putFile(file);
               uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                       pg.dismiss();
                       if(task.isSuccessful()){
                           Toast.makeText(getActivity(), "image uploaded", Toast.LENGTH_SHORT).show();
                           simage=   task.getResult().getDownloadUrl().toString();
                       }

                       else{
                           Toast.makeText(getActivity(), "image uploaded failed", Toast.LENGTH_SHORT).show();
                       }


                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(getActivity(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                   }
               });


            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getActivity(), "please choose an image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
