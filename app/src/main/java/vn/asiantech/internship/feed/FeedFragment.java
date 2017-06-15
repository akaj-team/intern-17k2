package vn.asiantech.internship.feed;

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
import vn.asiantech.internship.feed.adapters.FeedAdapter;
import vn.asiantech.internship.feed.adapters.FeedItem;

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
        // TODO: 6/15/2017 dummy data
        feeds.add(new FeedItem("Daxua unty gank tem 15p gg", new int[]{R.drawable.bg_yasuo_1, R.drawable.bg_yasuo_2, R.drawable.bg_yasuo_3, R.drawable.bg_yasuo_square_0}, getResources().getString(R.string.yasuo)));
        feeds.add(new FeedItem("Cao Van Cuong", new int[]{R.drawable.bg_anivia_0, R.drawable.bg_aatrox_1, R.drawable.bg_aatrox_2, R.drawable.bg_anivia_4, R.drawable.bg_anivia_1,}, "Aatrox"));
        feeds.add(new FeedItem("Cao Van Cuong", new int[]{R.drawable.bg_anivia_0, R.drawable.bg_anivia_1, R.drawable.bg_anivia_4}, "Axtrox"));
        feeds.add(new FeedItem("Cao Van Cuong", new int[]{R.drawable.bg_anivia_0, R.drawable.bg_anivia_1, R.drawable.bg_anivia_4}, "sdfdsf"));
        feeds.add(new FeedItem("Cao Van Cuong", new int[]{R.drawable.bg_anivia_0, R.drawable.bg_anivia_1, R.drawable.bg_anivia_4}, "Axsdftrox"));
        feeds.add(new FeedItem("Cao Van Cuong", new int[]{R.drawable.bg_anivia_0, R.drawable.bg_anivia_1, R.drawable.bg_anivia_4}, "Axtrox"));
        feeds.add(new FeedItem("Cao Van Cuong", new int[]{R.drawable.bg_anivia_0, R.drawable.bg_anivia_1, R.drawable.bg_anivia_4}, "Axtrox"));
        feeds.add(new FeedItem("Cao Van Cuong", new int[]{R.drawable.bg_anivia_0, R.drawable.bg_anivia_1, R.drawable.bg_anivia_4}, "Axsdfdsftrox"));
        feeds.add(new FeedItem("Cao Van Cuong", new int[]{R.drawable.bg_anivia_0, R.drawable.bg_anivia_1, R.drawable.bg_anivia_4}, "Axtrox"));
        feeds.add(new FeedItem("Cao Van Cuong", new int[]{R.drawable.bg_anivia_0, R.drawable.bg_anivia_1, R.drawable.bg_anivia_4}, "Axtssdfsdfrox"));
        feeds.add(new FeedItem("Cao Van Cuong", new int[]{R.drawable.bg_anivia_0, R.drawable.bg_anivia_1, R.drawable.bg_anivia_4}, "Axtrox"));
        FeedAdapter adapter = new FeedAdapter(feeds);
        recyclerViewFeeds.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFeeds.setAdapter(adapter);
        return view;
    }
}
