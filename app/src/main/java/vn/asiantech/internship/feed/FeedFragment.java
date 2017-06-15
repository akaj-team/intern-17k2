package vn.asiantech.internship.feed;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.FeedItem;

/**
 * Created by Hai on 6/15/2017.
 */

public class FeedFragment extends Fragment {
    private RecyclerView mRecyclerViewFeed;
    private FeedAdapter mFeedAdapter;
    private List<FeedItem> mFeedItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        mRecyclerViewFeed = (RecyclerView) view.findViewById(R.id.recyclerViewFeed);
        return view;
    }

    public List<FeedItem> createData() {
        mFeedItems = new ArrayList<>();
        mFeedItems.add(0, new FeedItem("HaiNgo", new int[]{R.drawable.img_01, R.drawable.img_02, R.drawable.img_03}, "lalaao"));
    }
}
