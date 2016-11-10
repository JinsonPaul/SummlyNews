package com.jinson.irins.summlynews;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 09-11-2016.
 */
public class HomeScreen extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    public static int totalpages=3;
    public static TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.search);
        tabLayout.getTabAt(2).setIcon(R.drawable.location);

    }



    public static class MainNews extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private SectionsPagerAdapter mSectionsPagerAdapter;


        private ViewPager mViewPager;
        public static int totalpages=3;
        public static TabLayout tabLayout;


        public MainNews() {
        }


        public static MainNews newInstance(int sectionNumber) {
            MainNews fragment = new MainNews();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_main, container, false);

            //mSectionsPagerAdapter = new PagerAdapter(getSupportFragmentManager());

            mViewPager = (ViewPager)rootView. findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);

            tabLayout.getTabAt(0).setIcon(R.drawable.home);
            tabLayout.getTabAt(1).setIcon(R.drawable.search);
            tabLayout.getTabAt(2).setIcon(R.drawable.location);

            return rootView;
        }
        public class PagerAdapter extends FragmentPagerAdapter {

            public PagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {

                return MainActivity.PlaceholderFragment.newInstance(position + 1);
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






    public static class Search extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public Search() {
        }


        public static Search newInstance(int sectionNumber) {
            Search fragment = new Search();
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
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics())), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);


            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            String sms= (String) textView.getText();
            String pos=sms.replaceAll("[^0-9]", "");
            //         posi=Integer.parseInt(pos);
            JSONArray jsonarray;



            return rootView;
        }
    }





    public static class Location extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public Location() {
        }


        public static Location newInstance(int sectionNumber) {
            Location fragment = new Location();
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
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics())), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);


            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            String sms= (String) textView.getText();
            String pos=sms.replaceAll("[^0-9]", "");
            //         posi=Integer.parseInt(pos);
            JSONArray jsonarray;





            return rootView;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return Search.newInstance(position);
                case 1 : return Search.newInstance(position);
                case 2 : return Location.newInstance(position);

            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {


            return null;
        }
    }
    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }




}
