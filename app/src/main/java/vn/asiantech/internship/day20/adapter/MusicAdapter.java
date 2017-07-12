package vn.asiantech.internship.day20.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.asiantech.internship.R;
import vn.asiantech.internship.day20.model.Song;

/**
 * Created by at-dinhvo on 03/07/2017.
 */
public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ItemViewHolder> {

    private List<Song> mSongs;
    private OnShowMusicPlayer mOnShowMusicPlayer;

    public MusicAdapter(Context context, List<Song> songs, OnShowMusicPlayer onShowMusicPlayer) {
        mSongs = songs;
        mOnShowMusicPlayer = onShowMusicPlayer;
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
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
        ImageLoader.getInstance().displayImage(mSongs.get(position).getImage(), holder.mImgMusic);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    /**
     * ItemViewHolder
     */
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvSong;
        private TextView mTvSinger;
        private CircleImageView mImgMusic;

        ItemViewHolder(View itemView) {
            super(itemView);
            mTvSong = (TextView) itemView.findViewById(R.id.tvSongName);
            mTvSinger = (TextView) itemView.findViewById(R.id.tvSinger);
            mImgMusic = (CircleImageView) itemView.findViewById(R.id.imgItemMusic);
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
