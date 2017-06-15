package vn.asiantech.internship.ui.feed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.adapter.ViewPagerAdapter;

/**
 * Created by anhhuy on 15/06/2017.
 */

public class FeedFragment extends Fragment {
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        setViewPager();
        return view;
    }

    private void setViewPager(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), 2);
        mViewPager.setAdapter(viewPagerAdapter);
    }

}
