package local.tomo.login.swipe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import local.tomo.login.swipe.DiseaseFragment;
import local.tomo.login.swipe.MedicamentFragment;

/**
 * Created by tomo on 2016-06-19.
 */

//http://www.truiton.com/2015/06/android-tabs-example-fragments-viewpager/
public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MedicamentFragment medicamentFragment = new MedicamentFragment();
                return medicamentFragment;
            case 1:
                DiseaseFragment diseaseFragment = new DiseaseFragment();
                return diseaseFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
