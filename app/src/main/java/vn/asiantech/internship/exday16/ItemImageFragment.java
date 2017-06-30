package vn.asiantech.internship.exday16;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import vn.asiantech.internship.R;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 28-06-2017.
 */
public class ItemImageFragment extends Fragment {
    private ImageView mImageView;
    private boolean mIsStarted;
    private boolean mIsVisible;
    private View mView;
    private String mImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.item_viewpager, container, false);
        mImage = getArguments().getString("image");
        mImageView = (ImageView) mView.findViewById(R.id.imgView);
        if (!mIsStarted && getUserVisibleHint()) {
            viewDidAppear();
        }
        return mView;
    }

    public static ItemImageFragment newInstance(String image) {
        Bundle args = new Bundle();
        args.putString("image", image);
        ItemImageFragment fragment = new ItemImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        mIsStarted = true;
        if (mIsVisible) {
            viewDidAppear();
        }
    }

    @Override
    public boolean getUserVisibleHint() {
        return mIsVisible;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisible = isVisibleToUser;
        if (isVisibleToUser) {
            viewDidAppear();
        }
    }

    public void viewDidAppear() {
        if (mView != null) {
            Glide.with(getActivity()).load(mImage).into(mImageView);
            mIsStarted = true;
        } else {
            mIsStarted = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsStarted = false;
    }
}
