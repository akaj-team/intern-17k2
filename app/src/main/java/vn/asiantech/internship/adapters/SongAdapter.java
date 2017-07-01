package vn.asiantech.internship.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Song;

/**
 * Created by quanghai on 30/06/2017.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ItemViewHolder> {
    private List<Song> mSongs = new ArrayList<>();
    private OnListener mOnListener;
    private int mPosition = 0;

    public SongAdapter(List<Song> mSongs, OnListener onListener) {
        this.mSongs = mSongs;
        mOnListener = onListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.tvTitle.setText(mSongs.get(position).getTitle());
        holder.tvArtist.setText(mSongs.get(position).getArtist());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnListener.onItemClick(position);
                mPosition = position;
                Log.d("xxx", "onClick: ");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvArtist;

        ItemViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvSongTitle);
            tvArtist = (TextView) itemView.findViewById(R.id.tvArtist);
        }
    }

    public interface OnListener{
        void onItemClick(int position);
    }
}