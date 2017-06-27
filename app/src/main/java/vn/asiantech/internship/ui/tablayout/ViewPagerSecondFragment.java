package vn.asiantech.internship.ui.tablayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.tablayout.transformer.TabletTransformer;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerSecondFragment extends Fragment {

    private ViewPager mViewPager;
    private GridView mGridView;

    private ViewPagerSecondAdapter mViewPagerSecondAdapter;
    private int mTabSelected = 0;
    private String[] mUrls = {
            "https://s-media-cache-ak0.pinimg.com/736x/bc/82/d6/bc82d6709eaa6921d32f25b2567cdc6d.jpg",
            "https://mfiles.alphacoders.com/579/579712.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/33/cd/16/33cd1631698f5e78a3e676f9fc222c59.jpg",
            "https://mfiles.alphacoders.com/600/600454.jpg",
            "http://p1.i.ntere.st/9133e51d84e89162372fc45429844911_480.jpg"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_pager_second, container, false);
        mGridView = (GridView) v.findViewById(R.id.gridView);

        mViewPagerSecondAdapter = new ViewPagerSecondAdapter(getChildFragmentManager(), mUrls);
        mViewPager = (ViewPager) v.findViewById(R.id.viewPager);
        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mTabSelected == 0) {
            mViewPager.setAdapter(mViewPagerSecondAdapter);
            mViewPager.setPageTransformer(false, new TabletTransformer());
            mTabSelected = 1;
        }
    }
}
