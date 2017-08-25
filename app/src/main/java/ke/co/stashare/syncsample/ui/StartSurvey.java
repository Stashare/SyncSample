package ke.co.stashare.syncsample.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

import ke.co.stashare.syncsample.R;
import ke.co.stashare.syncsample.helper.RecyclerAdapter;
import ke.co.stashare.syncsample.helper.Section;

/**
 * Created by Ken Wainaina on 05/07/2017.
 */

public class StartSurvey extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar mToolbar;
    RecyclerAdapter adapter;
    DividerItemDecoration mDividerItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_survey);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);

        adapter=new RecyclerAdapter(StartSurvey.this,getSections(), new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Section item) {
                String title = item.getTitle();
                //promptPayBill();
                //onSearchClick();
                Toast.makeText(StartSurvey.this, title, Toast.LENGTH_LONG).show();


            }
        },new RecyclerAdapter.OnItemLongClickListener(){
            @Override
            public void onItemLongClicked(Section item) {

            }


        });

        recyclerView.setAdapter(adapter);

        recyclerView.setAdapter(adapter);



        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(llm);

        mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                llm.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);


    }

    private ArrayList<Section> getSections() {
        //COLECTION OF CRIME MOVIES
        ArrayList<Section> sections =new ArrayList<>();

        Section sect=new Section("HOUSEHOLD DEMOGRAPHICS (GENERAL)","https://firebasestorage.googleapis.com/v0/b/wipaydb.appspot.com/o/uploads%2Ffamily_96px.png?alt=media&token=c1db74a9-950f-448e-ab23-fd90953d6ff8");

        //ADD ITR TO COLLECTION
        sections.add(sect);

        sect = new Section("HOUSEHOLD FOOD SECURITY","https://firebasestorage.googleapis.com/v0/b/wipaydb.appspot.com/o/uploads%2Fplant_under_rain_100px.png?alt=media&token=f31f340a-c703-4c8c-a47d-4ec98b7e0d80");
        sections.add(sect);

        sect = new Section("AGRICULTURAL PRODUCTION","https://firebasestorage.googleapis.com/v0/b/wipaydb.appspot.com/o/uploads%2Ffield_96px.png?alt=media&token=338587e6-0558-47c6-98d6-edadb0049e68");
        sections.add(sect);

        sect = new Section("GENERAL HOUSEHOLD INCOME, EXPENDITURES & LIVELIHOODS SUPPORT","https://firebasestorage.googleapis.com/v0/b/wipaydb.appspot.com/o/uploads%2Fsell_property_96px.png?alt=media&token=9584484e-c40c-42e9-b13a-eaedaec85fe0");
        sections.add(sect);

        return sections;
    }
}
