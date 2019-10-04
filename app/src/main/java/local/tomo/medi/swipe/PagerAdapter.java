package local.tomo.medi.swipe;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

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
