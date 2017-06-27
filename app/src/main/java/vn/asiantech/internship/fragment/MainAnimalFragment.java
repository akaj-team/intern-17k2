package vn.asiantech.internship.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.PageTransformer.DepthPageTransformer;
import vn.asiantech.internship.R;
import vn.asiantech.internship.adapter.AnimalAdapter;
import vn.asiantech.internship.ui.CustomViewPager;

/**
 * Created by ducle on 26/06/2017.
 */
public class MainAnimalFragment extends android.support.v4.app.Fragment {
    private List<String> mAnimalImages;
    private List<String> mDogImages;
    private TabLayout mTabLayout;
    private CustomViewPager mViewPager;
    private AnimalAdapter mAnimalAdapter;
    private int type = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_main_animal, container, false);
        mAnimalImages = new ArrayList<>();
        mAnimalImages.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTz2iSLK1tdMjHEQh1YjbSqB1pTwa4SBMc21xfj6pIcAc91G4TMWQ");
        mAnimalImages.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcT8HXlCWhKYB_OxaGSEByylhMArifOfjCJeQQZ4yw_CTJA6Yf");
        mAnimalImages.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQaOxpARyat0Qz2iywi_RqwLPa3cCvYtrhNcUoXDFFDFBz2z8Zj");
        mAnimalImages.add("http://ichef.bbci.co.uk/wwfeatures/live/384_216/images/live/p0/2f/85/p02f857y.jpg");
        mAnimalImages.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0tYz4qqNPYqp45HIVE3f-LKsKCu0r34ITULxO46pJZHXhQfri");
        mDogImages = new ArrayList<>();
        mDogImages.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQcT8HXlCWhKYB_OxaGSEByylhMArifOfjCJeQQZ4yw_CTJA6Yf");
        mDogImages.add("https://www.cesarsway.com/sites/newcesarsway/files/styles/large_article_preview/public/Common-dog-behaviors-explained.jpg?itok=FSzwbBoi");
        mDogImages.add("https://www.rover.com/blog/wp-content/uploads/2015/05/dog-candy-junk-food-599x340.jpg");
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mViewPager = (CustomViewPager) view.findViewById(R.id.viewPager);
        mAnimalAdapter = new AnimalAdapter(getFragmentManager(), mAnimalImages, mDogImages);
        mViewPager.setAdapter(mAnimalAdapter);
        mViewPager.setScrollDurationFactor(30);
        //mViewPager.setPageTransformer(true,new ZoomOutPagerTransformer());
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
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
        mTabLayout.setupWithViewPager(mViewPager);
        setUpTabIcons();
        //runThread();
        return view;
    }

    private void setUpTabIcons() {
        int[] tabIcons = {R.drawable.ic_cat,
                R.drawable.ic_dog,
                R.drawable.ic_mouse,
                R.drawable.ic_penguin,
                R.drawable.ic_gorilla};
        mTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        mTabLayout.getTabAt(1).setIcon(tabIcons[1]);
        mTabLayout.getTabAt(2).setIcon(tabIcons[2]);
        mTabLayout.getTabAt(3).setIcon(tabIcons[3]);
        mTabLayout.getTabAt(4).setIcon(tabIcons[4]);
    }

    private void runThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (type != 2) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (type == 0) {
                                if (mViewPager.getCurrentItem() < 4) {
                                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                                } else {
                                    type = 1;
                                }
                            } else {
                                if (mViewPager.getCurrentItem() > 0) {
                                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                                } else {
                                    type = 0;
                                }
                            }
                        }
                    });
                }
            }
        }).start();
    }
}
