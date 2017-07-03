package vn.asiantech.internship.notesqlite;

import android.graphics.BitmapFactory;
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
 * Used to collect and display list data to the View.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-9
 */
class NoteAdapter extends RecyclerView.Adapter {
    private List<Note> mNotes = new ArrayList<>();
    private final OnItemClickListener mClickListener;

    NoteAdapter(List<Note> notes, OnItemClickListener itemClickListener) {
        this.mNotes = notes;
        this.mClickListener = itemClickListener;
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
        noteViewHolder.bind(mNotes.get(position), mClickListener);
        noteViewHolder.mTvDayOfWeek.setText(mNotes.get(position).getDayOfWeek());
        noteViewHolder.mTvDay.setText(mNotes.get(position).getDay());
        noteViewHolder.mTvMonth.setText(mNotes.get(position).getMonth());
        noteViewHolder.mTvHour.setText(mNotes.get(position).getHour());
        noteViewHolder.mTvTitle.setText(mNotes.get(position).getTitle());
        noteViewHolder.mTvContent.setText(mNotes.get(position).getContent());
        noteViewHolder.mImage.setImageBitmap(BitmapFactory.decodeFile(mNotes.get(position).getPathImage()));
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    /**
     * Used to register for note.
     */
    private class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvDayOfWeek;
        private final TextView mTvDay;
        private final TextView mTvMonth;
        private final TextView mTvHour;
        private final TextView mTvTitle;
        private final TextView mTvContent;
        private final ImageView mImage;

        NoteViewHolder(View itemView) {
            super(itemView);
            mTvDayOfWeek = (TextView) itemView.findViewById(R.id.tvDayOfWeek);
            mTvDay = (TextView) itemView.findViewById(R.id.tvDay);
            mTvMonth = (TextView) itemView.findViewById(R.id.tvMonth);
            mTvHour = (TextView) itemView.findViewById(R.id.tvHour);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvContent = (TextView) itemView.findViewById(R.id.tvContent);
            mImage = (ImageView) itemView.findViewById(R.id.imgNoteAdd);
        }

        private void bind(final Note note, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(note);
                }
            });
        }
    }

    interface OnItemClickListener {
        void onItemClick(Note note);
    }
}
