package local.tomo.medi.swipe;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import local.tomo.medi.R;

public class SwipeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swipe, container, false);

        ViewPager viewPager = view.findViewById(R.id.pager);

        PagerAdapter adapter = new PagerAdapter
                (getChildFragmentManager(), 1);
        viewPager.setAdapter(adapter);

        return view;
    }
}
