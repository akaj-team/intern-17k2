package vn.asiantech.internship.day5.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.asiantech.internship.R;

/**
 * Created by PC on 6/9/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<String> mDataset;
    private LayoutInflater mInflater;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mDataset) {
        this.mContext = mContext;
        this.mDataset = mDataset;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.day5_item_recyclerview_ex, parent, false);
        return new MyViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTvName.setText(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTvName;
        private Context mContext;

        public MyViewHolder(View itemView, Context context) {
            super(itemView);
            mContext = context;
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvName.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvName:
                    Toast.makeText(mContext, "Hello: " + mTvName.getText(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
