package vn.asiantech.internship.ui.music;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Music;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by root on 6/19/17.
 * Note Adapter
 */
class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {

    private List<Music> mMusics;
    private ImageLoader mImageLoader;
    private OnMusicListener mOnMusicListener;
    private int mHighLight;

    MusicAdapter(ArrayList<Music> musics, OnMusicListener onMusicListener) {
        mMusics = musics;
        mOnMusicListener = onMusicListener;
        mHighLight = -1;
    }

    @Override
    public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music, parent, false);
        return new MusicViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MusicViewHolder holder, int position) {
        Music music = mMusics.get(position);
        holder.mTvSongName.setText(music.getNameSong());
        holder.mTvSingerName.setText(music.getNameSinger());
        setToImageView(music.getUrlAvatar(), holder.mImgThumb, holder.itemView.getContext());
        if (position != mHighLight) {
            holder.mRlContainer.setSelected(false);
        } else {
            holder.mRlContainer.setSelected(true);
            Log.d(TAG, "setHighLightxx: " + mHighLight + position);
        }
    }

    @Override
    public int getItemCount() {
        return mMusics.size();
    }

    /**
     * Note view holder
     */
    class MusicViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvSongName;
        private TextView mTvSingerName;
        private ImageView mImgThumb;
        private RelativeLayout mRlContainer;

        MusicViewHolder(final View itemView) {
            super(itemView);
            mTvSongName = (TextView) itemView.findViewById(R.id.tvSongName);
            mTvSingerName = (TextView) itemView.findViewById(R.id.tvSingerName);
            mImgThumb = (ImageView) itemView.findViewById(R.id.imgThumb);
            mRlContainer = (RelativeLayout) itemView.findViewById(R.id.rlContainer);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnMusicListener.onItemClick(getLayoutPosition());
                }
            });
        }
    }

    private void setToImageView(String urlImage, ImageView imageView, Context context) {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading)
                .showImageOnFail(R.drawable.ic_avatar)
                .build();
        getImageLoader(context);
        mImageLoader.displayImage(urlImage, imageView, displayImageOptions);
    }

    private ImageLoader getImageLoader(Context context) {
        if (mImageLoader == null) {
            mImageLoader = ImageLoader.getInstance();
            mImageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }
        return this.mImageLoader;
    }

    void setHighLight(int position) {
        mHighLight = position;
        notifyDataSetChanged();
    }

    /**
     * MusicAdapter interface
     */
    interface OnMusicListener {
        void onItemClick(int position);
    }
}
