package vn.asiantech.internship.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.asiantech.internship.R;
import vn.asiantech.internship.DrawerAdapter;

/**
 * DrawerFragment create on 12/06 by Thien Nguyen
 */
public class DrawerFragment extends Fragment {

    private String[] mListDrawer;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_drawer, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.rvDrawer);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListDrawer = getResources().getStringArray(R.array.listDrawer);
        mRecyclerView.setAdapter(new DrawerAdapter(mListDrawer));

        return v;
    }


}
