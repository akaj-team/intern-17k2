package vn.asiantech.internship.friend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by PC on 6/9/2017.
 */
public class ListFriendAdapter extends RecyclerView.Adapter<ListFriendAdapter.FriendViewHolder> {
    private List<Friend> mFriends;
    private Context mContext;
    private LayoutInflater mInflater;

    public ListFriendAdapter(List<Friend> friends, Context context) {
        this.mFriends = friends;
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ListFriendAdapter.FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recyler_friends, parent, false);

        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListFriendAdapter.FriendViewHolder holder, int position) {
        Friend p = mFriends.get(position);
        holder.mTvName.setText(p.getName());
        if (p.isFriend()) {
            holder.mBtnFriendShip.setBackgroundResource(R.drawable.bg_friend_button);
            holder.mBtnFriendShip.setText(mContext.getResources().getString(R.string.friend));
            holder.mBtnFriendShip.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_check, 0, 0, 0);
        } else {
            holder.mBtnFriendShip.setBackgroundResource(R.drawable.bg_addfriend_button);
            holder.mBtnFriendShip.setText(mContext.getResources().getString(R.string.add));
            holder.mBtnFriendShip.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_add, 0, 0, 0);
        }
    }

    @Override
    public int getItemCount() {
        return mFriends.size();
    }

    /**
     * This class used to custom item of RecyclerView
     */
    public class FriendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTvName;
        public Button mBtnFriendShip;

        public FriendViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mBtnFriendShip = (Button) itemView.findViewById(R.id.btnFriendShip);

            mBtnFriendShip.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnFriendShip:
                    mFriends.get(getAdapterPosition()).setFriend();
                    notifyDataSetChanged();
                    break;
            }
        }
    }
}
