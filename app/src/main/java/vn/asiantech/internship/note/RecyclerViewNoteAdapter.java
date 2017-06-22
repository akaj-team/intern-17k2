package vn.asiantech.internship.note;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by DatBui on 19/06/2017.
 */
class RecyclerViewNoteAdapter extends RecyclerView.Adapter<RecyclerViewNoteAdapter.MyHolder> {
    private Context mContext;
    private List<ItemNote> mNoteList;
    private Bitmap mImage;

    RecyclerViewNoteAdapter(Context context, List<ItemNote> noteList) {
        mContext = context;
        mNoteList = noteList;

    }

    void setAvatar(Bitmap image) {
        mImage = image;
    }

    @Override
    public RecyclerViewNoteAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_note, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewNoteAdapter.MyHolder holder, int position) {
        holder.mTvTitle.setText(mNoteList.get(position).getTitle());
        holder.mTvNote.setText(mNoteList.get(position).getNote());
        holder.mTvTime.setText(mNoteList.get(position).getStringTime());
        if (mNoteList.get(position).getImage() != null) {
            holder.mImgNote.setVisibility(View.VISIBLE);
//            holder.mImgNote.setImageResource(Integer.parseInt(mNoteList.get(position).getImage()));
        }
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvTime;
        private TextView mTvTitle;
        private TextView mTvNote;
        private ImageView mImgNote;

        MyHolder(View itemView) {
            super(itemView);
            mTvTime = (TextView) itemView.findViewById(R.id.tvTime);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvNote = (TextView) itemView.findViewById(R.id.tvNote);
            mImgNote = (ImageView) itemView.findViewById(R.id.imgNote);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
