package vn.asiantech.internship.ui.feed;

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
import vn.asiantech.internship.models.Food;
import vn.asiantech.internship.ui.adapter.PageAdapter;

/**
 * Created by anhhuy on 15/06/2017.
 */

public class PageSecondFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private PageAdapter mPageAdapter;
    private List<Food> mFoodLists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewPage);
        initData();
        setAdapter();
        return view;
    }

    private void setAdapter(){
        mPageAdapter = new PageAdapter(mFoodLists);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mPageAdapter);
    }

    private void initData(){
        mFoodLists = new ArrayList<>();
        mFoodLists.add(new Food(R.drawable.bg_cat));
        mFoodLists.add(new Food(R.drawable.bg_cat));
        mFoodLists.add(new Food(R.drawable.bg_cat));
    }
}
