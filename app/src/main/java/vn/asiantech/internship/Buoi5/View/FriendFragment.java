package vn.asiantech.internship.Buoi5.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import vn.asiantech.internship.Buoi5.Model.FriendsMyAdapter;
import vn.asiantech.internship.Buoi5.Model.User;
import vn.asiantech.internship.R;

public class FriendFragment extends Fragment {


    private ArrayList<User> mUserArrayList = new ArrayList<>();
    private FriendsMyAdapter mMyAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.friends_fragment, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        addUser();
        mMyAdapter = new FriendsMyAdapter(mUserArrayList, getActivity());
        mRecyclerView.setAdapter(mMyAdapter);
        return v;
    }

    private void addUser() {
        for (int i = 0; i < 1000; i++) {
            mUserArrayList.add(new User(i, "User " + i, false));
        }
    }
}
