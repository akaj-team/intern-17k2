package vn.asiantech.internship.ui.feeds;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Feed;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedsFragment extends Fragment {

    private RecyclerView mRvFeeds;
    private List<Feed> mFeeds = new ArrayList<>();
    private FeedsAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feeds, container, false);
        mRvFeeds = (RecyclerView) v.findViewById(R.id.rvFeeds);
        mRvFeeds.setLayoutManager(new LinearLayoutManager(getContext()));
        addData();
        mAdapter = new FeedsAdapter(mFeeds, getContext());
        mRvFeeds.setAdapter(mAdapter);
        return v;
    }

    private void addData() {
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                mFeeds.add(new Feed(R.drawable.ic_one, getString(R.string.author) + " " + i, R.drawable.bg_stevi));
            } else {
                mFeeds.add(new Feed(R.drawable.ic_two, getString(R.string.author) + " " + i, R.drawable.bg_steve));
            }
        }
    }

}
