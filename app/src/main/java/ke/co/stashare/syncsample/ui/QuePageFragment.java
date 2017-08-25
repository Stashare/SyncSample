package ke.co.stashare.syncsample.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ke.co.stashare.syncsample.R;
import ke.co.stashare.syncsample.navigator.Contact;
import ke.co.stashare.syncsample.survey.helper.Answers;
import ke.co.stashare.syncsample.survey.helper.CheckModel;
import ke.co.stashare.syncsample.survey.helper.FeederAdapter;
import ke.co.stashare.syncsample.survey.helper.MultipleTextAdapter;
import ke.co.stashare.syncsample.survey.helper.MyEditTextAdapter;
import ke.co.stashare.syncsample.survey.helper.Quiz;
import ke.co.stashare.syncsample.survey.helper.SampleCheckAdapter;

/**
 * Created by Ken Wainaina on 12/08/2017.
 */

public class QuePageFragment extends Fragment {
    FeederAdapter feederAdapter;
    SampleCheckAdapter sampleCheckAdapter;
    List<CheckModel> checkList;
    List<Answers>answers;
    List<String> adapterList;
    MyEditTextAdapter myEditTextAdapter;
    MultipleTextAdapter multipleTextAdapter;
    public static QuePageFragment newInstance(Quiz singleque) {

        QuePageFragment pageFragment = new QuePageFragment();
        Bundle bundle = new Bundle();
      /*  bundle.putString("name", singleContact.getName());
        bundle.putString("phone", singleContact.getPhone());
        pageFragment.setArguments(bundle);*/

        bundle.putSerializable("question", singleque);
        pageFragment.setArguments(bundle);

        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_que_fragment, container, false);

        final TextView que = (TextView) view.findViewById(R.id.que);
        final TextView subque = (TextView) view.findViewById(R.id.subque);
        final TextView toolbarText = (TextView) view.findViewById(R.id.toolbar_text);
        final TextView subsection_text = (TextView) view.findViewById(R.id.sub_section);

        RecyclerView que_ans =(RecyclerView)view.findViewById(R.id.que_answer);

        Toolbar mToolbar = (Toolbar)view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(mToolbar);

        final ActionBar actionBar = activity.getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);

        Quiz quiz= (Quiz) getArguments().getSerializable("question");

        checkList = new ArrayList<>();
        adapterList =new ArrayList<>();
        answers = new ArrayList<>();

        assert quiz != null;
        toolbarText.setText(quiz.getSection());
        String sub_section="* "+ quiz.getSub_section();
        subsection_text.setText(sub_section);
        que.setText(quiz.getQue());

        String que_no = quiz.getQue_no();

        Answers ans = new Answers("A.1","Male");
        answers.add(ans);

        if(Objects.equals(quiz.getQue_type(), "Multi-radio")){

            adapterList = Arrays.asList(quiz.getSelections().split(","));

            Log.d("ADAPLIST", String.valueOf(adapterList));

            //FeederAdapter(Context context, List<String> feedList,String[] myDataset,String question_no)

            feederAdapter = new FeederAdapter(getActivity(),adapterList,quiz.getQue_no());

            feederAdapter.setWithRadioBtnElement(true);

            que_ans.setHasFixedSize(true);

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());

            que_ans.setLayoutManager(llm);
            que_ans.setAdapter(feederAdapter);

        }else if(Objects.equals(quiz.getQue_type(), "Single-Response")){


            adapterList = Arrays.asList(quiz.getSingle_response().split(","));

            Log.d("ADAPLIST", String.valueOf(adapterList));

            String[] mDataSet = new String[adapterList.size()];

            String[] headers = new String[adapterList.size()];

            for (int i = 0; i<adapterList.size(); i++) {

                String sel= adapterList.get(i);

                headers[i] = sel;

            }

            myEditTextAdapter = new MyEditTextAdapter(quiz.getQue_no(),headers,mDataSet);


            que_ans.setHasFixedSize(true);

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());

            que_ans.setLayoutManager(llm);
            que_ans.setAdapter(myEditTextAdapter);
        }
        else if (Objects.equals(quiz.getQue_type(), "Multiple")){

            adapterList = Arrays.asList(quiz.getSelections().split(","));

            Log.d("ADAPLIST", String.valueOf(adapterList));

            String[] mDataSet = new String[adapterList.size()];

            String[] headers = new String[adapterList.size()];

            for (int i = 0; i<adapterList.size(); i++) {

                String sel= adapterList.get(i);

                headers[i] = sel;

            }

            multipleTextAdapter = new MultipleTextAdapter(quiz.getQue_no(),headers,mDataSet);

            que_ans.setHasFixedSize(true);

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());

            que_ans.setLayoutManager(llm);

            que_ans.setAdapter(multipleTextAdapter);
        }

        else if (Objects.equals(quiz.getQue_type(), "Multiple-Months")){

            adapterList = Arrays.asList(quiz.getSelections().split(","));
            for (int i = 0; i<adapterList.size(); i++) {

                String sel= adapterList.get(i);

                CheckModel checkModel = new CheckModel(sel,false);

                //ADD ITR TO COLLECTION
                checkList.add(checkModel);


                //temp_result.add(i, results.get(i));

            }

            Log.d("SELE_MONTHS", String.valueOf(checkList.size()));

            sampleCheckAdapter= new SampleCheckAdapter(getActivity(),quiz.getQue_no(),checkList);

            que_ans.setHasFixedSize(true);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);

            que_ans.setLayoutManager(gridLayoutManager);

            que_ans.setAdapter(sampleCheckAdapter);
        }
        else{
            adapterList = Arrays.asList(quiz.getSelections().split(","));
            for (int i = 0; i<adapterList.size(); i++) {

                String sel= adapterList.get(i);

                CheckModel checkModel = new CheckModel(sel,false);

                //ADD ITR TO COLLECTION
                checkList.add(checkModel);


                //temp_result.add(i, results.get(i));

            }

            Log.d("SELE", String.valueOf(checkList.size()));

            sampleCheckAdapter= new SampleCheckAdapter(getActivity(),quiz.getQue_no(),checkList);

            que_ans.setHasFixedSize(true);

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());

            que_ans.setLayoutManager(llm);

            que_ans.setAdapter(sampleCheckAdapter);
        }


        //subque.setText(quiz.getPhone());

       /* Button btnClick=(Button) view.findViewById(R.id.btnClick);

        btnClick.setOnClickListener(new  View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Clciked "+textView1.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });*/


        return view;
    }
}

