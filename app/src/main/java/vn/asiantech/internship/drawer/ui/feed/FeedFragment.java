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
        mRecyclerView.setAdapter(new FeedAdapter(getContext(), feedItems));
    }

    private void initFeed(List<FeedItem> list){
        list.add(new FeedItem("DinhVo", "No comment"));
        list.add(new FeedItem("DinhVo", "No comment"));
        list.add(new FeedItem("DinhVo", "No comment"));
        list.add(new FeedItem("DinhVo", "No comment"));
        list.add(new FeedItem("DinhVo", "No comment"));
        list.add(new FeedItem("DinhVo", "No comment"));
        list.add(new FeedItem("DinhVo", "No comment"));
        list.add(new FeedItem("DinhVo", "No comment"));
        list.add(new FeedItem("DinhVo", "No comment"));
    }
}
