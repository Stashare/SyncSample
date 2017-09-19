package ke.co.stashare.syncsample.survey.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ke.co.stashare.syncsample.navigator.Contact;
import ke.co.stashare.syncsample.navigator.PageFragment;
import ke.co.stashare.syncsample.ui.QuePageFragment;
import ke.co.stashare.syncsample.ui.models.Quiza;

/**
 * Created by Ken Wainaina on 12/08/2017.
 */

public class QueFragmentPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Quiza> ques;

    public QueFragmentPagerAdapter(FragmentManager fm, ArrayList<Quiza> queList) {
        super(fm);
        this.ques = queList;
    }


    @Override
    public Fragment getItem(int index) {

        return QuePageFragment.newInstance(ques.get(index));
    }


    @Override
    public int getCount() {

        return ques.size();
    }


}
