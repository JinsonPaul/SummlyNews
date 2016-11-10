package com.jinson.irins.summlynews;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by HP on 09-11-2016.
 */
public class HomeNews extends Fragment {
    //private SectionsPagerAdapter mSectionsPagerAdapter;
    private SectPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public static TabLayout tabLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main, container, false);

        mSectionsPagerAdapter = new SectPagerAdapter(getFragmentManager());

        mViewPager = (ViewPager)v.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        tabLayout = (TabLayout)v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);




        return v;


    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            AlbumsAdapter adapter;
            List<Album> albumList;
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);

            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
            albumList = new ArrayList<>();
            adapter = new AlbumsAdapter(getContext(), albumList);
            Resources r = getResources();
            RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(mLayoutManager);
//            Resources r = getResources();
            recyclerView.addItemDecoration(new MainActivity.GridSpacingItemDecoration(2, Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics())), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);

            int b=tabLayout.getTabCount();
            System.out.println("b" + toString().valueOf(b));

            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            String sms= (String) textView.getText();
            String pos=sms.replaceAll("[^0-9]", "");
            MainActivity.posi=Integer.parseInt(pos);
            JSONArray jsonarray;
            int k=0;
            if (MainActivity.message != null) {
                try {
                    jsonarray = new JSONArray(MainActivity.message);
                    int j=0;
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String category=jsonobject.getString("category");
                        String sender = jsonobject.getString("title");
                        String message = jsonobject.getString("description");
                        if(category.contains(MainActivity.titles[MainActivity.posi])) {
                            Album a = new Album(sender, message, category);
                            albumList.add(a);
                            adapter.notifyDataSetChanged();
                            k++;
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return rootView;
        }
    }


    public class SectPagerAdapter extends FragmentPagerAdapter {

        public SectPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            //return PlaceholderFragment.newInstance(position + 1);
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return MainActivity.totalpages;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            for(int i=0;i<MainActivity.totalpages;i++){
                if(i==position){
                    return MainActivity.titles[i];
                }
            }
            return null;
        }
    }










}