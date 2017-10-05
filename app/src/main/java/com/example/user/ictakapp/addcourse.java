package com.example.user.ictakapp;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
                            send("new course added","/topics/student");
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

    private void send(String body,String to) {
        final ProgressDialog pr = new ProgressDialog(getActivity());
        pr.show();
        HashMap<String,String> map = new HashMap<String, String>();
        //change key from firebase conosle
        map.put("Authorization","key=AAAAhBRO_eE:APA91bHQ7CWW6-clKIwuWqmRZagQ3SfBrmeccN5G2XgWIR_wtCXEAAGQDUQsadKVZlLa0KhKRXLAAbW-SGIkKTGEGEcgamNx7VqTbJy7eYjCY5E3OnrMoO0ZJHJGklGTUxxLr3oQpOXb");
        APIInterface ap = APIClient.getClient1().create(APIInterface.class);
        NotifyData notifydata = new NotifyData(body);
        Call<ResponseBody> call2 = ap.sendMessage(map,new Message(to,notifydata,"high"));
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                pr.dismiss();
                Log.d("Response", "onResponse");
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Response ", "onFailure");
                pr.dismiss();

            }
        });
    }


}
