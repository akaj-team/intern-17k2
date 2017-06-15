package vn.asiantech.internship.feed;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 15-06-2017.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyHolder> {
    List<Feed> feedList = new ArrayList<>();

    FeedAdapter(List<Feed> feedList) {
        feedList = feedList;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private ViewPager viewPager;
        private TextView tvTitle;

        public MyHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            viewPager = (ViewPager) itemView.findViewById(R.id.viewPager);

        }
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Feed feed = feedList.get(position);
        holder.tvTitle.setText(feed.getTitle());
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

}
