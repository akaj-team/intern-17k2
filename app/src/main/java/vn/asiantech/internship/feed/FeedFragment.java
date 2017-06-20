package vn.asiantech.internship.feed;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import vn.asiantech.internship.database.FeedDatabase;
import vn.asiantech.internship.feed.adapters.FeedAdapter;
import vn.asiantech.internship.models.FeedItem;

/**
 * FeedFragment
 *
 * @author at-cuongcao
 * @version 1.0
 * @since 06/15/2017
 */
public class FeedFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        RecyclerView recyclerViewFeeds = (RecyclerView) view.findViewById(R.id.recyclerViewFeed);
        List<FeedItem> feeds = new ArrayList<>();
        FeedDatabase feedDatabase = null;
        try {
            feedDatabase = new FeedDatabase(getContext());
            feedDatabase.openDatabase();
            feeds = feedDatabase.getFeeds();
            FeedAdapter adapter = new FeedAdapter(feeds);
            recyclerViewFeeds.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewFeeds.setAdapter(adapter);
            feedDatabase.close();
        } catch (IOException e) {
            Log.i("TagError", e.getMessage());
        }
        return view;
    }
}
