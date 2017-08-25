package ke.co.stashare.syncsample.survey;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Double2;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ke.co.stashare.syncsample.R;
import ke.co.stashare.syncsample.survey.helper.DatabaseHelper;
import ke.co.stashare.syncsample.survey.helper.JoinTester;
import ke.co.stashare.syncsample.survey.helper.Questions;
import ke.co.stashare.syncsample.survey.helper.SubQuestions;


/**
 * Created by Ken Wainaina on 24/07/2017.
 */


public class Test extends AppCompatActivity {

    //database helper object
    private DatabaseHelper db;
    String single_response;
    String que_no;
    String uniqueid;
    String que;
    String section;
    String sub_sections; String type;
    String selections;
    String is_subquestion;

    String sub_que;
    String subque_type;
    List<Questions> queList;
    List<SubQuestions>subQList;
    List<JoinTester>joinSubque;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roundoff);

        //initializing views and objects
        db = new DatabaseHelper(this);

        queList = new ArrayList<>();

        subQList = new ArrayList<>();

        joinSubque = new ArrayList<>();
/*
        single_response="NULL"; que_no = "A.1"; que = "Sex of respondent (Select one)";
        section = "A. HOUSEHOLD DEMOGRAPHICS (GENERAL)";
        sub_sections="Interviewee Information (General)"; type="multiradio"; selections="Male,Female";
        is_subquestion="NO";

        //Log.d("TAG", section);

        db.addQuestions(que_no, single_response,que,section,sub_sections,type,is_subquestion,selections);

        single_response="(# years) Age at last birthday";  que_no = "A.2"; que = "Age of respondent";
        section = "A. HOUSEHOLD DEMOGRAPHICS (GENERAL)"; selections="null";
        sub_sections="Interviewee Information (General)";  type="single"; is_subquestion="NO";

        db.addQuestions(que_no, single_response,que,section,sub_sections,type,is_subquestion,selections);

        single_response="(#) Household members";  que_no = "A.4"; que = "Household Make up";
        section = "A. HOUSEHOLD DEMOGRAPHICS (GENERAL)";selections="null";
        sub_sections="Interviewee Information (General)";  type="single"; is_subquestion="YES";

        db.addQuestions(que_no, single_response,que,section,sub_sections,type,is_subquestion,selections);

        que_no = "A.4"; sub_que="Indicate the number of people in the household living / eating at home daily";
        subque_type="multiple"; selections=" (#) Females,(#) Males,(#) Female children aged under 18 years," +
                "(#) Male children aged  under 18 years";
        uniqueid= "A.4.1"; single_response="NULL";;

        db.addSubQuestions(sub_que,uniqueid,que_no,subque_type,selections,single_response);

        single_response="NULL";  que_no = "B.5";
        que = "Yesterday, through the day and night, did you eat or drink any of the following? (Select all that apply)";
        section = "B. HOUSEHOLD FOOD SECURITY";
        selections="Milk or anything made from milk (eg; tinned milk, powdered milk, fresh animal milk, yoghurt, cheese)," +
                "Meat or fish / seafood,Tea or coffee," +
                "Food made from roots or tubers (eg; Cassava,  yams, Irish potatoes, Sweet potatoes, plantain)";
        sub_sections="Food Consumption";  type="multichecked"; is_subquestion="NO";

        db.addQuestions(que_no, single_response,que,section,sub_sections,type,is_subquestion,selections);


*/

        //loadQuestions();


        //loadSubQue();
    }

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
  /*  private void loadQuestions() {

        queList.clear();

        Cursor cursor = db.getQuestions();

        while(cursor.moveToNext()) {
            queList.add(new Questions(
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUE_NO)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SELECTIONS)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SINGLE_RESPONSE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SECTION)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SUB_SECTIONS)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUE_TYPE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IS_SUBQUE)))
                    );


        }


        int listSize = queList.size();

        for (Questions questions : queList) {
            if (Objects.equals(questions.getIs_subquestion(), "YES")) {

                joinSubque.clear();

                String question_No = questions.getQue_no();

                cursor = db.getJoinSubQue(question_No);

                while(cursor.moveToNext()) {
                    joinSubque.add(new JoinTester(
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUE_NO)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_UNIQUEID)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUE)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SUBQUE)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUE_TYPE)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SUBQUE_TYPE)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IS_SUBQUE)))
                    );







                }
                for (JoinTester joinTester : joinSubque) {


                    Log.i("JOIN parent", String.valueOf(joinTester.getQue()));
                    Log.i("JOIN subque", String.valueOf(joinTester.getSub_que()));

                    Log.i("JOIN parent_type", String.valueOf(joinTester.getQue_type()));
                    Log.i("JOIN subque_type", String.valueOf(joinTester.getSubque_type()));

                }

            }

            else{
                Log.i("JOQuestion number ", String.valueOf(questions.getQue_no()));
                Log.i("JOIQuestion ", String.valueOf(questions.getQue()));
                Log.i("JOIQuestion Type ", String.valueOf(questions.getType()));
                Log.i("JOIQuestion subquestion", String.valueOf(questions.getIs_subquestion()));

            }


        }

    }
*/
   /* @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void loadSubQue() {

        subQList.clear();

        Cursor cursor = db.getSubQuestions();



        while(cursor.moveToNext()) {
            subQList.add(new SubQuestions(
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SUBQUE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_UNIQUEID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PARENTQUE_NO)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SUB_SELECTIONS)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SUBQUE_TYPE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SUB_SINGLE_RESPONSE)))

                    );


        }
       int count= cursor.getCount();

        int listSize = subQList.size();

        for (SubQuestions subQuestions : subQList) {

                Log.i("Question unique_number ", String.valueOf(subQuestions.getUnique_subqueid()));
                Log.i("Question ", String.valueOf(subQuestions.getSub_que()));
                Log.i("Question Type ", String.valueOf(subQuestions.getType()));
                Log.i("Question SingleR ", String.valueOf(subQuestions.getSingle_response()));


        }

        //Log.d("SUBQLIST", String.valueOf(listSize));

    }*/




}
