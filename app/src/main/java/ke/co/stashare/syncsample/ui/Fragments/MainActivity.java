package ke.co.stashare.syncsample.ui.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ke.co.stashare.syncsample.R;

/**
 * Created by Ken Wainaina on 17/09/2017.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button argumentFragment;
    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragi_main);

        fragmentManager = getSupportFragmentManager();//Get Fragment Manager


        argumentFragment = (Button) findViewById(R.id.setArgumentFragment);

        argumentFragment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setArgumentFragment:

                Fragment argumentFragment = new ArgumentFragment();//Get Fragment Instance
                Bundle data = new Bundle();//Use bundle to pass data
                data.putString("data", "This is Argument Fragment");//put string, int, etc in bundle with a key value
                argumentFragment.setArguments(data);//Finally set argument bundle to fragment

                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, argumentFragment).commit();//now replace the argument fragment

                /**  Note: If you are passing argument in fragment then don't use below code always replace fragment instance where we had set bundle as argument as we had done above else it will give exception  **/
                //   fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new ArgumentFragment()).commit();
                break;
        }

    }
}
