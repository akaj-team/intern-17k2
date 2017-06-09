package vn.asiantech.internship.adapter;

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
import vn.asiantech.internship.model.User;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder> {
    private ArrayList<User> mUsers;
    private Context mContext;

    public FriendsAdapter(ArrayList<User> users, Context context) {
        this.mUsers = users;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_friend, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User user = mUsers.get(position);
        holder.mTvName.setText(user.getName());
        if (user.isFriend()) {
            holder.mBtnAdd.setText(R.string.button_text_friend);
            holder.mBtnAdd.setBackgroundColor(Color.RED);
            holder.mBtnAdd.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked_32, 0, 0, 0);
        } else {
            holder.mBtnAdd.setBackgroundColor(Color.GREEN);
            holder.mBtnAdd.setText(R.string.button_add);
            holder.mBtnAdd.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_menu_add, 0, 0, 0);
        }
        holder.mBtnAdd.setWidth(50);
    }

    @Override
    public int getItemCount() {
        return mUsers == null ? 0 : mUsers.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvName;
        private Button mBtnAdd;

        MyViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mBtnAdd = (Button) itemView.findViewById(R.id.btnAdd);
            mBtnAdd.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnDoSth) {
                Log.i("TAG1", "pressed");
                if (mUsers.get(getAdapterPosition()).isFriend()) {
                    mUsers.get(getAdapterPosition()).setFriend(false);
                } else {
                    mUsers.get(getAdapterPosition()).setFriend(true);
                }
                notifyDataSetChanged();
            }
        }
    }
}
