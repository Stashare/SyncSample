package ke.co.stashare.syncsample.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import ke.co.stashare.syncsample.R;
import ke.co.stashare.syncsample.helper.AppController;
import ke.co.stashare.syncsample.navigator.*;
import ke.co.stashare.syncsample.survey.helper.Answers;
import ke.co.stashare.syncsample.survey.helper.DatabaseHelper;
import ke.co.stashare.syncsample.survey.helper.DbList;
import ke.co.stashare.syncsample.survey.helper.Que;
import ke.co.stashare.syncsample.survey.helper.QueFragmentPagerAdapter;
import ke.co.stashare.syncsample.survey.helper.Quiz;
import ke.co.stashare.syncsample.survey.helper.RandList;
import ke.co.stashare.syncsample.survey.helper.UploadLst;

/**
 * Created by Ken Wainaina on 19/08/2017.
 */

public class QuestionPage  extends AppCompatActivity implements View.OnClickListener  {

    private ViewPager mViewPager;
    ImageView prev;
    ImageView next;
    Button savie;

    private Toolbar mToolbar;

    private TextView toolbar_text;
    TextView subsection_text;

    ArrayList<Quiz> sample;
    List<Colvalues> coluValue;
    List<String> rando;
    private QueFragmentPagerAdapter mMyFragmentPagerAdapter;
    private DatabaseHelper db;
    String[] qsnList;
    List<String>user_ids;
    String[] ansList;
    List <String> temp_hh;
    String[] qsnList_temp;
    List<String> hh;
    String[] ansList_temp;
    List<String> qList_temp;
    String[] temp_colNames;
    List<String> aList_temp;
    String[] valueof_Colms;
    ProgressDialog progressDialog2;
    RelativeLayout saveBtn;
    private List<Answers> temp_ans;
   ProgressDialog progressDialog;
    public static final String IMPORT_DB_CREATETABLE = "http://104.236.111.61/testApi/createTable.php";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_page);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        progressDialog2 = new ProgressDialog(this);
        progressDialog2.setMessage("Uploading Records to Database");



        mViewPager = (ViewPager) findViewById(R.id.viewpager);


        progressDialog = new ProgressDialog(this);

        sample = new ArrayList<>();
        user_ids = new ArrayList<>();
        temp_hh = new ArrayList<>();
        temp_ans = new ArrayList<>();
        qList_temp = new ArrayList<>();
        aList_temp = new ArrayList<>();
        rando = new ArrayList<>();
        db = new DatabaseHelper(this);
        hh = new ArrayList<>();
        coluValue = new ArrayList<>();


        prev = (ImageView) findViewById(R.id.back);
        next = (ImageView) findViewById(R.id.next);
        savie = (Button) findViewById(R.id.save_data);
        saveBtn = (RelativeLayout)findViewById(R.id.saveBtn_lay);

        loadQue(DatabaseHelper.QUE_TABLE,DatabaseHelper.QUE);

        for (int i=0; i<sample.size(); i++){

            Log.d("QP_LIST", String.valueOf(sample.get(i).getQue_type()));

        }

        mMyFragmentPagerAdapter = new QueFragmentPagerAdapter(
                getSupportFragmentManager(), sample);

        mViewPager.setAdapter(mMyFragmentPagerAdapter);

        mViewPager.setOffscreenPageLimit(sample.size());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("CURRENT", String.valueOf(position));

                if (mViewPager.getCurrentItem() == (sample.size()-1)) {
                    next.setVisibility(View.INVISIBLE);
                    prev.setVisibility(View.VISIBLE);
                    saveBtn.setVisibility(View.VISIBLE);

                    /*new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            saveBtn.setVisibility(View.VISIBLE);
                        }
                    });*/
                } else {
                    saveBtn.setVisibility(View.INVISIBLE);
                    next.setVisibility(View.VISIBLE);

                }

                if (mViewPager.getCurrentItem() == 0) {
                    prev.setVisibility(View.INVISIBLE);
                    next.setVisibility(View.VISIBLE);
                } else {
                    prev.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        Log.d("CURRENT", String.valueOf(mViewPager.getCurrentItem()));

        if (mViewPager.getCurrentItem() == 0) {
            prev.setVisibility(View.INVISIBLE);
            next.setVisibility(View.VISIBLE);
        }

        if (mViewPager.getCurrentItem() == (sample.size()-2)) {
            next.setVisibility(View.INVISIBLE);
            prev.setVisibility(View.VISIBLE);
        }


        next.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        prev.setVisibility(View.VISIBLE);
                        if (mViewPager.getCurrentItem() == sample.size()-2) {
                            next.setVisibility(View.INVISIBLE);
                        }

                        mViewPager.setCurrentItem(getItem(+1), true); //getItem(-1) for previous

                        //Log.d("CURRENT", String.valueOf(mViewPager.getCurrentItem()));
                    }
                });

        prev.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                next.setVisibility(View.VISIBLE);

                if (mViewPager.getCurrentItem() == 1) {
                    prev.setVisibility(View.INVISIBLE);
                }
                mViewPager.setCurrentItem(getItem(-1), true); //getItem(-1) for previous
                //Log.d("CURRENT", String.valueOf(mViewPager.getCurrentItem()));
            }
        });

        savie.setOnClickListener(this);

    }

    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }


    private void loadQue(String tbl,String order_col) {

        sample.clear();
        Cursor cursor = db.getQue(tbl, order_col);
        if (cursor.moveToFirst()) {
            do {
                Quiz quiz = new Quiz(
                        cursor.getString(cursor.getColumnIndex(String.valueOf("que"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("que_no"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("is_subquestion"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("que_type"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("singleresponse_text"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("selections"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("section"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("sub_section")))
                );
                sample.add(quiz);
            } while (cursor.moveToNext());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.save_data:
                DbList dbL = get_DbList_From_Shared_Prefs(QuestionPage.this);

                temp_ans.clear();

                temp_ans = dbL.getResults();

                qsnList = new String[temp_ans.size()];
                ansList = new String[temp_ans.size()];

                for (int i = 0; i < temp_ans.size(); i++) {

                    String qsn = temp_ans.get(i).getQue();

                    String new_qsn = qsn.replace(".", "_");

                    String n_qsn= new_qsn.toLowerCase();

                    qsnList[i] = n_qsn;

                }


                for (int i = 0; i < temp_ans.size(); i++) {

                    String asw = temp_ans.get(i).getAns();

                    //String new_ans = asw.replace(",", "|");
                   /* if (Objects.equals(asw, "")||Objects.equals(asw, null)) {

                        asw = "none";
                    }*/

                    ansList[i] = asw;

                }

                Collections.addAll(qList_temp, qsnList);

                Collections.addAll(aList_temp, ansList);

              //qList_temp  = Arrays.asList(qsnList);
                //aList_temp = Arrays.asList(ansList);

                user_ids.add("1");

                String r_id = AppController.getInstance().getGenRandom();

                RandList randList = get_RandomList_From_Shared_Prefs(getApplicationContext());

                user_ids.clear();

                user_ids = randList.getResults();

                user_ids.add(r_id);

                RandList dbLis = new RandList(user_ids);

                save_RandomList_To_Shared_Prefs(getApplicationContext(), dbLis);


                qList_temp.add(0,"user_id");

                aList_temp.add(0,r_id);

                qsnList_temp = new String[qList_temp.size()];
                ansList_temp = new String[aList_temp.size()];

                qsnList_temp  = qList_temp.toArray(qsnList_temp);
                ansList_temp = aList_temp.toArray(ansList_temp);

                Log.d("QSNS", Arrays.toString(qsnList_temp));

                Log.d("ANSW", Arrays.toString(ansList_temp));

                Cursor c = db.createUsersTable("section_answers",qsnList_temp,ansList_temp);

                Log.d("C_SIZE", String.valueOf(c.getCount()));


                for(String id_num : user_ids){

                        Cursor colmns = db.getColumns("section_answers", id_num);

                        String[] colNames = colmns.getColumnNames();

                       temp_hh = new ArrayList<>();

                        hh = Arrays.asList(colNames);



                    for(int w=0; w < hh.size();w++){
                        String sel = hh.get(w);

                        if(!(Objects.equals(sel, "_id"))){

                            temp_hh.add(sel);
                        }
                    }

                    temp_colNames = new String[(temp_hh.size())];

                    temp_colNames  = temp_hh.toArray(temp_colNames);

                    int count = colmns.getColumnCount();

                    valueof_Colms= new String[count];
                    Log.d("COL_COUNT", String.valueOf(count));

                    rando.clear();

                    if  (colmns.moveToFirst())
                    {
                        do
                        {
                            for (int i =1 ; i < count; i++)
                            {
                                //rando.add(colmns.getString(i));

                                rando.add(colmns.getString(colmns
                                        .getColumnIndex(colNames[i])));
                            }
                        }
                        while (colmns.moveToNext());
                    }

                    //Log.d("C_RANDO", String.valueOf(rando.size()));


                    String col = android.text.TextUtils.join("|", temp_colNames);
                    Log.d("C_COLNAMES", col);
                    String colVal = android.text.TextUtils.join("|", rando);
                    Log.d("C_COLVALUES", colVal);

                    Colvalues colvalues = new Colvalues(col,colVal);

                    coluValue.add(colvalues);

                    //createTable(DatabaseHelper.SECTION_ANSWERS, col, colVal);
                }
                Log.d("coluValue", String.valueOf(coluValue.size()));

                UploadLst uploadLst = new UploadLst(coluValue);

                save_UploadList_To_Shared_Prefs (getApplicationContext(), uploadLst);

                Intent intent = new Intent(QuestionPage.this, Assesso.class);
                startActivity(intent);
                finish();
                break;
        }


    }

    public void save_UploadList_To_Shared_Prefs(Context context,UploadLst dbList) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dbList);
        prefsEditor.putString("uploadList", json);
        prefsEditor.apply();

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public  DbList get_DbList_From_Shared_Prefs(Context context) {

        List<Answers> results = new ArrayList<>();

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("dblist", "");

        DbList dbList= new DbList(results);

        if(!Objects.equals(json, "")){

            dbList = gson.fromJson(json, DbList.class);
        }

        return dbList;
    }

    public void save_RandomList_To_Shared_Prefs(Context context,RandList dbList) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dbList);
        prefsEditor.putString("randoList", json);
        prefsEditor.apply();

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public  RandList get_RandomList_From_Shared_Prefs(Context context) {

        List<String> results = new ArrayList<>();

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("randoList", "");

        RandList dbList= new RandList(results);

        if(!Objects.equals(json, "")){

            dbList = gson.fromJson(json, RandList.class);
        }

        return dbList;
    }

    private void createTable(final String tbl, final String col, final String colVal) {

        progressDialog2.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, IMPORT_DB_CREATETABLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog2.hide();
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
                params.put("questions", col);
                params.put("answers", colVal);
                params.put("table_name", tbl);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);



    }


}
