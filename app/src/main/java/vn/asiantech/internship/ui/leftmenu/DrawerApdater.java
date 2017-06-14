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
 * This class used to custom DrawerLayout
 *
 * @author at-cuongcao
 * @version 1.0
 * @since 06/12/2017
 */
public class DrawerApdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 0;
    private List<DrawerItem> mItems;
    private Context mContext;
    private MainActivity.OnItemClickListener mListener;
    private Bitmap mBitmap;
    private Drawable mWallpaper;

    public DrawerApdater(Context context, List<DrawerItem> items,
                         MainActivity.OnItemClickListener listener) {
        this.mItems = items;
        this.mContext = context;
        mListener = listener;
        mWallpaper = WallpaperManager.getInstance(mContext).getDrawable();
    }

    public void setAvtar(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_header, parent, false);
                return new DrawerLayoutHeader(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer, parent, false);
                return new DrawerLayoutItem(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DrawerLayoutItem) {
            DrawerLayoutItem item = (DrawerLayoutItem) holder;
            DrawerItem drawerItem = mItems.get(position - 1);
            item.mTvName.setText(drawerItem.getName());
            if (drawerItem.isSelected()) {
                item.mTvName.setTextColor(Color.parseColor(mContext.getResources().getString(R.string.drawerItemChooserTextColor)));
            } else {
                item.mTvName.setTextColor(Color.WHITE);
            }
            return;
        }
        if (holder instanceof DrawerLayoutHeader) {
            DrawerLayoutHeader header = (DrawerLayoutHeader) holder;
            // TODO: 6/12/2017 dummy data
            header.mTvName.setText(R.string.user_name);
            header.mTVEmail.setText(R.string.email);
            if (mBitmap != null) {
                header.mImgAvatar.setImageBitmap(mBitmap);
            }
            header.mImgHeaderBg.setImageBitmap(((BitmapDrawable) mWallpaper).getBitmap());
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size() + 1;
    }

    /**
     * This class used to custom list Item of DrawerLayout
     */
    private class DrawerLayoutItem extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvName;

        private DrawerLayoutItem(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvItemName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(getAdapterPosition() - 1);
            }
        }
    }

    /**
     * This class used to custom header of DrawerLayout
     */
    private class DrawerLayoutHeader extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvName;
        private TextView mTVEmail;
        private CircleImageView mImgAvatar;
        private ImageView mImgHeaderBg;

        private DrawerLayoutHeader(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTVEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            mImgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
            mImgHeaderBg = (ImageView) itemView.findViewById(R.id.imgHeaderBg);
            mImgAvatar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgAvatar:
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(R.string.choose_action).setItems(R.array.pick_image_chooser, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    if (mListener != null) {
                                        mListener.onAvatarClick(MainActivity.REQUEST_CODE_GALLERY);
                                    }
                                    break;
                                case 1:
                                    if (mListener != null) {
                                        mListener.onAvatarClick(MainActivity.REQUEST_CODE_CAMERA);
                                    }
                                    break;
                                default:
                                    dialog.dismiss();
                            }
                        }
                    });
                    builder.show();
                    break;
            }
        }
    }
}
