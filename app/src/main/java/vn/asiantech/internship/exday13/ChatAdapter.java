package vn.asiantech.internship.exday13;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 21-06-2017.
 */
class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.Myholder> {
    private static final int TYPE_CHAT = 1;
    private static final int TYPE_REP = 0;

    private List<ItemChat> mItemChat;

    ChatAdapter(List<ItemChat> mItemChat) {
        this.mItemChat = mItemChat;
    }

    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_CHAT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rep, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        }
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {
        holder.mTvChat.setText(mItemChat.get(position).getText());
    }

    @Override
    public int getItemViewType(int position) {
        if (mItemChat.get(position).isCheck()) {
            return TYPE_CHAT;
        }
        return TYPE_REP;
    }

    @Override
    public int getItemCount() {
        return mItemChat.size();
    }

    /**
     * Created by datbu on 21-06-2017.
     */
    class Myholder extends RecyclerView.ViewHolder {
        private TextView mTvChat;

        Myholder(View itemView) {
            super(itemView);
            mTvChat = (TextView) itemView.findViewById(R.id.tvChat);
        }
    }
}
