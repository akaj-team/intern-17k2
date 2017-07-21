package vn.asiantech.internship.music.ui.home;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.music.models.Song;

/**
 * Created by ducle on 19/07/2017.
 * SongAdapter
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
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
        return mSongs == null ? 0 : mSongs.size();
    }

    class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvTitle;
        private TextView mTvArtist;
        private CardView mCardView;

        public SongViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mTvArtist = (TextView) itemView.findViewById(R.id.tvArtist);
            mCardView = (CardView) itemView.findViewById(R.id.cardView);
            mCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == mCardView && view.getContext() instanceof OnItemClickListener) {
                ((OnItemClickListener) view.getContext()).onClickItem(getAdapterPosition());
            }
        }
    }

    /**
     * OnItemClickListener set click for an item
     */
    public interface OnItemClickListener {
        void onClickItem(int position);
    }
}
