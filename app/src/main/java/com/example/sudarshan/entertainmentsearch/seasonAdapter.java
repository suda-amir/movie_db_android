package com.example.sudarshan.entertainmentsearch;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class seasonAdapter extends RecyclerView.Adapter<seasonAdapter.ViewHolder> {
    private List<Episode> mDataset;
    private Context mCtx;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView mTextView;
        public  TextView t1, t2, t3, t4, t5;
        public  ImageView i1;
        public ViewHolder(CardView v) {
            super(v);
            t1 = v.findViewById(R.id.plot);
            t2 = v.findViewById(R.id.episode_title);
            t3 = v.findViewById(R.id.airdate);
            t4 = v.findViewById(R.id.actors);
            t5 = v.findViewById(R.id.genre);
            i1 = v.findViewById(R.id.epd_img);
            mTextView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)

    public seasonAdapter(Context mCtx, List<Episode> productList) {
        this.mCtx = mCtx;
        mDataset = productList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public seasonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_season, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Episode ep = mDataset.get(position);
        holder.t1.setText(ep.getPlot());
        holder.t2.setText(ep.getTitle());
        holder.t3.setText(ep.getAirdate());
        holder.t4.setText(ep.getActors());
        holder.t5.setText(ep.getGenre());
        Picasso.get().load(ep.getPoster()).into(holder.i1);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}