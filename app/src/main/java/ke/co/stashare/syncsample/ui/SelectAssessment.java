package ke.co.stashare.syncsample.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import ke.co.stashare.syncsample.R;
import ke.co.stashare.syncsample.helper.AppController;
import ke.co.stashare.syncsample.survey.helper.DatabaseHelper;
import ke.co.stashare.syncsample.ui.models.AssessoModel;

/**
 * Created by Ken Wainaina on 15/09/2017.
 */

public class SelectAssessment extends AppCompatActivity {

    private List<String> suvs_spinner;
    private List<AssessoModel> sample;
    List<String>temp_spinner;

    ImageView fetchAssessment;
    private DatabaseHelper db;
    private String survey_idno;
    ArrayAdapter<String> arrayAdapter;
    Spinner spinner;

    private List<String>strtTimeDateCol;

    private List<String> strtTimeDateValues;

    String[] startTimeDateCol;
    String[]startTimeDateValues;
    private Toolbar mToolbar;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiary__assessments);

        Intent getSuv_id = getIntent();

        survey_idno = getSuv_id.getStringExtra("idi");

        Log.d("survey_idno", String.valueOf(survey_idno));

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Choose Assessment");


        spinner = (Spinner) findViewById(R.id.spinnerAssessments);

        fetchAssessment = (ImageView) findViewById(R.id.assess_arrow);


        db = new DatabaseHelper(this);
        sample = new ArrayList<>();


        strtTimeDateCol = new ArrayList<>();

        strtTimeDateValues= new ArrayList<>();

        suvs_spinner = new ArrayList<>();

        temp_spinner = new ArrayList<>();

        loadAssessments(DatabaseHelper.QUE_TABLE,DatabaseHelper.QUE, survey_idno);

        AssessoModel assessMod = new AssessoModel("0","0","assessName");

        sample.add(0,assessMod);

        for(AssessoModel assMod: sample){

            String assnem = assMod.getAssessment_name();

            if(!Objects.equals(assMod.getAssessment_name(), "assessName")){

                temp_spinner.add(assnem);
            }
        }

        for(String sss : temp_spinner){

            if(!suvs_spinner.contains(sss))
                suvs_spinner.add(sss);

        }


        suvs_spinner.add(0,"Click to Select Assessment");

        arrayAdapter = new ArrayAdapter<>(
                this, R.layout.spinner_item, suvs_spinner);

        spinner.setAdapter(arrayAdapter);



        spinner.setOnItemSelectedListener(new MySpinner());


        //sample.clear();
    }

    private void loadAssessments(String tbl,String order_col,String survey_id) {
        sample.clear();
        suvs_spinner.clear();
        Cursor cursor = db.getAssessments(tbl, order_col,survey_id);
        int counti =cursor.getCount();

        Log.d("counti", String.valueOf(counti));
        if (cursor.moveToFirst()) {
            do {
                AssessoModel quiz = new AssessoModel(
                        cursor.getString(cursor.getColumnIndex(String.valueOf("survey_id"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("assessment_id"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("assessment_name")))

                );
                sample.add(quiz);
            } while (cursor.moveToNext());
        }
            }

    private class MySpinner implements AdapterView.OnItemSelectedListener{

        public MySpinner(){}

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if(Objects.equals(suvs_spinner.get(position), "Click to Select Assessment")&& Objects.equals(sample.get(position).getAssessment_id(), "0")) {
                String itemSel = sample.get(position).getAssessment_id();

                fetchAssessment.setVisibility(View.GONE);
            }else{
                String itemSel = sample.get(position).getAssessment_id();
                Toast.makeText(SelectAssessment.this, itemSel, Toast.LENGTH_LONG).show();
                fetchAssessment.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void promptDialog(View view) {

        int position= spinner.getSelectedItemPosition();

        String assessID= sample.get(position).getAssessment_id();
        int randomNumber = random_num();

        String randomi = Integer.toString(randomNumber);

        AppController.getInstance().addRando(randomi);

        Log.d("randomNumber", String.valueOf(randomNumber));


        createTimeStamp();

        Intent intent = new Intent(SelectAssessment.this, QuestionPage.class);

        intent.putExtra("idi",survey_idno);
        intent.putExtra("assessID",assessID);

        startActivity(intent);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createTimeStamp(){
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
}