package vn.asiantech.internship.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Song;
import vn.asiantech.internship.ui.music.SongPlayingActivity;

/**
 *
 * Created by quanghai on 30/06/2017.
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ItemViewHolder> {
    private List<Song> mSongs = new ArrayList<>();
    private OnSelectSongListener mOnSelectSongListener;
    private SongPlayingActivity mActivity = new SongPlayingActivity();

    public SongAdapter(List<Song> mSongs, OnSelectSongListener onSelectSongListener) {
        this.mSongs = mSongs;
        mOnSelectSongListener = onSelectSongListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        Song song = mSongs.get(position);
        holder.mTvTitle.setText(song.getTitle());
        holder.mTvArtist.setText(song.getArtist());
        holder.mTvTime.setText(mActivity.showTime(song.getTime()));
        holder.mImgPlaying.setVisibility(song.isPlaying() ? View.VISIBLE : View.GONE);
        mActivity.setImageLoader(holder.itemView.getContext(), song, holder.mImgIcon);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    /**
     * Item song
     */
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;
        private TextView mTvArtist;
        private TextView mTvTime;
        private CircleImageView mImgIcon;
        private ImageView mImgPlaying;

        ItemViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvSongTitle);
            mTvArtist = (TextView) itemView.findViewById(R.id.tvArtist);
            mTvTime = (TextView) itemView.findViewById(R.id.tvTime);
            mImgIcon = (CircleImageView) itemView.findViewById(R.id.imgIcon);
            mImgPlaying = (ImageView) itemView.findViewById(R.id.imgPlaying);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("xxx", "onClick: ");
                    mOnSelectSongListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    /**
     * Created by quanghai on 30/06/2017.
     */
    public interface OnSelectSongListener {
        void onItemClick(int position);
    }
}