package ke.co.stashare.syncsample.navigator;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ke.co.stashare.syncsample.R;
import ke.co.stashare.syncsample.helper.AppController;
import ke.co.stashare.syncsample.survey.helper.Col;
import ke.co.stashare.syncsample.survey.helper.DatabaseHelper;
import ke.co.stashare.syncsample.survey.helper.Que;
import ke.co.stashare.syncsample.survey.helper.SavedData;
import ke.co.stashare.syncsample.ui.QuestionPage;
import ke.co.stashare.syncsample.ui.StartAssessment;
import ke.co.stashare.syncsample.ui.StartSurvey;

/**
 * Created by Ken Wainaina on 12/08/2017.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    private static final int NUMBER_OF_PAGES = 10;
    private static final String TAG = "Pager";

    public static final String IMPORT_DB_GENQCOL = "http://104.236.111.61/testApi/GetGenQueCol.php";
    public static final String IMPORT_DB_QCOL = "http://104.236.111.61/testApi/GetQueCol.php";
    public static final String IMPORT_DB_SUBQCOL = "http://104.236.111.61/testApi/GetSubQueCol.php";

    public static final String IMPORT_DB_GETGENINFO = "http://104.236.111.61/testApi/GetGenInfo.php";
    public static final String IMPORT_DB_GETQUE = "http://104.236.111.61/testApi/GetQuestions.php";
    public static final String IMPORT_DB_GETSUBQUE = "http://104.236.111.61/testApi/GetSubQue.php";




    List<String>tables_name;
    List<Que> sample;
    List<String>questions_col;
    List<String>genquestions_col;
    List<String>subquestion_col;
    List<String> que_feed;
    List<String> genque_feed;
    List<String>temp_quefeed;
    List<String> subque_feed;
    Button assessment;

    private Toolbar mToolbar;
    //database helper object
    private DatabaseHelper db;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_assessment);

        tables_name = new ArrayList<>();
        questions_col = new ArrayList<>();
        subquestion_col = new ArrayList<>();
        genquestions_col = new ArrayList<>();
        que_feed = new ArrayList<>();
        genque_feed = new ArrayList<>();
        sample = new ArrayList<>();
        subque_feed = new ArrayList<>();

        db = new DatabaseHelper(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        assessment = (Button)findViewById(R.id.assessment_start);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Importing database,please wait...");
        progressDialog.show();

        importDb();
        //Adding listener to button
        assessment.setOnClickListener(this);
    }

    private void importDb() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, IMPORT_DB_GENQCOL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        genquestions_col.clear();
                        try {
                            JSONObject res = new JSONObject(response);
                            JSONArray que_col = res.getJSONArray("genquestions_col");

                            for (int i = 0; i < que_col.length(); i++) {
                                JSONObject obj = que_col.getJSONObject(i);

                                String col = obj.getString("COLUMN_NAME");

                                genquestions_col.add(col);
                            }

                            sourceGenQuestions();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        AppController.getInstance().addToRequestQueue(stringRequest);


        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, IMPORT_DB_QCOL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        questions_col.clear();
                        try {
                            JSONObject res = new JSONObject(response);
                            JSONArray que_col = res.getJSONArray("questions_col");

                            for (int i = 0; i < que_col.length(); i++) {
                                JSONObject obj = que_col.getJSONObject(i);

                                String col = obj.getString("COLUMN_NAME");

                                questions_col.add(col);
                            }

                            //now that i have the columns, source the questions and create a table
                            sourceQuestions();

                          /*  Col col = new Col(questions_col);

                            save_Columns_To_Shared_Prefs(getApplicationContext(), col);
*/
                           /* if(questions_col.size()>0) {
                                Col col = new Col(questions_col);

                                save_Columns_To_Shared_Prefs(getApplicationContext(), col);
                            }*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        AppController.getInstance().addToRequestQueue(stringRequest2);

        StringRequest stringRequest3 = new StringRequest(Request.Method.GET, IMPORT_DB_SUBQCOL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject res = new JSONObject(response);
                            JSONArray subque_col = res.getJSONArray("subquestions_col");

                            for (int i = 0; i < subque_col.length(); i++) {
                                JSONObject obj = subque_col.getJSONObject(i);

                                String col = obj.getString("COLUMN_NAME");

                                subquestion_col.add(col);


                            }

                            //now that i have the columns, source the sub_questions and create a table

                            sourceSubQuestions();
                            /*Col col = new Col(subquestion_col);

                            save_Subque_Columns_To_Shared_Prefs(getApplicationContext(), col);

*/


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        AppController.getInstance().addToRequestQueue(stringRequest3);

        Log.d("COL_QUE", String.valueOf(questions_col));
        Log.d("COL_SUBQUE", String.valueOf(subquestion_col));

    }


    private void sourceGenQuestions(){

        StringRequest stringRequestGetQue = new StringRequest(Request.Method.GET, IMPORT_DB_GETGENINFO,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject res = new JSONObject(response);
                            JSONArray que = res.getJSONArray("genquestions");

                            for (int i = 0; i < que.length(); i++) {
                                JSONObject obj = que.getJSONObject(i);

                                for(int j = 0; j < genquestions_col.size(); j++){

                                    String tbl = obj.getString(genquestions_col.get(j));

                                    if (Objects.equals(tbl, "")){
                                        tbl="none";
                                    }

                                    Log.d("GEN_COL", String.valueOf(tbl));
                                    genque_feed.add(tbl);
                                }
                                Log.d("GENQUE_FEED", String.valueOf(genque_feed));

                                Log.d("GENQUE_COL", String.valueOf(genquestions_col));
                                db.CreateTableRow (DatabaseHelper.GENERALINFO_TABLE, genquestions_col,genque_feed);
                                genque_feed.clear();


                            }


                            //loadQue(DatabaseHelper.QUE_TABLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        AppController.getInstance().addToRequestQueue(stringRequestGetQue);

    }

    private void sourceQuestions(){
        StringRequest stringRequestGetQue = new StringRequest(Request.Method.GET, IMPORT_DB_GETQUE,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject res = new JSONObject(response);
                            JSONArray que = res.getJSONArray("questions");

                            for (int i = 0; i < que.length(); i++) {
                                JSONObject obj = que.getJSONObject(i);

                                for(int j = 0; j < questions_col.size(); j++){

                                    String tbl = obj.getString(questions_col.get(j));

                                    if (Objects.equals(tbl, "")){
                                        tbl="none";
                                    }

                                    Log.d("JSON_OBJ", String.valueOf(tbl));
                                    que_feed.add(tbl);
                                }
                                Log.d("LOADED_QUE_FEED", String.valueOf(que_feed));
                                db.CreateTableRow (DatabaseHelper.QUE_TABLE,questions_col,que_feed);
                                que_feed.clear();

                                //db.CreateTableRow (DatabaseHelper.QUE_TABLE,questions_col,que_feed);
                                //que_feed.clear();

                                Log.d("LOADED_TBL", String.valueOf(DatabaseHelper.QUE_TABLE));
                                Log.d("LOADED_QUECOL", String.valueOf(questions_col));

                            }


                            //loadQue(DatabaseHelper.QUE_TABLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        AppController.getInstance().addToRequestQueue(stringRequestGetQue);

    }

    private void sourceSubQuestions(){
        StringRequest stringRequestGetSubque = new StringRequest(Request.Method.GET, IMPORT_DB_GETSUBQUE,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        try {
                            JSONObject res = new JSONObject(response);
                            JSONArray subque = res.getJSONArray("sub_questions");

                            for (int i = 0; i < subque.length(); i++) {

                                JSONObject obj = subque.getJSONObject(i);

                                for(int j = 0; j < subquestion_col.size(); j++){

                                    String tbl = obj.getString(subquestion_col.get(j));

                                    if (Objects.equals(tbl, "")){
                                        tbl="none";
                                    }

                                    Log.d("JSON_OBJ_SUB", String.valueOf(tbl));
                                    subque_feed.add(tbl);
                                }

                                Log.d("LOADED_SUB_QUE_FEED", String.valueOf(subque_feed));

                                db.CreateTableRow (DatabaseHelper.SUBQUE_TABLE,subquestion_col,subque_feed);
                                subque_feed.clear();


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        AppController.getInstance().addToRequestQueue(stringRequestGetSubque);


    }



    @Override
    public void onClick(View v) {
     if (v == assessment) {
         Intent intent = new Intent(MainActivity.this, StartAssessment.class);
         startActivity(intent);
         finish();
     }
    }
}
