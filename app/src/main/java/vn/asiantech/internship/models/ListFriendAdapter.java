package vn.asiantech.internship.models;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by root on 6/9/17.
 */

public class ListFriendAdapter extends RecyclerView.Adapter<ListFriendAdapter.MyViewHolder> {

    private List<Friend> mFriends;
    private Context mContext;

    public ListFriendAdapter(List<Friend> FriendArrayList, Context context) {
        this.mFriends = FriendArrayList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_friend, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(v);
        holder.setIsRecyclable(false);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ListFriendAdapter.MyViewHolder myViewHolder, int i) {
        //   MyViewHolder mViewHolder =  myViewHolder;
        myViewHolder.mTvFriendName.setText(mFriends.get(i).getNameFriend());
        myViewHolder.mIsFriend = mFriends.get(i).isFriend();
        if (mFriends.get(i).isFriend()) {
            myViewHolder.mBtnAdd.setBackground(mContext.getResources().getDrawable(R.drawable.friends_bg_btn_add_selected));
            myViewHolder.mBtnAdd.setText(mContext.getResources().getString(R.string.Button_Text_Friend));
            myViewHolder.mBtnAdd.setTextColor(mContext.getResources().getColor(R.color.friendsColorWhite));
        }
    }


    @Override
    public int getItemCount() {
        return mFriends.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvFriendName;
        private TextView mTvFriendDes;
        private ImageView mImageView;
        private Button mBtnAdd;
        private boolean mIsFriend;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvFriendName = (TextView) itemView.findViewById(R.id.tvUserName);
            mTvFriendDes = (TextView) itemView.findViewById(R.id.tvDescription);
            mImageView = (ImageView) itemView.findViewById(R.id.imgAvatar);
            mBtnAdd = (Button) itemView.findViewById(R.id.btnAdd);
            mBtnAdd.setOnClickListener(this);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnAdd:
                    if (!mIsFriend) {
                        setColor();
                    } else {
                        unSetColor();
                    }
                    break;
            }
        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void setColor() {
            mBtnAdd.setBackground(mContext.getResources().getDrawable(R.drawable.friends_bg_btn_add_selected));
            mBtnAdd.setText(mContext.getResources().getString(R.string.Button_Text_Friend));
            mBtnAdd.setTextColor(mContext.getResources().getColor(R.color.friendsColorWhite));
            mFriends.get(getLayoutPosition()).setFriend(true);
            mIsFriend = true;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void unSetColor() {
            mBtnAdd.setBackground(mContext.getResources().getDrawable(R.drawable.friends_bg_btn_add));
            mBtnAdd.setText(mContext.getResources().getString(R.string.Button_Text_Add));
            mBtnAdd.setTextColor(mContext.getResources().getColor(R.color.friendsColorBorder));
            mFriends.get(getLayoutPosition()).setFriend(false);
            mIsFriend = false;
        }
    }


}
