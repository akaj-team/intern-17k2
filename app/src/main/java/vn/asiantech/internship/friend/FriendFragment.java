package vn.asiantech.internship.friend;
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
import vn.asiantech.internship.friend.model.Friend;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<Friend> mNotes;
    private FriendAdapter mFriendAdapter;

    public FriendFragment() {
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
        mNotes = new ArrayList<>();
        initFriendData(mNotes);
        mFriendAdapter = new FriendAdapter(mNotes);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mFriendAdapter);
    }

    private void initFriendData(List<Friend> arrayList) {
        arrayList.add(new Friend("Abc xyz 001"));
        arrayList.add(new Friend("Abc xyz 001"));
        arrayList.add(new Friend("Abc xyz 001"));
        arrayList.add(new Friend("Abc xyz 001"));
        arrayList.add(new Friend("Abc xyz 001"));
        arrayList.add(new Friend("Abc xyz 001"));
        arrayList.add(new Friend("Abc xyz 001"));
        arrayList.add(new Friend("Abc xyz 001"));
        arrayList.add(new Friend("Abc xyz 001"));
        arrayList.add(new Friend("Abc xyz 001"));
    }
}
