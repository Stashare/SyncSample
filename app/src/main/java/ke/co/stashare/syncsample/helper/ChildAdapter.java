package ke.co.stashare.syncsample.helper;

/**
 * Created by Ken Wainaina on 10/07/2017.
 */

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ke.co.stashare.syncsample.R;

public class ChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Child> childData;
    private ArrayList<Child> childDataBk;

    public ChildAdapter(ArrayList<Child> childData) {
        this.childData = childData;
        childDataBk = new ArrayList<>(childData);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_child) TextView tvChild;
        @Bind(R.id.iv_expand_collapse_toggle) ImageView tvExpandCollapseToggle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nested_recycler_item_child, parent, false);

        ChildAdapter.ViewHolder cavh = new ChildAdapter.ViewHolder(itemLayoutView);
        return cavh;
    }


    final Handler handler = new Handler();
    Runnable collapseList = new Runnable() {
        @Override
        public void run() {
            if (getItemCount() > 1) {
                childData.remove(1);
                notifyDataSetChanged();
                handler.postDelayed(collapseList, 50);
            }
        }
    };

    Runnable expandList = new Runnable() {
        @Override
        public void run() {
            int currSize = childData.size();
            if (currSize == childDataBk.size()) return;

            if (currSize == 0) {
                childData.add(childDataBk.get(currSize));
            } else {
                childData.add(childDataBk.get(currSize));
            }
            notifyDataSetChanged();

            handler.postDelayed(expandList, 50);
        }
    };


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder vh = (ViewHolder) holder;

        if (position == 0 && getItemCount() == 1) {
            vh.tvExpandCollapseToggle.setImageResource(R.drawable.ic_arrow_down);
            vh.tvExpandCollapseToggle.setVisibility(View.VISIBLE);
        } else if (position == childDataBk.size() - 1) {
            vh.tvExpandCollapseToggle.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
            vh.tvExpandCollapseToggle.setVisibility(View.VISIBLE);
        } else {
            vh.tvExpandCollapseToggle.setVisibility(View.GONE);
        }

        Child c = childData.get(position);
        vh.tvChild.setText(c.getChild_name());

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItemCount() > 1) {
                    handler.post(collapseList);
                } else {
                    handler.post(expandList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return childData.size();
    }
}
