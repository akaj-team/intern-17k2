package vn.asiantech.internship.adapter;

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
import vn.asiantech.internship.model.User;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private List<User> mListUser = new ArrayList<>();
    private LinearLayout.LayoutParams mLayoutParamFriend;
    private LinearLayout.LayoutParams mLayoutParamAdd;
    private Context mContext;
    private Drawable mDrawableChecked;
    private Drawable mDrawableAdd;

    public RecyclerViewAdapter(Context context, List<User> users) {
        this.mContext = context;
        this.mListUser = users;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_list_friend, parent, false);
        mLayoutParamFriend = new LinearLayout.LayoutParams(300, 150);
        mLayoutParamAdd = new LinearLayout.LayoutParams(200, 150);
        mDrawableChecked = mContext.getDrawable(R.drawable.ic_checked);
        mDrawableAdd = mContext.getDrawable(R.drawable.ic_plus);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.mTvName.setText(mListUser.get(position).getName());
        if (mListUser.get(position).isFriend()) {
            holder.mBtnAdd.setText(R.string.friend);
            holder.mBtnAdd.setBackgroundColor(ContextCompat.getColor(mContext, R.color.friend));
            holder.mBtnAdd.setLayoutParams(mLayoutParamFriend);
            holder.mBtnAdd.setCompoundDrawablesWithIntrinsicBounds(mDrawableChecked, null, null, null);
        } else {
            holder.mBtnAdd.setText(R.string.add);
            holder.mBtnAdd.setBackgroundColor(ContextCompat.getColor(mContext, R.color.add));
            holder.mBtnAdd.setLayoutParams(mLayoutParamAdd);
            holder.mBtnAdd.setCompoundDrawablesWithIntrinsicBounds(mDrawableAdd, null, null, null);
        }
    }

    @Override
    public int getItemCount() {
        return mListUser.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private Button mBtnAdd;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mBtnAdd = (Button) itemView.findViewById(R.id.btnAdd);
            mBtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListUser.get(getAdapterPosition()).isFriend()) {
                        mBtnAdd.setText(R.string.add);
                        mListUser.get(getAdapterPosition()).setFriend(false);
                        mBtnAdd.setBackgroundColor(ContextCompat.getColor(mContext, R.color.add));
                        mBtnAdd.setLayoutParams(mLayoutParamAdd);
                        mBtnAdd.setCompoundDrawablesWithIntrinsicBounds(mDrawableAdd, null, null, null);
                    } else {
                        mBtnAdd.setText(R.string.friend);
                        mListUser.get(getAdapterPosition()).setFriend(true);
                        mBtnAdd.setBackgroundColor(ContextCompat.getColor(mContext, R.color.friend));
                        mBtnAdd.setLayoutParams(mLayoutParamFriend);
                        mBtnAdd.setCompoundDrawablesWithIntrinsicBounds(mDrawableChecked, null, null, null);
                    }
                }
            });
        }
    }
}
