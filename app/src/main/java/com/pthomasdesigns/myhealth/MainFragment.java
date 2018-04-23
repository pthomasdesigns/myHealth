package com.pthomasdesigns.myhealth;

/**
 * Created by tthomas on 4/14/2018.
 */

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pthomasdesigns.myhealth.rest.model.UserInfo;

import org.parceler.Parcels;
import org.w3c.dom.Text;

public class MainFragment extends Fragment {

    private CardView mVisitsCardView;
    private CardView mBillsCardView;
    private CardView mMessagesCardView;
    private TextView mVisitsTextView;
    private TextView mBillsTextView;
    private TextView mMessagesTextView;

    private View view;
    private UserInfo mUserInfo;
    public static final String TAG = "MainFragment";

    public void setUserInfo(UserInfo userInfo) {
        mUserInfo = userInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mUserInfo = Parcels.unwrap(savedInstanceState.getParcelable(UserInfo.TAG));
        }

        mVisitsCardView = view.findViewById(R.id.view_visits);
        mBillsCardView = view.findViewById(R.id.view_bills);
        mMessagesCardView = view.findViewById(R.id.view_messages);

        mVisitsTextView = view.findViewById(R.id.text_view_visits);
        mVisitsTextView.setText(String.format(getString(R.string.medical_visit_advice), mUserInfo.getVisits()));

        mBillsTextView = view.findViewById(R.id.text_view_bills);
        mBillsTextView.setText(String.format(getString(R.string.medical_bills_advice), mUserInfo.getBills()));

        mMessagesTextView = view.findViewById(R.id.text_view_messages);
        mMessagesTextView.setText(String.format(getString(R.string.messages_advice), mUserInfo.getMessages()));


        mVisitsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra(MainActivity.FRAGMENT_ID, R.id.nav_visits);
                startActivity(intent);
                getActivity().finish();
            }
        });

        mBillsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra(MainActivity.FRAGMENT_ID, R.id.nav_bills);
                startActivity(intent);
                getActivity().finish();
            }
        });

        mMessagesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra(MainActivity.FRAGMENT_ID, R.id.nav_messages);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(UserInfo.TAG, Parcels.wrap(mUserInfo));

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

}
