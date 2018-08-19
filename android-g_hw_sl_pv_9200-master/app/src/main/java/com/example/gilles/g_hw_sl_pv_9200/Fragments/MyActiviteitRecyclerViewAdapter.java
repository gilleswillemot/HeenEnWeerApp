package com.example.gilles.g_hw_sl_pv_9200.Fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gilles.g_hw_sl_pv_9200.Activities.KalenderActivity;
import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Models.Activiteit;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link ActiviteitlijstFragment.Callback}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyActiviteitRecyclerViewAdapter extends RecyclerView.Adapter<MyActiviteitRecyclerViewAdapter.ViewHolder> {

    private final List<Activiteit> mValues;
    private final ActiviteitlijstFragment.Callback mListener;


    /**
     * nieuwe recyclerviewadapter voor het activiteitdetail fragment aanmaken
     * @param context
     * @param listener
     */
    public MyActiviteitRecyclerViewAdapter(Context context, ActiviteitlijstFragment.Callback listener) {
        mValues = ((KalenderActivity) context).getActiviteiten();
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_activiteit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        String min;
        if(mValues.get(position).getDatumStart().getMinutes()==0) {
            min="00";
        }
        else{
            min="" + mValues.get(position).getDatumStart().getMinutes();
        }
        holder.mTijdsView.setText(mValues.get(position).getDatumStart().getHours() +
                "u" + min);
        holder.mTitelView.setText(mValues.get(position).getTitel());
        holder.mDeelnemersView.setText(mValues.get(position).getDeelnemers());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTijdsView;
        public final TextView mTitelView;
        public final TextView mDeelnemersView;
        public Activiteit mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTijdsView = (TextView) view.findViewById(R.id.tijdstip);
            mTitelView = (TextView) view.findViewById(R.id.titelActiviteit);
            mDeelnemersView = (TextView) view.findViewById(R.id.deelNemers);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitelView.getText() + "'";
        }
    }
}
