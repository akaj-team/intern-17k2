package vn.asiantech.internship.ui.feeds;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.database.FeedDatabaseHelper;
import vn.asiantech.internship.models.Feed;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedsFragment extends Fragment {

    private List<Feed> mFeeds;
    private FeedDatabaseHelper mFeedDatabaseHelper;
    private RecyclerView mRvFeeds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feeds, container, false);
        mRvFeeds = (RecyclerView) v.findViewById(R.id.rvFeeds);
        mRvFeeds.setLayoutManager(new LinearLayoutManager(getContext()));
        try {
            mFeedDatabaseHelper = new FeedDatabaseHelper(getContext());
        } catch (IOException e) {
            Log.d("FeedsFragment", "onCreateView: " + e.toString());
        }
        mFeeds = new ArrayList<>();
        mFeeds = getDataFromDatabase();
        FeedsAdapter adapter = new FeedsAdapter(mFeeds);
        mRvFeeds.setAdapter(adapter);
        return v;
    }

    private List<Feed> getDataFromDatabase() {
        return mFeedDatabaseHelper.getAllFeeds();
    }
}
