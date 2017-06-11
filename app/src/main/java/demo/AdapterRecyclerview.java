package demo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Used to collect and display data to the View.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-9
 */

class AdapterRecyclerView extends RecyclerView.Adapter {
    private List<String> mNames = new ArrayList<>();
    public static final String TAG = "Log position item";

    AdapterRecyclerView(List<String> names) {
        this.mNames = names;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.mTvName.setText(mNames.get(position));
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    private static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvName;

        RecyclerViewHolder(final View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvYourName);
            mTvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, getAdapterPosition() + "");
                }
            });
        }
    }
}
