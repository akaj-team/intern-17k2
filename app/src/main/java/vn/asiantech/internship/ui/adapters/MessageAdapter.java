package vn.asiantech.internship.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Message;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/21/2017
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private static final int TYPE_INBOX = 1;
    private static final int TYPE_SENT = 0;

    private List<Message> mList;

    public MessageAdapter(List<Message> messages) {
        this.mList = messages;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_INBOX) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_inbox, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_sent, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.mTvMessage.setText(mList.get(position).getMessage());
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).isInbox()) {
            return TYPE_INBOX;
        }
        return TYPE_SENT;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * Custom item for MessageList
     */
    class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvMessage;

        private MessageViewHolder(View itemView) {
            super(itemView);
            mTvMessage = (TextView) itemView.findViewById(R.id.tvMessage);
        }
    }
}
