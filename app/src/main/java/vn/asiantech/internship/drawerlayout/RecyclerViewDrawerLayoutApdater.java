package vn.asiantech.internship.drawerlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by PC on 6/12/2017.
 */

public class RecyclerViewDrawerLayoutApdater extends RecyclerView.Adapter<RecyclerViewDrawerLayoutApdater.VHItem> {
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 0;
    private List<Object> mItems;
    private Context mContext;
    private LayoutInflater mInflater;

    public RecyclerViewDrawerLayoutApdater(List<Object> mItems, Context mContext) {
        this.mItems = mItems;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerViewDrawerLayoutApdater.VHItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_ITEM) {
            view = mInflater.inflate(R.layout.item_drawer_list, parent);
        } else {
            view = mInflater.inflate(R.layout.header_drawer_list, parent);
        }
        return new VHItem(view, viewType);

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_HEADER;
        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(VHItem holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            User user = (User) mItems.get(position);
            holder.mTvName.setText(user.getName());

        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /**
     * This class used to custom list Item for RecyclerView of DrawerLayout
     */
    public class VHItem extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvName;
        private TextView mTVEmail;
        private ImageView mImgItemIcon;

        public VHItem(View itemView, int type) {
            super(itemView);
            if (type == TYPE_ITEM) {
                mTvName = (TextView) itemView.findViewById(R.id.tvItemName);
                mImgItemIcon = (ImageView) itemView.findViewById(R.id.imgItemIcon);
            } else {
                mTvName = (TextView) itemView.findViewById(R.id.tvName);
                mTVEmail = (TextView) itemView.findViewById(R.id.tvEmail);
                mImgItemIcon = (ImageView) itemView.findViewById(R.id.imgAvatar);
                mImgItemIcon.setOnClickListener(this);
            }

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgAvatar:
                    Toast.makeText(mContext, "Bấm vào avatar", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

}
