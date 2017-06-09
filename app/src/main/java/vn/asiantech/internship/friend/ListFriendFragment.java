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

import vn.asiantech.internship.R;

/**
 * Created by PC on 6/9/2017.
 */

public class ListFriendFragment extends Fragment {

    private ArrayList<Friend> mDataset;
    private ListFriendAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_friend, container);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFriends);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDataset = new ArrayList<>();
        mDataset.add(new Friend("Ronaldo", false));
        mDataset.add(new Friend("Messi", false));
        mDataset.add(new Friend("Kaka", false));
        mDataset.add(new Friend("Ramos", false));
        mDataset.add(new Friend("Pique", false));
        mDataset.add(new Friend("Xavi", false));
        mDataset.add(new Friend("Iniesta", false));
        mDataset.add(new Friend("Dani Alves", false));
        mDataset.add(new Friend("Rooney", false));
        mDataset.add(new Friend("David Villa", false));
        mDataset.add(new Friend("Torres", false));
        mAdapter = new ListFriendAdapter(mDataset, getContext());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
