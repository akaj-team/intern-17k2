package vn.asiantech.internship.music;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * As a data adapter for song recyclerView .
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-7-1
 */
class MusicRecyclerViewAdapter extends RecyclerView.Adapter {
    private final List<Song> mSongs;
    private final OnItemClickListener mClickListener;

    MusicRecyclerViewAdapter(List<Song> songs, OnItemClickListener clickListener) {
        this.mClickListener = clickListener;
        this.mSongs = songs;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_song_list, parent, false);
        return new MusicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MusicViewHolder musicViewHolder = (MusicViewHolder) holder;
        musicViewHolder.bind(mSongs.get(position), position, mClickListener);
        musicViewHolder.mTvName.setText(mSongs.get(position).getName());
        musicViewHolder.mTvSinger.setText(mSongs.get(position).getSinger());
        musicViewHolder.mImgSong.setImageResource(mSongs.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    /*
     * Used to register for song
     */
    private class MusicViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvName;
        private final TextView mTvSinger;
        private final ImageView mImgSong;

        MusicViewHolder(final View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvMusicTitle);
            mTvSinger = (TextView) itemView.findViewById(R.id.tvSinger);
            mImgSong = (ImageView) itemView.findViewById(R.id.imgSong);
        }

        private void bind(final Song song, final int position, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(song, position);
                }
            });
        }
    }

    /*
     * Used to get song and position in recyclerView
     */
    interface OnItemClickListener {
        void onItemClick(Song song, int position);
    }
}
