package vn.asiantech.internship.ui.feeds;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.database.DatabaseHelper;
import vn.asiantech.internship.models.Feed;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedsFragment extends Fragment implements FeedsAdapter.OnFeedsListener {

    private DatabaseHelper mDatabaseHelper;
    private RecyclerView mRvFeeds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feeds, container, false);
        mRvFeeds = (RecyclerView) v.findViewById(R.id.rvFeeds);
        mRvFeeds.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabaseHelper = new DatabaseHelper(getContext(), true);
        List<Feed> feeds = getDataFromDatabase();
        FeedsAdapter adapter = new FeedsAdapter(feeds, this);
        mRvFeeds.setAdapter(adapter);
        return v;
    }

    private List<Feed> getDataFromDatabase() {
        return mDatabaseHelper.getAllFeeds();
    }

    @Override
    public void onScrollToPosition(int position) {
        mRvFeeds.scrollToPosition(position);
    }
}
