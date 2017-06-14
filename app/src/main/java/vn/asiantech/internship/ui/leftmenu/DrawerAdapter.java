package vn.asiantech.internship.ui.leftmenu;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.main.MainActivity;
import vn.asiantech.internship.models.DrawerItem;

/**
 *
 * Created by datbu on 12-06-2017.
 */

public class DrawerAdapter extends RecyclerView.Adapter {
    private static final int VIEW_USER = 1;
    private static final int VIEW_ITEM = 0;
    private Context mContext;
    private List<DrawerItem> mDrawerMenuList;
    private DrawerAdapter.OnItemClick mOnItemClickListener;
    private Bitmap avatar;

    public DrawerAdapter(Context context, List<DrawerItem> DrawerMenuList, DrawerAdapter.OnItemClick listener) {
        this.mDrawerMenuList = DrawerMenuList;
        this.mContext = context;
        mOnItemClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_USER;
        }
        return VIEW_ITEM;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_navigation, parent, false);
                return new DrawerViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_header, parent, false);
                return new UserViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof UserViewHolder) {
            ((UserViewHolder) holder).mTvName.setText(R.string.app_name);
            ((UserViewHolder) holder).mTvEmail.setText(R.string.action_settings);
            if (avatar != null) {
                ((UserViewHolder) holder).mImgAvatar.setImageBitmap(avatar);
            }
            Drawable wallpaper = WallpaperManager.getInstance(mContext).getDrawable();
            ((UserViewHolder) holder).mImgHeaderBg.setImageBitmap(((BitmapDrawable) wallpaper).getBitmap());
        } else if (holder instanceof DrawerViewHolder) {
            DrawerItem drawerItem = mDrawerMenuList.get(position);
            ((DrawerViewHolder) holder).mTvTitle.setText(mDrawerMenuList.get(position).getTitle());
            if (drawerItem.isChoose()) {
                ((DrawerViewHolder) holder).mTvTitle.setTextColor(Color.parseColor("#C1C0C6"));

            } else {
                ((DrawerViewHolder) holder).mTvTitle.setTextColor(Color.parseColor("#ffffff"));
            }
        }

    }

    @Override
    public int getItemCount() {
        return mDrawerMenuList.size();
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

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
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose action").setItems(R.array.choose, new DialogInterface.OnClickListener() {
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
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }

        }
    }

    private class DrawerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
                    Log.d("tag11", "onClick：" + getAdapterPosition());

                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface OnItemClick {
        void onItemClick(int position);

        void onAvatarClick(int select);
    }
}
