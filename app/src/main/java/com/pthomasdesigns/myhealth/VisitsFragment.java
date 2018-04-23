package com.pthomasdesigns.myhealth;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pthomasdesigns.myhealth.rest.model.MedicalVisit;
import com.pthomasdesigns.myhealth.rest.service.RestClient;

import org.parceler.Parcels;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VisitsFragment extends Fragment {
    private View view;
    public static final String TAG = "BillsFragment";
    private RecyclerView mVisitsRecyclerView;
    private VisitsAdapter mVisitsAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    String mUserId;
    private List<MedicalVisit> mDataSet = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_visits, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mVisitsRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_visits);
        setRecyclerViewLayoutManager();
        mVisitsAdapter = new VisitsAdapter();
        mVisitsRecyclerView.setAdapter(mVisitsAdapter);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.medical_visits);

        if (savedInstanceState != null) {
            mDataSet = Parcels.unwrap(savedInstanceState.getParcelable(MedicalVisit.TAG));
            mVisitsAdapter.setData(mDataSet);
            //mVisitsAdapter.notifyDataSetChanged();
        } else {
            getUserData();
        }
    }

    public void setRecyclerViewLayoutManager() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mVisitsRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    private void getUserData() {
        Call<List<MedicalVisit>> call = RestClient.getInstance().getApiService().getMedicalVisits();
        call.enqueue(new Callback<List<MedicalVisit>>() {
            @Override
            public void onResponse(Call<List<MedicalVisit>> call, Response<List<MedicalVisit>> response) {
                if (response.body() != null) {
                    mDataSet = response.body();
                    mVisitsAdapter.setData(mDataSet);
                    mVisitsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<MedicalVisit>> call, Throwable t) {
                // TODO: display error message
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(MedicalVisit.TAG, Parcels.wrap(mDataSet));

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }
}
