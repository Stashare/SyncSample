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
import android.support.v4.app.Fragment;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import ke.co.stashare.syncsample.ui.models.AddedSavedList;
import ke.co.stashare.syncsample.ui.models.Quiza;

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

    //ArrayList<Quiz> sample;
    ArrayList<Quiza> sample2;
    List<Colvalues> coluValue;
    List<Colvalues> gen_coluValue;
    List<String> rando;
    List<String> gen_rando;
    private QueFragmentPagerAdapter mMyFragmentPagerAdapter;
    private DatabaseHelper db;
    String[] qsnList;
    List<String>user_ids;
    String[] ansList;
    List <String> temp_hh;
    List <String> gentemp_hh;
    String[] qsnList_temp;
    List<String> hh;
    List<String> genhh;
    String[] ansList_temp;
    List<String> qList_temp;
    String[] temp_colNames;
    String[] temp_gencolNames;
    List<String> aList_temp;
    String survey_idno;
    String[] valueof_Colms;
    ProgressDialog progressDialog2;
    RelativeLayout saveBtn;
    private List<Answers> temp_ans;
    Handler mHandler;

    String[] startTimeDateCol;
    String[]startTimeDateValues;

    String[] qsnList_temp2;
    String[] ansList_temp2;
    List<String> qList_temp2;
    List<String> aList_temp2;



    List<String>listTimeDateCol;
    List<String> listTimeDateValues;

    List<String>strtTimeDateCol;
    List<String> strtTimeDateValues;


    ProgressDialog progressDialog;
    public static final String IMPORT_DB_CREATETABLE = "http://104.236.111.61/testApi/createTable.php";
    private String assess_idno;

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

        mHandler = new Handler();

        mViewPager = (ViewPager) findViewById(R.id.viewpager);


        progressDialog = new ProgressDialog(this);


        qList_temp2= new ArrayList<>();
        aList_temp2= new ArrayList<>();

        //sample = new ArrayList<>();
        sample2 = new ArrayList<>();
        user_ids = new ArrayList<>();
        temp_hh = new ArrayList<>();
        gentemp_hh = new ArrayList<>();
        temp_ans = new ArrayList<>();
        qList_temp = new ArrayList<>();
        aList_temp = new ArrayList<>();
        rando = new ArrayList<>();
        gen_rando= new ArrayList<>();
        db = new DatabaseHelper(this);
        hh = new ArrayList<>();
        genhh = new ArrayList<>();
        coluValue = new ArrayList<>();
        gen_coluValue = new ArrayList<>();

       listTimeDateCol= new ArrayList<>();
       listTimeDateValues=new ArrayList<>();


        prev = (ImageView) findViewById(R.id.back);
        next = (ImageView) findViewById(R.id.next);
        savie = (Button) findViewById(R.id.save_data);
        saveBtn = (RelativeLayout)findViewById(R.id.saveBtn_lay);

        Intent getSuv_id = getIntent();

        survey_idno = getSuv_id.getStringExtra("idi");

        assess_idno = getSuv_id.getStringExtra("assessID");

        Log.d("survey_idno", String.valueOf(survey_idno));

        loadQue(DatabaseHelper.QUE_TABLE,DatabaseHelper.QUE, survey_idno,assess_idno);

        for (int i=0; i<sample2.size(); i++){

            Log.d("QP_LIST", String.valueOf(sample2.get(i).getQuestion_name()));

        }

        mMyFragmentPagerAdapter = new QueFragmentPagerAdapter(
                getSupportFragmentManager(), sample2);

        mViewPager.setAdapter(mMyFragmentPagerAdapter);

        mViewPager.setOffscreenPageLimit(sample2.size());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("CURRENT", String.valueOf(position));

                if (mViewPager.getCurrentItem() == (sample2.size()-1)) {
                    next.setVisibility(View.INVISIBLE);
                    prev.setVisibility(View.VISIBLE);
/*


                    Fragment quePageFragment = new QuePageFragment();//Get Fragment Instance
                    Bundle data = new Bundle();//Use bundle to pass data
                    data.putString("data", "This is Argument Fragment");//put string, int, etc in bundle with a key value
                    quePageFragment.setArguments(data);//Finally set argument bundle to fragment
*/

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

        if (mViewPager.getCurrentItem() == (sample2.size()-2)) {
            next.setVisibility(View.INVISIBLE);
            prev.setVisibility(View.VISIBLE);
        }


        next.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        prev.setVisibility(View.VISIBLE);
                        if (mViewPager.getCurrentItem() == sample2.size()-2) {
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


    private void loadQue(String tbl,String order_col,String survey_id,String assess_id) {

        sample2.clear();
        Cursor cursor = db.getQue2(tbl, order_col,survey_id,assess_id);
        int counti =cursor.getCount();

        Log.d("counti", String.valueOf(counti));
        if (cursor.moveToFirst()) {
            do {
                Quiza quiz = new Quiza(
                        cursor.getString(cursor.getColumnIndex(String.valueOf("survey_id"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("question_id"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("question_name"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("question_description"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("answer_type"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("sub_question_status"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("section_name"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("sub_section"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("column_name"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("parent_question_id"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("selections"))),

                        cursor.getString(cursor.getColumnIndex(String.valueOf("assessment_id"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("assessment_name"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("country_status"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("display_status"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("submitted_date"))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf("project_id")))

                );
                sample2.add(quiz);
            } while (cursor.moveToNext());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.save_data:
                    Runnable mPendingRunnable = new Runnable() {
                        @Override
                        public void run() {
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


                                ansList[i] = asw;

                            }

                            Collections.addAll(qList_temp, qsnList);

                            Collections.addAll(aList_temp, ansList);

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

                            AddedSavedList aSl = get_AddedList_From_Shared_Prefs(QuestionPage.this);

                            aList_temp2.clear();

                            aList_temp2 = aSl.getResults();

                                qList_temp2.add("project_id");
                                qList_temp2.add("survey_id");
                                qList_temp2.add("assessment_id");
                                qList_temp2.add("country_status");
                                qList_temp2.add("display_status");
                                qList_temp2.add("submitted_date");


                            qList_temp2.add(0,"user_id");
                            aList_temp2.add(0,r_id);

                            qsnList_temp2 = new String[qList_temp2.size()];
                            ansList_temp2 = new String[aList_temp2.size()];

                            qsnList_temp2  = qList_temp2.toArray(qsnList_temp2);
                            ansList_temp2 = aList_temp2.toArray(ansList_temp2);

                            Log.d("QSNS2", Arrays.toString(qsnList_temp2));

                            Log.d("ANSW2", Arrays.toString(ansList_temp2));


                            Cursor c2 = db.createUsersTable("section_answers",qsnList_temp2,ansList_temp2);


                            listTimeDateCol.add("user_id");
                            listTimeDateCol.add("Assessment_end_time");
                            listTimeDateCol.add("Assessment_total_time");

                            listTimeDateValues.add(r_id);





                            long srt = AppController.getInstance().getStartTime();


                            DateFormat assessTime = new SimpleDateFormat("h:mm a", Locale.ENGLISH);

                            String endTime = assessTime.format(Calendar.getInstance().getTime());

                            listTimeDateValues.add(endTime);


                            try {
                                Date date = assessTime.parse(endTime);

                                long milliseconds = date.getTime();

                                long total = milliseconds-srt;

                                //int seconds=(int) (total/1000)%60;

                                //long milliseconds = stopWatch.getTime();

                                int sec = (int) ((total / 1000) % 60);
                                int minutes = (int) ((total / 1000) / 60);

                                String sec_v = String.valueOf(sec)+" seconds";

                                String min_total= String.valueOf(minutes)+" minutes";


                                listTimeDateValues.add(min_total);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            startTimeDateCol = new String[listTimeDateCol.size()];

                            startTimeDateValues = new String[listTimeDateValues.size()];

                            startTimeDateCol = listTimeDateCol.toArray(startTimeDateCol);

                            startTimeDateValues=  listTimeDateValues.toArray(startTimeDateValues);

                            Log.d("GenInfoQSNS", Arrays.toString(startTimeDateCol));

                            Log.d("GenInfoANSW", Arrays.toString(startTimeDateValues));

                            db.createGenInfoTable("geninfo_answers", startTimeDateCol, startTimeDateValues);






                            qsnList_temp = new String[qList_temp.size()];
                            ansList_temp = new String[aList_temp.size()];

                            qsnList_temp  = qList_temp.toArray(qsnList_temp);
                            ansList_temp = aList_temp.toArray(ansList_temp);

                            Log.d("QSNS", Arrays.toString(qsnList_temp));

                            Log.d("ANSW", Arrays.toString(ansList_temp));

                            Cursor c = db.createUsersTable("section_answers",qsnList_temp,ansList_temp);

                            Log.d("C_SIZE", String.valueOf(c.getCount()));


                            //store finish time plus total time taken
                            //add col- Time of Assessment (Finish),Time of Assessment (Total)

                            //int end_time = Integer.parseInt(endTime);



                            for(String id_num : user_ids){

                                Cursor colmns = db.getColumns("section_answers", id_num);

                                Cursor getGenTbl=  db.getColumns("geninfo_answers", id_num);

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







                                String[] genInfo_colNames = getGenTbl.getColumnNames();

                                gentemp_hh = new ArrayList<>();

                                genhh = Arrays.asList(genInfo_colNames);



                                for(int w=0; w < genhh.size();w++){
                                    String sel = genhh.get(w);

                                    if(!(Objects.equals(sel, "_id"))){

                                        gentemp_hh.add(sel);
                                    }
                                }

                                temp_gencolNames = new String[(gentemp_hh.size())];

                                temp_gencolNames  = gentemp_hh.toArray(temp_gencolNames);

                                int gencount = getGenTbl.getColumnCount();

                                valueof_Colms= new String[gencount];

                                Log.d("COL_COUNT", String.valueOf(gencount));

                                gen_rando.clear();

                                if  (getGenTbl.moveToFirst())
                                {
                                    do
                                    {
                                        for (int i =1 ; i < gencount; i++)
                                        {
                                            //rando.add(colmns.getString(i));

                                            gen_rando.add(getGenTbl.getString(getGenTbl
                                                    .getColumnIndex(genInfo_colNames[i])));
                                        }
                                    }
                                    while (getGenTbl.moveToNext());
                                }




                                //Log.d("C_RANDO", String.valueOf(rando.size()));


                                String col = android.text.TextUtils.join("|", temp_colNames);
                                Log.d("C_COLNAMES", col);
                                String colVal = android.text.TextUtils.join("|", rando);
                                Log.d("C_COLVALUES", colVal);

                                Colvalues colvalues = new Colvalues(col,colVal);

                                coluValue.add(colvalues);

                                String gen_col = android.text.TextUtils.join("|", temp_gencolNames);
                                Log.d("temp_gencolNames", gen_col);
                                String gen_colVal = android.text.TextUtils.join("|", gen_rando);
                                Log.d("gen_rando", gen_colVal);

                                Colvalues gen_colvalues = new Colvalues(gen_col,gen_colVal);

                                gen_coluValue.add(gen_colvalues);

                                //createTable(DatabaseHelper.SECTION_ANSWERS, col, colVal);
                            }
                            Log.d("coluValue", String.valueOf(coluValue.size()));
                            Log.d("gen_coluValue", String.valueOf(gen_coluValue.size()));

                            UploadLst uploadLst = new UploadLst(coluValue);

                            save_UploadList_To_Shared_Prefs (getApplicationContext(), uploadLst,"uploadList");

                            UploadLst uploadLst2 = new UploadLst(gen_coluValue);

                            save_UploadList_To_Shared_Prefs (getApplicationContext(), uploadLst2,"uploadList2");


                            Intent getSuv_id = getIntent();

                            survey_idno = getSuv_id.getStringExtra("idi");
                            assess_idno = getSuv_id.getStringExtra("assessID");

                            Intent intent = new Intent(QuestionPage.this, Assesso.class);

                            intent.putExtra("idi",survey_idno);
                            intent.putExtra("assessID",assess_idno);
                            startActivity(intent);
                            finish();

                        }
                    };

                    // If mPendingRunnable is not null, then add to the message queue
                    mHandler.post(mPendingRunnable);

                    break;


            }
        }

    public void save_UploadList_To_Shared_Prefs(Context context,UploadLst dbList,String listStringName) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dbList);
        //prefsEditor.putString("uploadList", json);

        prefsEditor.putString(listStringName, json);
        prefsEditor.apply();

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public AddedSavedList get_AddedList_From_Shared_Prefs(Context context) {

        List<String> results = new ArrayList<>();

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("addedList", "");

        AddedSavedList dbList= new AddedSavedList(results);

        if(!Objects.equals(json, "")){

            dbList = gson.fromJson(json, AddedSavedList.class);
        }

        return dbList;
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

        db.createGenInfoTable(DatabaseHelper.GENERALINFO_TABLE, startTimeDateCol,startTimeDateValues);

    }


}
