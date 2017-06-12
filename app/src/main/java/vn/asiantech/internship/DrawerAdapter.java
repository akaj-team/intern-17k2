package vn.asiantech.internship;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by root on 6/12/17.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.MyViewHolder> {

    private String[] mItems;

    public DrawerAdapter(String[] items) {
        this.mItems = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_drawer, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.mTextView.setText(mItems[i]);
    }

    @Override
    public int getItemCount() {
        return mItems.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tvName);

        }
    }
}
