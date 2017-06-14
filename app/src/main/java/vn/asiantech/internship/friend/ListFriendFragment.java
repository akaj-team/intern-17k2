package vn.asiantech.internship.friend;

import android.content.Context;
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
 * Created by PC on 6/9/2017.
 */
public class ListFriendFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        List<Friend> friends;
        ListFriendAdapter adapter;
        RecyclerView recyclerView;
        View view = inflater.inflate(R.layout.fragment_list_friend, container);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewListFriend);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        friends = new ArrayList<>();
        friends.add(new Friend("Ronaldo", false));
        friends.add(new Friend("Messi", false));
        friends.add(new Friend("Kaka", false));
        friends.add(new Friend("Ramos", false));
        friends.add(new Friend("Pique", false));
        friends.add(new Friend("Xavi", false));
        friends.add(new Friend("Iniesta", false));
        friends.add(new Friend("Dani Alves", false));
        friends.add(new Friend("Rooney", false));
        friends.add(new Friend("David Villa", false));
        friends.add(new Friend("Torres", false));
        adapter = new ListFriendAdapter(friends, getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }

}
