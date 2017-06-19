package vn.asiantech.internship.ui.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.NoteItem;
import vn.asiantech.internship.ui.main.NoteDetailActivity;

/**
 * Custom Note RecylerView
 *
 * @author at-cuongcao
 * @version 1.0
 * @since 06/19/2017
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteItemHolder> {

    public static final String NOTE_KEY = "Note";

    private List<NoteItem> mNoteList;

    public NoteAdapter(ArrayList<NoteItem> notes) {
        mNoteList = notes;
    }

    @Override
    public NoteItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteItemHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteItemHolder holder, int position) {
        holder.mTvTime.setText(mNoteList.get(position).getTime());
        holder.mTvTitle.setText(mNoteList.get(position).getTitle());
        holder.mTvContent.setText(mNoteList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    /**
     * Item for Note RecyclerView
     */
    public class NoteItemHolder extends RecyclerView.ViewHolder {
        private TextView mTvTime;
        private TextView mTvTitle;
        private TextView mTvContent;
        private ImageView mImgPhoto;

        public NoteItemHolder(final View itemView) {
            super(itemView);
            mTvTime = (TextView) itemView.findViewById(R.id.tvNoteTime);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvNoteTitle);
            mTvContent = (TextView) itemView.findViewById(R.id.tvNoteContent);
            mImgPhoto = (ImageView) itemView.findViewById(R.id.imgNotePhoto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), NoteDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(NOTE_KEY, mNoteList.get(getAdapterPosition()));
                    intent.putExtras(bundle);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

}
