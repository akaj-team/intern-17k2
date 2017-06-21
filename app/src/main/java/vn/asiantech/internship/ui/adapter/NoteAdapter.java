package vn.asiantech.internship.ui.adapter;

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
import vn.asiantech.internship.models.Note;

/**
 * Created by ducle on 21/06/2017.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> mNotes;

    public NoteAdapter(List<Note> notes) {
        this.mNotes = notes;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note=mNotes.get(position);
        holder.mTvDate.setText(note.getDate());
        holder.mTvTitle.setText(note.getTitle());
        holder.mTvContent.setText(note.getContent());
//        File file=new File(note.getUrlImage());
//        if (file.exists()){
//            holder.mImgNote.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
//        }
    }

    @Override
    public int getItemCount() {
        return mNotes != null ? mNotes.size() : 0;
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvDate;
        private TextView mTvTitle;
        private TextView mTvContent;
        private ImageView mImgNote;

        public NoteViewHolder(View itemView) {
            super(itemView);
            mTvDate = (TextView) itemView.findViewById(R.id.tvDate);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvContent = (TextView) itemView.findViewById(R.id.tvContent);
            mImgNote = (ImageView) itemView.findViewById(R.id.imgNote);
        }
    }

}
