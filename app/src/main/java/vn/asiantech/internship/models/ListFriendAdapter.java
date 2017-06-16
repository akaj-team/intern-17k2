package vn.asiantech.internship.models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by Thanh Thien on 6/9/17.
 * Adapter of RecyclerView
 */
public class ListFriendAdapter extends RecyclerView.Adapter<ListFriendAdapter.MyViewHolder> {

    private List<Friend> mFriends;

    public ListFriendAdapter(List<Friend> friends) {
        this.mFriends = friends;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_friend, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListFriendAdapter.MyViewHolder myViewHolder, int i) {
        //   MyViewHolder mViewHolder =  myViewHolder;
        myViewHolder.mTvFriendName.setText(mFriends.get(i).getNameFriend());
        myViewHolder.mIsFriend = mFriends.get(i).isFriend();
        if (mFriends.get(i).isFriend()) {
            myViewHolder.mBtnAdd.setBackgroundResource(R.drawable.bg_btn_added_friend);
            myViewHolder.mBtnAdd.setText(myViewHolder.itemView.getContext().getResources().getString(R.string.button_text_friend));
            myViewHolder.mBtnAdd.setTextColor(myViewHolder.itemView.getContext().getResources().getColor(R.color.friendsColorWhite));
        } else {
            myViewHolder.mBtnAdd.setBackgroundResource(R.drawable.bg_btn_add_friend);
            myViewHolder.mBtnAdd.setText(myViewHolder.itemView.getContext().getResources().getString(R.string.button_text_add));
            myViewHolder.mBtnAdd.setTextColor(myViewHolder.itemView.getContext().getResources().getColor(R.color.friendsColorBorder));
        }
    }

    @Override
    public int getItemCount() {
        return mFriends.size();
    }

    /**
     * Viewhoder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvFriendName;
        private Button mBtnAdd;
        private boolean mIsFriend;

        MyViewHolder(View itemView) {
            super(itemView);
            mTvFriendName = (TextView) itemView.findViewById(R.id.tvUserName);
            mBtnAdd = (Button) itemView.findViewById(R.id.btnAdd);
            mBtnAdd.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnAdd:
                    if (!mIsFriend) {
                        setColor(v);
                    } else {
                        unSetColor(v);
                    }
                    break;
            }
        }

        private void setColor(View v) {
            mBtnAdd.setBackgroundResource(R.drawable.bg_btn_added_friend);
            mBtnAdd.setText(v.getContext().getResources().getString(R.string.button_text_friend));
            mBtnAdd.setTextColor(v.getContext().getResources().getColor(R.color.friendsColorWhite));
            mFriends.get(getLayoutPosition()).setFriend(true);
            mIsFriend = true;
        }

        private void unSetColor(View v) {
            mBtnAdd.setBackgroundResource(R.drawable.bg_btn_add_friend);
            mBtnAdd.setText(v.getContext().getResources().getString(R.string.button_text_add));
            mBtnAdd.setTextColor(v.getContext().getResources().getColor(R.color.friendsColorBorder));
            mFriends.get(getLayoutPosition()).setFriend(false);
            mIsFriend = false;
        }
    }
}
