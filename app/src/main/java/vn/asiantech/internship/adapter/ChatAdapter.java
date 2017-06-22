package vn.asiantech.internship.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Message;

/**
 * Created by ducle on 22/06/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Message> mMessages;

    public ChatAdapter(List<Message> messages) {
        this.mMessages = messages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_chat_1, parent, false);
            return new Chat1ViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_chat_2, parent, false);
            return new Chat2ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Chat1ViewHolder) {
            ((Chat1ViewHolder) holder).mTvChat1.setText(mMessages.get(position).getSubstance());
        } else {
            ((Chat2ViewHolder) holder).mTvChat2.setText(mMessages.get(position).getSubstance());
        }
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMessages.get(position).getType();
    }

    /**
     * Chat1ViewHolder to show type chat 1
     */
    class Chat1ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvChat1;

        public Chat1ViewHolder(View itemView) {
            super(itemView);
            mTvChat1 = (TextView) itemView.findViewById(R.id.tvChat1);
        }
    }

    /**
     * Chat1ViewHolder to show type chat 1
     */
    class Chat2ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvChat2;

        public Chat2ViewHolder(View itemView) {
            super(itemView);
            mTvChat2 = (TextView) itemView.findViewById(R.id.tvChat2);
        }
    }
}
