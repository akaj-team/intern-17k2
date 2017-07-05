package vn.asiantech.internship.note;

import android.net.Uri;
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
    private List<ItemNote> mNoteList;
    private OnClickItemNoteListener mOnItemClickListener;

    RecyclerViewNoteAdapter(List<ItemNote> noteList, OnClickItemNoteListener listener) {
        mNoteList = noteList;
        mOnItemClickListener = listener;
    }

    @Override
    public RecyclerViewNoteAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_note, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewNoteAdapter.MyHolder holder, int position) {
        ItemNote itemNote = mNoteList.get(position);
        holder.mTvTitle.setText(itemNote.getTitle());
        holder.mTvNote.setText(itemNote.getNote());
        holder.mTvTime.setText(itemNote.getTime());
        if (mNoteList.get(position).getImage() != null) {
            holder.mImgNote.setVisibility(View.VISIBLE);
            holder.mImgNote.setImageURI(Uri.parse(mNoteList.get(position).getImage()));
        } else {
            holder.mImgNote.setVisibility(View.VISIBLE);
            holder.mImgNote.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    /**
     * Copyright © 2016 AsianTech inc.
     * Created by DatBui on 19/06/2017.
     */
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

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(mNoteList.get(getAdapterPosition()));
            }
        }
    }

    /**
     * Copyright © 2016 AsianTech inc.
     * Created by DatBui on 19/06/2017.
     */
    interface OnClickItemNoteListener {
        void onItemClick(ItemNote itemNote);
    }
}


