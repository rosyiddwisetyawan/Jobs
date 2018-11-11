package com.rds.jobs.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.rds.jobs.R;
import com.rds.jobs.adapter.ForYouAdapter;
import com.rds.jobs.adapter.SavedAdapter;
import com.rds.jobs.data.Jobs;
import com.rds.jobs.data.JobsWish;
import com.rds.jobs.helper.DBHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SavedFragment extends Fragment {

    private List<JobsWish> dbWishes;
    SavedAdapter savedAdapter;
    private static final int NUM_COLUMNS = 2;
    private DBHelper dba;
    Boolean isScroll = false;
    int currentItem, totalItem, scrollItem;
    View vi;

    public SavedFragment() {
        // Required empty public constructor
    }


    public static SavedFragment newInstance() {
        SavedFragment fragment = new SavedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vi = inflater.inflate(R.layout.fragment_saved, container, false);

        dbWishes = new ArrayList<>();
        refreshData();
        savedAdapter = new SavedAdapter(getContext(), dbWishes);

        RecyclerView recyclerViewtop = (RecyclerView) vi.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerViewtop.setAdapter(savedAdapter);
        recyclerViewtop.setLayoutManager(staggeredGridLayoutManager);
        recyclerViewtop.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScroll = true;
                    //fetchData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = new LinearLayoutManager(getActivity()).getChildCount();
                totalItem = new LinearLayoutManager(getActivity()).getItemCount();
                scrollItem = new LinearLayoutManager(getActivity()).findFirstVisibleItemPosition();

                if (isScroll && (currentItem + scrollItem == totalItem)) {
                    isScroll = false;
                }
            }
        });
        return vi;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(false);
        if (isVisibleToUser) {
            View view = vi ;

            dbWishes = new ArrayList<>();
            refreshData();
            savedAdapter = new SavedAdapter(getContext(), dbWishes);

            RecyclerView recyclerViewtop = (RecyclerView) view.findViewById(R.id.recycler_view);
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
            recyclerViewtop.setAdapter(savedAdapter);
            recyclerViewtop.setLayoutManager(staggeredGridLayoutManager);
            recyclerViewtop.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        isScroll = true;
                        //fetchData();
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    currentItem = new LinearLayoutManager(getActivity()).getChildCount();
                    totalItem = new LinearLayoutManager(getActivity()).getItemCount();
                    scrollItem = new LinearLayoutManager(getActivity()).findFirstVisibleItemPosition();

                    if (isScroll && (currentItem + scrollItem == totalItem)) {
                        isScroll = false;
                    }
                }
            });

        }
    }

    private void refreshData() {
        dbWishes.clear();
        dba = new DBHelper(getContext());
        List<JobsWish> wishesFromDB = dba.getWishes();

        for (int i = 0; i < wishesFromDB.size(); i++) {

            String currency = wishesFromDB.get(i).getMatauang();
            String min = wishesFromDB.get(i).getGajiawal();
            String max = wishesFromDB.get(i).getGajiakhir();
            String id = wishesFromDB.get(i).getId();
            String image = wishesFromDB.get(i).getImage();
            String company = wishesFromDB.get(i).getCompany();
            String date = wishesFromDB.get(i).getDate();
            String position = wishesFromDB.get(i).getPosition();
            String jobtype = wishesFromDB.get(i).getJobtype();
            String city = wishesFromDB.get(i).getCity();
            String url = wishesFromDB.get(i).getUrl();
            String description = wishesFromDB.get(i).getDescription();
            String responsibility = wishesFromDB.get(i).getResponsibility();
            String requirement = wishesFromDB.get(i).getRequirement();

            JobsWish myWish = new JobsWish();
            myWish.setMatauang(currency);
            myWish.setGajiawal(min);
            myWish.setGajiakhir(max);
            myWish.setId(id);
            myWish.setImage(image);
            myWish.setCompany(company);
            myWish.setDate("Posted "+date+" days ago");
            myWish.setPosition(position);
            myWish.setJobtype(jobtype);
            myWish.setCity(city);
            myWish.setUrl(url);
            myWish.setDescription(description);
            myWish.setResponsibility(responsibility);
            myWish.setRequirement(requirement);

            dbWishes.add(myWish);

        }
        dba.close();
    }

}
