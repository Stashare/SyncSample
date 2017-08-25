package ke.co.stashare.syncsample.helper;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import ke.co.stashare.syncsample.R;

/**
 * Created by Ken Wainaina on 15/03/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private List<Section> results;
    private final OnItemClickListener listener;
    private final OnItemLongClickListener longclick;
    private Context context;


    public interface OnItemClickListener {

        void onItemClick(Section item);

    }

    public interface OnItemLongClickListener {
        void onItemLongClicked(Section item);
    }


    public RecyclerAdapter(Context context,List<Section> results, OnItemClickListener listener, OnItemLongClickListener longclick) {

        this.results = results;
        this.listener=listener;
        this.longclick=longclick;
        this.context = context;

    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new RecyclerViewHolder(v);
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        //holder.bind(position);
        holder.bind(results.get(position), listener,longclick);

    }

    @Override
    public int getItemCount()    {
        return results.size();
    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView mAccessPoint;
        private ImageView avatar;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mAccessPoint = (TextView) itemView.findViewById(R.id.description);
            avatar = (ImageView) itemView.findViewById(R.id.list_avatar);
        }

        public void bind(final Section item,final OnItemClickListener listener,final OnItemLongClickListener longclick) {
            mAccessPoint.setText(item.getTitle());
            Picasso.with(context).load(item.getUrl()).placeholder((R.drawable.placeholder)).error(R.drawable.placeholder).into(avatar);

            //Glide.with(context).load(item.getUrl()).into(avatar);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View v) {

                    listener.onItemClick(item);

                }

            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    longclick.onItemLongClicked(item);

                    return true;
                }
            });

        }

    }


}


