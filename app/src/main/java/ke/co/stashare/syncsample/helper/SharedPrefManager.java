package ke.co.stashare.syncsample.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ken Wainaina on 13/07/2017.
 */

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "islamicSharedPref";
    private static final String NUMBER = "number";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //this method will save the device token to shared preferences
    public boolean saveNumber(String num){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NUMBER, num);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getNumber(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(NUMBER, null);
    }

//final String token = SharedPrefManager.getInstance(this).getDeviceToken();

//SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);


}
