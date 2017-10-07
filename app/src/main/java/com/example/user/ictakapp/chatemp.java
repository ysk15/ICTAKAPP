package com.example.user.ictakapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class chatemp extends Fragment {
    RecyclerView rv;
    DatabaseReference dr;
    DatabaseReference ds;
    EditText text;
    Button b;
    ArrayList<chatbean> arr = new ArrayList<>();
    RecyclerView.Adapter ad;
    LinearLayoutManager lm;
    Sqlite sq;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_chatstud, container, false);
        sq = new Sqlite(getActivity());
        dr= FirebaseDatabase.getInstance().getReference();
        ad = new cadpater(arr,getActivity());
        lm=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);

        //ds=dr.child(Studentlist.regno);
        ds=dr.child("chat");
        b=(Button)v.findViewById(R.id.buttonstu);
        text=(EditText)v.findViewById(R.id.editTextstu);
        rv= (RecyclerView)v.findViewById(R.id.rvstu);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "type message", Toast.LENGTH_SHORT).show();
                }
                else {
                    chatbean cb = new chatbean();
                    cb.setSender(sq.CheckLogin()[0]);
                    cb.setReciever("a");
                    cb.setText(text.getText().toString());
                    ds.push().setValue(cb);
                    text.setText("");
                }

            }
        });

        ds.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arr.clear();
                for(DataSnapshot sn: dataSnapshot.getChildren() ){
                    chatbean be = sn.getValue(chatbean.class);
                    if(be.getSender().equals(sq.CheckLogin()[0])||be.getReciever().equals(sq.CheckLogin()[0])){
                        arr.add(be);
                    }
                }
                lm.setStackFromEnd(true);
                rv.setAdapter(ad);
                rv.setLayoutManager(lm);
                rv.setHasFixedSize(true);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }

}
