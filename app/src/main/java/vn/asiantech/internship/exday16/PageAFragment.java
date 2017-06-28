package vn.asiantech.internship.exday16;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

import static vn.asiantech.internship.R.id.tabLayout;

/**
 * Created by datbu on 26-06-2017.
 */
public class PageAFragment extends Fragment {
    private static List<String> mImages;
    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private final String[] mTextTab = {"Tab 1", "Tab 2", "Tab 3", "Tab 4", "Tab 5"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_a, container, false);
        mImages = new ArrayList<>();
        String image1 = "http://cdn.runescape.com/assets/img/external/news/2015/03/dark_lord_outfit.jpg";
        mImages.add(image1);
        String image2 = "http://vignette2.wikia.nocookie.net/runescape2/images/3/36/Lord_Amlodd_concept_art.jpg/revision/latest?cb=20140811105559";
        mImages.add(image2);
        String image3 = "https://dviw3bl0enbyw.cloudfront.net/uploads/forum_attachment/file/139844/Male_voodoo_armor_concept_art.jpg";
        mImages.add(image3);
        String image4 = "https://cdna.artstation.com/p/assets/images/images/002/854/562/large/jonas-lopez-moreno-jonaslopezmoreno-saitan-web.jpg?1466498557";
        mImages.add(image4);
        String image5 = "http://cdn.runescape.com/assets/img/external/news/2015/03/dark_lord_outfit.jpg";
        mImages.add(image5);

        mTabLayout = (TabLayout) view.findViewById(tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), mImages);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(1);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        return view;
    }

//    public void customTab() {
//        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
//            View view = LayoutInflater.from(getContext()).inflate(R.layout.tablayout, null, false);
//            CustomTab customTab = (CustomTab) view.findViewById(R.id.myView);
//            if (i == 0) {
//                customTab.setSelected(true);
//            }
//            TextView tabTitle = (TextView) view.findViewById(R.id.tvTabTitle);
//            tabTitle.setText(mTextTab[i]);
//            TabLayout.Tab tab = mTabLayout.getTabAt(i);
//            final int position = i;
//            if (tab != null) {
//                tab.setCustomView(view);
//            }
//        }
//    }
}
