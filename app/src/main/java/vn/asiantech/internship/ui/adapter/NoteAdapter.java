package vn.asiantech.internship.ui.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.NoteItem;

/**
 * Custom Note RecylerView
 *
 * @author at-cuongcao
 * @version 1.0
 * @since 06/19/2017
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteItemHolder> {

    private List<NoteItem> mNoteList;
    private OnItemClickListener mListener;

    public NoteAdapter(List<NoteItem> notes, OnItemClickListener listener) {
        mNoteList = notes;
        mListener = listener;
    }

    @Override
    public NoteItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteItemHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteItemHolder holder, int position) {
        Log.i("tag11111", "onBindViewHolder: " + position);
        holder.mTvTime.setText(mNoteList.get(position).getStringTime());
        holder.mTvTitle.setText(mNoteList.get(position).getTitle());
        holder.mTvContent.setText(mNoteList.get(position).getContent());
        if (mNoteList.get(position).getImage() != null) {
            holder.mImgPhoto.setVisibility(View.VISIBLE);
            holder.mImgPhoto.setImageURI(Uri.parse(mNoteList.get(position).getImage()));
        } else {
            holder.mImgPhoto.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    /**
     * Item for Note RecyclerView
     */
    public class NoteItemHolder extends RecyclerView.ViewHolder {
        private TextView mTvTime;
        private TextView mTvTitle;
        private TextView mTvContent;
        private ImageView mImgPhoto;

        public NoteItemHolder(final View itemView) {
            super(itemView);
            Log.i("tag111111", "NoteItemHolder: " + getAdapterPosition());
            mTvTime = (TextView) itemView.findViewById(R.id.tvNoteTime);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvNoteTitle);
            mTvContent = (TextView) itemView.findViewById(R.id.tvNoteContent);
            mImgPhoto = (ImageView) itemView.findViewById(R.id.imgNotePicture);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        Log.i("tag1111", "onClick: " + getAdapterPosition());
                        mListener.onItemClick(mNoteList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    /**
     * This interface used to handle item of RecyclerView click
     */
    public interface OnItemClickListener {
        void onItemClick(NoteItem note);
    }
}
