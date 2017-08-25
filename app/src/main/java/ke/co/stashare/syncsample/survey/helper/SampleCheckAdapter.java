package ke.co.stashare.syncsample.survey.helper;

/**
 * Created by Ken Wainaina on 09/08/2017.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ke.co.stashare.syncsample.R;
import ke.co.stashare.syncsample.helper.AppController;

public class SampleCheckAdapter extends RecyclerView.Adapter<SampleCheckAdapter.ViewHolder> {

    //ArrayList<Number> numbers;
    private final List<CheckModel> numbers;
    private List<String> selected = new ArrayList<>();
    private List<Answers> ans;
    Context context;
    private List<String>ansWithComma;
    private String question_no;

    public SampleCheckAdapter(Context context,String question,List<CheckModel> numbers) {
        this.context = context;
        this.numbers = numbers;
        this.question_no = question;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_boxes, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.checkbox.setText(numbers.get(position).getSelection());
        //holder.bindData(numbers.get(position));

        //in some cases, it will prevent unwanted situations
        holder.checkbox.setOnCheckedChangeListener(null);

        //if true, your checkbox will be selected, else unselected
        holder.checkbox.setChecked(numbers.get(position).isSelected());

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                numbers.get(holder.getAdapterPosition()).setSelected(isChecked);
                CheckModel s = numbers.get(holder.getAdapterPosition());


                ansWithComma = new ArrayList<>();
                ans= new ArrayList<>();
/*



                if(ansWithComma.size()>0){

                    for(int i=0; i<ansWithComma.size();i++){
                        if(!Objects.equals(ansWithComma.get(i), selected)){
                            ansWithComma.add(selected);

                        }
                    }

                }
                else{
                    ansWithComma.add(selected);
                }
                String majibu= android.text.TextUtils.join(",", ansWithComma);

                Answers ansi = new Answers(question_no,majibu);
                ans.add(ansi);
                for (Answers aswe : ans) {

                    String jibu = aswe.getAns();

                    Log.d("Que_No", String.valueOf(aswe.getQue()));
                    Log.d("Answers", String.valueOf(jibu));

                }

            }*/
                if(s.isSelected()){
                    selected.add(s.getSelection());
                    String majibu= android.text.TextUtils.join(",", selected);

                    Answers ansi = new Answers(question_no,majibu);
                    DbList get_saved_AnswersList = AppController.get_DbList_From_Shared_Prefs(context);

                    ans = get_saved_AnswersList.getResults();

                    if(ans.size()>0){
                        for (final Answers answers : ans) {

                            final String jib = answers.getQue();

                            if(!Objects.equals(jib, question_no)){
                                ans.add(ansi);
                                DbList dbList = new DbList(ans);

                                AppController.save_DbList_To_Shared_Prefs(context, dbList);
                            }
                            else{

                                if(answers.getQue().equals(question_no)){

                                    ans.remove(answers);
                                    break;

                                }
                                ans.add(ansi);

                                DbList dbList = new DbList(ans);

                                AppController.save_DbList_To_Shared_Prefs(context, dbList);

                            }

                            //check if the key value of individual is equals to que_no
                        }

                    }
                    else {

                        ans.add(ansi);
                        DbList dbList = new DbList(ans);

                        AppController.save_DbList_To_Shared_Prefs(context, dbList);

                    }

                    for (Answers aswe : ans) {

                        String jibu = aswe.getAns();

                        Log.d("Que_No", String.valueOf(aswe.getQue()));
                        Log.d("Answers", String.valueOf(jibu));

                    }



                }
                else {
                    selected.remove(s.getSelection());

                    String majibu = android.text.TextUtils.join(",", selected);

                    Answers ansi = new Answers(question_no, majibu);


                    DbList get_saved_AnswersList = AppController.get_DbList_From_Shared_Prefs(context);

                    ans = get_saved_AnswersList.getResults();

                    if (ans.size() > 0) {
                        for (final Answers answers : ans) {

                            final String jib = answers.getQue();

                            if (!Objects.equals(jib, question_no)) {
                                ans.add(ansi);
                                DbList dbList = new DbList(ans);

                                AppController.save_DbList_To_Shared_Prefs(context, dbList);
                            } else {

                                if (answers.getQue().equals(question_no)) {

                                    ans.remove(answers);
                                    break;

                                }
                                ans.add(ansi);

                                DbList dbList = new DbList(ans);

                                AppController.save_DbList_To_Shared_Prefs(context, dbList);

                            }

                            //check if the key value of individual is equals to que_no
                        }

                    } else {

                        ans.add(ansi);
                        DbList dbList = new DbList(ans);

                        AppController.save_DbList_To_Shared_Prefs(context, dbList);

                    }

                    Log.d("Ans_Size", String.valueOf(ans.size()));



                }

                }
        });
    }
    /*public static void save_Selection(Context context, User _USER) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(_USER);
        prefsEditor.putString("user", json);
        prefsEditor.apply();

    }*/

    @Override
    public int getItemCount() {
        return numbers.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView ONEs;
        private TextView textONEs;
        private CheckBox checkbox;

        public ViewHolder(View v) {
            super(v);
            /*ONEs = (TextView) v.findViewById(R.id.ONEs);
            textONEs = (TextView) v.findViewById(R.id.textONEs);*/
            checkbox = (CheckBox) v.findViewById(R.id.check_soln);
        }

       /* public void bindData(Number number) {
            ONEs.setText(number.getONEs());
            textONEs.setText(number.getTextONEs());
        }*/
    }
}
