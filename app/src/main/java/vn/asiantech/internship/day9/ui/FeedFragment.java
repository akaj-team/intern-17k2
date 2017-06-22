package vn.asiantech.internship.day9.ui;

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
import vn.asiantech.internship.day9.adapter.FeedRecyclerViewAdapter;
import vn.asiantech.internship.day9.model.User;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 15/06/2017.
 */
public class FeedFragment extends Fragment {
    private List<User> mUsers = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feed, container, false);
        RecyclerView recyclerViewFeed = (RecyclerView) v.findViewById(R.id.recyclerViewFeed);
        if (getActivity() instanceof FeedActivity) {
            String[] users = getActivity().getResources().getStringArray(R.array.list_user);
            String[] descriptions = getActivity().getResources().getStringArray(R.array.list_description);
            for (int i = 0; i < users.length; i++) {
                mUsers.add(new User(users[i], descriptions[i], R.mipmap.ic_avatar));
            }
            FeedRecyclerViewAdapter feedAdapter = new FeedRecyclerViewAdapter(getActivity(), mUsers);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerViewFeed.setLayoutManager(linearLayoutManager);
            recyclerViewFeed.setAdapter(feedAdapter);
            feedAdapter.notifyDataSetChanged();
        }
        return v;
    }
}
