package ke.co.stashare.syncsample.sharedelements.adapter.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ken Wainaina on 07/07/2017.
 */

public class AnimalItem implements Parcelable {

    public String name;
    public String detail;
    public String imageUrl;


    protected AnimalItem(Parcel in) {
        name = in.readString();
        detail = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<AnimalItem> CREATOR = new Creator<AnimalItem>() {
        @Override
        public AnimalItem createFromParcel(Parcel in) {
            return new AnimalItem(in);
        }

        @Override
        public AnimalItem[] newArray(int size) {
            return new AnimalItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(detail);
        dest.writeString(imageUrl);
    }
}
