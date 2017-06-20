package vn.asiantech.internship.feed;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 15-06-2017.
 */
public class FeedFragment extends Fragment {
    private Context mContext;
    private Feed mFeed;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        List<Feed> feedList = new ArrayList<>();
        int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5};
        Log.d("tag", "onCreateView: " + sampleImages);
        String test = Arrays.toString(sampleImages);
        Log.d("tag", "onCreateView: " + test);
        String[] array = test.substring(1, test.length() - 1).split(", ");

//        Log.d("tag1", "onCreateView: " + array);
        feedList.add(new Feed(getString(R.string.feedlist_name), sampleImages, "9"));
        feedList.add(new Feed(getString(R.string.feedlist_name), sampleImages, "9"));
        feedList.add(new Feed(getString(R.string.feedlist_name), sampleImages, "9"));
//        Log.d("tag", "onCreateView: " + feedList);

        DataBaseFeed dataBaseFeed = new DataBaseFeed(getContext());
        dataBaseFeed.open();
        dataBaseFeed.insertItemFeed(mFeed);

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(manager);

        // Our classic custom Adapter.
        FeedAdapter adapter = new FeedAdapter(feedList);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
