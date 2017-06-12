package vn.asiantech.internship.friend;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by PC on 6/9/2017.
 */
public class Day5MainActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private List<String> mDataset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day5_activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewExmple);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataset = new ArrayList<>();
        mDataset.add("Ten1");
        mDataset.add("Ten2");
        mDataset.add("Ten3");
        mDataset.add("Ten4");
        mDataset.add("Ten5");
        mAdapter = new RecyclerViewAdapter(this, mDataset);
        mRecyclerView.setAdapter(mAdapter);

    }
}
