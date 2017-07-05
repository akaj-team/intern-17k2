package vn.asiantech.internship.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Song;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 07/01/2017
 */
public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongHolder> {
    private Context mContext;
    private List<Song> mSongs;
    private OnItemClickListener mListener;

    public SongListAdapter(Context context, List<Song> songs, OnItemClickListener listener) {
        mContext = context;
        this.mSongs = songs;
        this.mListener = listener;
    }

    @Override
    public SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music_main_list, parent, false);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(final SongHolder holder, int position) {
        final int pos = position;
        holder.mTvSongName.setText(mSongs.get(position).getName());
        holder.mTvSingerName.setText(mSongs.get(position).getSinger());
        if (!mSongs.get(position).isLoaded()) {
            Picasso.with(mContext).load(mSongs.get(pos).getImageUrl()).into(new com.squareup.picasso.Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    mSongs.get(pos).setIconBitmap(bitmap);
                    mSongs.get(pos).setLoaded();
                    holder.mImgSongIcon.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    holder.mImgSongIcon.setImageResource(R.mipmap.ic_launcher_round);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        } else {
            holder.mImgSongIcon.setImageBitmap(mSongs.get(position).getIconBitmap());
        }
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    /**
     * Custom Item of main list
     */
    final class SongHolder extends RecyclerView.ViewHolder {

        ImageView mImgSongIcon;
        TextView mTvSongName;
        TextView mTvSingerName;

        SongHolder(View itemView) {
            super(itemView);
            mImgSongIcon = (ImageView) itemView.findViewById(R.id.imgSongIcon);
            mTvSongName = (TextView) itemView.findViewById(R.id.tvSongName);
            mTvSingerName = (TextView) itemView.findViewById(R.id.tvSingerName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(mSongs.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }

    /**
     * This interface used to handle onItemClick event of RecyclerView
     */
    public interface OnItemClickListener {
        void onItemClick(Song song, int position);
    }
}
