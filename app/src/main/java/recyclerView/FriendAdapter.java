package recyclerView;

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
class FriendAdapter extends RecyclerView.Adapter {
    private final List<User> mUsers;

    FriendAdapter(List<User> users) {
        this.mUsers = users;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_friend_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.mTvName.setText(mUsers.get(position).getName());
        myViewHolder.mTvDescription.setText(mUsers.get(position).getDescription());
        if (position == 0 || position == 1) {
            setBackgroundAdd(myViewHolder.mImgBtnFriend);
            mUsers.get(position).setState(true);
        } else {
            setBackgroundFriend(myViewHolder.mImgBtnFriend);
            mUsers.get(position).setState(false);
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    /**
     * MyViewHolder register for user.
     */
    private class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvName;
        private final TextView mTvDescription;
        private final ImageButton mImgBtnFriend;

        MyViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            mImgBtnFriend = (ImageButton) itemView.findViewById(R.id.imgBtnFriend);
            mImgBtnFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mUsers.get(getAdapterPosition()).isState()) {
                        mUsers.get(getAdapterPosition()).setState(false);
                        setBackgroundAdd(mImgBtnFriend);
                    } else {
                        mUsers.get(getAdapterPosition()).setState(true);
                        setBackgroundFriend(mImgBtnFriend);
                    }
                }
            });
        }
    }

    private void setBackgroundAdd(ImageButton imgBtnAdd) {
        imgBtnAdd.setBackgroundResource(R.mipmap.ic_friend);
    }

    private void setBackgroundFriend(ImageButton imgBtnAdd) {
        imgBtnAdd.setBackgroundResource(R.mipmap.ic_add);
    }
}
