package vn.asiantech.internship.ui.feed;

import android.graphics.Rect;
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
import vn.asiantech.internship.ui.adapter.FeedAdapter;

/**
 * Fragment for Feed
 * Created by anhhuy on 15/06/2017.
 */

public class FeedFragment extends Fragment {
    private RecyclerView mRecyclerViewFeed;
    private List<Food> mFoodLists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        mRecyclerViewFeed = (RecyclerView) view.findViewById(R.id.recyclerViewFeed);

        initDataFeed();
        setFeedAdapter();
        return view;
    }

    private void setFeedAdapter() {
        FeedAdapter feedAdapter = new FeedAdapter(mFoodLists);
        mRecyclerViewFeed.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewFeed.setAdapter(feedAdapter);
        mRecyclerViewFeed.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                outRect.set(25, position == 0 ? 10 : 17, 25, 17);
            }
        });
    }

    private void initDataFeed() {
        mFoodLists = new ArrayList<>();
        mFoodLists.add(new Food(getResources().getString(R.string.textview_fragment_icePuppy),
                new int[]{R.drawable.img_ice_puppey, R.drawable.img_ice, R.drawable.img_ice_chip},
                getResources().getString(R.string.textview_fragment_content)));
        mFoodLists.add(new Food(getResources().getString(R.string.textview_fragment_ice),
                new int[]{R.drawable.img_ice, R.drawable.img_ice_puppey, R.drawable.img_ice_chip},
                getResources().getString(R.string.textview_fragment_content)));
        mFoodLists.add(new Food(getResources().getString(R.string.textview_fragment_iceChip),
                new int[]{R.drawable.img_ice_chip, R.drawable.img_ice_puppey, R.drawable.img_ice},
                getResources().getString(R.string.textview_fragment_content)));
        mFoodLists.add(new Food(getResources().getString(R.string.textview_fragment_icePuppy),
                new int[]{R.drawable.img_ice_puppey, R.drawable.img_ice, R.drawable.img_ice_chip},
                getResources().getString(R.string.textview_fragment_content)));
        mFoodLists.add(new Food(getResources().getString(R.string.textview_fragment_ice),
                new int[]{R.drawable.img_ice, R.drawable.img_ice_puppey, R.drawable.img_ice_chip},
                getResources().getString(R.string.textview_fragment_content)));
        mFoodLists.add(new Food(getResources().getString(R.string.textview_fragment_iceChip),
                new int[]{R.drawable.img_ice_chip, R.drawable.img_ice_puppey, R.drawable.img_ice},
                getResources().getString(R.string.textview_fragment_content)));
    }
}
