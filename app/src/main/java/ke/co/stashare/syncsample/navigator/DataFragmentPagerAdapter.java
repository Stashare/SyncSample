package ke.co.stashare.syncsample.navigator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Ken Wainaina on 12/08/2017.
 */

public class DataFragmentPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Contact> users;

    public DataFragmentPagerAdapter(FragmentManager fm, ArrayList<Contact> usersList) {
        super(fm);
        this.users=usersList;
    }

    @Override
    public Fragment getItem(int index) {

        return PageFragment.newInstance(users.get(index));
    }


    @Override
    public int getCount() {

        return users.size();
    }


}
