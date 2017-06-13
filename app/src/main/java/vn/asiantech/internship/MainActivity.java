package vn.asiantech.internship;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RelativeLayout mRlContent;
    private ActionBarDrawerToggle mToggle;
    private static TextView sTVContent;
    private static DrawerLayout sDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        sTVContent = (TextView) findViewById(R.id.tvContent);
        mRlContent = (RelativeLayout) findViewById(R.id.rlContent);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sDrawerLayout = (DrawerLayout) findViewById(R.id.dlContainer);
        mToggle = new ActionBarDrawerToggle(
                this, sDrawerLayout, mToolbar, R.string.Text_Messages_Navigation_Open, R.string.Text_Messages_Navigation_Close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                //slide content when drawer openning
                mRlContent.setTranslationX(slideOffset * drawerView.getWidth());
                sDrawerLayout.bringChildToFront(drawerView);
                sDrawerLayout.requestLayout();
                sDrawerLayout.setScrimColor(Color.TRANSPARENT);
            }
        };
        sDrawerLayout.setDrawerListener(mToggle);
        mToggle.syncState();
    }

    /**
     * @param s is a String of content
     */
    public static void setMainText(String s) {
        sTVContent.setText(s);
    }

    /**
     * close drawble
     */
    public static void closeDrawer() {
        sDrawerLayout.closeDrawers();
    }

}
