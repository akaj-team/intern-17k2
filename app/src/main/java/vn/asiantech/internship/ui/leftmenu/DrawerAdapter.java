package vn.asiantech.internship.ui.leftmenu;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.asiantech.internship.R;
import vn.asiantech.internship.models.DrawerItem;
import vn.asiantech.internship.ui.main.MainActivity;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by DatBui on 15/06/2017.
 */
public class DrawerAdapter extends RecyclerView.Adapter {
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 0;

    private List<DrawerItem> mDrawerItems;
    private OnItemClickListener mOnItemClickListener;
    private Bitmap mAvatar;
    private Drawable mWallpaper;

    public DrawerAdapter(Context context, List<DrawerItem> drawerItems, OnItemClickListener listener) {
        mDrawerItems = drawerItems;
        mOnItemClickListener = listener;
        mWallpaper = WallpaperManager.getInstance(context).getDrawable();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_navigation, parent, false);
                return new DrawerViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_header, parent, false);
                return new UserViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof UserViewHolder) {
            UserViewHolder userViewHolder = ((UserViewHolder) holder);
            userViewHolder.mTvName.setText(R.string.app_name);
            userViewHolder.mTvEmail.setText(R.string.action_settings);
            if (mAvatar != null) {
                userViewHolder.mImgAvatar.setImageBitmap(mAvatar);
            }
            userViewHolder.mImgHeaderBg.setImageBitmap(((BitmapDrawable) mWallpaper).getBitmap());
        } else if (holder instanceof DrawerViewHolder) {
            DrawerViewHolder drawerViewHolder = ((DrawerViewHolder) holder);
            DrawerItem drawerItem = mDrawerItems.get(position);
            drawerViewHolder.mTvTitle.setText(mDrawerItems.get(position).getTitle());
            if (drawerItem.isChoose()) {
                drawerViewHolder.mTvTitle.setTextColor(drawerViewHolder.mTvTitle.getContext().getResources().getColor(R.color.item_choose));

            } else {
                drawerViewHolder.mTvTitle.setTextColor(drawerViewHolder.mTvTitle.getContext().getResources().getColor(R.color.white));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDrawerItems.size();
    }

    public void setAvatar(Bitmap avatar) {
        mAvatar = avatar;
    }

    /**
     * Copyright © 2016 AsianTech inc.
     * Created by DatBui on 15/06/2017.
     */
    private class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvName;
        private TextView mTvEmail;
        private CircleImageView mImgAvatar;
        private ImageView mImgHeaderBg;

        UserViewHolder(View itemView) {
            super(itemView);
            mImgAvatar = (CircleImageView) itemView.findViewById(R.id.imgProf);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            mImgHeaderBg = (ImageView) itemView.findViewById(R.id.imgBegin);

            mImgAvatar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.imgProf) {
                AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                builder.setTitle(R.string.Alert_choose).setItems(R.array.choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mOnItemClickListener.onAvatarClick(MainActivity.REQUEST_CODE_GALERY);
                                break;
                            case 1:
                                mOnItemClickListener.onAvatarClick(MainActivity.REQUEST_CODE_CAMERA);
                                break;
                            default:
                                dialog.dismiss();
                        }
                    }
                }).setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        }
    }

    /**
     * Copyright © 2016 AsianTech inc.
     * Created by DatBui on 15/06/2017.
     */
    private class DrawerViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;

        DrawerViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    /**
     * Copyright © 2016 AsianTech inc.
     * Created by DatBui on 15/06/2017.
     */
    public interface OnItemClickListener {
        void onItemClick(int position);

        void onAvatarClick(int select);
    }
}
