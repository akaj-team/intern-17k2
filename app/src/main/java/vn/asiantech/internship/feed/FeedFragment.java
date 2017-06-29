package vn.asiantech.internship.feed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 15-06-2017.
 */
public class FeedFragment extends Fragment {
    private List<Feed> mFeedList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        DataBaseHandler dataBaseHandler;
        try {
            dataBaseHandler = new DataBaseHandler(getContext());
            dataBaseHandler.opendatabase();
            mFeedList = dataBaseHandler.getFeeds();
            dataBaseHandler.close();
        } catch (IOException e) {
            throw new Error("Error copying database");
        }

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(manager);

        // Our classic custom Adapter.
        FeedAdapter adapter = new FeedAdapter(mFeedList);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
