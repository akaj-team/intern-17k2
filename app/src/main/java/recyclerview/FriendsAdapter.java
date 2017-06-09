package recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;


import vn.asiantech.internship.R;


class FriendsAdapter extends RecyclerView.Adapter {
    private List<User> users;

    FriendsAdapter(List<User> users) {
        this.users = users;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.item_friend_list, parent, false);
        return new FriendsAdapter.MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.mTvName.setText(users.get(position).getName());
        myViewHolder.mTvDesciption.setText(users.get(position).getDescription());
        if (position == 0 || position == 1) {
            setBackgouundAdd(myViewHolder.mBtFriend);
            users.get(position).setState(true);
        } else {
            setBackgouundFriend(myViewHolder.mBtFriend);
            users.get(position).setState(false);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTvName;
        TextView mTvDesciption;
        ImageButton mBtFriend;

        MyViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvDesciption = (TextView) itemView.findViewById(R.id.tvDescription);
            mBtFriend = (ImageButton) itemView.findViewById(R.id.btnFriend);
            mBtFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (users.get(getAdapterPosition()).isState()) {
                        users.get(getAdapterPosition()).setState(false);
                        setBackgouundAdd(mBtFriend);
                    } else {
                        users.get(getAdapterPosition()).setState(true);
                        setBackgouundFriend(mBtFriend);
                    }
                }
            });
        }
    }

    private void setBackgouundAdd(ImageButton btn) {
        btn.setBackgroundResource(R.drawable.icon_friend);
    }

    private void setBackgouundFriend(ImageButton btn) {
        btn.setBackgroundResource(R.drawable.icon_add);
    }
}
