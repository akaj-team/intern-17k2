package vn.asiantech.internship.day5;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.asiantech.internship.R;

public class FriendsFragment extends Fragment {
    private RecyclerView mRecyclerViewFriends;
    private ArrayList<UserDay5> mUsers;
    private FriendsAdapter mFriendsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        mRecyclerViewFriends = (RecyclerView) view.findViewById(R.id.recyclerViewFriends);
        mUsers = new ArrayList<>();
        mUsers.add(new UserDay5("Anh A", true));
        mUsers.add(new UserDay5("Anh B", true));
        mUsers.add(new UserDay5("Anh C", true));
        mUsers.add(new UserDay5("Anh D", false));
        mUsers.add(new UserDay5("Anh E", false));
        mUsers.add(new UserDay5("Anh F", false));
        mUsers.add(new UserDay5("Anh E", false));
        mUsers.add(new UserDay5("Anh A", false));
        mUsers.add(new UserDay5("Anh B", false));
        mUsers.add(new UserDay5("Anh C", false));
        mUsers.add(new UserDay5("Anh D", false));
        mUsers.add(new UserDay5("Anh E", false));
        mUsers.add(new UserDay5("Anh F", false));
        mUsers.add(new UserDay5("Anh E", false));
        mFriendsAdapter = new FriendsAdapter(mUsers, getActivity());
        mRecyclerViewFriends.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewFriends.setAdapter(mFriendsAdapter);
        return view;
    }
}
