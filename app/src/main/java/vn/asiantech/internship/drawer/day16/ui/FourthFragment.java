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
public class FourthFragment extends Fragment {

    private View mView;

    private boolean isVisible;

    public FourthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
            imageView.setImageResource(R.drawable.img_binhdinh);
            isVisible = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isVisible = false;
    }
}
