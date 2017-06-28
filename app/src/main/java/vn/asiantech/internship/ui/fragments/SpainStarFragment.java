package vn.asiantech.internship.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapters.SpainStarAdapter;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/26/2017
 */
public class SpainStarFragment extends Fragment {

    private View mView;
    private ViewPager mViewPagerSpainStar;
    private SpainStarAdapter mAdapter;
    private int[] spainStarPhotos = {R.drawable.bg_ramos, R.drawable.bg_iniesta, R.drawable.bg_torres};
    private boolean dataLoaded = false;
    private boolean isVisible;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_spain_star, container, false);
        mViewPagerSpainStar = (ViewPager) mView.findViewById(R.id.viewPagerSpainStar);
        if (!dataLoaded && getUserVisibleHint()) {
            loadData();
        }
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dataLoaded = false;
        Log.i("tag11", "OnDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("tag11", "OnDestroy");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        isVisible = isVisibleToUser;
        if (isVisibleToUser) {
            loadData();
        }
    }

    @Override
    public boolean getUserVisibleHint() {
        return isVisible;
    }

    private void loadData() {
        if (mView != null) {
            if (mAdapter == null) {
                mAdapter = new SpainStarAdapter(spainStarPhotos);
            }
            mViewPagerSpainStar.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            dataLoaded = true;
        } else {
            dataLoaded = false;
        }
    }
}
