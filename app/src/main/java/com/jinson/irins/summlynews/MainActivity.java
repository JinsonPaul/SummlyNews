package com.jinson.irins.summlynews;

/**
 * Created by HP on 26-10-2016.
 */

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

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    public static int totalpages;
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    public static int posi;
    public static TabLayout tabLayout;
    public static String[] titles=new String[100];
    public static int tabposition=0;
    public static String message;
    public JSONArray jsonarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        int a=tabLayout.getTabCount();
        System.out.println("a" + toString().valueOf(a));

       // tabLayout.getTabAt(5).setIcon(R.drawable.rotate);
        //tabLayout.getTabAt(2).


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int mul = (3 * totalpages) / 9;
                AlbumsAdapter.t1.stop();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


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
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics())), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);

            int b=tabLayout.getTabCount();
            System.out.println("b" + toString().valueOf(b));

            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            String sms= (String) textView.getText();
            String pos=sms.replaceAll("[^0-9]", "");
            posi=Integer.parseInt(pos);
            JSONArray jsonarray;
            int k=0;
            if (message != null) {
                try {
                    jsonarray = new JSONArray(message);
                    int j=0;
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String category=jsonobject.getString("category");
                        String sender = jsonobject.getString("title");
                        String message = jsonobject.getString("description");
                        if(category.contains(titles[posi-1])) {
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
            final DbHandler db = new DbHandler(this.getContext());
            List<Saved> saved = db.getAllContacts();

            for (int i = 0; i < saved.size(); i++) {
                if(titles[posi - 1].contains("Saved")){
                    Album a = new Album(saved.get(i).getTitle(), saved.get(i).getContent(),"Saved");
                    albumList.add(a);
                    adapter.notifyDataSetChanged();
                }

            }
            db.close();



            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return totalpages;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            for(int i=0;i<totalpages;i++){
                if(i==position){
                    return titles[i];
                }
            }
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

    /**
     * Converting dp to pixel
     */
    public int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
