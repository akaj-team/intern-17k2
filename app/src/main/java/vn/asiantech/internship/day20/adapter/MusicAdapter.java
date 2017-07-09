package vn.asiantech.internship.day20.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day20.model.Song;

/**
 * Created by at-dinhvo on 03/07/2017.
 */
public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ItemViewHolder> {

    private List<Song> mSongs;
    private OnShowMusicPlayer mOnShowMusicPlayer;
    private ImageLoader mImageLoader;
    private DisplayImageOptions mDefaultOptions;
    private Context mContext;

    public MusicAdapter(Context context, List<Song> songs, OnShowMusicPlayer onShowMusicPlayer) {
        mContext = context;
        mSongs = songs;
        mOnShowMusicPlayer = onShowMusicPlayer;
        configImage();
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
        loadImage(mSongs.get(position).getImage(), holder.mImgMusic);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    private void configImage() {
        mDefaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext.getApplicationContext())
                .defaultDisplayImageOptions(mDefaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(config);
    }

    private void loadImage(String link, ImageView imageView) {
        mImageLoader.displayImage(link, imageView, mDefaultOptions);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvSong;
        private TextView mTvSinger;
        private ImageView mImgMusic;

        ItemViewHolder(View itemView) {
            super(itemView);
            mTvSong = (TextView) itemView.findViewById(R.id.tvSongName);
            mTvSinger = (TextView) itemView.findViewById(R.id.tvSinger);
            mImgMusic = (ImageView) itemView.findViewById(R.id.imgItemMusic);
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
