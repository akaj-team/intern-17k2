package vn.asiantech.internship.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Chat;

/**
 * Adapter for Chat Fragment.
 * Created by huypham on 21/06/2017.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<Chat> mMessageList;

    public ChatAdapter(List<Chat> messageList) {
        this.mMessageList = messageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position) {
        Chat chat = mMessageList.get(position);
        holder.setData(position);
        if (chat.isIndex()) {
            holder.mTvMessageReceive.setVisibility(View.VISIBLE);
            holder.mTvMessageSend.setVisibility(View.GONE);
        } else {
            holder.mTvMessageReceive.setVisibility(View.GONE);
            holder.mTvMessageSend.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    /**
     * View Holder for Recycler View All Chat.
     * Created by huypham on 21/06/2017.
     */
    final class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvMessageSend;
        private TextView mTvMessageReceive;

        private ViewHolder(View itemView) {
            super(itemView);
            mTvMessageSend = (TextView) itemView.findViewById(R.id.tvMessage2);
            mTvMessageReceive = (TextView) itemView.findViewById(R.id.tvMessage1);
        }

        private void setData(int position) {
            Chat chat = mMessageList.get(position);
            mTvMessageSend.setText(chat.getMessage());
            mTvMessageReceive.setText(chat.getMessage());
        }
    }
}
