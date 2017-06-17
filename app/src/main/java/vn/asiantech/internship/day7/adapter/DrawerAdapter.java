package vn.asiantech.internship.day7.adapter;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day7.event.OnRecyclerViewClickListener;

/**
 * create RecyclerViewAdapter
 * Created by at-hoavo on 09/06/2017.
 */
public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<String> mFunctions;
    private OnRecyclerViewClickListener mOnRecyclerViewClickListener;
    private Context mContext;
    private Bitmap mAvatarBitmap;
    private Drawable mWallpaperDrawable;
    private int mPosition;

    public DrawerAdapter(List<String> items, Context context, OnRecyclerViewClickListener onRecyclerViewClickListener) {
        mFunctions = items;
        mContext = context;
        mOnRecyclerViewClickListener = onRecyclerViewClickListener;
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(mContext);
        mWallpaperDrawable = wallpaperManager.getDrawable();
        mAvatarBitmap = getRoundedCornerBitmap(drawableToBitmap(mContext.getResources().getDrawable(R.mipmap.ic_user)), R.dimen.drawable_pixel);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                View viewHeader = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_drawer, parent, false);
                return new ItemHeaderViewHolder(viewHeader);
            default:
                View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_drawer, parent, false);
                return new ItemViewHolder(viewItem);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemHeaderViewHolder) {
            ItemHeaderViewHolder itemHeaderViewHolder = (ItemHeaderViewHolder) holder;
            itemHeaderViewHolder.mImgAvatar.setImageBitmap(mAvatarBitmap);
            itemHeaderViewHolder.mImgBackground.setImageDrawable(mWallpaperDrawable);
        } else {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.mTvFunction.setText(mFunctions.get(position));
            if (position == mPosition) {
                itemViewHolder.mItemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorItemPressed));
            } else {
                itemViewHolder.mItemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorItem));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mFunctions.size();
    }

    public void setBitMapAvatar(Bitmap avatarBitmap) {
        mAvatarBitmap = getRoundedCornerBitmap(avatarBitmap, R.dimen.drawable_pixel);
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    /**
     * create ItemViewHolder
     */
    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvFunction;
        private View mItemView;

        ItemViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mTvFunction = (TextView) itemView.findViewById(R.id.tvFunction);
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnRecyclerViewClickListener != null) {
                        mOnRecyclerViewClickListener.onClick(getAdapterPosition(), true);
                    }
                }
            });
        }
    }

    /**
     * create ItemHeaderViewHolder
     */
    private class ItemHeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgAvatar;
        private ImageView mImgBackground;

        ItemHeaderViewHolder(View itemView) {
            super(itemView);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            mImgBackground = (ImageView) itemView.findViewById(R.id.imgBackground);
            mImgAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnRecyclerViewClickListener != null) {
                        mOnRecyclerViewClickListener.onClick(getAdapterPosition(), false);
                    }
                }
            });
        }
    }

    private Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(mContext.getResources().getColor(R.color.avatar_bolder));
        canvas.drawRoundRect(rectF,(float)pixels,(float)pixels,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        return bitmap;
    }
}
