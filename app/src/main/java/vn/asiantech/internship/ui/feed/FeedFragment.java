package vn.asiantech.internship.ui.feed;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.asiantech.internship.R;
import vn.asiantech.internship.databases.SQLiteHelper;

/**
 * class fragment use show data to list
 * <p>
 * Created by Hai on 6/15/2017.
 */
public class FeedFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        RecyclerView recyclerViewFeed = (RecyclerView) view.findViewById(R.id.recyclerViewFeed);
        SQLiteHelper sqLiteHelper = new SQLiteHelper(getActivity());
        FeedAdapter feedAdapter = new FeedAdapter(sqLiteHelper.getAllFeedItems());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewFeed.setLayoutManager(layoutManager);
        recyclerViewFeed.setAdapter(feedAdapter);
        return view;
    }
}
