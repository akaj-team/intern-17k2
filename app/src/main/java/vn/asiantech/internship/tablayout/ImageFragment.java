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
    private int mImage;
    private boolean mIsLoadData = false;
    private View mView;
    private boolean mIsShow;
    private ImageView mImgScenery;

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

    public void setData(int i) {
        mImage = i;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        mIsShow = isVisibleToUser;
        if (isVisibleToUser) {
            loadData();
        }
    }

    private void loadData() {
        if (mView != null) {
            mImgScenery.setBackgroundResource(mImage);
            mIsLoadData = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsLoadData = false;
    }
}
