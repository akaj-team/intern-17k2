package vn.asiantech.internship.Day5;

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

/**
 * Fragment contain RecyclerView
 *
 * @author at-HoaVo
 * @version 1.0
 * @since 2017-6-12
 */
public class FriendFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<String> mUsers = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friend, container, false);
        String[] users = getResources().getStringArray(R.array.list_user);
        for (int i = 0; i < users.length; i++)
            mUsers.add(users[i]);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mUsers);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
        return v;
    }
}
