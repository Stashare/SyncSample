package ke.co.stashare.syncsample.ui;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ke.co.stashare.syncsample.R;
import ke.co.stashare.syncsample.helper.AppController;
import ke.co.stashare.syncsample.survey.helper.DatabaseHelper;
import ke.co.stashare.syncsample.survey.helper.Que;
import ke.co.stashare.syncsample.survey.helper.Selected_SUVLIST;
import ke.co.stashare.syncsample.ui.models.Surveys;
import ke.co.stashare.syncsample.ui.models.SuvModel;
import ke.co.stashare.syncsample.ui.models.Suv_Adapter;

import static ke.co.stashare.syncsample.navigator.MainActivity.IMPORT_DB_GETGENINFO;
import static ke.co.stashare.syncsample.navigator.MainActivity.IMPORT_DB_GETQUE;

/**
 * Created by Ken Wainaina on 11/09/2017.
 */

public class SelectSurvey extends AppCompatActivity {

    private Spinner spinner;

    private List<Surveys> suvs;
    List<String> addedSurvies;
    private List<String> suvs_spinner;
    Suv_Adapter suv_adapter;
    Button fetchQue;
    ArrayAdapter<String> arrayAdapter;
    List<SuvModel> saveSuv_listo;
    List<String> addedSv;
    List<String>tables_name;
    List<Que> sample;
    List<String>questions_col;
    List<String>genquestions_col;
    List<String>subquestion_col;
    List<String> que_feed;
    List<SuvModel> get_savedList;
    List<String> genque_feed;
    List<String>temp_quefeed;
    List<String> subque_feed;

    private List<String>strtTimeDateCol;
    private List<String> strtTimeDateValues;
    private String[] startTimeDateCol;
    private String[]startTimeDateValues;

    public static final String URL_FETCH_SURVEYS = "http://104.236.111.61/testApi/getSurveys.php";

