package vn.asiantech.internship.adapter.PatchImageAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Adapter for Chat Fragment.
 * Created by huypham on 21/06/2017.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<String> mMessageList;

    public ChatAdapter(List<String> messageList) {
        mMessageList = messageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    /**
     * View Holder for Recycler View All Chat.
     * Created by huypham on 21/06/2017.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvMessage1;
        private TextView mTvMessage2;

        ViewHolder(View itemView) {
            super(itemView);
            mTvMessage1 = (TextView) itemView.findViewById(R.id.tvMessage1);
            mTvMessage2 = (TextView) itemView.findViewById(R.id.tvMessage2);
        }

        private void setData(int position) {
            String s = mMessageList.get(position);
            mTvMessage1.setText(s);
            mTvMessage2.setText(s);
        }
    }
}
