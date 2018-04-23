package com.pthomasdesigns.myhealth;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.pthomasdesigns.myhealth.rest.model.MedicalVisit;
import com.pthomasdesigns.myhealth.rest.model.UserInfo;

import org.parceler.Parcels;
import org.w3c.dom.Text;


public class VisitDetailActivity extends AppCompatActivity {

    private ActionBar mActionBar;
    private MedicalVisit mUserData;
    private TextView mDateTextView;
    private TextView mProviderTextView;
    private TextView mAddressTextView;
    private TextView mReasonTextView;
    private TextView mDiagnosisTextView;
    private TextView mMedicationsTextView;
    private TextView mInstructionsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        mDateTextView = (TextView) findViewById(R.id.visit_detail_date);
        mProviderTextView = (TextView) findViewById(R.id.visit_detail_provider);
        mAddressTextView = (TextView) findViewById(R.id.visit_detail_address);
        mReasonTextView = (TextView) findViewById(R.id.visit_detail_reason);
        mDiagnosisTextView = (TextView) findViewById(R.id.visit_detail_diagnosis);
        mMedicationsTextView = (TextView) findViewById(R.id.visit_detail_medications);
        mInstructionsTextView = (TextView) findViewById(R.id.visit_detail_instructions);

        if (savedInstanceState == null) {
            Parcelable pExtras = getIntent().getParcelableExtra(MedicalVisit.TAG);
            if (pExtras != null) {
                mUserData = Parcels.unwrap(pExtras);
                fillData(mUserData);
              }
        }
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

    private void fillData (MedicalVisit visit) {
        mDateTextView.setText(visit.getDate());
        mProviderTextView.setText(visit.getName());
        mAddressTextView.setText(visit.getAddress());
        mReasonTextView.setText(String.format(getString(R.string.medical_visit_reason), visit.getReason()));
        mDiagnosisTextView.setText(String.format(getString(R.string.medical_visit_diagnosis), visit.getDiagnosis()));
        mMedicationsTextView.setText(String.format(getString(R.string.medical_visit_medications), visit.getMedications()));
        mInstructionsTextView.setText(String.format(getString(R.string.medical_visit_instructions), visit.getInstructions()));
    }
}
