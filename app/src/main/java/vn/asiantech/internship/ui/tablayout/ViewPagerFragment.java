package vn.asiantech.internship.ui.tablayout;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.asiantech.internship.R;


public class ViewPagerFragment extends Fragment {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_pager, container, false);

        mViewPager = (ViewPager) v.findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) v.findViewById(R.id.tlMain);
        return v;
    }
}
