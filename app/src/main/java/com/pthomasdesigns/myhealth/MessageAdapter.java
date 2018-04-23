package com.pthomasdesigns.myhealth;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pthomasdesigns.myhealth.rest.model.Message;

import java.util.List;

/**
 * Created by tthomas on 4/17/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<Message> mData;
    String fromUserDn;

    private static final String TAG = "myHealth:MessagesAdpater";

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mDateTextView;
        public TextView mMessageTextView;
        public CardView mMessageCardView;

        public ViewHolder(View view, List<Message> data) {

            super(view);
            final List<Message> mData = data;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                          }
            });
            mDateTextView = (TextView) view.findViewById(R.id.row_item_message_date);
            mMessageTextView = (TextView) view.findViewById(R.id.row_item_message);
            mMessageCardView = (CardView) view.findViewById(R.id.row_item_message_card);
        }
    }

    public void setData(List<Message> data, String from) {
        mData = data;
        fromUserDn = from;
    }

    // Create new views
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item_message, viewGroup, false);

        return new MessageAdapter.ViewHolder(view, mData);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position);

        viewHolder.mDateTextView.setText(mData.get(position).getDate());
        viewHolder.mMessageTextView.setText(mData.get(position).getMessage());

        if (!isSender(mData.get(position), fromUserDn)) {
            viewHolder.mMessageTextView.setGravity(Gravity.RIGHT);
            viewHolder.mDateTextView.setGravity(Gravity.RIGHT);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (mData != null) ? mData.size() : 0;
    }

    private boolean isSender(Message message, String fromDn) {
        return message.getTodn().equals(fromDn);
    }
}
