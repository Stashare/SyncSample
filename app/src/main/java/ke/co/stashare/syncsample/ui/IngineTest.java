package ke.co.stashare.syncsample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ke.co.stashare.syncsample.R;
import ke.co.stashare.syncsample.survey.helper.Answers;
import ke.co.stashare.syncsample.survey.helper.CheckModel;
import ke.co.stashare.syncsample.survey.helper.DatabaseHelper;
import ke.co.stashare.syncsample.survey.helper.FeederAdapter;
import ke.co.stashare.syncsample.survey.helper.MultipleTextAdapter;
import ke.co.stashare.syncsample.survey.helper.MyEditTextAdapter;
import ke.co.stashare.syncsample.survey.helper.SampleCheckAdapter;
import ke.co.stashare.syncsample.survey.helper.SavedData;

/**
 * Created by Ken Wainaina on 27/07/2017.
 */

public class IngineTest  extends AppCompatActivity implements View.OnClickListener {

    List<String> adapterList;
    List<CheckModel>checkList;
    List<String>answers;
    List<Answers>answers_list;
    List<SavedData> sample;
    List<String> columns;
    FeederAdapter feederAdapter;
    SampleCheckAdapter sampleCheckAdapter;
    MyEditTextAdapter myEditTextAdapter;
    MultipleTextAdapter multipleTextAdapter;
    RecyclerView recyclerView;

    //database helper object
    private DatabaseHelper db;

    EditText country; EditText province; EditText district;EditText division;
    EditText ward; EditText village; EditText group_name;EditText interviewer_name;
    EditText latDir; EditText latDegree;EditText latMin;EditText longDir;
    EditText longDegree;EditText longMin;
    Button proceed;
    String[] mDataSet;
    String[] headers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_test);


        db = new DatabaseHelper(this);

        sample = new ArrayList<>();
        answers = new ArrayList<>();
        columns = new ArrayList<>();
