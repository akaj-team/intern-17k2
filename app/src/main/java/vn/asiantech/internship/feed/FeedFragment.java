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

/**
 * Created by datbu on 15-06-2017.
 */

public class FeedFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        List<Feed> feedList = new ArrayList<>();
        int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5};
        feedList.add(new Feed("dasdas", sampleImages, "1"));
        feedList.add(new Feed("dasdas", sampleImages, "2"));
        feedList.add(new Feed("dasdas", sampleImages, "3"));
        feedList.add(new Feed("dasdas", sampleImages, "4"));
        feedList.add(new Feed("dasdas", sampleImages, "5"));
        feedList.add(new Feed("dasdas", sampleImages, "6"));
        feedList.add(new Feed("dasdas", sampleImages, "7"));
        feedList.add(new Feed("dasdas", sampleImages, "8"));
        feedList.add(new Feed("dasdas", sampleImages, "9"));

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(manager);
        // Our classic custom Adapter.
        FeedAdapter adapter = new FeedAdapter(feedList);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
