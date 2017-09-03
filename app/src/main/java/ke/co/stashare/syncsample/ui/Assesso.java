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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ke.co.stashare.syncsample.R;
import ke.co.stashare.syncsample.helper.AppController;
import ke.co.stashare.syncsample.navigator.*;
import ke.co.stashare.syncsample.survey.helper.DatabaseHelper;
import ke.co.stashare.syncsample.survey.helper.UploadLst;

import static ke.co.stashare.syncsample.ui.QuestionPage.IMPORT_DB_CREATETABLE;

/**
 * Created by Ken Wainaina on 29/08/2017.
 */

public class Assesso extends AppCompatActivity  implements View.OnClickListener  {

    Button new_assess;
    private Handler mHandler;
    List<Colvalues> getUploadList2;
    List<Colvalues> getUploadList;
    ProgressDialog progressDialog2;
    private Toolbar mToolbar;
    String col;
    String colVal;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_assess);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mHandler = new Handler();

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


        new_assess = (Button)findViewById(R.id.assessment_new);
        new_assess.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.assessment_new:
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                Intent intent = new Intent(Assesso.this, StartAssessment.class);
                startActivity(intent);
                finish();

                break;
        }
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


}
