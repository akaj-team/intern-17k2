package vn.asiantech.internship.ui.tablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.asiantech.internship.R;

/**
 * Fragment third.
 * Created by AnhHuy on 27-Jun-17.
 */
public class TabThirdFragment extends Fragment {
    private ImageView mImgTab3;
    private boolean mIsLoad;
    private boolean mIsVisible;

    public static TabThirdFragment newInstance() {
        return new TabThirdFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mImgTab3 = (ImageView) inflater.inflate(R.layout.item_list_image, container, false);
        if (!mIsLoad && getUserVisibleHint()) {
            loadImage();
        }
        return mImgTab3;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        mIsVisible = isVisibleToUser;
        if (isVisibleToUser) {
            if (mImgTab3 != null) {
                loadImage();
            }
        }
    }

    @Override
    public boolean getUserVisibleHint() {
        return mIsVisible;
    }

    private void loadImage() {
        mImgTab3.setImageResource(R.drawable.img_tab3);
        mIsLoad = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsLoad = false;
    }
}
