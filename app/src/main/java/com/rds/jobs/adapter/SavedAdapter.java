package com.rds.jobs.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.rds.jobs.GaleryActivity;
import com.rds.jobs.R;
import com.rds.jobs.data.Jobs;
import com.rds.jobs.data.JobsWish;
import com.rds.jobs.helper.DBHelper;

import java.util.List;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

/**
 * Created by Team 2 on 11/9/2018.
 */

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder> {

    private Context context;
    private List<JobsWish> list;
    DBHelper dbHelper;

    public SavedAdapter(Context context, List<JobsWish> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list, parent, false);
        SavedAdapter.ViewHolder holder = new SavedAdapter.ViewHolder(view);
        context = parent.getContext();
        dbHelper = new DBHelper(context);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final JobsWish jobs = list.get(position);
        Glide.with(context)
                .load(jobs.getImage())
                .apply(centerCropTransform()
                        .override(100, 100)
                        .placeholder(R.drawable.thumbnailimage)
                        .error(R.drawable.thumbnailimage)
                        .priority(Priority.HIGH))
                .into(holder.centerimage);

        holder.companyname.setText(jobs.getCompany());
        holder.date.setText(jobs.getDate());
        holder.position.setText(jobs.getPosition());
        holder.jobtype.setText("* "+jobs.getJobtype());
        holder.city.setText("* "+jobs.getCity());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobsWish jobSaved = new JobsWish();
                String id = jobSaved.getId();
                dbHelper.deleteWish(id);
            }
        });

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

    }//////

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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
            centerimage = itemView.findViewById(R.id.centerimage);
            delete = itemView.findViewById(R.id.delete);
            companyname = itemView.findViewById(R.id.companyname);
            date = itemView.findViewById(R.id.date);
            position = itemView.findViewById(R.id.position);
            jobtype = itemView.findViewById(R.id.jobtype);
            city = itemView.findViewById(R.id.city);
            parentLayout = itemView.findViewById(R.id.parent_layout_list);
        }
    }
}
