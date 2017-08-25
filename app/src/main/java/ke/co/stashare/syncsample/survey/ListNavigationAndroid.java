package ke.co.stashare.syncsample.survey;

/**
 * Created by Ken Wainaina on 23/07/2017.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ke.co.stashare.syncsample.R;


public class ListNavigationAndroid  extends AppCompatActivity implements View.OnClickListener {

    private List<String> namesList;

    private Button btnPrevious;
    private Button btnNext;
    private int navIndex = 0;
    private LinearLayout listView;
    int currentPage = -1;
    int pageSize = 5;
    //    private int originalSize;
//    int currentIndex = 0;
    private int from;
    private int to;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_list);
        namesList = new ArrayList<>();
        btnPrevious = (Button) findViewById(R.id.btn_previous);
        btnPrevious.setOnClickListener(this);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
        listView = (LinearLayout) findViewById(R.id.data_list);
        loadNamesFromAssets();
        loadNextSet();
    }


    private void loadNamesFromAssets() {
        try {
            StringBuilder buf = new StringBuilder();
            InputStream json = getAssets().open("names.json");
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                buf.append(str);
            }

            in.close();

            Log.d("DATA", "" + buf.toString());
            JSONArray jsonArray = new JSONArray(buf.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                namesList.add(object.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }




        //Log.d("LIST_SIZE", "" + b);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_previous:
                loadPreviousSet();
                break;
            case R.id.btn_next:
                loadNextSet();
        }
    }

    private void loadNextSet() {

      /* *//* if(currentPage == 0){
            btnPrevious.setVisibility(View.INVISIBLE);
        }*//*
        float a = namesList.size()/5;
        //System.out.println(Math.ceil(a / 100));
        int b = Math.round(a);


        if (currentPage >= b) {

            btnNext.setVisibility(View.INVISIBLE);
            return;
        }*/

        if (currentPage >= 5) {
            //btnPrevious.setVisibility(View.INVISIBLE);
            return;
        }

        try {
            ++currentPage;
            listView.removeAllViews();
            updatePaginationParams();
            List<String> subList = namesList.subList(from, to);
            for (String name : subList) {
                TextView textView = new TextView(this);
                textView.setText(name);
                listView.addView(textView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPreviousSet() {
        if (currentPage <= 0) {
            //btnPrevious.setVisibility(View.INVISIBLE);
            return;
        }
        --currentPage;
        listView.removeAllViews();
        updatePaginationParams();
        List<String> subList = namesList.subList(from, to);
        for (String name : subList) {
            TextView textView = new TextView(this);
            textView.setText(name);
            listView.addView(textView);
        }
    }

    private void updatePaginationParams() {

        from = Math.max(0, currentPage * pageSize);
        to = Math.min(namesList.size(), (currentPage + 1) * pageSize);
    }
}
