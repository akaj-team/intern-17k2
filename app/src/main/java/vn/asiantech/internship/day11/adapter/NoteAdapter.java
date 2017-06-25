package vn.asiantech.internship.day11.adapter;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day11.model.Note;
import vn.asiantech.internship.day11.ui.activity.NoteActivity;
import vn.asiantech.internship.day11.ui.fragment.InformationNoteFragment;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 19/06/2017.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> mNotes;
    private NoteActivity mNoteActivity;

    public NoteAdapter(List<Note> notes, NoteActivity noteActivity) {
        mNotes = notes;
        mNoteActivity = noteActivity;
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
        holder.mTvDay.setText(mNotes.get(position).getDay());
        holder.mTvDate.setText(mNotes.get(position).getDate());
        holder.mTvTime.setText(mNotes.get(position).getTime());
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
            mTvDescription = (TextView) itemView.findViewById(R.id.tvDescriptionItemNote);
            mImgNote = (ImageView) itemView.findViewById(R.id.imgItemNote);
            mTvDay = (TextView) itemView.findViewById(R.id.tvDay);
            mTvDate = (TextView) itemView.findViewById(R.id.tvDate);
            mTvTime = (TextView) itemView.findViewById(R.id.tvTime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InformationNoteFragment informationNoteFragment = new InformationNoteFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("Time", mNotes.get(getAdapterPosition()).getDay() + " " + mNotes.get(getAdapterPosition()).getDate() + " " + mNotes.get(getAdapterPosition()).getTime());
                    bundle.putString("Title", mNotes.get(getAdapterPosition()).getTitle());
                    bundle.putString("Description", mNotes.get(getAdapterPosition()).getDescription());
                    bundle.putString("UriImage", mNotes.get(getAdapterPosition()).getImageNote());
                    informationNoteFragment.setArguments(bundle);
                    mNoteActivity.changeFragment(informationNoteFragment);
                }
            });
        }
    }
}
