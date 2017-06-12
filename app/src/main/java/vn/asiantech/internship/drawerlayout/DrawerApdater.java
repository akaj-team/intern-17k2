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
import vn.asiantech.internship.models.DrawerItem;

/**
 * Created by PC on 6/12/2017.
 */
public class DrawerApdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 0;
    private List<DrawerItem> mItems;
    private Context mContext;
    private OnItemClickListener mListener;

    public DrawerApdater(Context context, List<DrawerItem> items,
                         OnItemClickListener listener) {
        this.mItems = items;
        this.mContext = context;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_drawer_list, parent, false);
                return new VHHeader(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_list, parent, false);
                return new VHItem(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VHItem) {
            VHItem item = (VHItem) holder;
            DrawerItem drawerItem = mItems.get(position - 1);
            item.mTvName.setText(drawerItem.getName());
            if (drawerItem.isSelected()) {
                item.mImgItemIcon.setBackgroundResource(android.R.drawable.star_big_on);
            } else {
                item.mImgItemIcon.setBackgroundResource(android.R.drawable.star_big_off);
            }
            return;
        }
        if (holder instanceof VHHeader) {
            VHHeader header = (VHHeader) holder;

            // TODO: 6/12/2017 dummy data
            header.mTvName.setText("Cao Van Cuong");
            header.mTVEmail.setText("vancuong.itf@gmail.com");
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size() + 1;
    }

    /**
     * This class used to custom list Item for RecyclerView of DrawerLayout
     */
    public class VHItem extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvName;
        private ImageView mImgItemIcon;

        public VHItem(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvItemName);
            mImgItemIcon = (ImageView) itemView.findViewById(R.id.imgItemIcon);
            mTvName.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(getAdapterPosition() - 1);
            }
        }
    }

    /**
     * This class used to custom list Item for RecyclerView of DrawerLayout
     */
    public class VHHeader extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvName;
        private TextView mTVEmail;
        private ImageView mImgItemIcon;

        public VHHeader(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTVEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            mImgItemIcon = (ImageView) itemView.findViewById(R.id.imgAvatar);
            mImgItemIcon.setOnClickListener(this);
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

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
