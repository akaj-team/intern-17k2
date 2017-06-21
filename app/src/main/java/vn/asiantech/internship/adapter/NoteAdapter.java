package vn.asiantech.internship.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Note;

/**
 * Created by root on 6/19/17.
 * Note Adapter
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> mNotes;

    public NoteAdapter(List<Note> notes) {
        mNotes = notes;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note = mNotes.get(position);
        holder.mTvDate.setText(note.getNoteDate());
        holder.mTvTitle.setText(note.getNoteTile());
        holder.mTvDescription.setText(note.getNoteDescription());
        if (note.getNoteImagesThumb().equalsIgnoreCase("")){
            holder.mImgAvatar.setVisibility(View.GONE);
        } else {
            holder.mImgAvatar.setImageBitmap(getBitmap(note.getNoteImagesThumb()));
        }
    }

    private Bitmap getBitmap(String noteImagesThumb) {
        return BitmapFactory.decodeFile(noteImagesThumb);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * Note view holder
     */
    class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;
        private TextView mTvDescription;
        private TextView mTvDate;
        private ImageView mImgAvatar;

        NoteViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvDate = (TextView) itemView.findViewById(R.id.tvDate);
            mTvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
        }
    }
}