    private DatabaseHelper db;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiary__surveys);

        spinner = (Spinner) findViewById(R.id.spinnerSurveys);
        fetchQue = (Button)findViewById(R.id.fetchquiz);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Choose Survey");

        get_savedList= new ArrayList<>();
        spinner.setOnItemSelectedListener(new MySpinner());

        saveSuv_listo=new ArrayList<>();
        suvs = new ArrayList<>();
        suvs_spinner = new ArrayList<>();
        addedSv = new ArrayList<>();
        addedSurvies = new ArrayList<>();

        tables_name = new ArrayList<>();
        questions_col = new ArrayList<>();
        subquestion_col = new ArrayList<>();
        genquestions_col = new ArrayList<>();
        que_feed = new ArrayList<>();
        genque_feed = new ArrayList<>();
        sample = new ArrayList<>();
        subque_feed = new ArrayList<>();

        strtTimeDateCol = new ArrayList<>();

       strtTimeDateValues= new ArrayList<>();


        db = new DatabaseHelper(this);

        loadSurveys();

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void proceedAssessment(View view) {

        //List<String> get_savedList2= new ArrayList<>();
        get_savedList.clear();

        String selItem = spinner.getSelectedItem().toString();
        int pos= spinner.getSelectedItemPosition();

        Selected_SUVLIST suvList = get_Suvlist_From_Shared_Prefs(getApplicationContext());

        //get saved list first.
        //addedSv.clear();

        get_savedList = suvList.getResults();


            if (!Objects.equals(selItem, "Click to Select Survey")) {

                String itemSel = suvs.get(pos).getSuv_id();

                Toast.makeText(getApplicationContext(), itemSel, Toast.LENGTH_LONG).show();

                loadQuestions(itemSel);

                SuvModel suviM = new SuvModel(itemSel, selItem);

                saveSuv_listo.add(suviM);

                Selected_SUVLIST ssv = new Selected_SUVLIST(saveSuv_listo);

                save_Suvlist_To_Shared_Prefs(getApplicationContext(), ssv);

        }
        suvs_spinner.clear();
        suvs.clear();
        loadSurveys();
        promptNice();
        saveSuv_listo.clear();

        get_savedList.clear();

    }

    private void promptNice() {

        LayoutInflater inflater = getLayoutInflater();

        final AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);


        alertDialogBuilderUserInput.setMessage("Nice! download successful, click the green arrow" +
                " below to select a survey you wish to conduct");

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        // ToDo get user input here
                        dialogBox.dismiss();
                    }
                });
        AlertDialog alertDialogAndroid= alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

    private void loadQuestions(String itemSel) {

       /* genquestions_col.clear();
        genquestions_col.add("survey_id"); genquestions_col.add("question_id"); genquestions_col.add("question_name");
        genquestions_col.add("answer_type"); genquestions_col.add("column_name"); genquestions_col.add("question_description");
        genquestions_col.add("section_id");
        sourceGenQuestions(itemSel);*/

        questions_col.clear();

        questions_col.add("survey_id");questions_col.add("question_id");

        questions_col.add("question_name"); questions_col.add("question_description");questions_col.add("answer_type");
        questions_col.add("sub_question_status"); questions_col.add("sub_section");
        questions_col.add("column_name");questions_col.add("parent_question_id");
        questions_col.add("assessment_id");questions_col.add("assessment_name");questions_col.add("country_status");
        questions_col.add("display_status");questions_col.add("submitted_date");questions_col.add("selections");
        questions_col.add("section_name"); questions_col.add("project_id");

        //now that i have the columns, source the questions and create a table
        sourceQuestions(itemSel);
    }
    private void sourceQuestions(final String item){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sourcing Questions...");
        progressDialog.show();

        StringRequest stringRequestGetQue = new StringRequest(Request.Method.POST, IMPORT_DB_GETQUE,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("suv_id", item);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequestGetQue);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void promptDialog(View view) {


        List<String>a_id = new ArrayList<>();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.radio_lay, null);
        dialogBuilder.setView(dialogView);

        final RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.survey_item_recycler);

        Selected_SUVLIST suvList = get_Suvlist_From_Shared_Prefs(getApplicationContext());


        saveSuv_listo.clear();

        saveSuv_listo = suvList.getResults();

        addedSv.clear();

        Log.d("SIZE", String.valueOf(saveSuv_listo.size()));

        for(SuvModel suvmdl: saveSuv_listo){

            String survey= suvmdl.getSurvey_name();
            String survey_id= suvmdl.getId();

            a_id.add(survey_id);

            addedSv.add(survey);
        }


        suv_adapter = new Suv_Adapter(SelectSurvey.this, addedSv, a_id,strtTimeDateCol,strtTimeDateValues,db);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(suv_adapter);

        final AlertDialog b = dialogBuilder.create();
        b.show();


    }

    private class MySpinner implements AdapterView.OnItemSelectedListener{

        public MySpinner(){}

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if(Objects.equals(suvs_spinner.get(position), "Click to Select Survey")&& Objects.equals(suvs.get(position).getSuv_id(), "0")) {
                String itemSel = suvs.get(position).getSuv_id();

                fetchQue.setVisibility(View.GONE);
            }else{
                fetchQue.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private void loadSurveys() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Surveys...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_FETCH_SURVEYS,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                JSONArray jsonDevices = obj.getJSONArray("surveys");

                                for (int i = 0; i < jsonDevices.length(); i++) {
                                    JSONObject d = jsonDevices.getJSONObject(i);
                                    String sid= d.getString("survey_id");
                                    String sname= d.getString("name");

                                    Surveys surveys = new Surveys(sid,sname);
                                    suvs.add(surveys);
                                    suvs_spinner.add(surveys.getSuv_name());
                                }


                                Surveys surveys = new Surveys("0","heading");
                                suvs.add(0,surveys);
                                suvs_spinner.add(0,"Click to Select Survey");

                               aaa(suvs_spinner);

                                spinner.setAdapter(arrayAdapter);
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
                }) {

        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void aaa(final List<String>ccc){

        Selected_SUVLIST suvList = get_Suvlist_From_Shared_Prefs(getApplicationContext());

        saveSuv_listo.clear();

        saveSuv_listo = suvList.getResults();

        // Initializing an ArrayAdapter
          arrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item, ccc){
            @Override
            public boolean isEnabled(int position){


                for(SuvModel suvModel: saveSuv_listo) {

                    String savee = suvModel.getSurvey_name();
                    if (Objects.equals(ccc.get(position), savee)) {
                        return false;
                    }
                }

                return true;

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public View getDropDownView(int position, View convertView,
                                        @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                 tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));


                    for (SuvModel suvMod : saveSuv_listo) {
                        String namee=suvMod.getSurvey_name();

                        if (Objects.equals(ccc.get(position), namee)) {

                            tv.setTextColor(Color.GRAY);

                        }

                    }
                return view;
            }
        };

    }

    public void save_Suvlist_To_Shared_Prefs(Context context, Selected_SUVLIST sel_List) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(sel_List);
        prefsEditor.putString("selected_suvlist", json);
        prefsEditor.apply();

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Selected_SUVLIST get_Suvlist_From_Shared_Prefs(Context context) {

        List<SuvModel> results = new ArrayList<>();

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("selected_suvlist", "");

        Selected_SUVLIST dbList = new Selected_SUVLIST(results);

        if (!Objects.equals(json, "")) {

            dbList = gson.fromJson(json, Selected_SUVLIST.class);
        }

        return dbList;
    }

}
