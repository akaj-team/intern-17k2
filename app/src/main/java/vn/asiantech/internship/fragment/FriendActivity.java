package vn.asiantech.internship.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapter.RecyclerViewAdapter;
import vn.asiantech.internship.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hai on 6/9/2017.
 */

public class FriendActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_friend, container, false);
        RecyclerView mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);
        RecyclerViewAdapter mRecyclerViewAdapter = new RecyclerViewAdapter(createDataListUser(), getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        return layout;
    }

    private List<User> createDataListUser() {
        ArrayList<User> userArrayList = new ArrayList<>();
        userArrayList.add(0, new User("Hai", true));
        userArrayList.add(1, new User("Tuan", false));
        userArrayList.add(2, new User("Hai", true));
        userArrayList.add(3, new User("Tuan", false));
        userArrayList.add(4, new User("Hai", true));
        userArrayList.add(5, new User("Tuan", false));
        userArrayList.add(0, new User("Hai", true));
        userArrayList.add(1, new User("Tuan", false));
        userArrayList.add(2, new User("Hai", true));
        userArrayList.add(3, new User("Tuan", false));
        userArrayList.add(4, new User("Hai", true));
        userArrayList.add(5, new User("Tuan", false));
        userArrayList.add(0, new User("Hai", true));
        userArrayList.add(1, new User("Tuan", false));
        userArrayList.add(2, new User("Hai", true));
        userArrayList.add(3, new User("Tuan", false));
        userArrayList.add(4, new User("Hai", true));
        userArrayList.add(5, new User("Tuan", false));
        return userArrayList;
    }
}
