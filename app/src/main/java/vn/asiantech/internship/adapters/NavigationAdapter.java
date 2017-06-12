package vn.asiantech.internship.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Created by Administrator on 6/12/2017.
 */
public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.MyViewHolder>{
    private static final int TYPE_HEADER=0;
    private static final int TYPE_ITEM=0;
    private String[] mTitle;
    private String mName;
    private String mEmail;

    public NavigationAdapter(String[] title,String name,String email) {
        mTitle=title;
        mName=name;
        mEmail=email;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==TYPE_ITEM){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_navigation,parent,false);
            return new MyViewHolder(view,viewType);
        }else{
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.header_navigation,parent,false);
            return new MyViewHolder(view,viewType);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (holder.mHolderId==1){
            holder.mTvName.setText(mName);
            holder.mTvEmail.setText(mEmail);
        }else{
            holder.mTvItem.setText(mTitle[position-1]);
        }
    }

    @Override
    public int getItemCount() {
        return mTitle==null?1:mTitle.length+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return TYPE_HEADER;
        }else{
            return TYPE_ITEM;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private int mHolderId;
        private TextView mTvItem;
        private TextView mTvName;
        private TextView mTvEmail;
        public MyViewHolder(View itemView, int viewType) {
            super(itemView);
            if(viewType==TYPE_HEADER){
                mTvName=(TextView) itemView.findViewById(R.id.tvName);
                mTvEmail=(TextView) itemView.findViewById(R.id.tvEmail);
                mHolderId=1;
            }else{
                mTvItem=(TextView) itemView.findViewById(R.id.tvItem);
                mHolderId=0;
            }
        }
    }
}
