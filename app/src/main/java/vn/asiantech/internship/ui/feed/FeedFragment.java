package vn.asiantech.internship.ui.feed;

import android.graphics.Rect;
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
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.database.FeedItemDatabase;
import vn.asiantech.internship.models.Feed;
import vn.asiantech.internship.ui.adapters.FeedAdapter;

/**
 * Fragment for Feed
 * Created by huypham on 15/06/2017
 * Change on 20/6/2017 SQLite for load information
 */
public class FeedFragment extends Fragment {
    private RecyclerView mRecyclerViewFeed;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        mRecyclerViewFeed = (RecyclerView) view.findViewById(R.id.recyclerViewFeed);

        setFeedAdapter();
        return view;
    }

    private void setFeedAdapter() {
        try {
            FeedItemDatabase itemDatabase = new FeedItemDatabase(getContext());
            itemDatabase.openDatabase();
            List<Feed> mFeedList = itemDatabase.getFeedList();
            FeedAdapter feedAdapter = new FeedAdapter(mFeedList);
            mRecyclerViewFeed.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerViewFeed.setAdapter(feedAdapter);
            mRecyclerViewFeed.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    int position = parent.getChildAdapterPosition(view);
                    outRect.set(25, position == 0 ? 10 : 17, 25, 17);
                }
            });
            itemDatabase.closeDatabase();
        } catch (IOException e) {
            Log.i("Error", "Read Database: " + e.getMessage());
        }
    }
}
