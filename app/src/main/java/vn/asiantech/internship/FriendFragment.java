package vn.asiantech.internship;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FriendFragment extends Fragment{

    public FriendFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);

        List<Friend> friendList = new ArrayList<>();
        friendList.add(new Friend("Alex", true));
        friendList.add(new Friend("Sun", true));
        friendList.add(new Friend("Sat", false));
        friendList.add(new Friend("Pink", false));
        friendList.add(new Friend("Long", false));
        friendList.add(new Friend("Linh", false));

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        RecyclerView RecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.setLayoutManager(manager);
        // Our classic custom adapter.

        RecyclerAdapter Adapter = new RecyclerAdapter(friendList);
        RecyclerView.setAdapter(Adapter);
        Adapter.notifyDataSetChanged();

        return view;
    }
}
