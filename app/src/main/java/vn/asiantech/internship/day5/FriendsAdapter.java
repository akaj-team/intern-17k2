package vn.asiantech.internship.day5;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import vn.asiantech.internship.R;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder> {
    private ArrayList<UserDay5> mUsers;
    private Context mContext;

    public FriendsAdapter(ArrayList<UserDay5> mUsers, Context mContext) {
        this.mUsers = mUsers;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UserDay5 user = mUsers.get(position);
        holder.mTvName.setText(user.getName());
        if (user.isFriend()) {
            holder.mBtnDoSth.setText(R.string.button_text_friend);
            holder.mBtnDoSth.setBackgroundColor(Color.RED);
            holder.mBtnDoSth.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked_32, 0, 0, 0);
        } else {
            holder.mBtnDoSth.setBackgroundColor(Color.GREEN);
            holder.mBtnDoSth.setText(R.string.button_add);
            holder.mBtnDoSth.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_menu_add, 0, 0, 0);
        }
        holder.mBtnDoSth.setWidth(50);
    }

    @Override
    public int getItemCount() {
        return mUsers == null ? 0 : mUsers.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvName;
        public Button mBtnDoSth;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mBtnDoSth = (Button) itemView.findViewById(R.id.btnDoSth);
            mBtnDoSth.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnDoSth) {
                Log.i("TAG1", "pressed");
                if (mUsers.get(getAdapterPosition()).isFriend()) {
                    mUsers.get(getAdapterPosition()).setFriend(false);
                    notifyDataSetChanged();
                } else {
                    mUsers.get(getAdapterPosition()).setFriend(true);
                    notifyDataSetChanged();
                }
            }
        }
    }
}
