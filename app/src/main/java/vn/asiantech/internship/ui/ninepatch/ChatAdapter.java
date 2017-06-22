package vn.asiantech.internship.ui.ninepatch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Message;

/**
 * Created by Thanh Thien on 6/22/17.
 * Adapter Of Chat
 */
class ChatAdapter extends RecyclerView.Adapter {

    private List<Message> mMessages = new ArrayList<>();

    ChatAdapter(List<Message> messages) {
        mMessages = messages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_line_chat, parent, false);
        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatViewHolder chatViewHolder = (ChatViewHolder) holder;
        chatViewHolder.mTvShowMessage.setText(mMessages.get(position).getContent());
        chatViewHolder.mImgAvatar.setImageResource(mMessages.get(position).getState());
        if (mMessages.get(position).getState() == R.drawable.ic_three) {
            chatViewHolder.mTvShowMessage.setBackgroundResource(R.drawable.bg_chat_badboy);
        } else if (mMessages.get(position).getState() == R.drawable.ic_two) {
            chatViewHolder.mTvShowMessage.setBackgroundResource(R.drawable.bg_chat_vegeta);
        } else {
            chatViewHolder.mTvShowMessage.setBackgroundResource(R.drawable.bg_chat_power);
        }
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    /**
     * Used to register for message.
     */
    private class ChatViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvShowMessage;
        private ImageView mImgAvatar;

        ChatViewHolder(View itemView) {
            super(itemView);
            mTvShowMessage = (TextView) itemView.findViewById(R.id.tvShowMessage);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
        }
    }
}
