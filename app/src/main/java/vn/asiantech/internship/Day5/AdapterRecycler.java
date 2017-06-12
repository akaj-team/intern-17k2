package vn.asiantech.internship.Day5;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import vn.asiantech.internship.R;

/**
 * Use to create a RecyclerView
 *
 * @author at-HoaVo
 * @version 1.0
 * @since 2017-6-12
 */
class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.RecyclerViewHoder> {
    private ArrayList<String> mFriends;

    AdapterRecycler(ArrayList<String> friends) {
        this.mFriends = friends;
    }

    @Override
    public RecyclerViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new RecyclerViewHoder(inflater.inflate(R.layout.item_list_friends, parent, false));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHoder holder, int position) {
        holder.mTvName.setText(mFriends.get(position));
        if (position <= 1) {
            holder.mBtnAdd.setVisibility(View.INVISIBLE);
            holder.mBtnFriends.setVisibility(View.VISIBLE);
        } else {
            holder.mBtnAdd.setVisibility(View.VISIBLE);
            holder.mBtnFriends.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mFriends.size();
    }

    /**
     * Use to create a RecyclerViewHoder
     */
    class RecyclerViewHoder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private Button mBtnAdd;
        Button mBtnFriends;

        RecyclerViewHoder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mBtnAdd = (Button) itemView.findViewById(R.id.btnAdd);
            mBtnFriends = (Button) itemView.findViewById(R.id.btnFriends);
            mBtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBtnAdd.setVisibility(View.INVISIBLE);
                    mBtnFriends.setVisibility(View.VISIBLE);
                }
            });
            mBtnFriends.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBtnAdd.setVisibility(View.VISIBLE);
                    mBtnFriends.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}
