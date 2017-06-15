package vn.asiantech.internship.ui.leftmenu;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Drawer;
import vn.asiantech.internship.ui.main.MainActivity;

/**
 * Adapter for Navigation
 * Created by anhhuy on 14/06/2017.
 */

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_DRAWER = 1;

    private MainActivity.OnItemClickListener mItemClickListener;
    private List<Drawer> mDrawerLists;
    private Bitmap mBitmap;

    public DrawerAdapter(Context context, List<Drawer> drawerLists, MainActivity.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        mDrawerLists = drawerLists;
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_navigation_header, parent, false);
                return new DrawerHeaderViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_navigation, parent, false);
                return new DrawerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DrawerViewHolder) {
            DrawerViewHolder drawer = (DrawerViewHolder) holder;
            Drawer itemDrawer = mDrawerLists.get(position - 1);
            drawer.mTvColor.setText(itemDrawer.getDrawerName());
            if (itemDrawer.isChoosed()) {
                drawer.mTvColor.setTextColor(ContextCompat.getColor(drawer.itemView.getContext(), R.color.colorAccent));
            } else {
                drawer.mTvColor.setTextColor(Color.WHITE);
            }
        }

        if (holder instanceof DrawerHeaderViewHolder) {
            DrawerHeaderViewHolder drawerHeader = (DrawerHeaderViewHolder) holder;
            drawerHeader.mTvName.setText(R.string.textview_header_name);
            drawerHeader.mTvEmail.setText(R.string.textview_header_email);
            if (mBitmap != null) {
                drawerHeader.mCivAvatar.setImageBitmap(mBitmap);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDrawerLists.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_DRAWER;
    }

    /**
     * View Holder for Drawer Item
     * Created by huypham on 15/6/2017
     */

    private class DrawerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvColor;

        public DrawerViewHolder(View itemView) {
            super(itemView);
            mTvColor = (TextView) itemView.findViewById(R.id.tvColor);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(getAdapterPosition() - 1);
            }
        }
    }

    /**
     * View Holder for Drawer Header
     * Created by huypham on 15/6/2017
     */

    private class DrawerHeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final int CAMERA = 0;
        private static final int GALLERY = 1;

        private TextView mTvName, mTvEmail;
        private CircleImageView mCivAvatar;

        public DrawerHeaderViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            mCivAvatar = (CircleImageView) itemView.findViewById(R.id.civAvatar);
            mCivAvatar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.civAvatar:
                    setDialogChoose(v.getContext());
                    break;
            }
        }

        private void setDialogChoose(Context context) {
            AlertDialog.Builder dialogChoose = new AlertDialog.Builder(context);
            dialogChoose.setTitle(R.string.dialog_choose)
                    .setItems(R.array.list, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case CAMERA:
                                    if (mItemClickListener != null) {
                                        mItemClickListener.dialogChoose(MainActivity.REQUEST_CAMERA);
                                    }
                                    break;
                                case GALLERY:
                                    if (mItemClickListener != null) {
                                        mItemClickListener.dialogChoose(MainActivity.REQUEST_GALLERY);
                                    }
                                    break;
                                default:
                                    dialog.dismiss();
                            }
                        }
                    });
            dialogChoose.show();
        }
    }
}
