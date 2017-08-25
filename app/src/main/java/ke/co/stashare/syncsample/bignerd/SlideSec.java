package ke.co.stashare.syncsample.bignerd;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.Toast;

import ke.co.stashare.syncsample.R;

/**
 * Created by Ken Wainaina on 17/07/2017.
 */

public class SlideSec extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_questions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

      /*  ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));*/
    }
    public void radioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // This check which radio button was clicked
        switch (view.getId()) {
            case R.id.male_selection:
                if (checked)
                    //Do something when radio button is clicked
                    Toast.makeText(getApplicationContext(), "u selected MALE", Toast.LENGTH_SHORT).show();
                break;

            case R.id.female_selection:
                //Do something when radio button is clicked
                Toast.makeText(getApplicationContext(), "u selected FEMALE", Toast.LENGTH_SHORT).show();
                break;

        }
    }

}
