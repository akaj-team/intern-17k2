package vn.asiantech.internship.feed;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by sony on 15/06/2017.
 */

public class FeedFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.feedRecyclerView);
        List<Feed> feeds = new ArrayList<>();
        int[] images= {R.mipmap.ic_one,R.mipmap.ic_two,R.mipmap.ic_three,R.mipmap.iv_four,R.mipmap.ic_five, R.mipmap.ic_six,
                R.mipmap.ic_seven,R.mipmap.ic_eight,R.mipmap.ic_nine,R.mipmap.ic_ten};
        feeds.add(new Feed(R.mipmap.ic_person1,"AAAAA","aaaaaaaaaaaa",images));
        feeds.add(new Feed(R.mipmap.ic_person1,"BBBBB","bbbbbbbbbbbb",images));
        feeds.add(new Feed(R.mipmap.ic_person1,"CCCCC","cccccccccccc",images));
        feeds.add(new Feed(R.mipmap.ic_person1,"DDDDD","dddddddddddd",images));
        FeedAdapter adapter = new FeedAdapter(getActivity(), feeds);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
