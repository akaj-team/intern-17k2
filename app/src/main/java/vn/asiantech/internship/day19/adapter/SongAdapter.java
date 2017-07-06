package vn.asiantech.internship.day19.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day19.activity.MusicActivity;
import vn.asiantech.internship.day19.model.Song;
import vn.asiantech.internship.day19.service.Action;
import vn.asiantech.internship.day19.service.SongService;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 01/07/2017.
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private List<Song> mSongs;
    private MusicActivity mMusicActivity;
    private int mPositionCurrent;
    private OnChooseSongListener mOnChooseSongListener;

    public SongAdapter(List<Song> songs, MusicActivity musicActivity, OnChooseSongListener onChooseSongListener) {
        mSongs = songs;
        mMusicActivity = musicActivity;
        mOnChooseSongListener = onChooseSongListener;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SongViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_song, parent, false));
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        if (mPositionCurrent == position) {
            holder.mItemView.setBackgroundColor(Color.LTGRAY);
        } else {
            holder.mItemView.setBackgroundColor(ContextCompat.getColor(mMusicActivity, R.color.avatar_bolder));
        }
        holder.mTvSongName.setText(mSongs.get(position).getName());
        holder.mTvSongArtist.setText(mSongs.get(position).getArtist());
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public void setPosition(int position) {
        mPositionCurrent = position;
    }

    /**
     * create SongViewHolder
     */
    class SongViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvSongName;
        private TextView mTvSongArtist;
        private View mItemView;

        SongViewHolder(View itemView) {
            super(itemView);
            mTvSongName = (TextView) itemView.findViewById(R.id.tvItemSongName);
            mTvSongArtist = (TextView) itemView.findViewById(R.id.tvItemSongArtist);
            mItemView = itemView;
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnChooseSongListener.OnSongUpdate(getLayoutPosition());
                    mPositionCurrent = getLayoutPosition();
                    Intent intent = new Intent(mMusicActivity, SongService.class);
                    intent.setAction(Action.CHOOSE_PLAY.getValue());
                    intent.putExtra(MusicActivity.TYPE_POSITION, getLayoutPosition());
                    mMusicActivity.startService(intent);
                }
            });
        }
    }
}
