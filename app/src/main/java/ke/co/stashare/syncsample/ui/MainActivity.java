package ke.co.stashare.syncsample.ui;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.Buffer;
import java.util.ArrayList;

import ke.co.stashare.syncsample.R;
import ke.co.stashare.syncsample.helper.RecyclerAdapter;
import ke.co.stashare.syncsample.helper.Section;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String  myString;

    private EditText editQsn;
    private Toolbar mToolbar;
    private Button saveBtn;
    private Button startSurvey;
    private RecyclerView recyclerView;
    RecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


      mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);

        editQsn = (EditText) findViewById(R.id.editQsn);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        startSurvey = (Button) findViewById(R.id.startSurvey);


        saveBtn.setOnClickListener(this);
        startSurvey.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v == saveBtn) {
            myString = editQsn.getText().toString().trim();
            myString = myString.replaceAll(" ", "_").toLowerCase();
            Toast.makeText(this, myString, Toast.LENGTH_LONG).show();
            //Log.d("TAG", String.valueOf(myString));
        }else if (v == startSurvey) {
            startActivity(new Intent(MainActivity.this, StartSurvey.class));

        }
    }




}
