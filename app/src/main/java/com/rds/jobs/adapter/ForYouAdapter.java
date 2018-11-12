package com.rds.jobs.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.google.gson.Gson;
import com.rds.jobs.GaleryActivity;
import com.rds.jobs.R;
import com.rds.jobs.data.Jobs;
import com.rds.jobs.fragment.SavedFragment;
import com.rds.jobs.helper.DBHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

/**
 * Created by Team 2 on 11/9/2018.
 */

public class ForYouAdapter extends RecyclerView.Adapter<ForYouAdapter.ViewHolder> {

    private Context context;
    private List<Jobs> list;
    SharedPreferences shared;
    ArrayList<String> arrPackage;
    SavedAdapter savedAdapter;
    DBHelper dbHelper;


    public ForYouAdapter(Context context, List<Jobs> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list, parent, false);
        ForYouAdapter.ViewHolder holder = new ForYouAdapter.ViewHolder(view);
        context = parent.getContext();
        arrPackage = new ArrayList<>();
        shared = context.getApplicationContext().getSharedPreferences("JOBS", 0);
        dbHelper = new DBHelper(context);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Jobs jobs = list.get(position);
        Glide.with(context)
                .load(jobs.getImage())
                .apply(centerCropTransform()
                        .override(100, 100)
                        .placeholder(R.drawable.thumbnailimage)
                        .error(R.drawable.thumbnailimage)
                        .priority(Priority.HIGH))
                .into(holder.centerimage);

        holder.love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Jobs jobSaved = new Jobs();
                jobSaved.setMatauang(jobs.getMatauang());
                jobSaved.setGajiawal(jobs.getGajiawal());
                jobSaved.setGajiakhir(jobs.getGajiakhir());
                jobSaved.setId(jobs.getId());
                jobSaved.setImage(jobs.getImage());
                jobSaved.setCompany(jobs.getCompany());
                jobSaved.setDate(jobs.getDate());
                jobSaved.setPosition(jobs.getPosition());
                jobSaved.setJobtype(jobs.getJobtype());
                jobSaved.setCity(jobs.getCity());
                jobSaved.setUrl(jobs.getUrl());
                jobSaved.setDescription(jobs.getDescription());
                jobSaved.setResponsibility(jobs.getResponsibility());
                jobSaved.setRequirement(jobs.getRequirement());
                dbHelper.addWishes(jobSaved);
                holder.love.setImageResource(R.drawable.ic_action_favorite_true);
//                String statu = dbHelper.fetchdatabyfilter(jobs.getId().toString());
//                if(statu.equalsIgnoreCase("false")){
//                    Log.i("STATUS","INSERT");
//                    dbHelper.addWishes(jobSaved);
//                    holder.love.setImageResource(R.drawable.ic_action_favorite_true);
//                }else {
//                    Log.i("STATUS", "GAGAL");
//                }
                dbHelper.close();
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Jobs jobSaved = new Jobs();
                String id = jobSaved.getId();
                dbHelper.deleteWish(id);
            }
        });

        holder.companyname.setText(jobs.getCompany());
        holder.date.setText("Posted "+jobs.getDate()+" days ago");
        holder.position.setText(jobs.getPosition());
        holder.jobtype.setText("* "+jobs.getJobtype());
        holder.city.setText("* "+jobs.getCity());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, GaleryActivity.class);
                intent.putExtra("currency", jobs.getMatauang());
                intent.putExtra("min", jobs.getGajiawal());
                intent.putExtra("max", jobs.getGajiakhir());
                intent.putExtra("id", jobs.getId());
                intent.putExtra("image", jobs.getImage());
                intent.putExtra("company", jobs.getCompany());
                intent.putExtra("date", jobs.getDate());
                intent.putExtra("position", jobs.getPosition());
                intent.putExtra("jobtype", jobs.getJobtype());
                intent.putExtra("city", jobs.getCity());
                intent.putExtra("url", jobs.getUrl());
                intent.putExtra("description", jobs.getDescription());
                intent.putExtra("responsibility", jobs.getResponsibility());
                intent.putExtra("requirement", jobs.getRequirement());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView love;
        ImageView delete;
        ImageButton centerimage;
        TextView companyname;
        TextView date;
        TextView position;
        TextView jobtype;
        TextView city;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            love = itemView.findViewById(R.id.love);
            delete = itemView.findViewById(R.id.delete);
            centerimage = itemView.findViewById(R.id.centerimage);
            companyname = itemView.findViewById(R.id.companyname);
            date = itemView.findViewById(R.id.date);
            position = itemView.findViewById(R.id.position);
            jobtype = itemView.findViewById(R.id.jobtype);
            city = itemView.findViewById(R.id.city);
            parentLayout = itemView.findViewById(R.id.parent_layout_list);
        }
    }
}
