package vn.asiantech.internship;


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

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<FriendObject> mFriends;
    private FriendAdapter mFriendAdapter;

    public FriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_friend, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recycleViewFriend);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
    }

    private void initUI() {
        mFriends = new ArrayList<>();
        initFriendDatas(mFriends);
        mFriendAdapter = new FriendAdapter(mFriends);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mFriendAdapter);
    }

    private void initFriendDatas(List<FriendObject> arrayList) {
        arrayList.add(new FriendObject("Abc xyz 001"));
        arrayList.add(new FriendObject("Abc xyz 001"));
        arrayList.add(new FriendObject("Abc xyz 001"));
        arrayList.add(new FriendObject("Abc xyz 001"));
        arrayList.add(new FriendObject("Abc xyz 001"));
        arrayList.add(new FriendObject("Abc xyz 001"));
        arrayList.add(new FriendObject("Abc xyz 001"));
        arrayList.add(new FriendObject("Abc xyz 001"));
        arrayList.add(new FriendObject("Abc xyz 001"));
        arrayList.add(new FriendObject("Abc xyz 001"));
    }
}
