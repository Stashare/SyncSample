package ke.co.stashare.syncsample.survey.helper;

/**
 * Created by Ken Wainaina on 28/08/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import ke.co.stashare.syncsample.R;
import ke.co.stashare.syncsample.helper.AppController;

public class GeneralInfoAdapter  extends RecyclerView.Adapter<GeneralInfoAdapter.ViewHolder> {

    private String[] mDataset;
    private String[] headerTexts;
    private String[] que_titles;
    private String question_no;
    private String rando;
    private String[] temp_headerTexts;
    private String[] temp_ansTexts;
    private List<String> adapterList;
    private List<Answers> ans;
    private List<String> ansWithComma;
    private List<String> mFeedList;
    private DatabaseHelper db;
    private List<Answers> temp_ans;
    Context context;

    public GeneralInfoAdapter(DatabaseHelper db,Context context, String[] headerTexts,String[] que_titles, String[] myDataset,String rando) {
        //this.mFeedList = feedList;
        this.context = context;
        this.db = db;
        this.mDataset = myDataset;
        this.rando = rando;
        this.headerTexts = headerTexts;
        this.que_titles = que_titles;

    }

    @Override
    public GeneralInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.generalinfo_item, parent, false);
        // pass MyCustomEditTextListener to viewholder in onCreateViewHolder
        // so that we don't have to do this expensive allocation in onBindViewHolder
        ViewHolder vh = new ViewHolder(v, new MyCustomEditTextListener());

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // update MyCustomEditTextListener every time we bind a new item
        // so that it knows what item in mDataset to update
        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
        holder.header.setText(headerTexts[holder.getAdapterPosition()]);
        holder.mEditText.setText(mDataset[holder.getAdapterPosition()]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public EditText mEditText;
        public TextView header;
        public MyCustomEditTextListener myCustomEditTextListener;

        public ViewHolder(View v, MyCustomEditTextListener myCustomEditTextListener) {
            super(v);
            this.header = (TextView) v.findViewById(R.id.header_text);
            this.mEditText = (EditText) v.findViewById(R.id.editText);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.mEditText.addTextChangedListener(myCustomEditTextListener);
        }
    }

    // we make TextWatcher to be aware of the position it currently works with
    // this way, once a new item is attached in onBindViewHolder, it will
    // update current position MyCustomEditTextListener, reference to which is kept by ViewHolder
    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            mDataset[position] = charSequence.toString();

            ansWithComma = new ArrayList<>();
            adapterList = new ArrayList<>();

            //Login user
            AppController.getInstance().addRando(rando);

            ansWithComma.add(rando);

            ans = new ArrayList<>();
            temp_ans = new ArrayList<Answers>();
            //android.text.TextUtils.join(",", mDataset[position]);
            //Log.d("DATS", Arrays.toString(mDataset));

            Collections.addAll(ansWithComma, mDataset);

            Collections.replaceAll(ansWithComma, null, "none");

            Collections.replaceAll(ansWithComma, "", "none");

            String majibu = android.text.TextUtils.join(",", ansWithComma);

            Log.d("mDAT", majibu);


            Collections.addAll(adapterList, que_titles);

            adapterList.add(0,"random_id");

            Log.d("adapterList", String.valueOf((adapterList)));

            temp_headerTexts = new String[adapterList.size()];

            temp_headerTexts = adapterList.toArray(temp_headerTexts);
            Log.d("mDAT_col", Arrays.toString(temp_headerTexts));

            temp_ansTexts = new  String[ansWithComma.size()];


            temp_ansTexts = ansWithComma.toArray(temp_ansTexts);

            Log.d("mDAT_ans", Arrays.toString(temp_ansTexts));



            db.createGenInfoTable("geninfo_answers", temp_headerTexts,temp_ansTexts);




/*
            Answers ansi = new Answers(question_no, majibu);

            ans.add(ansi);


            DbList dbL = get_DbList_From_Shared_Prefs(context);

            temp_ans.clear();

            temp_ans = dbL.getResults();

            if (temp_ans.size() == 0) {

                DbList dbList = new DbList(ans);

                save_DbList_To_Shared_Prefs(context, dbList);
            } else {

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
*/


            ansWithComma.clear();

        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }


    public void save_DbList_To_Shared_Prefs(Context context, DbList dbList) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dbList);
        prefsEditor.putString("dblist", json);
        prefsEditor.apply();

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public DbList get_DbList_From_Shared_Prefs(Context context) {

        List<Answers> results = new ArrayList<>();

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("dblist", "");

        DbList dbList = new DbList(results);

        if (!Objects.equals(json, "")) {

            dbList = gson.fromJson(json, DbList.class);
        }

        return dbList;
    }
}
