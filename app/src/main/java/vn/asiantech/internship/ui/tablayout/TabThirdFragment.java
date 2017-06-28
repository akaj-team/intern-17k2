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
    private ImageView imgTab3;
    private boolean mIsLoad;
    private boolean mIsVisible;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        imgTab3 = (ImageView) inflater.inflate(R.layout.fragment_tab_single, container, false);
        if (!mIsLoad && getUserVisibleHint()) {
            loadImage();
        }
        return imgTab3;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        mIsVisible = isVisibleToUser;
        if (isVisibleToUser) {
            if (imgTab3 != null) {
                loadImage();
            }
        }
    }

    @Override
    public boolean getUserVisibleHint() {
        return mIsVisible;
    }

    private void loadImage() {
        imgTab3.setImageResource(R.drawable.img_tab3);
        mIsLoad = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsLoad = false;
    }
}
