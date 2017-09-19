package ke.co.stashare.syncsample.ui.models;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;
import ke.co.stashare.syncsample.R;
import ke.co.stashare.syncsample.helper.AppController;
import ke.co.stashare.syncsample.survey.helper.DatabaseHelper;
import ke.co.stashare.syncsample.ui.QuestionPage;
import ke.co.stashare.syncsample.ui.SelectAssessment;
import ke.co.stashare.syncsample.ui.StartAssessment;

/**
 * Created by Ken Wainaina on 12/09/2017.
 */

public class Suv_Adapter extends RecyclerView.Adapter<Suv_Adapter.ViewHolder> {


    private final List<String> list;
    private final List<String> list_Id;
    private List<String>ansWithComma;

    private List<String>strtTimeDateCol;
    private DatabaseHelper db;
    private List<String> strtTimeDateValues;
    private String[] startTimeDateCol;
    private String[]startTimeDateValues;

    private int lastCheckedPosition = -1;
    private Context context;

    public Suv_Adapter(Context context, List<String> list, List<String> list_Id,
                       List<String>strtTimeDateCol, List<String> strtTimeDateValues,DatabaseHelper db) {
        this.list = list;
        this.list_Id = list_Id;
        this.context= context;
        this.strtTimeDateCol=strtTimeDateCol;
        this.strtTimeDateValues = strtTimeDateValues;
        this.db = db;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.radio_lay_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.choiceName.setText(list[position]);
        holder.radioButton.setChecked(position == lastCheckedPosition);
        holder.radioButton.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        /* @Bind(R.id.choice_name)
         TextView choiceName;*/
        @Bind(R.id.survey_item)
        RadioButton radioButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    int copyOfLastCheckedPosition = lastCheckedPosition;
                    lastCheckedPosition = getAdapterPosition();
                    notifyItemChanged(copyOfLastCheckedPosition);

                    ansWithComma = new ArrayList<>();

                    notifyItemChanged(lastCheckedPosition);

                    String selected = list.get(lastCheckedPosition);
                    String selected_id = list_Id.get(lastCheckedPosition);

                    Toast.makeText(context, selected_id, Toast.LENGTH_LONG).show();
/*

                    int randomNumber = random_num();

                    String randomi = Integer.toString(randomNumber);

                    AppController.getInstance().addRando(randomi);

                    Log.d("randomNumber", String.valueOf(randomNumber));


                    createTimeStamp();
*/

                    Intent intent = new Intent(context, SelectAssessment.class);

                    intent.putExtra("idi",selected_id);

                    context.startActivity(intent);


                    //intent.putExtra("idi",selected_id);


                   /* ansWithComma.add(selected);

                    if(ansWithComma.size()> 0){

                        for(int i=0; i<ansWithComma.size();i++){
                            if(!Objects.equals(ansWithComma.get(i), selected)){
                                ansWithComma.add(selected);

                            }
                        }

                    }

                    ansWithComma.clear();*/
                    //Log.d("SIZE", String.valueOf(selected));

                   /* lastCheckedPosition = getAdapterPosition();
                    notifyItemRangeChanged(0, list.length);*/

                }
            });
        }

    }




}