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

    //    private boolean isViewShown = false;
    private View mLayout;

    public ThirdFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mLayout = inflater.inflate(R.layout.fragment_first, container, false);
        /*if (isViewShown) {
            ImageView imageView = (ImageView) mLayout.findViewById(R.id.imgTab);
            imageView.setImageResource(R.drawable.img_danang);
        }*/
        return mLayout;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mLayout != null) {
//            isViewShown = getUserVisibleHint();
            ImageView imageView = (ImageView) mLayout.findViewById(R.id.imgTab);
            imageView.setImageResource(R.drawable.img_danang);
        }
    }
}
