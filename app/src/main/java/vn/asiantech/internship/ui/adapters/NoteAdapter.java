package vn.asiantech.internship.ui.adapters;

import android.net.Uri;
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
 * Adapter for Home Fragment
 * Created by huypham on 22/06/2017.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<Note> mNoteList;
    private OnItemClickListener mClickListener;

    public NoteAdapter(List<Note> noteList, OnItemClickListener clickListener) {
        mNoteList = noteList;
        mClickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
        if (mNoteList.get(position).getImage() != null) {
            holder.mImgImage.setImageURI(Uri.parse(mNoteList.get(position).getImage()));
        }
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTime;
        private TextView mTvTitle;
        private TextView mTvContent;
        private ImageView mImgImage;

        ViewHolder(View itemView) {
            super(itemView);
            mTvTime = (TextView) itemView.findViewById(R.id.tvDayMonth);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvContent = (TextView) itemView.findViewById(R.id.tvContent);
            mImgImage = (ImageView) itemView.findViewById(R.id.imgImageChoose);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) {
                        mClickListener.onItemClick(mNoteList.get(getAdapterPosition()));
                    }
                }
            });
        }

        private void setData(int position) {
            Note note = mNoteList.get(position);
            mTvTime.setText(note.getStringTime());
            mTvTitle.setText(note.getTitle());
            mTvContent.setText(note.getContent());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }
}
