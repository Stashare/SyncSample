package ke.co.stashare.syncsample.survey.helper;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ke.co.stashare.syncsample.R;

/**
 * Created by Ken Wainaina on 21/08/2017.
 */

public class MyEditTextAdapter extends RecyclerView.Adapter<MyEditTextAdapter .ViewHolder> {

    private String[] mDataset;
    private String[] headerTexts;
    private String question_no;
    private List<Answers> ans;
    private List<String>ansWithComma;
    private List<String> mFeedList;

    public MyEditTextAdapter(String question_no,/*, List<String> feedList,*/String[] headerTexts,String[] myDataset) {
        //this.mFeedList = feedList;
        mDataset = myDataset;
        this.headerTexts = headerTexts;
        this.question_no = question_no;
    }

    @Override
    public MyEditTextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_edittext, parent, false);
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
            this.header = (TextView)v.findViewById(R.id.header_text);
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

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            mDataset[position] = charSequence.toString();

            ansWithComma = new ArrayList<>();
            ans= new ArrayList<>();
            //android.text.TextUtils.join(",", mDataset[position]);
            //Log.d("DATS", Arrays.toString(mDataset));

            for (String sel : mDataset) {

                ansWithComma.add(sel);

                //temp_result.add(i, results.get(i));

            }

            String majibu= android.text.TextUtils.join(",", ansWithComma);

            Answers ansi = new Answers(question_no,majibu);
            ans.add(ansi);
            for (Answers aswe : ans) {

                Log.d("Que_No", String.valueOf(aswe.getQue()));
                Log.d("Answers", String.valueOf(aswe.getAns()));

            }

            ansWithComma.clear();

        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
}

