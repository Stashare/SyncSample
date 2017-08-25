package ke.co.stashare.syncsample.bignerd;

import ke.co.stashare.syncsample.R;

/**
 * Created by Ken Wainaina on 17/07/2017.
 */

public enum CustomPagerEnum {
    RED(R.string.red, R.layout.view_red),
    BLUE(R.string.blue, R.layout.view_blue),
    ORANGE(R.string.orange, R.layout.view_orange);

    private int mTitleResId;
    private int mLayoutResId;

    CustomPagerEnum(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }


}
