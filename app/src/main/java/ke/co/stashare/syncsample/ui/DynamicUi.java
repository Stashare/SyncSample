package ke.co.stashare.syncsample.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.support.v4.widget.TextViewCompat;

import ke.co.stashare.syncsample.R;

import static ke.co.stashare.syncsample.R.id.textView;

/**
 * Created by Ken Wainaina on 27/07/2017.
 */

public class DynamicUi extends AppCompatActivity {

    LinearLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.source_db);


        //TableLayout tl = (TableLayout) findViewById(R.id.tabular_info);

        container =(LinearLayout)findViewById(R.id.container);


/*
        TextView mainQue = new TextView(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 5, 0, 0);
        mainQue.setLayoutParams(params);

        mainQue.setText("A.4  Household Make up");
        mainQue.setGravity(Gravity.START);



        //textViewLayoutParams.setMargins(50,30,10,30); // llp.setMargins(left, top, right, bottom);
        mainQue.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        mainQue.setId(5);
        mainQue.setTypeface(Typeface.create("sans-serif-condensed",Typeface.BOLD_ITALIC));
        mainQue.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.result_font));*/




        LayoutInflater inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);




/*
        TextView mainSingleResponse = new TextView(this);
        mainSingleResponse.setText("(#) Household members:");
        mainSingleResponse.setGravity(Gravity.START);

        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 7, 0, 0);
        mainSingleResponse.setLayoutParams(params);

        //textViewLayoutParams.setMargins(50,30,10,30); // llp.setMargins(left, top, right, bottom);
        mainSingleResponse.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        mainSingleResponse.setId(3);
        mainSingleResponse.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.single_response));

        container.addView(mainSingleResponse);

        *//*<EditText
        android:layout_marginStart="50dp"
        android:layout_marginTop="20dp"
        android:textStyle="bold"
        android:textColor="@color/colorPrimaryDark"
        android:id="@+id/total"
        android:textAlignment="center"
        android:layout_width="80dp"
        android:hint = "0"
        android:layout_height="wrap_content"
        android:layout_marginLeft="15dp" />*//*

        EditText feed_singleRespo = new EditText(this);
        feed_singleRespo.setHint("0");
        feed_singleRespo.setGravity(Gravity.CENTER);

        params = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(120, 10, 0, 0);
        feed_singleRespo.setLayoutParams(params);

        //textViewLayoutParams.setMargins(50,30,10,30); // llp.setMargins(left, top, right, bottom);
        feed_singleRespo.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        feed_singleRespo.setId(6);
        feed_singleRespo.setTypeface(null,Typeface.BOLD);


        container.addView(feed_singleRespo);*/

        View childLayout = inflater.inflate(R.layout.que_subque,
                (ViewGroup) findViewById(R.id.que_content));

        container.addView(childLayout);

        /*childLayout = inflater.inflate(R.layout.single_subquestion,
                (ViewGroup) findViewById(R.id.single_respo_content));
*/
        container.addView(childLayout);


        childLayout = inflater.inflate(R.layout.sub_que,
                (ViewGroup) findViewById(R.id.subque_content));

        container.addView(childLayout);

          childLayout = inflater.inflate(R.layout.que_multiple,
                (ViewGroup) findViewById(R.id.scroll));

        container.addView(childLayout);



    }
}
