package com.pthomasdesigns.myhealth;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pthomasdesigns.myhealth.rest.model.MedicalVisit;
import com.pthomasdesigns.myhealth.rest.model.Message;
import com.pthomasdesigns.myhealth.rest.model.UserInfo;
import com.pthomasdesigns.myhealth.rest.service.RestClient;

import org.parceler.Parcels;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesFragment extends Fragment {

    private View view;
    public static final String TAG = "MessagesFragment";
    private RecyclerView mMessagesRecyclerView;
    private MessagesAdapter mMessagesAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    UserInfo mUserInfo;
    private List<Message> mDataSet = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_messages, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mMessagesRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_messages);
        setRecyclerViewLayoutManager();
        mMessagesAdapter = new MessagesAdapter();
        mMessagesRecyclerView.setAdapter(mMessagesAdapter);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.messages);

        if (savedInstanceState != null) {
            mDataSet = Parcels.unwrap(savedInstanceState.getParcelable(Message.TAG));
            mUserInfo = Parcels.unwrap(savedInstanceState.getParcelable(UserInfo.TAG));
            Log.d(TAG, "onViewCreated " + mUserInfo.getId() + " " + savedInstanceState.getParcelable(Message.TAG));
            mMessagesAdapter.setData(mUserInfo.getId(), mDataSet);
            //mMessagesAdapter.notifyDataSetChanged();
        } else {
            getUserData();
        }
    }

    public void setRecyclerViewLayoutManager() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mMessagesRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void setUserInfo(UserInfo info) {
        mUserInfo = info;
    }

    private void getUserData() {
        Call<List<Message>> call = RestClient.getInstance().getApiService().getMessages();
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.body() != null) {
                    mDataSet = response.body();
                    mMessagesAdapter.setData(mUserInfo.getId(), mDataSet);
                    mMessagesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                // TODO: display error message
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState " + Parcels.wrap(mDataSet));
        outState.putParcelable(Message.TAG, Parcels.wrap(mDataSet));
        outState.putParcelable(UserInfo.TAG, Parcels.wrap(mUserInfo));

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }
}
