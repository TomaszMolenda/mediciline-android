package local.tomo.medi.swipe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

//http://www.truiton.com/2015/06/android-tabs-example-fragments-viewpager/
public class PagerAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MedicamentFragment medicamentFragment = new MedicamentFragment();
                return medicamentFragment;
            case 1:
                PatientFragment patientFragment = new PatientFragment();
                return patientFragment;
            case 2:
                DosageFragment dosageFragment = new DosageFragment();
                return dosageFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
