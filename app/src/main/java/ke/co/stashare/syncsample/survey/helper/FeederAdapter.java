package ke.co.stashare.syncsample.survey.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;
import ke.co.stashare.syncsample.R;
import ke.co.stashare.syncsample.helper.AppController;
import ke.co.stashare.syncsample.survey.Feed;

/**
 * Created by Ken Wainaina on 05/08/2017.
 */

public class FeederAdapter extends RecyclerView.Adapter{


    private static final int TYPE_MULTIPLE_ITEM = 2;
    private static final int TYPE_RADIO_ITEM = 3;
    private static final int TYPE_SINGLE_ITEM = 4;


    private boolean mWithMultipleElement = false;
    private boolean mWithRadioElement = false;
    private boolean mWithSingleElement = false;
    private List<String> mFeedList;
    //private final List<String> list;
    private Context context;
    private int lastCheckedPosition = -1;
    private String question_no;
    private List<Answers> ans;
    private String[] mDataset;
    private List<String>ansWithComma;

    public FeederAdapter(Context context, List<String> feedList, String question_no) {
        this.context = context;
        this.mFeedList = feedList;
        this.question_no=question_no;
        ans= new ArrayList<>();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
           itemView = View.inflate(parent.getContext(), R.layout.radio_selections, null);
            return new RadioBtnViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

       if (holder instanceof RadioBtnViewHolder) {
            RadioBtnViewHolder radioBtnViewHolder = (RadioBtnViewHolder)holder;
           radioBtnViewHolder.radioButton.setChecked(position == lastCheckedPosition);
           radioBtnViewHolder.radioButton.setText(mFeedList.get(position));

        }
    }


    @Override
    public int getItemCount() {

        return mFeedList.size();
    }

    @Override
    public int getItemViewType(int position) {


            return TYPE_RADIO_ITEM;

    }
    public void setWithRadioBtnElement(boolean value) {
        mWithRadioElement = value;
    }

    public class RadioBtnViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.radiobtn_soln)
        RadioButton radioButton;


        public RadioBtnViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    int copyOfLastCheckedPosition = lastCheckedPosition;
                    lastCheckedPosition = getAdapterPosition();
                    notifyItemChanged(copyOfLastCheckedPosition);

                    notifyItemChanged(lastCheckedPosition);

                    String selected= mFeedList.get(lastCheckedPosition);

                    ansWithComma = new ArrayList<>();
                    //ans= new ArrayList<>();

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

                    Log.d("Ans_Size", String.valueOf(ans.size()));
                }
            });
        }
    }





}
