package vn.asiantech.internship.ui.chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * adapter of recyclerview Chat
 * <p>
 * Created by Hai on 6/22/2017.
 */
class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ItemMessageViewHolder> {
    private List<String> mMessages = new ArrayList<>();

    ChatAdapter(List<String> messages) {
        mMessages = messages;
    }

    @Override
    public ItemMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new ItemMessageViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ItemMessageViewHolder holder, int position) {
        holder.mTvMessage.setText(mMessages.get(position));
    }


    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    /**
     * Item of recyclerview
     */
    class ItemMessageViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvMessage;

        ItemMessageViewHolder(View itemView) {
            super(itemView);
            mTvMessage = (TextView) itemView.findViewById(R.id.tvMessage);
        }
    }
}
