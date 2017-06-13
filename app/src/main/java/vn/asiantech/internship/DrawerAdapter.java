package vn.asiantech.internship;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Thanh Thien on 6/12/17.
 *
 */
public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_HEAD = 0;

    private Context mContext;
    private String[] mItems;
    private int mPositionSelected = -1; // mPositionSelected = -1 if nothing select

    public DrawerAdapter(Context context, String[] items) {
        this.mItems = items;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        switch (getItemViewType(i)) {
            case TYPE_HEAD:
                return new MyViewHolderHeader(LayoutInflater.from(mContext).inflate(R.layout.drawer_head, viewGroup, false));
            default:
                return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_drawer, viewGroup, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else {
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
            myViewHolder.mTextView.setText(mItems[i]);
            if (i == mPositionSelected) {
                viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorItemChoise));
            } else {
                viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorItem));
            }
        } else if (viewHolder instanceof MyViewHolderHeader) {
            MyViewHolderHeader myViewHolder = (MyViewHolderHeader) viewHolder;
            myViewHolder.tvAuthorName.setText(mContext.getString(R.string.app_author));
        }
    }

    @Override
    public int getItemCount() {
        return mItems.length - 1;
    }

    /**
     *
     * @param positionSelected is a position of item selected
     */
    public void setPositionSelected(int positionSelected) {
        this.mPositionSelected = positionSelected;
        notifyDataSetChanged();
    }

    /**
     * ItemView for content list
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tvName);
        }
    }

    /**
     *  View for header
     */
    public class MyViewHolderHeader extends RecyclerView.ViewHolder {

        private TextView tvAuthorName;

        MyViewHolderHeader(View itemView) {
            super(itemView);
            tvAuthorName = (TextView) itemView.findViewById(R.id.tvAuthorName);
        }
    }
}
