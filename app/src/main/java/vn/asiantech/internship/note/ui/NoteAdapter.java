package vn.asiantech.internship.note.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.note.model.Note;

/**
 * Created by at-dinhvo on 19/06/2017.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> mNotes;

    public NoteAdapter(List<Note> data) {
        mNotes = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvNoteTitle.setText(mNotes.get(position).getTitle());
        holder.mTvNoteContent.setText(mNotes.get(position).getContent());
//        holder.mImgNote.
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    /**
     * create RecyclerViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvNoteTitle;
        private TextView mTvNoteContent;
        private ImageView mImgNote;

        ViewHolder(View itemView) {
            super(itemView);
            mTvNoteTitle = (TextView) itemView.findViewById(R.id.tvNoteTitle);
            mTvNoteContent = (TextView) itemView.findViewById(R.id.tvNoteContent);
            mImgNote = (ImageView) itemView.findViewById(R.id.imgFriend);
        }
    }
}
