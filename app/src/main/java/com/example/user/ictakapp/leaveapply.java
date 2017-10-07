package com.example.user.ictakapp;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class leaveapply extends Fragment {
    EditText fdate,tdate;
    Spinner sp;
    Button bu;
    String fr,to,re;
    Calendar myCalendar = Calendar.getInstance();
    int i=0;
    DatabaseReference df;
    Sqlite sq;
    ProgressDialog pg;


    public leaveapply() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_leaveapply, container, false);
        fdate=(EditText)v.findViewById(R.id.fdate);
        tdate=(EditText)v.findViewById(R.id.todate);
        sp=(Spinner)v.findViewById(R.id.spinner);
        bu=(Button)v.findViewById(R.id.button7);
        sq= new Sqlite(getActivity());
        pg= new ProgressDialog(getActivity());
        df = FirebaseDatabase.getInstance().getReference().child("leavedet");
        final   DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
         @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }

        };
        fdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                i=0;
            }
        });
        tdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                i=1;
            }
        });
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fr = fdate.getText().toString();
                to=tdate.getText().toString();
                re=sp.getSelectedItem().toString();
                if(fr.equals("")||to.equals("")||sp.equals("PICK REASON")){
                    Toast.makeText(getActivity(), "Fill All Fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    pg.show();
                    String key = df.push().getKey();
                    Log.e("keypush",key);
                    leavedet ld = new leavedet();
                    ld.setStatus("ON REQUEST");
                    ld.setEmpl(sq.CheckLogin()[0]);
                    ld.setReason(re);
                    ld.setFdate(fr);
                    ld.setTodate(to);
                    ld.setNoofdays(noofdays(fr,to));
                    ld.setKey(key);
                    df.child(key).setValue(ld).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            pg.dismiss();
                            if(task.isSuccessful()){
                                send("new leave application","/topics/admin");
                                Toast.makeText(getActivity(), "Leave Succesfully Requested", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getActivity(), "Leave  Request Failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pg.dismiss();
                            Toast.makeText(getActivity(), "Leave  Request Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });

        return v;
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        if(i==0){
            fdate.setText(sdf.format(myCalendar.getTime()));
        }
        else {
            tdate.setText(sdf.format(myCalendar.getTime()));
        }

    }
   public String noofdays(String from,String to){
       String myFormat = "dd/MM/yyyy"; //In which you need put here
       String days= "";
       SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
       try {
           Date date1 = sdf.parse(from);
           Date date2 = sdf.parse(to);
           long diff = date2.getTime() - date1.getTime();
           days= ""+ TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
       } catch (ParseException e) {
           e.printStackTrace();
       }
       return days;

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
