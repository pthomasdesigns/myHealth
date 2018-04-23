package com.pthomasdesigns.myhealth;

/**
 * Created by tthomas on 4/15/2018.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BillsFragment extends Fragment {

    private View view;
    public static final String TAG = "BillsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bills, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.medical_bills);
        return view;
    }
}
