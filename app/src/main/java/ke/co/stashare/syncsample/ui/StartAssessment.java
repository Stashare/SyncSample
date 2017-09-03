package ke.co.stashare.syncsample.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import ke.co.stashare.syncsample.R;
import ke.co.stashare.syncsample.helper.AppController;
import ke.co.stashare.syncsample.navigator.*;
import ke.co.stashare.syncsample.survey.helper.DatabaseHelper;
import ke.co.stashare.syncsample.survey.helper.GenQuiz;
import ke.co.stashare.syncsample.survey.helper.GeneralInfoAdapter;
import ke.co.stashare.syncsample.survey.helper.MyEditTextAdapter;
import ke.co.stashare.syncsample.survey.helper.Quiz;

/**
 * Created by Ken Wainaina on 28/08/2017.
 */

public class StartAssessment  extends AppCompatActivity implements View.OnClickListener {

    GeneralInfoAdapter generalInfoAdapter;
    RecyclerView recyclerView;
    //database helper object
    private DatabaseHelper db;
    List<String> adapterList;
    String[] mDataSet;
    String[]que_titles;
    String[] startTimeDateCol;
    String[]startTimeDateValues;

    List<String>strtTimeDateCol;
    List<String> strtTimeDateValues;

    String[]hints;
    Button proceed;
    List<GenQuiz> sampleAns;
    List<GenQuiz> sample;
    String[] headers;
    private Handler mHandler;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_respodent);

        sample = new ArrayList<>();
        sampleAns = new ArrayList<>();
        adapterList = new ArrayList<>();
        strtTimeDateCol =new ArrayList<>();
        strtTimeDateValues = new ArrayList<>();

        db = new DatabaseHelper(this);
        proceed = (Button) findViewById(R.id.proceed);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mHandler = new Handler();

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {

                loadGenQue(DatabaseHelper.GENERALINFO_TABLE,DatabaseHelper.QUE);


                String[] mDataSet = new String[sample.size()];

                String[] headers = new String[sample.size()];

                que_titles = new String[sample.size()];
                hints = new String[sample.size()];


                for (int i = 0; i < sample.size(); i++) {

                    String sel = sample.get(i).getQue();
                    String title = sample.get(i).getQue_title();
                    String hint = sample.get(i).getHint();

                    headers[i] = sel;

                    que_titles[i]=title;
                    hints[i] = hint;

                }

                int randomNumber = random_num();

                String rando = Integer.toString(randomNumber);

                Log.d("randomNumber", String.valueOf(randomNumber));

                generalInfoAdapter = new GeneralInfoAdapter(db, getApplicationContext(), headers,que_titles, hints,mDataSet, rando);

                recyclerView.setAdapter(generalInfoAdapter);

            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        mHandler.post(mPendingRunnable);


        proceed.setOnClickListener(this);


        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(llm);



    }

    private int random_num() {
        long timeSeed = System.nanoTime(); // to get the current date time value

        double randSeed = Math.random() * 1000; // random number generation

        long midSeed = (long) (timeSeed * randSeed); // mixing up the time and
        // rand number.

        // variable timeSeed
        // will be unique


        // variable rand will
        // ensure no relation
        // between the numbers

        String s = midSeed + "";
        String subStr = s.substring(0, 9);

        int finalSeed = Integer.parseInt(subStr);    // integer value

        return finalSeed;

    }


    private void loadGenQue(String tbl, String order_col) {

        sample.clear();
        Cursor cursor = db.getAnyQue(tbl, order_col);
        if (cursor.moveToFirst()) {
            do {
                GenQuiz genQuiz = new GenQuiz(
                        cursor.getString(cursor.getColumnIndex(String.valueOf("que"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("que_title"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("hint")))
                );
                sample.add(genQuiz);
            } while (cursor.moveToNext());
        }

    }

    private void loadGenQueAns(String tbl, String order_col) {

        sampleAns.clear();
        Cursor cursor = db.getGenQueAns(tbl);
        int count = cursor.getCount();

        Log.d("GEN_ANS_COUNT", String.valueOf(count));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.proceed:
                //loadGenQueAns("geninfo_answers",DatabaseHelper.QUE);

                String user_id = AppController.getInstance().getGenRandom();

                DateFormat assessCalendar = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);
                DateFormat assessTime = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
                //DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm", Locale.ENGLISH);
                String date = assessCalendar.format(Calendar.getInstance().getTime());
                String startTime = assessTime.format(Calendar.getInstance().getTime());


                try {
                    Date date_start = assessTime.parse(startTime);

                    Log.d("Parsed date", "date: "+ date_start.getTime());

                    AppController.getInstance().addStartTime(date_start.getTime());

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //int s = Integer.parseInt(startTime);




                strtTimeDateCol.add("user_id");
                strtTimeDateCol.add("Assessment_start_time");
                strtTimeDateCol.add("Assessment_date");

                strtTimeDateValues.add(user_id);
                strtTimeDateValues.add(startTime);
                strtTimeDateValues.add(date);


                startTimeDateCol = new String[strtTimeDateCol.size()];

                startTimeDateValues = new String[strtTimeDateValues.size()];

                startTimeDateCol = strtTimeDateCol.toArray(startTimeDateCol);

                startTimeDateValues=  strtTimeDateValues.toArray(startTimeDateValues);

                db.createGenInfoTable("geninfo_answers", startTimeDateCol,startTimeDateValues);


                //Time of Assessment (Start),Time of Assessment (Finish),Time of Assessment (Total)-in minutes.

                //GET ASSESSMENT START TIME,ASSESSMENT CALENDAR IN  (DD/MM/YYYY) ,STORE IT KWA DB
                //CHECK WHETHER THE CURRENT GENINFO HAS THE COLUMNS GIVEN,KA HAINA CREATE AND FEED IT NA DATA
                Intent intent = new Intent(StartAssessment.this, QuestionPage.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
