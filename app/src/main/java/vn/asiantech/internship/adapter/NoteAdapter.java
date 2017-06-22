package vn.asiantech.internship.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Note;

/**
 * Created by root on 6/19/17.
 * Note Adapter
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> mNotes;
    private int[] mColors = {0xfff44336, 0xffe91e63, 0xff9c27b0, 0xff3f51b5, 0xff2196f3, 0xff00bcd4, 0xff4caf50, 0xff8bc34a, 0xffcddc39, 0xffffeb3b, 0xffffc107, 0xffff5722, 0xff795548, 0xff607d8b};

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
        Random random = new Random();
        holder.mTvDate.setText(note.getNoteDate());
        holder.mTvTitle.setText(note.getNoteTile());
        holder.mTvDescription.setText(note.getNoteDescription());
        holder.mView.setBackgroundColor(mColors[random.nextInt(mColors.length)]);
        if (note.getNoteImagesThumb().equalsIgnoreCase("")) {
            holder.mImgAvatar.setVisibility(View.GONE);
        } else {
            holder.mImgAvatar.setImageURI(Uri.parse(note.getNoteImagesThumb().trim() + ".thumb"));
        }
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    /**
     * Note view holder
     */
    class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;
        private TextView mTvDescription;
        private TextView mTvDate;
        private ImageView mImgAvatar;
        private View mView;

        NoteViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvDate = (TextView) itemView.findViewById(R.id.tvDate);
            mTvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            mView = itemView.findViewById(R.id.view);
        }
    }
}
