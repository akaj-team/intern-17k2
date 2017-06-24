package vn.asiantech.internship.drawer.ui.feed;

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
import vn.asiantech.internship.drawer.models.FeedItem;
import vn.asiantech.internship.drawer.ui.database.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private DatabaseHelper mDatabase;

    public FeedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_feed, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerViewFeed);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            mDatabase = new DatabaseHelper(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<FeedItem> feedItems = new ArrayList<>();
        initFeed(feedItems);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new FeedAdapter(getContext(), feedItems));
    }

    private List<Integer> initImages() {
        List<Integer> integers = new ArrayList<>();
        integers.add(R.drawable.img_sunwheel);
        integers.add(R.drawable.img_danang);
        integers.add(R.drawable.img_binhdinh);
        integers.add(R.drawable.img_caurong);
        integers.add(R.drawable.img_biendanang);
        return integers;
    }

    private void initFeed(List<FeedItem> items) {
        ArrayList<String[]> listImage = mDatabase.getAllData();
        int index = 0;
        for (String[] urls :
                listImage) {
            ++index;
            items.add(new FeedItem("Hi i'm DinhVo" + index, urls, "No comment" + index));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }
}
