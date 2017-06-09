package recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Used to collect and display list data to the View.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-9
 */

class FriendsAdapter extends RecyclerView.Adapter {
    private final List<User> mUsers;

    FriendsAdapter(List<User> users) {
        this.mUsers = users;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_friend_list, parent, false);
        RecyclerView.ViewHolder v = new MyViewHolder(itemView);
        return v;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.mTvName.setText(mUsers.get(position).getName());
        myViewHolder.mTvDescription.setText(mUsers.get(position).getDescription());
        if (position == 0 || position == 1) {
            setBackgroundAdd(myViewHolder.mBtFriend);
            mUsers.get(position).setState(true);
        } else {
            setBackgroundFriend(myViewHolder.mBtFriend);
            mUsers.get(position).setState(false);
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        final private TextView mTvName;
        final private TextView mTvDescription;
        final private ImageButton mBtFriend;

        MyViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            mBtFriend = (ImageButton) itemView.findViewById(R.id.imgBtnFriend);
            mBtFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mUsers.get(getAdapterPosition()).isState()) {
                        mUsers.get(getAdapterPosition()).setState(false);
                        setBackgroundAdd(mBtFriend);
                    } else {
                        mUsers.get(getAdapterPosition()).setState(true);
                        setBackgroundFriend(mBtFriend);
                    }
                }
            });
        }
    }

    private void setBackgroundAdd(ImageButton imgBtnAdd) {
        imgBtnAdd.setBackgroundResource(R.drawable.icon_friend);
    }

    private void setBackgroundFriend(ImageButton imgBtnAdd) {
        imgBtnAdd.setBackgroundResource(R.drawable.icon_add);
    }
}
