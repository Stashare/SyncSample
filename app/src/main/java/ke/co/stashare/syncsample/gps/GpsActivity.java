package ke.co.stashare.syncsample.gps;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.TextView;

import org.w3c.dom.Text;

import ke.co.stashare.syncsample.R;

/**
 * Created by Ken Wainaina on 30/08/2017.
 */

public class GpsActivity  extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 98;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_home);

        showPermissionDialog();
        // check if GPS enabled
        Tracker tracker = new Tracker(GpsActivity.this);

        // check if GPS enabled
        if(tracker.canGetLocation()){
            double latitude = tracker.getLatitude();
            //double longitude = tracker.getLongitude();


            String stringLatitude = String.valueOf(tracker.getLatitude());
            TextView lat = (TextView)findViewById(R.id.fieldLatitude);
            lat.setText((stringLatitude));

            String stringLongitude = String.valueOf(tracker.getLongitude());
            TextView longi = (TextView)findViewById(R.id.fieldLongitude);
            longi.setText(stringLongitude);

        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            tracker.showSettingsAlert();
        }
    }

    private void showPermissionDialog() {
        if (!GPSTracker.checkPermission(this)) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}


