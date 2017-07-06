package vn.asiantech.internship.exday22;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import vn.asiantech.internship.R;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 03-07-2017.
 */
class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.MyHolder> {
    private ArrayList<InformationItem> mInformationItems;

    InformationAdapter(ArrayList<InformationItem> mInformationItems) {
        this.mInformationItems = mInformationItems;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_annotations, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        InformationItem informationItem = mInformationItems.get(position);
        holder.mTvMobile.setText(informationItem.getMobile());
        holder.mTvName.setText(informationItem.getName());
        holder.mTvEmail.setText(informationItem.getEmail());
    }

    @Override
    public int getItemCount() {
        return mInformationItems.size();
    }

    /**
     * Copyright © 2016 AsianTech inc.
     * Created by datbu on 03-07-2017.
     */
    class MyHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private TextView mTvEmail;
        private TextView mTvMobile;

        MyHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            mTvMobile = (TextView) itemView.findViewById(R.id.tvMobile);
        }
    }
}
