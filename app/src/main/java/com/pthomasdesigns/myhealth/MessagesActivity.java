package com.pthomasdesigns.myhealth;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.pthomasdesigns.myhealth.rest.model.MedicalVisit;
import com.pthomasdesigns.myhealth.rest.model.Message;
import com.pthomasdesigns.myhealth.rest.model.UserInfo;

import org.parceler.Parcels;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by tthomas on 4/17/2018.
 */

public class MessagesActivity extends AppCompatActivity {

    private ActionBar mActionBar;
    public static final String TAG = "MessagesActivity";
    private RecyclerView mMessageRecyclerView;
    private MessageAdapter mMessageAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    private List<Message> mDataSet = null;
    private String fromUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        mMessageRecyclerView = (RecyclerView) findViewById(R.id.recycle_view_messages);
        setRecyclerViewLayoutManager();
        mMessageAdapter = new MessageAdapter();
        mMessageRecyclerView.setAdapter(mMessageAdapter);

        if (savedInstanceState == null) {
            Parcelable pExtras = getIntent().getParcelableExtra(Message.TAG);
            if (pExtras != null) {
                mDataSet = Parcels.unwrap(pExtras);
            }
            Collections.sort(mDataSet, new  Comparator<Message>() {
                @Override
                public int compare(Message lhs, Message rhs) {
                    if (lhs != null & rhs != null) {
                        return lhs.getDate().compareToIgnoreCase(rhs.getDate());
                    } else {
                        return 0;
                    }
               }
            });
            fromUser = getIntent().getStringExtra(UserInfo.USER_ID_TAG);
        } else {
            mDataSet = Parcels.unwrap(savedInstanceState.getParcelable(Message.TAG));
            fromUser = savedInstanceState.getString(UserInfo.USER_ID_TAG);
        }
        mMessageAdapter.setData(mDataSet, fromUser);
        mMessageAdapter.notifyDataSetChanged();
        getSupportActionBar().setTitle(fromUser);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setRecyclerViewLayoutManager() {
        mLayoutManager = new LinearLayoutManager(this);
        mMessageRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Message.TAG, Parcels.wrap(mDataSet));
        outState.putString(UserInfo.USER_ID_TAG, fromUser);

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }
}
