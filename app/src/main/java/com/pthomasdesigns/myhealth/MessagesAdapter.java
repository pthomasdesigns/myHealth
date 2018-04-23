package com.pthomasdesigns.myhealth;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pthomasdesigns.myhealth.rest.model.MedicalVisit;
import com.pthomasdesigns.myhealth.rest.model.Message;
import com.pthomasdesigns.myhealth.rest.model.UserInfo;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tthomas on 4/16/2018.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private List<Message> mData;
    private Map<String,List<Message>> perUserMessages;
    private List<String> userKeys;
    private String mUserId;
    private static final int MESSAGE_BRIEF_LEN = 50;

    private static final String TAG = "MessagesAdpater";

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mDisplayNameTextView;
        public TextView mMessageBriefTextView;
        private Map<String,List<Message>> perUserMessages;
        private List<String> userKeys;
        private String mUserId;

        public ViewHolder(View view, Map<String,List<Message>>  data, List<String> keys, String userId) {

            super(view);
            perUserMessages = data;
            userKeys = keys;
            mUserId = userId;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                    Intent intent = new Intent(view.getContext(), MessagesActivity.class);
                    intent.putExtra(Message.TAG, Parcels.wrap(perUserMessages.get(userKeys.get(getAdapterPosition()))));
                    String otherPartyDn = getOtherPartyDn(perUserMessages.get(userKeys.get(getAdapterPosition())).get(0), mUserId);
                    intent.putExtra(UserInfo.USER_ID_TAG, otherPartyDn);
                    view.getContext().startActivity(intent);
                }
            });
            mDisplayNameTextView = (TextView) view.findViewById(R.id.message_display_name);
            mMessageBriefTextView = (TextView) view.findViewById(R.id.message_brief);
        }


        private String getOtherPartyDn(Message message, String selfId) {
            String name =  message.getTo().equals(selfId) ? message.getFromdn() : message.getTodn();
            Log.d(TAG, "getOtherPartyDn " + name);
            return name;
        }
    }


    public void setData(String userId, List<Message> data) {
        mUserId = userId;
        mData = data;
        classifyDataPerUser(mData);
    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item_messages_per_user, viewGroup, false);

        return new ViewHolder(view, perUserMessages, userKeys, mUserId);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position);
        Log.d(TAG, "Key " + userKeys.get(position));
        Message message = perUserMessages.get(userKeys.get(position)).get(0);
        String displayName = message.getFrom().equals(mUserId) ? message.getTodn() : message.getFromdn();

        viewHolder.mDisplayNameTextView.setText(displayName);
        int blen = message.getMessage().length() > MESSAGE_BRIEF_LEN ? MESSAGE_BRIEF_LEN : message.getMessage().length();
        viewHolder.mMessageBriefTextView.setText(message.getMessage().substring(0, blen));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        int count = (userKeys != null) ? userKeys.size() : 0;

        Log.d(TAG, "getItemCount " + count);
        return count;
    }

    private void classifyDataPerUser(List<Message> data) {
        perUserMessages = new HashMap<>();
        userKeys = new ArrayList<>();

        for(Message message: data) {
            String otherParty = getOtherParty(message, mUserId);
            Log.d(TAG, "Other party: " + otherParty);
            List<Message> messages = perUserMessages.get(otherParty);
            if (messages == null) {
                Log.d(TAG, "Other party not present");
                messages = new ArrayList<>();
                perUserMessages.put(otherParty, messages);
                userKeys.add(otherParty);
            }
            messages.add(message);
        }
        Log.d(TAG, "userKeys " + userKeys);
    }

    private String getOtherParty(Message message, String selfId) {
        return message.getTo().equals(selfId) ? message.getFrom() : message.getTo();
    }

}
