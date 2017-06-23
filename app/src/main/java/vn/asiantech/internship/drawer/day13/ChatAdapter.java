package vn.asiantech.internship.drawer.day13;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import vn.asiantech.internship.R;

/**
 * Created by at-dinhvo on 21/06/2017.
 */
class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<String> mMessages;

    ChatAdapter(List<String> messages) {
        mMessages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // random to choose Send or Receiver
        switch (new Random().nextInt(2)) {
            case 0:
                holder.mTvMessageReceive.setText(mMessages.get(position));
                holder.mTvMessageReceive.setVisibility(View.VISIBLE);
                holder.mTvMessageSend.setVisibility(View.GONE);
                break;
            default:
                holder.mTvMessageSend.setText(mMessages.get(position));
                holder.mTvMessageSend.setVisibility(View.VISIBLE);
                holder.mTvMessageReceive.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    /**
     * ViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvMessageReceive;
        private TextView mTvMessageSend;

        ViewHolder(View itemView) {
            super(itemView);
            mTvMessageReceive = (TextView) itemView.findViewById(R.id.tvMessageReceive);
            mTvMessageSend = (TextView) itemView.findViewById(R.id.tvMessageSend);
        }
    }
}
