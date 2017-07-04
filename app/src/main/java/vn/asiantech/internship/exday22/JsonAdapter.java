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
class JsonAdapter extends RecyclerView.Adapter<JsonAdapter.MyHolder> {
    private ArrayList<JsonItem> mJsonItems;

    JsonAdapter(ArrayList<JsonItem> mJsonItems) {
        this.mJsonItems = mJsonItems;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_annotations, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        JsonItem jsonItem = mJsonItems.get(position);
        holder.mTvMobile.setText(jsonItem.getMobile());
        holder.mTvName.setText(jsonItem.getName());
        holder.mTvEmail.setText(jsonItem.getEmail());
    }

    @Override
    public int getItemCount() {
        return mJsonItems.size();
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
