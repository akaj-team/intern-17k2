package vn.asiantech.internship.ninepath;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Used to collect and display data on recyclerView.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-21
 */
class ChatAdapter extends RecyclerView.Adapter {
    private List<Message> mMessages = new ArrayList<>();

    ChatAdapter(List<Message> feeds) {
        this.mMessages = feeds;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_sms_list, parent, false);
        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FeedViewHolder myViewHolder = (FeedViewHolder) holder;
        myViewHolder.mTvSend.setText(mMessages.get(position).getSms());
        myViewHolder.mTvRecieve.setText(mMessages.get(position).getSms());
        if (mMessages.get(position).isState()) {
            myViewHolder.mTvSend.setVisibility(View.VISIBLE);
            myViewHolder.mTvRecieve.setVisibility(View.GONE);
        } else {
            myViewHolder.mTvRecieve.setVisibility(View.VISIBLE);
            myViewHolder.mTvSend.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    /**
     * Used to register for message.
     */
    private class FeedViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvSend;
        private final TextView mTvRecieve;

        FeedViewHolder(View itemView) {
            super(itemView);
            mTvSend = (TextView) itemView.findViewById(R.id.tvSend);
            mTvRecieve = (TextView) itemView.findViewById(R.id.tvRecieve);
        }
    }
}
