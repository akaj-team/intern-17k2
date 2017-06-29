package vn.asiantech.internship.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.asiantech.internship.R;
import vn.asiantech.internship.Ultils.DepthPageTransformer;
import vn.asiantech.internship.Ultils.MyCustomTab;
import vn.asiantech.internship.adapters.FootballStarAdapter;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/26/2017
 */
public class FootballStarActivity extends AppCompatActivity {

    private int[] mTabIcons = {R.drawable.ic_messi, R.drawable.ic_spain, R.drawable.ic_ronaldo, R.drawable.ic_reus, R.drawable.ic_kaka};

    private TabLayout mTabLayout;
    private FootballStarAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football_star);

        mAdapter = new FootballStarAdapter(getSupportFragmentManager());
        ViewPager viewPagerFootballStar = (ViewPager) findViewById(R.id.recyclerViewFootballStar);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPagerFootballStar.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(viewPagerFootballStar);
        viewPagerFootballStar.setPageTransformer(true, new DepthPageTransformer());
        customTab();
    }

    private void customTab() {
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.tablayout_custom, null, false);
            MyCustomTab myCustomTab = (MyCustomTab) view.findViewById(R.id.myView);
            if (i == 0) {
                myCustomTab.setSelected(true);
            }
            CircleImageView icon = (CircleImageView) view.findViewById(R.id.imgTabIcon);
            icon.setImageResource(mTabIcons[i]);
            TextView tabTitle = (TextView) view.findViewById(R.id.tvTabTitle);
            tabTitle.setText(mAdapter.getPageTitle(i));
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(view);
            }
        }
    }
}
