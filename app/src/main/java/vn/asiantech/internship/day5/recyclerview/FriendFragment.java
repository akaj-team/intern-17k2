package vn.asiantech.internship.day5.recyclerview;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.asiantech.internship.R;

import java.util.ArrayList;

/**
 * Created by PC on 6/9/2017.
 */

public class FriendFragment extends Fragment {

    private Context mContext;
    private ArrayList<Person> mDataset;
    private FriendsAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFriends);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataset = new ArrayList<>();
        mDataset.add(new Person("Ronaldo", false));
        mDataset.add(new Person("Messi", false));
        mDataset.add(new Person("Kaka", false));
        mDataset.add(new Person("Ramos", false));
        mDataset.add(new Person("Pique", false));
        mDataset.add(new Person("Xavi", false));
        mDataset.add(new Person("Iniesta", false));
        mDataset.add(new Person("Dani Alves", false));
        mDataset.add(new Person("Rooney", false));
        mDataset.add(new Person("David Villa", false));
        mDataset.add(new Person("Torres", false));
        mAdapter = new FriendsAdapter(mDataset, mContext);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
