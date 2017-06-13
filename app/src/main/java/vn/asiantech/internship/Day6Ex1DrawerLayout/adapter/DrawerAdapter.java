package vn.asiantech.internship.Day6Ex1DrawerLayout.adapter;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.Day6Ex1DrawerLayout.event.OnRecyclerViewClickListener;
import vn.asiantech.internship.Day6Ex1DrawerLayout.ui.MainActivity;
import vn.asiantech.internship.R;

/**
 * create RecyclerViewAdapter
 * Created by at-hoavo on 09/06/2017.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.RecyclerViewHolder> {
    private List<String> mFunctions;
    private OnRecyclerViewClickListener iOnRecyclerViewClickListener;
    private Context mContext;

    public DrawerAdapter(List<String> items, Context context, OnRecyclerViewClickListener onRecyclerViewClickListener) {
        this.mFunctions = items;
        this.mContext = context;
        iOnRecyclerViewClickListener = onRecyclerViewClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View viewONE = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_header, parent, false);
                return new RecyclerViewHolder(viewONE, false);
            case 1:
                View viewTWO = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_function, parent, false);
                return new RecyclerViewHolder(viewTWO, true);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                if (!MainActivity.sCheckPicture) {
                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(holder.mImgScreen.getContext());
                    Drawable wallpaperDrawable = wallpaperManager.getDrawable();
                    holder.mImgScreen.setImageDrawable(wallpaperDrawable);
                } else {
                    holder.mImgScreen.setImageBitmap(MainActivity.sNewProfilePic);
                }
                break;
            case 1:
                holder.mTvFunction.setText(mFunctions.get(position));
                if (position == MainActivity.sSelectedPosition) {
                    holder.mItemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorItemPressed));

                } else {
                    holder.mItemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorItem));
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mFunctions.size();
    }

    /**
     * create RecyclerViewHolder
     */
    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvFunction;
        private View mItemView;
        private ImageView mImgScreen;

        public RecyclerViewHolder(View itemView, boolean check) {
            super(itemView);
            mItemView = itemView;
            if (check) {
                mTvFunction = (TextView) itemView.findViewById(R.id.tvFunction);
                mItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iOnRecyclerViewClickListener.onClick(getAdapterPosition(), true);
                    }
                });
            } else {
                mImgScreen = (ImageView) itemView.findViewById(R.id.imgScreen);
                mImgScreen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iOnRecyclerViewClickListener.onClick(getAdapterPosition(), false);
                    }
                });
            }
        }

    }
}
