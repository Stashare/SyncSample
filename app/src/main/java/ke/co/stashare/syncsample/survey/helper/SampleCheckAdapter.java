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
import java.util.Iterator;
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
    private List<Answers> temp_ans;
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
                temp_ans = new ArrayList<>();
                ans= new ArrayList<>();
                ansWithComma = new ArrayList<>();

                if(s.isSelected()){
                    selected.add(s.getSelection());
                    String majibu= android.text.TextUtils.join(",", selected);

                    Answers ansi = new Answers(question_no,majibu);
                    ans.clear();
                    ans.add(ansi);

                    DbList dbL = get_DbList_From_Shared_Prefs(context);

                    temp_ans.clear();

                    temp_ans = dbL.getResults();

                    if(temp_ans.size()== 0){

                        DbList dbList = new DbList(ans);

                        save_DbList_To_Shared_Prefs(context, dbList);
                    }
                    else {

                        for (Iterator<Answers> iterator = temp_ans.iterator(); iterator.hasNext(); ) {
                            Answers value = iterator.next();
                            if (Objects.equals(value.getQue(), question_no)) {
                                iterator.remove();
                            }
                        }

                        temp_ans.add(ansi);

                        Log.d("temp_ans", String.valueOf(temp_ans.size()));

                        DbList dbList = new DbList(temp_ans);

                        save_DbList_To_Shared_Prefs(context, dbList);

                    }

            }
                else {
                    selected.remove(s.getSelection());

                    String majibu = android.text.TextUtils.join(",", selected);

                    Answers ansi = new Answers(question_no, majibu);

                    ans.clear();
                    ans.add(ansi);

                    DbList dbL = get_DbList_From_Shared_Prefs(context);

                    temp_ans.clear();

                    temp_ans = dbL.getResults();

                    if(temp_ans.size()== 0){

                        DbList dbList = new DbList(ans);

                        save_DbList_To_Shared_Prefs(context, dbList);
                    }
                    else {

                        for (Iterator<Answers> iterator = temp_ans.iterator(); iterator.hasNext(); ) {
                            Answers value = iterator.next();
                            if (Objects.equals(value.getQue(), question_no)) {
                                iterator.remove();
                            }
                        }

                        temp_ans.add(ansi);

                        Log.d("temp_ans", String.valueOf(temp_ans.size()));

                        DbList dbList = new DbList(temp_ans);

                        save_DbList_To_Shared_Prefs(context, dbList);

                    }



                }

                }
        });
    }

    public void save_DbList_To_Shared_Prefs(Context context,DbList dbList) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dbList);
        prefsEditor.putString("dblist", json);
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
