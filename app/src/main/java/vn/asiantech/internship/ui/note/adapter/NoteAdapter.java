package vn.asiantech.internship.ui.note.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Note;

/**
 * adapter recyclerview of NoteFragment
 * <p>
 * Created by Hai on 6/19/2017.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ItemNoteViewHolder> {
    private List<Note> mNotes = new ArrayList<>();

    public NoteAdapter(List<Note> notes) {
        mNotes = notes;
    }

    @Override
    public ItemNoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new ItemNoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemNoteViewHolder holder, int position) {
        holder.mTvDayOfWeek.setText(mNotes.get(position).getDayOfWeek());
        holder.mTvDayOfMonth.setText(mNotes.get(position).getDayOfMonth());
        holder.mTvTime.setText(mNotes.get(position).getTime());
        holder.mTvContent.setText(mNotes.get(position).getContent());
        holder.mImgContent.setImageURI(Uri.parse(mNotes.get(position).getImage()));
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    /**
     * item of recyclerview
     */
    class ItemNoteViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvDayOfWeek;
        private TextView mTvDayOfMonth;
        private TextView mTvTime;
        private TextView mTvContent;
        private ImageView mImgContent;

        ItemNoteViewHolder(View itemView) {
            super(itemView);
            mTvDayOfWeek = (TextView) itemView.findViewById(R.id.tvDayOfWeek);
            mTvDayOfMonth = (TextView) itemView.findViewById(R.id.tvDatOfMonth);
            mTvTime = (TextView) itemView.findViewById(R.id.tvTime);
            mTvContent = (TextView) itemView.findViewById(R.id.tvContent);
            mImgContent = (ImageView) itemView.findViewById(R.id.imgContent);
        }
    }
}
