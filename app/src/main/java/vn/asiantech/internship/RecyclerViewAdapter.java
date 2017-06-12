package vn.asiantech.internship;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by DatBui on 10/06/2017.
 */
class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyHolder> {
    private List<Friend> mFriendList;

    RecyclerViewAdapter(List<Friend> friendList) {
        this.mFriendList = friendList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_friend, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Friend friend = mFriendList.get(position);
        holder.mTvName.setText(friend.getName());

        Button btn = holder.btnAdd;
        if (friend.isFriend()) {
            btn.setText(R.string.btn_friend);
            btn.setBackgroundColor(Color.RED);
            btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.imgcheck, 0, 0, 0);
        } else {
            btn.setText(R.string.btn_add);
            btn.setBackgroundColor(Color.GRAY);
            btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.imgadd, 0, 0, 0);
        }
    }

    @Override
    public int getItemCount() {
        return mFriendList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvName;
        private Button btnAdd;

        MyHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            btnAdd = (Button) itemView.findViewById(R.id.btnAdd);
            btnAdd.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnAdd:
                    mFriendList.get(getAdapterPosition()).setFriend();
                    notifyDataSetChanged();
                    break;
            }
        }
    }
}
