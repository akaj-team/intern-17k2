package vn.asiantech.internship.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

/**
 * Created by Hai on 6/9/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private List<User> mListUser = new ArrayList<>();
    private LinearLayout.LayoutParams mLayoutParamFriend;
    private LinearLayout.LayoutParams mLayoutParamAdd;
    private Context mContext;
    private Drawable mImg_Checked;
    private Drawable mImg_Add;

    public RecyclerViewAdapter(List<User> mListUser, Context context) {
        this.mListUser = mListUser;
        this.mContext = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_list_friend, parent, false);
        mLayoutParamFriend = new LinearLayout.LayoutParams(300, 150);
        mLayoutParamAdd = new LinearLayout.LayoutParams(200, 150);
        mImg_Checked = mContext.getDrawable(R.drawable.ic_checked);
        mImg_Add = mContext.getDrawable(R.drawable.ic_plus);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.mTvName.setText(mListUser.get(position).getName());
        if (mListUser.get(position).isFriend()) {
            holder.mBtnAdd.setText(R.string.isFriend_true);
            holder.mBtnAdd.setBackgroundColor(Color.parseColor("#F55A9E"));
            holder.mBtnAdd.setLayoutParams(mLayoutParamFriend);
            holder.mBtnAdd.setCompoundDrawablesWithIntrinsicBounds(mImg_Checked, null, null, null);
        } else {
            holder.mBtnAdd.setText(R.string.isFriend_false);
            holder.mBtnAdd.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.mBtnAdd.setLayoutParams(mLayoutParamAdd);
            holder.mBtnAdd.setCompoundDrawablesWithIntrinsicBounds(mImg_Add, null, null, null);
        }
    }

    @Override
    public int getItemCount() {
        return mListUser.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private Button mBtnAdd;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mBtnAdd = (Button) itemView.findViewById(R.id.btnAdd);
            mBtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListUser.get(getAdapterPosition()).isFriend()) {
                        mBtnAdd.setText(R.string.isFriend_false);
                        mListUser.get(getAdapterPosition()).setFriend(false);
                        mBtnAdd.setBackgroundColor(Color.parseColor("#ffffff"));
                        mBtnAdd.setLayoutParams(mLayoutParamAdd);
                        mBtnAdd.setCompoundDrawablesWithIntrinsicBounds(mImg_Add, null, null, null);
                    } else {
                        mBtnAdd.setText(R.string.isFriend_true);
                        mListUser.get(getAdapterPosition()).setFriend(true);
                        mBtnAdd.setBackgroundColor(Color.parseColor("#F55A9E"));
                        mBtnAdd.setLayoutParams(mLayoutParamFriend);
                        mBtnAdd.setCompoundDrawablesWithIntrinsicBounds(mImg_Checked, null, null, null);
                    }
                }
            });
        }
    }
}
