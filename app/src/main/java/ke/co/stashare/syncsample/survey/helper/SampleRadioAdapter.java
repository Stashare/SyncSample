package ke.co.stashare.syncsample.survey.helper;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ke.co.stashare.syncsample.R;

/**
 * Created by Ken Wainaina on 07/08/2017.
 */

public class SampleRadioAdapter extends RecyclerView.Adapter<SampleRadioAdapter.ViewHolder> {


    private final List<String> list;
    private int lastCheckedPosition = -1;

    public SampleRadioAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.radio_selections, null);
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
        @Bind(R.id.radiobtn_soln)
        RadioButton radioButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int copyOfLastCheckedPosition = lastCheckedPosition;
                    lastCheckedPosition = getAdapterPosition();
                    notifyItemChanged(copyOfLastCheckedPosition);

                    notifyItemChanged(lastCheckedPosition);

                    String selected= list.get(lastCheckedPosition);

                    Log.d("SIZE", String.valueOf(selected));

                   /* lastCheckedPosition = getAdapterPosition();
                    notifyItemRangeChanged(0, list.length);*/

                }
            });
        }
    }

}
