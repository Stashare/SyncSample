package ke.co.stashare.syncsample.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import ke.co.stashare.syncsample.R;
import ke.co.stashare.syncsample.helper.SharedPrefManager;

/**
 * Created by Ken Wainaina on 12/07/2017.
 */

public class TestUi extends AppCompatActivity {

    AppCompatEditText num;
    RelativeLayout add;
    RelativeLayout remove;
    int original = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.household_gender);

        num = (AppCompatEditText)findViewById(R.id.number);
        add = (RelativeLayout)findViewById(R.id.add);
        remove = (RelativeLayout)findViewById(R.id.remove);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        add.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                //Do something when radio button is clicked
                Toast.makeText(getApplicationContext(), "long clicked", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

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

    public void buttonRemove(View view) {
        //Do something when radio button is clicked

        num.clearFocus();
        if(original < 1){
            System.out.println("error");
        }else {
            //SharedPrefManager.getInstance(getApplicationContext()).saveNumber(numStr);
            original--;
            //num.setFocusable(false);
            String numStr = Integer.toString(original);
            num.setText(numStr);

        }

         //Toast.makeText(getApplicationContext(), "Remove clicked", Toast.LENGTH_SHORT).show();


    }

    public void ButtonAdd(View view) {
        //Do something when radio button is clicked

        num.clearFocus();
        if(original > 98){
            num.setText("0");
            System.out.println("error");
        }else {
            //SharedPrefManager.getInstance(getApplicationContext()).saveNumber(numStr);
            original++;
            //num.setFocusable(false);
            String numStr = Integer.toString(original);
            num.setText(numStr);
            //Toast.makeText(getApplicationContext(), "Add clicked", Toast.LENGTH_SHORT).show();

        }


    }
}
