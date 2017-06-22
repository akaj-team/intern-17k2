package vn.asiantech.internship.day11.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day11.model.Note;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 19/06/2017.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> mNotes;

    public NoteAdapter(List<Note> notes) {
        mNotes = notes;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_note, parent, false));
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.mTvTitle.setText(mNotes.get(position).getTitle());
        holder.mTvDescription.setText(mNotes.get(position).getDescription());
        holder.mImgNote.setImageURI(Uri.parse(mNotes.get(position).getImageNote()));
        holder.mTvDay.setText(convertToDay(mNotes.get(position).getTime()));
        holder.mTvDate.setText(convertToDate(mNotes.get(position).getTime()));
        holder.mTvTime.setText(convertToTime(mNotes.get(position).getTime()));
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    /**
     * create NoteViewHolder
     */
    class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;
        private TextView mTvDescription;
        private ImageView mImgNote;
        private TextView mTvDay;
        private TextView mTvDate;
        private TextView mTvTime;

        NoteViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitleNote);
            mTvDescription = (TextView) itemView.findViewById(R.id.tvDescriptionNote);
            mImgNote = (ImageView) itemView.findViewById(R.id.imgItemNote);
            mTvDay = (TextView) itemView.findViewById(R.id.tvDay);
            mTvDate = (TextView) itemView.findViewById(R.id.tvDate);
            mTvTime = (TextView) itemView.findViewById(R.id.tvTime);
        }
    }

    @NonNull
    private String convertToDay(String s) {
        return s.substring(0, 4);
    }

    @NonNull
    private String convertToDate(String s) {
        return s.substring(4, 15);
    }

    @NonNull
    private String convertToTime(String s) {
        return s.substring(15, s.length());
    }
}