/*

        country = (EditText) findViewById(R.id.editTextCountry); province = (EditText) findViewById(R.id.editTextprovince);
        district = (EditText) findViewById(R.id.editTextcounty); division = (EditText) findViewById(R.id.editTextdivision);
        ward = (EditText) findViewById(R.id.editTextward); village = (EditText) findViewById(R.id.editTextVillage);
        group_name = (EditText) findViewById(R.id.editTextGroup); interviewer_name = (EditText) findViewById(R.id.editTextInterviewer);
        latDir = (EditText) findViewById(R.id.editTextDir); latDegree = (EditText) findViewById(R.id.editTextDegrees);
        latMin = (EditText) findViewById(R.id.editTextMinutes); longDir = (EditText) findViewById(R.id.editTextLongDir);
        longDegree = (EditText) findViewById(R.id.editTextLong_Degrees); longMin = (EditText) findViewById(R.id.editTextLongMinutes);

        proceed =(Button)findViewById(R.id.proceed);

        proceed.setOnClickListener(this);

*/


        //db.CreateDynamicTable();

        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);

        /*String str = "(#) Household members";
        adapterList = Arrays.asList(str.split(","));

        Log.d("ADAPLIST", String.valueOf(adapterList));

        //checkList = Arrays.asList(str.split(","));

        feederAdapter = new FeederAdapter(getApplicationContext(),adapterList);
        feederAdapter.setWithSingletElement(true);

        recyclerView.setAdapter(feederAdapter);


        checkList = new ArrayList<>();





        Log.d("SELE", String.valueOf(checkList.size()));

        sampleCheckAdapter= new SampleCheckAdapter(checkList);


*/
        // generating text for editText Views


        Answers ans = new Answers("A.1","");

        answers_list = new ArrayList<>();

        answers_list.add(ans);

        String str = "(#) Total Dependents,(#) Dependent Children (less than 18 years) including those in boarding school," +
                "(#) Dependent Youth (aged between 18-24),(#) Dependent Adults (between 25 and 60 years),(#) Dependent Elderly (aged over 60 years)";

        adapterList = Arrays.asList(str.split(","));

        mDataSet = new String[adapterList.size()];

        headers = new String[adapterList.size()];

        for (int i = 0; i<adapterList.size(); i++) {

            String sel= adapterList.get(i);

            headers[i] = sel;

        }

            myEditTextAdapter = new MyEditTextAdapter(IngineTest.this,"A.1",headers,mDataSet);

        //multipleTextAdapter = new MultipleTextAdapter("A.1",headers,mDataSet);


        recyclerView.setAdapter(myEditTextAdapter);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(llm);


    }

    /*private void loadNames() {
        sample.clear();
        Cursor cursor = db.getGeneralData();
        if (cursor.moveToFirst()) {
            do {
                SavedData savedData = new SavedData(
                        cursor.getInt(cursor.getColumnIndex(String.valueOf(R.string.id_unique))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf(R.string.country))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf(R.string.latitude))),
                        cursor.getString(cursor.getColumnIndex(String.valueOf(R.string.longitude)))
                );
                sample.add(savedData);
            } while (cursor.moveToNext());
        }

        Log.d("LOADED", String.valueOf(sample));
    }*/

    //saving the name to local storage
    private void saveNameToLocalStorage(String name, int status) {
        /*editTextName.setText("");
        db.addName(name, status);*/
        //refreshList();
    }

    @Override
    public void onClick(View v) {
       /* switch (v.getId()) {
            case R.id.proceed:
                columns = Arrays.asList(getResources().getStringArray(R.array.general_intro_info));

                if ((!country.getText().toString().equals("") && !province.getText().toString().equals("") &&
                        !district.getText().toString().equals("") && !division.getText().toString().equals("")) &&
                        !ward.getText().toString().equals("") && !village.getText().toString().equals("")
                        && !interviewer_name.getText().toString().equals("") &&
                        !latDir.getText().toString().equals("") && !latDegree.getText().toString().equals("") &&
                        !latMin.getText().toString().equals("") && !longDir.getText().toString().equals("") &&
                        !longDegree.getText().toString().equals("") && !longMin.getText().toString().equals("")&&
                        !group_name.getText().toString().equals(""))
                {
                    String ltDir= latDir.getText().toString().trim(); String ltDeg = latDegree.getText().toString().trim();
                    String ltMin= latMin.getText().toString().trim();

                    String lgDir= longDir.getText().toString().trim(); String lgDeg= longDegree.getText().toString().trim();
                    String lgMin= longMin.getText().toString().trim();

                    String lat= "Direction: " +ltDir+ "," + "Degrees: " +ltDeg+ "," + "Minutes: " +ltMin;
                    String longt= "Direction: " +lgDir+ "," + "Degrees: " +lgDeg+ "," + "Minutes: " +lgMin;




                   answers.add(country.getText().toString().trim()); answers.add(province.getText().toString().trim());
                   answers.add(district.getText().toString().trim());  answers.add(division.getText().toString().trim());
                   answers.add(ward.getText().toString().trim());  answers.add(village.getText().toString().trim());
                    answers.add(group_name.getText().toString().trim());  answers.add(interviewer_name.getText().toString().trim());
                    answers.add(lat);  answers.add(longt);



                    Log.d("EMPTY_NOT", String.valueOf(answers));

                    //db.CreateTable(columns,answers);
                    *//*String err = "Group Name Not Empty";

                    Snackbar.make(v, err, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*//*


                } else if ((!country.getText().toString().equals("") && !province.getText().toString().equals("") &&
                        !district.getText().toString().equals("") && !division.getText().toString().equals("")) &&
                        !ward.getText().toString().equals("") && !village.getText().toString().equals("")
                        && !interviewer_name.getText().toString().equals("") &&
                        !latDir.getText().toString().equals("") && !latDegree.getText().toString().equals("") &&
                        !latMin.getText().toString().equals("") && !longDir.getText().toString().equals("") &&
                        !longDegree.getText().toString().equals("") && !longMin.getText().toString().equals("")&&
                        group_name.getText().toString().equals(""))
                    {
                        String ltDir= latDir.getText().toString().trim(); String ltDeg = latDegree.getText().toString().trim();
                        String ltMin= latMin.getText().toString().trim();

                        String lgDir= longDir.getText().toString().trim(); String lgDeg= longDegree.getText().toString().trim();
                        String lgMin= longMin.getText().toString().trim();

                        String lat= "Direction: " +ltDir+ "," + "Degrees: " +ltDeg+ "," + "Minutes: " +ltMin;
                        String longt= "Direction: " +lgDir+ "," + "Degrees: " +lgDeg+ "," + "Minutes: " +lgMin;

                        answers.add(country.getText().toString().trim()); answers.add(province.getText().toString().trim());
                        answers.add(district.getText().toString().trim());  answers.add(division.getText().toString().trim());
                        answers.add(ward.getText().toString().trim());  answers.add(village.getText().toString().trim());
                        answers.add("none");  answers.add(interviewer_name.getText().toString().trim());
                        answers.add(lat);  answers.add(longt);

                        Log.d("EMPTY", String.valueOf(answers));
                        //db.CreateTable(columns,answers);
                    *//*String err = "Group Name Empty";

                    Snackbar.make(v, err, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*//*
                }
                else{
                    String err = "Please fill in the required fields";

                    Snackbar.make(v, err, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }

                break;

        }
    }*/
    }
}
