package vn.asiantech.internship.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.models.Note;

/**
 * Created by root on 6/19/17.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private int mResource;
    private List<Note> mNotes;

    public NoteAdapter(int resource, List<Note> notes) {
        mResource = resource;
        mNotes = notes;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(mResource, parent, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;
        private TextView mTvDescription;
        private TextView mTvDate;
        public NoteViewHolder(View itemView) {
            super(itemView);
        }
    }
}
