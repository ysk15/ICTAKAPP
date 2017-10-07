package com.example.user.ictakapp;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ociuz 2 on 28-Jan-17.
 */

public class cadpater extends RecyclerView.Adapter {
    ArrayList<chatbean> arr = new ArrayList<chatbean>();
    public  int lastPosition=-2;
    byte[] decodedString;
  Context c;


    public cadpater(ArrayList<chatbean> arr, Context c) {
        this.arr = arr;
        this.c = c;

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        switch (viewType) {
            case 1:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat, parent, false);
                    return new Holder0(v);
            case 2:
                 v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chattwo, parent, false);
                return new Holder1(v);
        }
        return null;


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                chatbean b = arr.get(position);
        if(holder.getItemViewType() == 0){

        }

        if (holder.getItemViewType() == 1) {

            ((Holder0) holder).mess.setText(b.getText());


        } else if (holder.getItemViewType() == 2) {
            ((Holder1) holder).message.setText(b.getText());


        }


        setAnimation(holder.itemView,position);

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    @Override
    public int getItemViewType(int position) {
        int t = 0;
        Sqlite sq = new Sqlite(c);

        if(arr.get(position).getReciever().equals(sq.CheckLogin()[0])){
            t=1;
        }
        else if(arr.get(position).getSender().equals(sq.CheckLogin()[0])) {
            t=2;
        }


        Log.i("tvalue",""+t);
        return t;


    }

    public static class Holder0 extends RecyclerView.ViewHolder {
        TextView mess;

        public Holder0(View itemView) {
            super(itemView);
            mess = (TextView) itemView.findViewById(R.id.chatone);

        }
    }

    public static class Holder1 extends RecyclerView.ViewHolder {
        TextView message;

        public Holder1(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.chattwo);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setAnimation(View viewToAnimate, int position) {

        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            TranslateAnimation an = new TranslateAnimation(-700,0,0,0);
            an.setDuration(600);
            an.setInterpolator(new LinearInterpolator());
           // Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(an);
            lastPosition = position;
           /* RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(700);
            rotate.setInterpolator(new LinearInterpolator());
            viewToAnimate.startAnimation(rotate);*/

        }
    }




}
