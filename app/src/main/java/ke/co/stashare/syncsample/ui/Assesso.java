package ke.co.stashare.syncsample.ui;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import ke.co.stashare.syncsample.R;
import ke.co.stashare.syncsample.helper.AppController;
import ke.co.stashare.syncsample.navigator.*;
import ke.co.stashare.syncsample.survey.helper.DatabaseHelper;
import ke.co.stashare.syncsample.survey.helper.UploadLst;


/**
 * Created by Ken Wainaina on 29/08/2017.
 */

public class Assesso extends AppCompatActivity  implements View.OnClickListener  {

    private static final String IMPORT_DB_CREATETABLE = "http://104.236.111.61/testApi/createTable.php";
    LinearLayout new_assess;
    private Handler mHandler;
    List<Colvalues> getUploadList2;
    List<Colvalues> getUploadList;
    ProgressDialog progressDialog2;
    private Toolbar mToolbar;
    String survey_idno;
    String col;
    String colVal;

    private List<String>strtTimeDateCol;
    private DatabaseHelper db;
    private List<String> strtTimeDateValues;
    private String[] startTimeDateCol;
    private String[]startTimeDateValues;
    private String assess_idno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_assess);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mHandler = new Handler();

        db = new DatabaseHelper(this);

        strtTimeDateCol = new ArrayList<>();

        strtTimeDateValues= new ArrayList<>();


        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        //Intent getTables = getIntent();

        getUploadList = new ArrayList<>();
        getUploadList2 = new ArrayList<>();


        new_assess = (LinearLayout)findViewById(R.id.assessment_new);
        new_assess.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.assessment_new:
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                Intent getSuv_id = getIntent();

                survey_idno = getSuv_id.getStringExtra("idi");
                assess_idno = getSuv_id.getStringExtra("assessID");

                Intent intent = new Intent(Assesso.this, QuestionPage.class);


                int randomNumber = random_num();

                String randomi = Integer.toString(randomNumber);

                AppController.getInstance().addRando(randomi);

                Log.d("randomNumber", String.valueOf(randomNumber));

                createTimeStamp();

                intent.putExtra("idi",survey_idno);
                intent.putExtra("assessID",assess_idno);

                startActivity(intent);
                finish();

                break;
        }
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


    //Creating option menu to add logout feature
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adding our menu to toolbar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //Adding logout option here
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuUpload) {
            Runnable mPendingRunnable = new Runnable() {
                @Override
                public void run() {
                    UploadLst uploadLst = get_UploadList_From_Shared_Prefs(Assesso.this,"uploadList");

                    getUploadList.clear();

                    getUploadList = uploadLst.getResults();


                    for(Colvalues colva: getUploadList){
                        String columns= colva.getCol();
                        String values = colva.getValues();

                        Log.d("ASSESS_COL", columns);

                        Log.d("ASSESS_COLVAL", values);
                        UploadDb(DatabaseHelper.SECTION_ANSWERS,columns,values);
                    }

                    UploadLst uploadLst2 = get_UploadList_From_Shared_Prefs(Assesso.this,"uploadList2");

                    getUploadList2.clear();

                    getUploadList2 = uploadLst2.getResults();


                    for(Colvalues colva: getUploadList2){
                        String columns= colva.getCol();
                        String values = colva.getValues();

                        Log.d("ASSESS_GENCOL", columns);

                        Log.d("ASSESS_GENCOL", values);

                        UploadDb(DatabaseHelper.GENERALINFO_ANSWERS,columns,values);
                    }
                }
            };

            // If mPendingRunnable is not null, then add to the message queue
            mHandler.post(mPendingRunnable);


        }
        else if (id == R.id.bck_arr) {
            Intent intent = new Intent(Assesso.this, SelectSurvey.class);

            startActivity(intent);

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void UploadDb(final String tbl, final String col, final String colVal) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading Records to Database");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, IMPORT_DB_CREATETABLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();

                        /*if(error!=null && error.getMessage() !=null){
                            Toast.makeText(getApplicationContext(),"error VOLLEY "+error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();

                        }*/

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("questions", col);
                params.put("answers", colVal);
                params.put("table_name", tbl);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);



    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public UploadLst get_UploadList_From_Shared_Prefs(Context context,String listName) {

        List<Colvalues> results = new ArrayList<>();

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString(listName, "");

        UploadLst uploadLst = new UploadLst(results);

        if(!Objects.equals(json, "")){

            uploadLst = gson.fromJson(json, UploadLst.class);
        }

        return uploadLst;
    }


    public void back_assessDash(View view) {


    }
}
