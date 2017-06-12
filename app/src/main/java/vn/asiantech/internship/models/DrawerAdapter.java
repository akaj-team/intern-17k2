package vn.asiantech.internship.models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Created by root on 6/12/17.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.MyViewHolder> {

    private Context mContext;
    private int mResource;
    private String[] mItems;

    public DrawerAdapter(Context context, int resource, String[] items) {
        this.mContext = context;
        this.mResource = resource;
        this.mItems = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(mResource, viewGroup, false);
        MyViewHolder mMyViewHolder = new MyViewHolder(v);
        mMyViewHolder.setIsRecyclable(false);
        return mMyViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.mTextView.setText(mItems[i]);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tvName);

        }
    }
}
