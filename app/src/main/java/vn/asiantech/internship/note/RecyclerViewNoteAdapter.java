package vn.asiantech.internship.note;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by DatBui on 19/06/2017.
 */
class RecyclerViewNoteAdapter extends RecyclerView.Adapter<RecyclerViewNoteAdapter.MyHolder> {
    private Context mContext;
    private List<ItemNote> mNoteList;
    private OnClickItemNoteListener mOnItemClickListener;

    RecyclerViewNoteAdapter(List<ItemNote> noteList) {
        mNoteList = noteList;
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
        if (itemNote.getImage() != null) {
            File file = new File(itemNote.getImage());
            if (file.exists()) {
                holder.mImgNote.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            }
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

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    /**
     * Copyright © 2016 AsianTech inc.
     * Created by DatBui on 19/06/2017.
     */
    public interface OnClickItemNoteListener {
        void onItemClick(int positon);
    }
}
