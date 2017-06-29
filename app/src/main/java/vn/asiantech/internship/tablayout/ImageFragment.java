package vn.asiantech.internship.tablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.asiantech.internship.R;

/**
 * Used to display image on fragment.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-27
 */
public class ImageFragment extends Fragment {
    private static final String BUNDLE_KEY = "image";
    private boolean mIsLoadData = false;
    private View mView;
    private boolean mIsShow;
    private ImageView mImgScenery;

    public static ImageFragment newInstance(int image) {
        Bundle args = new Bundle();
        args.putInt(BUNDLE_KEY, image);
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_image, container, false);
        mImgScenery = (ImageView) mView.findViewById(R.id.imgScenery);
        if (!mIsLoadData && mIsShow) {
            loadData();
        }
        return mView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        mIsShow = isVisibleToUser;
        if (isVisibleToUser) {
            loadData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsLoadData = false;
    }

    private void loadData() {
        if (getArguments() != null) {
            int image = getArguments().getInt(BUNDLE_KEY);
            if (mView != null) {
                mImgScenery.setBackgroundResource(image);
                mIsLoadData = true;
            }
        }
    }
}
