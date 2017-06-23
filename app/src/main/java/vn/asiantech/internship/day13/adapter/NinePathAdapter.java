package vn.asiantech.internship.day13.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day13.model.Chat;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 22/06/2017.
 */
public class NinePathAdapter extends RecyclerView.Adapter<NinePathAdapter.NinePathViewHolder> {
    private List<Chat> mChats;

    public NinePathAdapter(List<Chat> chats) {
        mChats = chats;
    }

    @Override
    public NinePathViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NinePathViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(NinePathViewHolder holder, int position) {
        holder.mTvChat1.setText(mChats.get(position).getText());
        holder.mTvChat2.setText(mChats.get(position).getText());
        if (mChats.get(position).isCheck()) {
            holder.mTvChat1.setVisibility(View.VISIBLE);
            holder.mTvChat2.setVisibility(View.INVISIBLE);
        } else {
            holder.mTvChat2.setVisibility(View.VISIBLE);
            holder.mTvChat1.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    /**
     * create class NinePathViewHolder
     */
    class NinePathViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvChat1;
        private TextView mTvChat2;

        NinePathViewHolder(View itemView) {
            super(itemView);
            mTvChat1 = (TextView) itemView.findViewById(R.id.tvChat1);
            mTvChat2 = (TextView) itemView.findViewById(R.id.tvChat2);
        }
    }
}
