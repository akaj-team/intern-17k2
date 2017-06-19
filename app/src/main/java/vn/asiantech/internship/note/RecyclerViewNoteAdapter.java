package vn.asiantech.internship.note;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by DatBui on 19/06/2017.
 */

public class RecyclerViewNoteAdapter extends RecyclerView.Adapter<RecyclerViewNoteAdapter.MyHolder> {

    public RecyclerViewNoteAdapter() {
    }

    @Override
    public RecyclerViewNoteAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_note, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewNoteAdapter.MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvDay;
        private TextView mTvNumDay;
        private TextView mTvMonth;
        private TextView mTvTime;
        private TextView mTvTitle;
        private TextView mTvNote;
        private ImageView mImgNote;
        private LinearLayout mLnItem;


        MyHolder(View itemView) {
            super(itemView);
            mTvDay = (TextView) itemView.findViewById(R.id.tvDay);
            mTvNumDay = (TextView) itemView.findViewById(R.id.tvNumDay);
            mTvMonth = (TextView) itemView.findViewById(R.id.tvMonth);
            mTvTime = (TextView) itemView.findViewById(R.id.tvTime);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvNote = (TextView) itemView.findViewById(R.id.tvNote);
            mImgNote = (ImageView) itemView.findViewById(R.id.imgNote);
            mLnItem = (LinearLayout) itemView.findViewById(R.id.lnNote);

            mLnItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lnNote:
                    break;
            }
        }
    }
}
