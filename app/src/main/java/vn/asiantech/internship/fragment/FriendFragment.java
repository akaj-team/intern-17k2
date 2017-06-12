package vn.asiantech.internship.fragment;

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
import vn.asiantech.internship.adapter.FriendAdapter;
import vn.asiantech.internship.model.User;

/**
 * fragment store list friend
 */
public class FriendFragment extends Fragment {
    private RecyclerView mRecyclerViewFriend;
    private List<User> mUsers;
    private FriendAdapter mFriendsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        mRecyclerViewFriend = (RecyclerView) view.findViewById(R.id.recyclerViewFriend);
        mUsers = new ArrayList<>();
        mUsers.add(new User("Anh A", true));
        mUsers.add(new User("Anh B", true));
        mUsers.add(new User("Anh C", true));
        mUsers.add(new User("Anh D", false));
        mUsers.add(new User("Anh E", false));
        mUsers.add(new User("Anh F", false));
        mUsers.add(new User("Anh E", false));
        mUsers.add(new User("Anh A", false));
        mUsers.add(new User("Anh B", false));
        mUsers.add(new User("Anh C", false));
        mUsers.add(new User("Anh D", false));
        mUsers.add(new User("Anh E", false));
        mUsers.add(new User("Anh F", false));
        mUsers.add(new User("Anh E", false));
        mFriendsAdapter = new FriendAdapter(mUsers, getActivity());
        mRecyclerViewFriend.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewFriend.setAdapter(mFriendsAdapter);
        return view;
    }
}
