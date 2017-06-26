package vn.asiantech.internship.ui.chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Message;

/**
 * adapter of recyclerview Chat
 * <p>
 * Created by Hai on 6/22/2017.
 */
class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Message> mMessages = new ArrayList<>();

    ChatAdapter(List<Message> messages) {
        mMessages = messages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
            return new MessageViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_two, parent, false);
            return new MessageTwoViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof MessageViewHolder) {
            ((MessageViewHolder)viewHolder).mTvMessage.setText(mMessages.get(position).getMessage());
            return;
        }
        ((MessageTwoViewHolder)viewHolder).mTvMessageTwo.setText(mMessages.get(position).getMessage());
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
     * Item message 1 of recyclerview
     */
    private class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvMessage;

        MessageViewHolder(View itemView) {
            super(itemView);
            mTvMessage = (TextView) itemView.findViewById(R.id.tvMessage);
        }
    }

    /**
     * Item message 2 of recyclerview
     */
    private class MessageTwoViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvMessageTwo;

        MessageTwoViewHolder(View itemView) {
            super(itemView);
            mTvMessageTwo = (TextView) itemView.findViewById(R.id.tvMessageTwo);
        }
    }
}
