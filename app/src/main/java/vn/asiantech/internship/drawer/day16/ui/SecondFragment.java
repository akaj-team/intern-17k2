package vn.asiantech.internship.drawer.day16.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.drawer.day16.adapter.ImagePagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {

    private boolean isVisible;

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewPager viewPager = null;
        if (isVisible) {
            viewPager = (ViewPager) inflater.inflate(R.layout.fragment_second, container, false);
            List<Integer> images = new ArrayList<>();
            images.add(R.drawable.img_binhdinh);
            images.add(R.drawable.img_sunwheel);
            ImagePagerAdapter adapter = new ImagePagerAdapter(images);
            viewPager.setAdapter(adapter);
        }
        return viewPager;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        isVisible = isVisibleToUser;
    }
}
