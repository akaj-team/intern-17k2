package vn.asiantech.internship.Day5.day9;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Created by rimoka on 15/06/2017.
 */

public class FeedFragment extends Fragment {
    private RecyclerView mRecyclerViewFeed;
    private TextView mTvUserName;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_feed,container,false);
        mRecyclerViewFeed= (RecyclerView) v.findViewById(R.id.recyclerViewFeed);
        return v;
    }
}
