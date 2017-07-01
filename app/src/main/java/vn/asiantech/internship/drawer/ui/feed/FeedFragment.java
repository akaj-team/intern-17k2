package vn.asiantech.internship.drawer.ui.feed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.drawer.models.FeedItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public FeedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_feed, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerViewFeed);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<FeedItem> feedItems = new ArrayList<>();
        initFeed(feedItems);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new FeedAdapter(feedItems));
    }

    private List<Integer> initImages() {
        List<Integer> integers = new ArrayList<>();
        integers.add(R.drawable.img_sunwheel);
        integers.add(R.drawable.img_danang);
        integers.add(R.drawable.img_binhdinh);
        integers.add(R.drawable.img_caurong);
        integers.add(R.drawable.img_biendanang);
        return integers;
    }

    private List<FeedItem> initFeed(List<FeedItem> feedItems) {
        List<Integer> images = initImages();
        feedItems.add(new FeedItem("At-DinhVo-01", images, "No comment 01"));
        feedItems.add(new FeedItem("At-DinhVo-02", images, "No comment 02"));
        feedItems.add(new FeedItem("At-DinhVo-03", images, "No comment 03"));
        feedItems.add(new FeedItem("At-DinhVo-04", images, "No comment 04"));
        feedItems.add(new FeedItem("At-DinhVo-05", images, "No comment 05"));
        return feedItems;
    }
}
