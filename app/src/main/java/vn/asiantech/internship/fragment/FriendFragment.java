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
import vn.asiantech.internship.models.User;

/**
 * fragment store list friend
 */
public class FriendFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        RecyclerView recyclerViewFriend = (RecyclerView) view.findViewById(R.id.recyclerViewFriend);
        List<User> users = new ArrayList<>();
        users.add(new User("Anh A", true));
        users.add(new User("Anh B", true));
        users.add(new User("Anh C", true));
        users.add(new User("Anh D", false));
        users.add(new User("Anh E", false));
        users.add(new User("Anh F", false));
        users.add(new User("Anh E", false));
        users.add(new User("Anh A", false));
        users.add(new User("Anh B", false));
        users.add(new User("Anh C", false));
        users.add(new User("Anh D", false));
        users.add(new User("Anh E", false));
        users.add(new User("Anh F", false));
        users.add(new User("Anh E", false));
        FriendAdapter friendAdapter = new FriendAdapter(users, getActivity());
        recyclerViewFriend.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewFriend.setAdapter(friendAdapter);
        return view;
    }
}
