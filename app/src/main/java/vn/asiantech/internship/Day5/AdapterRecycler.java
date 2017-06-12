package vn.asiantech.internship.Day5;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import vn.asiantech.internship.R;

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
        holder.tvName.setText(mFriends.get(position));
        if (position <= 1) {
            holder.btnAdd.setVisibility(View.INVISIBLE);
            holder.btnFriends.setVisibility(View.VISIBLE);
        } else {
            holder.btnAdd.setVisibility(View.VISIBLE);
            holder.btnFriends.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mFriends.size();
    }

    class RecyclerViewHoder extends RecyclerView.ViewHolder {
        TextView tvName;
        Button btnAdd;
        Button btnFriends;

        RecyclerViewHoder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            btnAdd = (Button) itemView.findViewById(R.id.btnAdd);
            btnFriends = (Button) itemView.findViewById(R.id.btnFriends);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnAdd.setVisibility(View.INVISIBLE);
                    btnFriends.setVisibility(View.VISIBLE);
                }
            });
            btnFriends.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnAdd.setVisibility(View.VISIBLE);
                    btnFriends.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}
