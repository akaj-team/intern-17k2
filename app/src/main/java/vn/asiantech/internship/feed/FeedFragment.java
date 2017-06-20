package vn.asiantech.internship.feed;

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
import vn.asiantech.internship.models.FeedItem;

/**
 * class fragment use show data to list
 * <p>
 * Created by Hai on 6/15/2017.
 */
public class FeedFragment extends Fragment {
    private SQLiteHelper mSQLiteHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        RecyclerView recyclerViewFeed = (RecyclerView) view.findViewById(R.id.recyclerViewFeed);
        mSQLiteHelper = new SQLiteHelper(getContext());
        addItem();
        FeedAdapter feedAdapter = new FeedAdapter(mSQLiteHelper.getAllFeedItems());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewFeed.setLayoutManager(layoutManager);
        recyclerViewFeed.setAdapter(feedAdapter);
        return view;
    }

    private void addItem() {
        String userName = getResources().getString(R.string.user_name);
        String userStatus = getResources().getString(R.string.user_status);
        String userStatus1 = getResources().getString(R.string.user_status_1);
        mSQLiteHelper.addFeed(new FeedItem(userName, new int[]{R.drawable.img_01, R.drawable.img_02}, userStatus));
        mSQLiteHelper.addFeed(new FeedItem(userName, new int[]{R.drawable.img_01}, userStatus1));
        mSQLiteHelper.addFeed(new FeedItem(userName, new int[]{R.drawable.img_04, R.drawable.img_05, R.drawable.img_06}, userStatus1));
    }
}
