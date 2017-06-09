package vn.asiantech.internship;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

/**
 * Created by BACKDOOR on 07-Feb-17.
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    private List<FriendObject> mFriends;

    public FriendAdapter(List<FriendObject> datas) {
        mFriends = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_friend_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvFriendName.setText(mFriends.get(position).getFriendName());
        switch (new Random().nextInt(4)) {
            case 0:
                holder.mImgFriend.setImageResource(R.drawable.img_summer01);
                break;
            case 1:
                holder.mImgFriend.setImageResource(R.drawable.img_summer02);
                break;
            case 2:
                holder.mImgFriend.setImageResource(R.drawable.img_summer03);
                break;
            default:
                holder.mImgFriend.setImageResource(R.drawable.img_summer04);
        }
    }

    @Override
    public int getItemCount() {
        return mFriends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTvFriendName;
        ImageView mImgFriend;
        Button mBtnAddFriend;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvFriendName = (TextView) itemView.findViewById(R.id.tvFriendName);
            mImgFriend = (ImageView) itemView.findViewById(R.id.imgFriend);
            mBtnAddFriend = (Button) itemView.findViewById(R.id.btnAddFriend);

            mBtnAddFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FriendObject friendObject = mFriends.get(getAdapterPosition());
                    if (!friendObject.isFriend()) {
                        mBtnAddFriend.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_friend_check_button, 0, 0, 0);
                        mBtnAddFriend.setBackgroundColor(0XFF79378B);
                        mBtnAddFriend.setText(String.valueOf("Friend"));
                        friendObject.setFriend(true);
                    } else {
                        mBtnAddFriend.setText(String.valueOf("Add"));
                        mBtnAddFriend.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_friend_add_button, 0, 0, 0);
                        mBtnAddFriend.setBackgroundColor(0XFF426EB4);
                        friendObject.setFriend(false);
                    }
                }
            });
        }
    }
}
