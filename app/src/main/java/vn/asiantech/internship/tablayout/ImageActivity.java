package vn.asiantech.internship.tablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Used to display viewpager on activity.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-27
 */
public class ImageActivity extends AppCompatActivity {
    private final List<Integer> mImages = Arrays.asList(R.drawable.ic_one, R.drawable.ic_two, R.drawable.ic_three, R.drawable.ic_four, R.drawable.ic_five);
    private final List<Integer> mIcons = Arrays.asList(R.drawable.ic_search_amber_900_36dp, R.drawable.ic_do_not_disturb_on_amber_900_36dp, R.drawable.ic_home_amber_900_36dp, R.drawable.ic_free_breakfast_amber_900_36dp, R.drawable.ic_person_pin_amber_900_36dp);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ViewPager viewPagerImage = (ViewPager) findViewById(R.id.BigViewPager);
        final TabLayout tlImage = (TabLayout) findViewById(R.id.tlImage);

        FragmentManager manager = getSupportFragmentManager();
        final BigAdapter adapter = new BigAdapter(manager, mImages);
        viewPagerImage.setAdapter(adapter);
        tlImage.setupWithViewPager(viewPagerImage);
        viewPagerImage.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlImage));

        for (int i = 0; i < adapter.getCount(); i++) {
            TextView tvTab = (TextView) LayoutInflater.from(this).inflate(R.layout.item_tab, null);
            tvTab.setText(adapter.getPageTitle(i));
            tvTab.setCompoundDrawablesWithIntrinsicBounds(0, mIcons.get(i), 0, 0);
            tlImage.getTabAt(i).setCustomView(tvTab);
        }
    }
}
