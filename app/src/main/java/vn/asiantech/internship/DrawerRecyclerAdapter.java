package vn.asiantech.internship;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by datbu on 12-06-2017.
 */

class DrawerRecyclerAdapter extends RecyclerView.Adapter {
    private static final int VIEW_USER = 1;
    private static final int VIEW_ITEM = 0;
    private List<DrawerItem> mDrawerMenuList;

    DrawerRecyclerAdapter(List<DrawerItem> DrawerMenuList) {
        this.mDrawerMenuList = DrawerMenuList;
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
            ((UserViewHolder) holder).mImgAvar.setImageResource(mDrawerMenuList.get(position).getAvatar());
            ((UserViewHolder) holder).mTvName.setText(mDrawerMenuList.get(position).getName());
            ((UserViewHolder) holder).mTvEmail.setText(mDrawerMenuList.get(position).getEmail());

        } else if (holder instanceof DrawerViewHolder) {
            ((DrawerViewHolder) holder).mTvTitle.setText(mDrawerMenuList.get(position - 1).getTitle());
            ((DrawerViewHolder) holder).mImgIcon.setImageResource(mDrawerMenuList.get(position - 1).getIcon());
        }
    }

    @Override
    public int getItemCount() {
        return mDrawerMenuList.size() + 1;
    }


    private class UserViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgAvar;
        private TextView mTvName;
        private TextView mTvEmail;

        public UserViewHolder(View itemView) {
            super(itemView);
            mImgAvar = (ImageView) itemView.findViewById(R.id.imgProf);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvEmail = (TextView) itemView.findViewById(R.id.tvEmail);

        }
    }

    private class DrawerViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;
        private ImageView mImgIcon;

        DrawerViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mImgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);

        }

    }
}
