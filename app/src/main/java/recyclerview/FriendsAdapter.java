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
    private final List<User> users;

    FriendsAdapter(List<User> users) {
        this.users = users;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_friend_list, parent, false);
        return new FriendsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.mTvName.setText(users.get(position).getName());
        myViewHolder.mTvDescription.setText(users.get(position).getDescription());
        if (position == 0 || position == 1) {
            setBackgroundAdd(myViewHolder.mBtFriend);
            users.get(position).setState(true);
        } else {
            setBackgroundFriend(myViewHolder.mBtFriend);
            users.get(position).setState(false);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView mTvName;
        final TextView mTvDescription;
        final ImageButton mBtFriend;

        MyViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            mBtFriend = (ImageButton) itemView.findViewById(R.id.btnFriend);
            mBtFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (users.get(getAdapterPosition()).isState()) {
                        users.get(getAdapterPosition()).setState(false);
                        setBackgroundAdd(mBtFriend);
                    } else {
                        users.get(getAdapterPosition()).setState(true);
                        setBackgroundFriend(mBtFriend);
                    }
                }
            });
        }
    }

    private void setBackgroundAdd(ImageButton btn) {
        btn.setBackgroundResource(R.drawable.icon_friend);
    }

    private void setBackgroundFriend(ImageButton btn) {
        btn.setBackgroundResource(R.drawable.icon_add);
    }
}
