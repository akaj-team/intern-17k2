package vn.asiantech.internship.exday16;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 28-06-2017.
 */
class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<String> mImages;
    private String mImage;

    ViewPagerAdapter(FragmentManager fm, String image) {
        super(fm);
        mImage = image;
        mImages = new ArrayList<>();
        mImages.add("http://cdn.runescape.com/assets/img/external/news/2015/03/dark_lord_outfit.jpg");
        mImages.add("http://vignette2.wikia.nocookie.net/runescape2/images/3/36/Lord_Amlodd_concept_art.jpg/revision/latest?cb=20140811105559");
        mImages.add("https://dviw3bl0enbyw.cloudfront.net/uploads/forum_attachment/file/139844/Male_voodoo_armor_concept_art.jpg");
        mImages.add("https://cdna.artstation.com/p/assets/images/images/002/854/562/large/jonas-lopez-moreno-jonaslopezmoreno-saitan-web.jpg?1466498557");
        mImages.add("https://dviw3bl0enbyw.cloudfront.net/uploads/forum_attachment/file/139844/Male_voodoo_armor_concept_art.jpg");
    }

    @Override
    public Fragment getItem(int position) {
        mImage = mImages.get(position);
        if (position == 1) {
            return PageBFragment.newInstance();
        }
        return ItemImageFragment.newInstance(mImage);
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int pos = position + 1;
        return "Picture " + pos;
    }
}
