package vn.asiantech.internship.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.PageTransformer.DepthPageTransformer;
import vn.asiantech.internship.R;
import vn.asiantech.internship.adapter.DogAdapter;
import vn.asiantech.internship.ui.CustomViewPager;

/**
 * Created by ducle on 27/06/2017.
 */

public class Tab2AnimalFragment extends Fragment {
    private static final String TAG = Tab2AnimalFragment.class.getSimpleName();
    private static final String DOG_IMAGES = "dog";
    private boolean mIsLoaded;
    private boolean mIsShowed;
    private List<String> mDogImages;
    private CustomViewPager mViewPagerTab2;
    private DogAdapter mDogAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2_animal, container, false);
        mDogImages = getArguments().getStringArrayList(DOG_IMAGES);
        mViewPagerTab2 = (CustomViewPager) view.findViewById(R.id.viewPagerTab2);
        mViewPagerTab2.setScrollDurationFactor(30);
        mDogAdapter = new DogAdapter(getFragmentManager(), mDogImages);
        if (mIsShowed && !mIsLoaded) {
            loadData();
        }
        return view;
    }

    public static Tab2AnimalFragment newInstance(List<String> dogImages) {
        Tab2AnimalFragment tab2AnimalFragment = new Tab2AnimalFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(DOG_IMAGES, (ArrayList<String>) dogImages);
        tab2AnimalFragment.setArguments(bundle);
        return tab2AnimalFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsShowed = isVisibleToUser;
        if (this.isVisible()) {
            if (mIsShowed && !mIsLoaded) {
                loadData();
                mIsLoaded = true;
            }
        }
    }

    private void loadData() {
        mViewPagerTab2.setAdapter(mDogAdapter);
        mViewPagerTab2.setPageTransformer(true, new DepthPageTransformer());
        Log.i(TAG, "load item 2");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsLoaded = false;
        Log.i(TAG, "destroy item 2");
    }
}
