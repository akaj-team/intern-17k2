package vn.asiantech.internship.exday19;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.asiantech.internship.R;

/**
 * Created by datbu on 02-07-2017.
 */
class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MyHolder> {
    private ArrayList<MusicItem> mMusicItemList;
    private Context mContext;
    private OnClickMusicItemListener mOnClickMusicItemListener;

    MusicListAdapter(Context context, ArrayList<MusicItem> mMusicItemList, OnClickMusicItemListener onClickMusicItemListener) {
        this.mMusicItemList = mMusicItemList;
        this.mContext = context;
        this.mOnClickMusicItemListener = onClickMusicItemListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        MusicItem musicItem = mMusicItemList.get(position);
        holder.mSongName.setText(musicItem.getSongName());
        Glide.with(mContext).load(musicItem.getImage()).into(holder.mImageSong);
    }

    @Override
    public int getItemCount() {
        return mMusicItemList.size();
    }

    /**
     * Created by datbu on 02-07-2017.
     */
    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView mImageSong;
        private TextView mSongName;

        MyHolder(View itemView) {
            super(itemView);
            mImageSong = (CircleImageView) itemView.findViewById(R.id.imgSong);
            mSongName = (TextView) itemView.findViewById(R.id.tvSongName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnClickMusicItemListener != null) {
                mOnClickMusicItemListener.onItemClick(mMusicItemList, getAdapterPosition());
            }
        }
    }

    /**
     * Created by datbu on 02-07-2017.
     */
    interface OnClickMusicItemListener {
        void onItemClick(ArrayList<MusicItem> musicItems, int position);
    }
}
