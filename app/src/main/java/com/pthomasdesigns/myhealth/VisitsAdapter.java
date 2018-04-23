package com.pthomasdesigns.myhealth;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pthomasdesigns.myhealth.rest.model.MedicalVisit;

import org.parceler.Parcels;

import java.util.List;


public class VisitsAdapter extends RecyclerView.Adapter<VisitsAdapter.ViewHolder> {
    List<MedicalVisit> mData;
    private static final String TAG = "VisitsAdapter";

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mDateTextView;
        public TextView mVisitTypeTextView;
        public TextView mProviderTextView;
        public TextView mAddressTextView;

        public ViewHolder(View view, List<MedicalVisit> data) {

            super(view);
            final List<MedicalVisit> mData = data;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                    Intent intent = new Intent(view.getContext(), VisitDetailActivity.class);
                    intent.putExtra(MedicalVisit.TAG, Parcels.wrap(mData.get(getAdapterPosition())));
                    view.getContext().startActivity(intent);
                }
            });
            mDateTextView = (TextView) view.findViewById(R.id.visit_date);
            mVisitTypeTextView = (TextView) view.findViewById(R.id.visit_type);
            mProviderTextView = (TextView) view.findViewById(R.id.provider_name);
            mAddressTextView = (TextView) view.findViewById(R.id.provider_address);
        }
    }


    public void setData(List<MedicalVisit> data) {
        mData = data;
    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
          View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item_visit, viewGroup, false);

        return new ViewHolder(view, mData);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        //Log.d(TAG, "Element " + position + " set.");
        viewHolder.mDateTextView.setText(mData.get(position).getDate());
        viewHolder.mVisitTypeTextView.setText(mData.get(position).getType());
        viewHolder.mProviderTextView.setText(mData.get(position).getName());
        viewHolder.mAddressTextView.setText(mData.get(position).getAddress());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (mData != null) ? mData.size() : 0;
    }
}
