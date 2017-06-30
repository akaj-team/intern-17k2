package vn.asiantech.internship.drawer.day16.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.asiantech.internship.R;

/**
 * Created by at-dinhvo on 29/06/2017.
 */
public class FifthFragment extends Fragment {

    private View mLayout;

    private boolean isVisible;

    public FifthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLayout = inflater.inflate(R.layout.fragment_first, container, false);
        if (isVisible && getUserVisibleHint()) {
            loadData();
        }
        return mLayout;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisibleToUser) {
            loadData();
        }
    }

    private void loadData() {
        if (mLayout != null) {
            ImageView imageView = (ImageView) mLayout.findViewById(R.id.imgFragment);
            imageView.setImageResource(R.drawable.img_danang);
            isVisible = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isVisible = false;
    }
}
