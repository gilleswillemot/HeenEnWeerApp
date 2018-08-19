package com.example.gilles.g_hw_sl_pv_9200.Fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gilles.g_hw_sl_pv_9200.Activities.Messenger_Activity;
import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Models.Gesprek;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a  and makes a call to the
 * specified {@link MessageListFragment.Callback}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMessageRecyclerViewAdapter extends RecyclerView.Adapter<MyMessageRecyclerViewAdapter.ViewHolder> {

    private final List<Gesprek> mValues;
    private final MessageListFragment.Callback mListener;

    /**
     * nieuwe adapter voor de messegaslijst (messengerlijstfragment)
     * @param context
     * @param listener
     */
    public MyMessageRecyclerViewAdapter(Context context, MessageListFragment.Callback listener) {
        mValues = ((Messenger_Activity) context).getGesprekken();
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_gesprek, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitelView.setText(mValues.get(position).getNaam());
        String result = "";
        for (String gbr :mValues.get(position).getDeelnemers()) {
            result=result + gbr;
        }
        holder.mDeelnemersView.setText(result);

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
        public final TextView mTitelView;
        public final TextView mDeelnemersView;
        public Gesprek mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitelView = (TextView) view.findViewById(R.id.titelGesprek);
            mDeelnemersView = (TextView) view.findViewById(R.id.deelNemersGesprek);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitelView.getText() + "'";
        }
    }
}
