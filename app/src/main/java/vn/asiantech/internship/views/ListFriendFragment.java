package vn.asiantech.internship.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.models.Friend;
import vn.asiantech.internship.models.ListFriendAdapter;
import vn.asiantech.internship.R;

public class ListFriendFragment extends Fragment {


    private List<Friend> mFriends = new ArrayList<>();
    private ListFriendAdapter mMyAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_friend, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        addFriend();
        mMyAdapter = new ListFriendAdapter(mFriends, getContext());
        mRecyclerView.setAdapter(mMyAdapter);
        return v;
    }

    private void addFriend() {
        for (int i = 0; i < 100; i++) {
            mFriends.add(new Friend(i, "Friend " + i, false));
        }
    }
}
