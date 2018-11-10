package com.rds.jobs.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rds.jobs.R;
import com.rds.jobs.adapter.ForYouAdapter;
import com.rds.jobs.data.Jobs;
import com.rds.jobs.helper.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ForYouFragment extends Fragment {

    private List<Jobs> list;
    private String urlJSON = "http://private-2232a-testjobs.apiary-mock.com/api/recommendation";
    ForYouAdapter forYouAdapter;
    Boolean isScroll = false;
    int currentItem, totalItem, scrollItem;
    private static final int NUM_COLUMNS = 2;
    String dates, returndates;
    DBHelper db;
    String matauang;
    public ForYouFragment() {
        // Required empty public constructor
    }

    public static ForYouFragment newInstance() {
        ForYouFragment fragment = new ForYouFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // do something when visible.
            Log.i("AKU","MIKIR");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_for_you, container, false);

        list = new ArrayList<>();

        forYouAdapter = new ForYouAdapter(getContext(), list);

        getData();

        RecyclerView recyclerViewtop = (RecyclerView) view.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerViewtop.setAdapter(forYouAdapter);
        recyclerViewtop.setLayoutManager(staggeredGridLayoutManager);
        recyclerViewtop.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScroll=true;
                    //fetchData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = new LinearLayoutManager(getActivity()).getChildCount();
                totalItem = new LinearLayoutManager(getActivity()).getItemCount();
                scrollItem = new LinearLayoutManager(getActivity()).findFirstVisibleItemPosition();

                if(isScroll && (currentItem + scrollItem == totalItem)){
                    isScroll = false;
                }
            }
        });

        return view;
    }

    private void getData() {
        list.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlJSON,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int p=0; p<jsonArray.length(); p++){
                                JSONObject jsonObject = jsonArray.getJSONObject(p);
                                Jobs jobs = new Jobs();
                                String date =jsonObject.getString("created_at");

                                dates = Long.toString(calculateDays(currentDate(),datezone(date)));
                                if(dates.equalsIgnoreCase("0")){
                                    returndates = "Posted Today";
                                }else {
                                    returndates = "Posted "+dates.toString()+" days ago";
                                }
                                JSONObject name = jsonObject.getJSONObject("salary");
                                if(name!=null) {
                                    String currency = name.getString("currency");
                                    if (currency.equalsIgnoreCase("USD")) {
                                        matauang = "$";
                                    } else if (currency.equalsIgnoreCase("SGD")) {
                                        matauang = "$";

                                    } else if (currency.equalsIgnoreCase("IDR")) {
                                        matauang = "Rp.";

                                    } else if (currency.equalsIgnoreCase("EUR")) {
                                        matauang = "€";

                                    } else {
                                        matauang = currency;
                                    }

                                    jobs.setMatauang(matauang);
                                    jobs.setGajiawal(name.getString("minimum"));
                                    jobs.setGajiakhir(name.getString("maximum"));
                                }else {
                                    jobs.setMatauang("null");
                                    jobs.setGajiawal("null");
                                    jobs.setGajiakhir("null");
                                }


                                jobs.setId(jsonObject.getString("id"));
                                jobs.setImage(jsonObject.getString("logo"));
                                jobs.setCompany(jsonObject.getString("company_name"));
                                jobs.setDate("Posted "+dates.toString()+" days ago");
                                jobs.setPosition(jsonObject.getString("job_title"));
                                jobs.setJobtype(jsonObject.getString("job_type"));
                                jobs.setCity(jsonObject.getString("city"));
                                jobs.setUrl(jsonObject.getString("share_url"));
                                jobs.setDescription(jsonObject.getString("description"));
                                jobs.setResponsibility(jsonObject.getString("responsibility"));
                                jobs.setRequirement(jsonObject.getString("requirement"));
                                list.add(jobs);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        forYouAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);
    }

    public long calculateDays(String current, String datezone) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date firstDate = null;
        Date secondDate = null;
        try {
            firstDate = sdf.parse(current);
            secondDate = sdf.parse(datezone);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (firstDate.getTime() - secondDate.getTime()) / (24 * 60 * 60 * 1000);
    }

    public String currentDate(){
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(date);
        return today;
    }

    public String datezone(String dates){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(dates);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = outputFormat.format(date);
        return formattedDate;
    }
}
