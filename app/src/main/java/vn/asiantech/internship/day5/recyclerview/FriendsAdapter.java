package vn.asiantech.internship.day5.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import vn.asiantech.internship.R;

/**
 * Created by PC on 6/9/2017.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendViewHolder> {
    private ArrayList<Person> mDataset;
    private Context mContext;
    private LayoutInflater mInflater;

    public FriendsAdapter(ArrayList<Person> mDataset, Context mContext) {
        this.mDataset = mDataset;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public FriendsAdapter.FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recyler_friends, parent, false);

        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendsAdapter.FriendViewHolder holder, int position) {
        Person p = mDataset.get(position);
        holder.mTvName.setText(p.getName());
        if (p.isFriend()) {
            holder.mBtnFriendShip.setBackgroundResource(R.drawable.bg_friend_button);
            holder.mBtnFriendShip.setText(mContext.getResources().getString(R.string.friend));
            holder.mBtnFriendShip.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check,0,0,0);
        } else {
            holder.mBtnFriendShip.setBackgroundResource(R.drawable.bg_addfriend_button);
            holder.mBtnFriendShip.setText(mContext.getResources().getString(R.string.add));
            holder.mBtnFriendShip.setCompoundDrawablesWithIntrinsicBounds(R.drawable.add,0,0,0);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

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
                    mDataset.get(getAdapterPosition()).setFriend();
                    notifyDataSetChanged();
                    break;
            }
        }
    }
}
