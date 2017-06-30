package vn.asiantech.internship.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.pagetransformer.DepthPageTransformer;
import vn.asiantech.internship.R;
import vn.asiantech.internship.adapter.AnimalAdapter;
import vn.asiantech.internship.ui.tablayouts.RoundArcView;

/**
 * Created by ducle on 26/06/2017.
 */
public class MainAnimalFragment extends Fragment {
    private List<String> mAnimalImages;
    private List<String> mDogImages;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private AnimalAdapter mAnimalAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_main_animal, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        initData();
        initViews(view);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initViews(View view) {
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mAnimalAdapter = new AnimalAdapter(getFragmentManager(), mAnimalImages, mDogImages);
        mViewPager.setAdapter(mAnimalAdapter);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mTabLayout.setupWithViewPager(mViewPager);
        setUpTab();
    }

    private void initData() {
        mAnimalImages = new ArrayList<>();
        mDogImages = new ArrayList<>();
        mAnimalImages.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTz2iSLK1tdMjHEQh1YjbSqB1pTwa4SBMc21xfj6pIcAc91G4TMWQ");
        mAnimalImages.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcT8HXlCWhKYB_OxaGSEByylhMArifOfjCJeQQZ4yw_CTJA6Yf");
        mAnimalImages.add("https://s-media-cache-ak0.pinimg.com/736x/fc/07/9e/fc079e5a81f0fbc62896ba2eec611074--pc-mouse-felt-animals.jpg");
        mAnimalImages.add("http://ichef.bbci.co.uk/wwfeatures/live/384_216/images/live/p0/2f/85/p02f857y.jpg");
        mAnimalImages.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0tYz4qqNPYqp45HIVE3f-LKsKCu0r34ITULxO46pJZHXhQfri");

        mDogImages.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcT8HXlCWhKYB_OxaGSEByylhMArifOfjCJeQQZ4yw_CTJA6Yf");
        mDogImages.add("https://www.cesarsway.com/sites/newcesarsway/files/styles/large_article_preview/public/Common-dog-behaviors-explained.jpg?itok=FSzwbBoi");
        mDogImages.add("https://www.rover.com/blog/wp-content/uploads/2015/05/dog-candy-junk-food-599x340.jpg");
    }

    private void setUpTab() {
        int[] tabIcons = {R.drawable.ic_cat,
                R.drawable.ic_dog,
                R.drawable.ic_mouse,
                R.drawable.ic_penguin,
                R.drawable.ic_gorilla};
        String[] tabStrings = {getString(R.string.cat),
                getString(R.string.dog),
                getString(R.string.mouse),
                getString(R.string.penguin),
                getString(R.string.gorilla)};
        for (int i = 0; i < tabIcons.length; i++) {
            View viewTab = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_view, new LinearLayout(getActivity(), null));
            TextView tvTab = (TextView) viewTab.findViewById(R.id.tvTab);
            tvTab.setText(tabStrings[i]);
            tvTab.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[i], 0, 0);
            RoundArcView roundArcView = (RoundArcView) viewTab.findViewById(R.id.customTab);
            if (i == 0) {
                roundArcView.setSelected(true);
            }
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(viewTab);
            }
        }
    }
}
