package vn.asiantech.internship.drawerlayout;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.asiantech.internship.R;
import vn.asiantech.internship.models.DrawerItem;
import vn.asiantech.internship.models.User;

/**
 * Created by PC on 6/12/2017.
 * This class used to custom DrawerLayout
 */
public class DrawerApdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 0;
    private List<Object> mItems;
    private Context mContext;
    private MainActivity.OnItemClickListener mListener;

    public DrawerApdater(Context context, List<Object> items,
                         MainActivity.OnItemClickListener listener) {
        this.mItems = items;
        this.mContext = context;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_drawer, parent, false);
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
            DrawerItem drawerItem = (DrawerItem) mItems.get(position);
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
            User user = (User) mItems.get(position);
            // TODO: 6/12/2017 dummy data
            header.mTvName.setText(user.getName());
            header.mTVEmail.setText(user.getEmail());
            if (user.getAvatar() != null) {
                header.mImgAvatar.setImageBitmap(user.getAvatar());
            }
            Drawable wallpaper = WallpaperManager.getInstance(mContext).getDrawable();
            header.mImgHeaderBg.setImageBitmap(((BitmapDrawable) wallpaper).getBitmap());
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
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
                mListener.onItemClick(getAdapterPosition());
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
                    builder.setPositiveButton("Garelly", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mListener.onAvatarClick(MainActivity.REQUEST_CODE_GARELLY);
                            dialog.dismiss();
                        }
                    }).setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mListener.onAvatarClick(MainActivity.REQUEST_CODE_CAMERA);
                            dialog.dismiss();
                        }
                    });
                    builder.setMessage("Chọn hành động!");
                    builder.show();
                    break;
            }
        }
    }

}
