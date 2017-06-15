package vn.asiantech.internship.ui.feeds;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.asiantech.internship.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedsFragment extends Fragment {

    private RecyclerView mRvFeeds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feeds, container, false);
        mRvFeeds = (RecyclerView) v.findViewById(R.id.rvFeeds);
        mRvFeeds.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

}
