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
import vn.asiantech.internship.ui.music.SongPlayingActivity;

/**
 *
 * Created by quanghai on 30/06/2017.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ItemViewHolder> {
    private List<Song> mSongs = new ArrayList<>();
    private OnListener mOnListener;
    private SongPlayingActivity mActivity = new SongPlayingActivity();

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
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.mTvTitle.setText(mSongs.get(position).getTitle());
        holder.mTvArtist.setText(mSongs.get(position).getArtist());
        holder.mTvTime.setText(mActivity.showTime(mSongs.get(position).getTime() / 1000));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnListener.onItemClick(holder.getAdapterPosition());
                Log.d("xxx", "onClick: ");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;
        private TextView mTvArtist;
        private TextView mTvTime;

        ItemViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvSongTitle);
            mTvArtist = (TextView) itemView.findViewById(R.id.tvArtist);
            mTvTime = (TextView) itemView.findViewById(R.id.tvTime);
        }
    }

    public interface OnListener{
        void onItemClick(int position);
    }
}