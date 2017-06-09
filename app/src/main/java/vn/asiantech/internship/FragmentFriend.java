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

public class FragmentFriend extends Fragment implements View.OnClickListener {

    public FragmentFriend() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);

        List<Name> nameList = new ArrayList<>();
        nameList.add(new Name("Alex", true));
        nameList.add(new Name("Sun", true));
        nameList.add(new Name("Sat", false));
        nameList.add(new Name("Pink", false));
        nameList.add(new Name("Long", false));
        nameList.add(new Name("Linh", false));

        LinearLayoutManager manager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(manager);
        // Our classic custom adapter.

        RecyclerAdapter mAdapter = new RecyclerAdapter(nameList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
