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

/*
    Display list user to RecyclerView
 */
public class FriendFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_friend, container, false);
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), createDataListUser());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        return layout;
    }

    private List<User> createDataListUser() {
        List<User> users = new ArrayList<>();
        users.add(0, new User("Hai", true));
        users.add(1, new User("Tuan", false));
        users.add(2, new User("Hai", true));
        users.add(3, new User("Tuan", false));
        users.add(4, new User("Hai", true));
        users.add(5, new User("Tuan", false));
        users.add(0, new User("Hai", true));
        users.add(1, new User("Tuan", false));
        users.add(2, new User("Hai", true));
        users.add(3, new User("Tuan", false));
        users.add(4, new User("Hai", true));
        users.add(5, new User("Tuan", false));
        users.add(0, new User("Hai", true));
        users.add(1, new User("Tuan", false));
        users.add(2, new User("Hai", true));
        users.add(3, new User("Tuan", false));
        users.add(4, new User("Hai", true));
        users.add(5, new User("Tuan", false));
        return users;
    }
}
