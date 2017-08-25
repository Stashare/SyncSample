package ke.co.stashare.syncsample.ui;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ke.co.stashare.syncsample.R;
import ke.co.stashare.syncsample.navigator.DataFragmentPagerAdapter;
import ke.co.stashare.syncsample.survey.helper.DatabaseHelper;
import ke.co.stashare.syncsample.survey.helper.Que;
import ke.co.stashare.syncsample.survey.helper.QueFragmentPagerAdapter;
import ke.co.stashare.syncsample.survey.helper.Quiz;

/**
 * Created by Ken Wainaina on 19/08/2017.
 */

public class QuestionPage  extends AppCompatActivity implements View.OnClickListener  {

    private ViewPager mViewPager;
    ImageView prev;
    ImageView next;

    private Toolbar mToolbar;

    private TextView toolbar_text;
    TextView subsection_text;

    ArrayList<Quiz> sample;
    private QueFragmentPagerAdapter mMyFragmentPagerAdapter;
    private DatabaseHelper db;
    ProgressDialog progressDialog;
    RelativeLayout saveBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_page);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        mViewPager = (ViewPager) findViewById(R.id.viewpager);


        progressDialog = new ProgressDialog(this);

        sample = new ArrayList<>();
        db = new DatabaseHelper(this);


        prev = (ImageView) findViewById(R.id.back);
        next = (ImageView) findViewById(R.id.next);
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

    @Override
    public void onClick(View v) {

    }
}
