package vn.asiantech.internship.exday21;

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
    private ArrayList<ItemInformation> mItemInformations;

    InformationAdapter(ArrayList<ItemInformation> mItemInformations) {
        this.mItemInformations = mItemInformations;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_json, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        ItemInformation itemInformation = mItemInformations.get(position);
        holder.mTvMobile.setText(itemInformation.getMobile());
        holder.mTvName.setText(itemInformation.getName());
        holder.mTvEmail.setText(itemInformation.getEmail());
    }

    @Override
    public int getItemCount() {
        return mItemInformations.size();
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
