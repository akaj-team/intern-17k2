package vn.asiantech.internship.day20.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day20.model.Song;

/**
 * Created by at-dinhvo on 03/07/2017.
 */
public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ItemViewHolder> {

    private List<Song> mSongs;
    private OnShowMusicPlayer mOnShowMusicPlayer;

    public MusicAdapter(List<Song> songs, OnShowMusicPlayer onShowMusicPlayer) {
        mSongs = songs;
        mOnShowMusicPlayer = onShowMusicPlayer;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_song, parent, false);
        return new ItemViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.mTvSong.setText(mSongs.get(position).getName());
        holder.mTvSinger.setText(mSongs.get(position).getSinger());
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvSong;
        private TextView mTvSinger;

        ItemViewHolder(View itemView) {
            super(itemView);
            mTvSong = (TextView) itemView.findViewById(R.id.tvSongName);
            mTvSinger = (TextView) itemView.findViewById(R.id.tvSinger);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnShowMusicPlayer.onShowPlayer(getAdapterPosition());
                }
            });
        }
    }

    /**
     * Callback show player
     */
    public interface OnShowMusicPlayer {
        void onShowPlayer(int position);
    }
}
