package ke.co.stashare.syncsample.helper;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ken Wainaina on 14/07/2017.
 */

public class SectionItem implements Parcelable  {
    protected SectionItem(Parcel in) {
    }

    public static final Creator<SectionItem> CREATOR = new Creator<SectionItem>() {
        @Override
        public SectionItem createFromParcel(Parcel in) {
            return new SectionItem(in);
        }

        @Override
        public SectionItem[] newArray(int size) {
            return new SectionItem[size];
        }
    };

    public String title;
    public String url;

    public SectionItem(String title, String url) {
        this.title = title;
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(url);
    }
}
