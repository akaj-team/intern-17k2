package vn.asiantech.internship;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

public class FriendsFragment extends Fragment {
    RecyclerView recyclerView;
    private ArrayList<String> mListUsers = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends, container, false);
        String[] list = getResources().getStringArray(R.array.list_users);
        Collections.addAll(mListUsers, list);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        AdapterRecycler adapter = new AdapterRecycler(mListUsers);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        return v;
    }
}
