package com.example.user.ictakapp;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class viewleaverequest extends Fragment {
    ListView lv;
    DatabaseReference df;
    ArrayAdapter<String> ad;
    ArrayList<String> arr;
    ArrayList<leavedet> arr1;
    ProgressDialog pg;

    public viewleaverequest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_viewleaverequest, container, false);
         lv = (ListView)v.findViewById(R.id.lvleave);
        df = FirebaseDatabase.getInstance().getReference().child("leavedet");
        arr = new ArrayList<>();
        arr1 = new ArrayList<>();
        pg = new ProgressDialog(getActivity());
        pg.show();
         ad = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arr);
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pg.dismiss();
                arr.clear();
                arr1.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    leavedet ld = ds.getValue(leavedet.class);
                    String det = "EMPLOYEE ID: "+ld.getEmpl()
                            +"\nFROM DATE: "+ld.getFdate()
                            +"\nTO DATE: "+ld.getTodate()
                            +"\n NO OF DAYS: "+ld.getNoofdays()
                            +"\n APPROOVAL STATUS: "+ld.getStatus();
                    arr.add(det);
                    arr1.add(ld);
                }
                lv.setAdapter(ad);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                pg.dismiss();

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder bu = new AlertDialog.Builder(getActivity());
                bu.setTitle("LEAVE APPROOVAL or REJECTION");
                bu.setPositiveButton("APPROOVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        leavedet dt = arr1.get(position);
                        dt.setStatus("APPROOVED");
                        df.child(dt.getKey()).setValue(dt).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                send("leave approoved","/topics/employee");
                                Toast.makeText(getActivity(), "LEAVE APPROOVED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "FAILED TO APPROOVE", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });
                bu.setNegativeButton("REJECT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        leavedet dt = arr1.get(position);
                        dt.setStatus("REJECTED");
                        df.child(dt.getKey()).setValue(dt).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                send("leave rejected","/topics/employee");
                                Toast.makeText(getActivity(), "LEAVE REJECTED ", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "FAILED TO REJECT", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }


                });
                bu.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                bu.show();

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
