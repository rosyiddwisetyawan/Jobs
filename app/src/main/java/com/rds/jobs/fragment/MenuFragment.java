package com.rds.jobs.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rds.jobs.R;
import com.rds.jobs.adapter.ViewPagerAdapter;

public class MenuFragment extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
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
        final View view = inflater.inflate(R.layout.fragment_menu, container, false);

        tabLayout = (TabLayout)view.findViewById(R.id.idtab2);
        viewPager = (ViewPager)view.findViewById(R.id.idview2);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.clearData();
        adapter.addFragment(new ForYouFragment(),"For You");
        adapter.addFragment(new SavedFragment(),"Saved");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

}
