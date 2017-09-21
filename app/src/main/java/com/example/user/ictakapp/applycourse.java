package com.example.user.ictakapp;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class applycourse extends Fragment {
    Spinner sp;
    Button bu;
    DatabaseReference df,dr,ds,dc;
    ProgressDialog pg;
    ArrayList<String> arr;
    ArrayList<coursedet> arr1;
    ArrayAdapter<String> ad;
    String name,cname,cduration,colleage,branch,semester;
    Sqlite sq;



    public applycourse() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_applycourse, container, false);
        sp=(Spinner)v.findViewById(R.id.spinner2);
        bu=(Button)v.findViewById(R.id.button8);
        pg = new ProgressDialog(getActivity());
        arr = new ArrayList<>();
        arr1 = new ArrayList<>();
        sq = new Sqlite(getActivity());
        ad = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arr);
        df= FirebaseDatabase.getInstance().getReference().child("coursedet");
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    coursedet cd = ds.getValue(coursedet.class) ;
                    String details = "COURSE NAME: "+cd.getName();
                    arr.add(details);
                    arr1.add(cd);
                }
                sp.setAdapter(ad);
                pg.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                pg.dismiss();
                Toast.makeText(getActivity(), "Network Problem", Toast.LENGTH_SHORT).show();
            }
        });
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(arr.size()==0){
                    Toast.makeText(getActivity(), "No Course To Apply", Toast.LENGTH_SHORT).show();
                }
                else {
                    pg.show();
                    cname = sp.getSelectedItem().toString();
                    cduration=arr1.get(sp.getSelectedItemPosition()).getDuration();
                    ds=FirebaseDatabase.getInstance().getReference().child("registerdet").child(sq.CheckLogin()[0]);
                            ds.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                        registerdet rd = dataSnapshot.getValue(registerdet.class);
                            colleage=rd.getCollege();
                            branch=rd.getBranch();
                            semester=rd.getSem();
                            dr=FirebaseDatabase.getInstance().getReference().child("courseapply");
                            courseapplydet ca = new courseapplydet();
                            ca.setName(sq.CheckLogin()[0]);
                            ca.setBranch(branch);
                            ca.setCduration(cduration);
                            ca.setCname(cname);
                            ca.setSemester(semester);
                            ca.setColleage(colleage);
                            dr.child(sq.CheckLogin()[0]+cname).setValue(ca).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pg.dismiss();
                                    Toast.makeText(getActivity(), "APPLIED FOR COURSE", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pg.dismiss();
                                    Toast.makeText(getActivity(), "FAILED TO APPLY", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            pg.dismiss();

                        }
                    });
                }
            }
        });
        return  v;
    }

}
