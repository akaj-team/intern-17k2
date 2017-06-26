package vn.asiantech.internship.tablayout;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.asiantech.internship.R;

/**
 * Created by sony on 26/06/2017.
 */

public class ImageFragment extends Fragment {
    private View mImgAnimal;
    private int mImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        mImgAnimal = view.findViewById(R.id.imgInViewPager);
        mImgAnimal.setBackgroundResource(mImage);
        return view;
    }

    public void setData(int i) {
        mImage = i;
    }
}
