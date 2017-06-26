package vn.asiantech.internship.ui.tablayout;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.asiantech.internship.R;

public class ViewPagerFragment extends Fragment {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Utils mUtils;
    private String[] mUrls = {
            "http://www.desktop-background.com/download/480x800/2015/12/08/1054402_download-wallpapers-gerrard-456t-dikirim-oleh-namik9-bola-net_1680x1050_h.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/f1/12/8d/f1128dc83ba00c9a01f1ea5ce42d2485.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/92/7b/da/927bda71d6d87be6c3b58f9b4162ef6d.jpg"
    };

    private String[] mDrawble = {
            "http://www.desktop-background.com/download/480x800/2015/12/08/1054402_download-wallpapers-gerrard-456t-dikirim-oleh-namik9-bola-net_1680x1050_h.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/f1/12/8d/f1128dc83ba00c9a01f1ea5ce42d2485.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/92/7b/da/927bda71d6d87be6c3b58f9b4162ef6d.jpg"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_pager, container, false);

        mViewPager = (ViewPager) v.findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) v.findViewById(R.id.tabLayout);

        mViewPager.setAdapter(new ViewPagerAdapter(getActivity().getSupportFragmentManager(), mUrls));
        mTabLayout.setupWithViewPager(mViewPager);
        setUtils(Utils.TEXT_ONLY);

        setTitle();
        return v;
    }

    private void setTitle() {
        if (mUtils == Utils.TEXT_ONLY) {
            int m = mUrls.length;
            for (int i = 0; i < m; i++) {
                setTabOnlyText(i);
            }
        } else if (mUtils == Utils.TEXT_AND_ICON) {

        }
    }

    private void setTabOnlyText(int i) {
        TextView tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.item_tab_layout_custom, null);
        tabOne.setText("Tab " + i);
    }

    private void setTabTextAnhImage(int i) {
        TextView tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.item_tab_layout_custom, null);
        tabOne.setText("Tab " + i);
    }

    private void setUtils(Utils utils) {
        mUtils = utils;
    }
}
