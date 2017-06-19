package vn.asiantech.internship.notesqlite;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by sony on 19/06/2017.
 */

public class NoteAdapter extends RecyclerView.Adapter {
    private List<Note> mNotes = new ArrayList<>();

    public NoteAdapter(List<Note> notes) {
        this.mNotes = notes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_note_list, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NoteViewHolder noteViewHolder = (NoteViewHolder) holder;
        noteViewHolder.mTvDate.setText(mNotes.get(position).getDate());
        noteViewHolder.mTvDay.setText(mNotes.get(position).getDay());
        noteViewHolder.mTvMonth.setText(mNotes.get(position).getMonth());
        noteViewHolder.mTvHour.setText(mNotes.get(position).getHour());
        noteViewHolder.mTvTitle.setText(mNotes.get(position).getTitle());
        noteViewHolder.mTvContent.setText(mNotes.get(position).getContent());
       // noteViewHolder.mImage.set(mNotes.get(position).getPathImage());
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    private class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvDate;
        private TextView mTvDay;
        private TextView mTvMonth;
        private TextView mTvHour;
        private TextView mTvTitle;
        private TextView mTvContent;
        private ImageView mImage;

        NoteViewHolder(View itemView) {
            super(itemView);
//            mTvDate = (TextView) itemView.findViewById(R.id.tvDate);
//            mTvDay = (TextView) itemView.findViewById(R.id.tvDay);
//            mTvMonth = (TextView) itemView.findViewById(R.id.tvMonth);
//            mTvHour = (TextView) itemView.findViewById(R.id.tvHour);
//            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
//            mTvContent = (TextView) itemView.findViewById(R.id.tvContent);
//            mImage = (ImageView) itemView.findViewById(R.id.imgNote);
        }
    }

}
