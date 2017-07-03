package vn.asiantech.internship.drawer.day16.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.asiantech.internship.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends Fragment {

    private View mView;

    private boolean isVisible;

    public ThirdFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_first, container, false);
        if (isVisible && getUserVisibleHint()) {
            loadData();
        }
        return mView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadData();
        }
        isVisible = isVisibleToUser;
    }

    private void loadData() {
        if (mView != null) {
            ImageView imageView = (ImageView) mView.findViewById(R.id.imgFragment);
            imageView.setImageResource(R.drawable.img_sunwheel);
            isVisible = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isVisible = false;
    }
}
