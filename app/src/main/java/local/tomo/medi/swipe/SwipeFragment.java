package local.tomo.medi.swipe;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import local.tomo.medi.R;

//http://www.truiton.com/2015/06/android-tabs-example-fragments-viewpager/
public class SwipeFragment extends Fragment {

    ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swipe, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Leki"));
        tabLayout.addTab(tabLayout.newTab().setText("Choroby"));
        tabLayout.addTab(tabLayout.newTab().setText("Osoby"));
        tabLayout.addTab(tabLayout.newTab().setText("Dawki"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        viewPager = (ViewPager) view.findViewById(R.id.pager);

        PagerAdapter adapter = new PagerAdapter
                (getChildFragmentManager(), 4);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }

    public static String getFragmentTag(int pos){
        return "android:switcher:"+R.id.pager+":"+pos;
    }
}
