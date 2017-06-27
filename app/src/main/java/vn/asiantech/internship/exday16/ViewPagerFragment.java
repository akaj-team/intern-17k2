package vn.asiantech.internship.exday16;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 26-06-2017.
 */
public class ViewPagerFragment extends Fragment {
    private static String image1 = "http://cdn.runescape.com/assets/img/external/news/2015/03/dark_lord_outfit.jpg";
    private static String image2 = "http://vignette2.wikia.nocookie.net/runescape2/images/3/36/Lord_Amlodd_concept_art.jpg/revision/latest?cb=20140811105559";
    private static String image3 = "https://dviw3bl0enbyw.cloudfront.net/uploads/forum_attachment/file/139844/Male_voodoo_armor_concept_art.jpg";
    private static String image4 = "https://cdna.artstation.com/p/assets/images/images/002/854/562/large/jonas-lopez-moreno-jonaslopezmoreno-saitan-web.jpg?1466498557";
    private static String image5 = "http://cdn.runescape.com/assets/img/external/news/2015/03/dark_lord_outfit.jpg";
    private static List<String> mImages;
    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private static int currentPage = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);
        mImages = new ArrayList<>();
        mImages.add(image1);
        mImages.add(image2);
        mImages.add(image3);
        mImages.add(image4);
        mImages.add(image5);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), mImages);
        Log.e("hhh", "onCreateView: " + mImages.size());
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(2);
        mViewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(mViewPager);
        Log.d("tag", "aaaa: " + mViewPager.getCurrentItem());
//        tabLayout.getId();
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
//        init();
        return view;
    }

    private void init() {
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == mImages.size()) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);
    }
}
