package ke.co.stashare.syncsample.helper;

/**
 * Created by Ken Wainaina on 21/02/2017.
 */

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import ke.co.stashare.syncsample.survey.helper.Constants;
import ke.co.stashare.syncsample.survey.helper.DbList;

//Class extending application
public class AppController extends Application {

    //Getting tag it will be used for displaying log and it is optional
    public static final String TAG = AppController.class.getSimpleName();

    //Creating a volley request queue object
    private RequestQueue mRequestQueue;

    //Creatting class object
    private static AppController mInstance;

    //Creating sharedpreferences object
    //We will store the user data in sharedpreferences
    private SharedPreferences sharedPreferences;

    //class instance will be initialized on app launch
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    //Public static method to get the instance of this class
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    //This method would return the request queue
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }


    //This method would add the requeust to the queue for execution
    public <T> void addToRequestQueue(Request<T> req) {
        //Setting a tag to the request
        req.setTag(TAG);

        //calling the method to get the request queue and adding the requeust the the queuue
        getRequestQueue().add(req);
    }

    //method to cancle the pending requests
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public static void save_DbList_To_Shared_Prefs(Context context, DbList dbList) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dbList);
        prefsEditor.putString("dblist", json);
        prefsEditor.apply();

    }

    public static DbList get_DbList_From_Shared_Prefs(Context context) {

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("user", "");


        DbList dbList = gson.fromJson(json, DbList.class);
        return dbList;
    }


    /*//Method to get sharedpreferences


    //This method will clear the sharedpreference
    //It will be called on logout
    public void logout() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.clear();
        editor.apply();
    }

    //This method will store the user data on sharedpreferences
    //It will be called on login
    public void loginUser(int id, String name, String email) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(Constants.USER_ID, id);
        editor.putString(Constants.USER_EMAIL, email);
        editor.putString(Constants.USER_NAME, name);
        editor.putBoolean(Constants.IS_LOGGED_IN, true);
        editor.apply();
    }

    //This method will check whether the user is logged in or not
    public boolean isLoggedIn() {
        return getSharedPreferences().getBoolean(Constants.IS_LOGGED_IN, false);
    }

    //This method will return the user id of logged in user
    public int getUserId() {
        return getSharedPreferences().getInt(Constants.USER_ID, -1);
    }

    //This method will return the username of logged in user
    public String getUserName() {
        return getSharedPreferences().getString(Constants.USER_NAME, null);
    }*/
}
