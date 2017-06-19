package vn.asiantech.internship.ui.friendlist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.User;

/**
 * Class RecyclerViewAdapter transfer data and show list data to RecyclerView
 *
 * @author at-haingo
 * @version 1.0
 * @since 2017-6-9
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private List<User> mUsers = new ArrayList<>();
    private LinearLayout.LayoutParams mLayoutParamFriend;
    private LinearLayout.LayoutParams mLayoutParamAdd;
    private Context mContext;
    private Drawable mDrawableChecked;
    private Drawable mDrawableAdd;

    public RecyclerViewAdapter(Context context, List<User> users) {
        this.mContext = context;
        this.mUsers = users;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_list_friend, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.mTvName.setText(mUsers.get(position).getName());
        if (mUsers.get(position).isFriend()) {
            holder.mBtnAdd.setText(R.string.friend);
            holder.mBtnAdd.setBackgroundColor(ContextCompat.getColor(mContext, R.color.button_friend_color));
            holder.mBtnAdd.setLayoutParams(mLayoutParamFriend);
            holder.mBtnAdd.setCompoundDrawablesWithIntrinsicBounds(mDrawableChecked, null, null, null);
        } else {
            holder.mBtnAdd.setText(R.string.add);
            holder.mBtnAdd.setBackgroundColor(ContextCompat.getColor(mContext, R.color.button_add_color));
            holder.mBtnAdd.setLayoutParams(mLayoutParamAdd);
            holder.mBtnAdd.setCompoundDrawablesWithIntrinsicBounds(mDrawableAdd, null, null, null);
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    /**
     * Class RecyclerViewHolder register and listen event user click button
     */
    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private Button mBtnAdd;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mBtnAdd = (Button) itemView.findViewById(R.id.btnAdd);
            mLayoutParamFriend = new LinearLayout.LayoutParams(300, 150);
            mLayoutParamAdd = new LinearLayout.LayoutParams(200, 150);
            mDrawableChecked = mContext.getDrawable(R.drawable.ic_checked);
            mDrawableAdd = mContext.getDrawable(R.drawable.ic_plus);
            mBtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mUsers.get(getAdapterPosition()).isFriend()) {
                        mBtnAdd.setText(R.string.add);
                        mUsers.get(getAdapterPosition()).setFriend(false);
                        mBtnAdd.setBackgroundColor(ContextCompat.getColor(mContext, R.color.button_add_color));
                        mBtnAdd.setLayoutParams(mLayoutParamAdd);
                        mBtnAdd.setCompoundDrawablesWithIntrinsicBounds(mDrawableAdd, null, null, null);
                    } else {
                        mBtnAdd.setText(R.string.friend);
                        mUsers.get(getAdapterPosition()).setFriend(true);
                        mBtnAdd.setBackgroundColor(ContextCompat.getColor(mContext, R.color.button_friend_color));
                        mBtnAdd.setLayoutParams(mLayoutParamFriend);
                        mBtnAdd.setCompoundDrawablesWithIntrinsicBounds(mDrawableChecked, null, null, null);
                    }
                }
            });
        }
    }
}
