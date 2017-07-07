package vn.asiantech.internship.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Song;

/**
 * Created by ducle on 05/07/2017.
 * SongAdapter is adapter for an item show a song info
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private static final String TAG = SongAdapter.class.getSimpleName();
    private List<Song> mSongs;

    public SongAdapter(List<Song> songs) {
        mSongs = songs;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        Song song = mSongs.get(position);
        holder.mTvTitle.setText(song.getTitle());
        holder.mTvArtist.setText(song.getArtist());
    }

    @Override
    public int getItemCount() {
        return mSongs != null ? mSongs.size() : 0;
    }

    /**
     * SongViewHolder ViewHolder for an item
     */
    class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvTitle;
        private TextView mTvArtist;
        private LinearLayout mLlItemSong;

        public SongViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvArtist = (TextView) itemView.findViewById(R.id.tvArtist);
            mLlItemSong = (LinearLayout) itemView.findViewById(R.id.llItemSong);
            mLlItemSong.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == mLlItemSong) {
                if (v.getContext() instanceof OnItemClickListener) {
                    ((OnItemClickListener) v.getContext()).onReplaceFragment(getAdapterPosition());
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onReplaceFragment(int position);
    }
}
