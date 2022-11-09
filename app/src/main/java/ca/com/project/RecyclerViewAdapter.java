package ca.com.project;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
 
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
 
    private ArrayList<Contact> DataArrayList;
    private Context mcontext;
 
    public RecyclerViewAdapter(ArrayList<Contact> recyclerDataArrayList, Context mcontext) {
        this.DataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }
 
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_card, parent, false);
        return new RecyclerViewHolder(view);
    }
 
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        final Contact recyclerData = DataArrayList.get(position);
        holder.TV.setText(recyclerData.getName());
        holder.IV.setImageDrawable(recyclerData.getImage());
        //Glide.with(mcontext).load("http://goo.gl/gEgYUd").into(holder.IV);

        holder.videobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcontext,CallInitiateActivity.class);
                Bundle b = new Bundle();
                b.putString("image",recyclerData.getImage().toString());
                b.putString("name",recyclerData.getName());
                b.putInt("id",recyclerData.getId());
                b.putInt("type",1);

                i.putExtras(b);

                mcontext.startActivity(i);


            }
        });

        holder.callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mcontext,CallInitiateActivity.class);
                Bundle b = new Bundle();
                b.putString("image",recyclerData.getImage().toString());
                b.putString("name",recyclerData.getName());
                b.putInt("id",recyclerData.getId());
                b.putInt("type",2);
                i.putExtras(b);
                mcontext.startActivity(i);

            }
        });

    }
 
    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return DataArrayList.size();
    }
 
    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
 
        private TextView TV;
        private ImageView IV;
        private ImageButton videobtn,callbtn;
 
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            TV = itemView.findViewById(R.id.idTV);
            IV = itemView.findViewById(R.id.idIV);
            videobtn = itemView.findViewById(R.id.recbtn);
            callbtn = itemView.findViewById(R.id.hangup);
        }
    }
}