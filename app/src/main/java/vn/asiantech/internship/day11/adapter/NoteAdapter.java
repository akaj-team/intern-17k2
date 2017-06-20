package vn.asiantech.internship.day11.adapter;

import android.content.Context;
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
 * Created by rimoka on 19/06/2017.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private Context mContext;
    private List<Note> mNotes;
    public NoteAdapter(Context context,List<Note> notes){
        mContext=context;
        mNotes=notes;
    }
    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_note,parent,false));
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
      holder.mTvTitle.setText(mNotes.get(position).getTitle());
        holder.mTvDescription.setText(mNotes.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{
        private TextView mTvTitle;
        private TextView mTvDescription;
        private ImageView mImgNote;
        public NoteViewHolder(View itemView) {
            super(itemView);
            mTvTitle= (TextView) itemView.findViewById(R.id.tvTitleNote);
            mTvDescription=(TextView) itemView.findViewById(R.id.tvDescriptionNote);
            mImgNote= (ImageView) itemView.findViewById(R.id.imgItemNote);
        }
    }
}
