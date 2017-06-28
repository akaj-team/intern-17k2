package vn.asiantech.internship.ui.tablayout;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.tablayout.transformer.CubePageTransformer;
import vn.asiantech.internship.ui.tablayout.transformer.DepthPageTransformer;
import vn.asiantech.internship.ui.tablayout.transformer.DrawFromBackTransformer;
import vn.asiantech.internship.ui.tablayout.transformer.ZoomOutPageTransformer;

import static vn.asiantech.internship.R.id.tabLayout;

/**
 * ViewPagerFragment
 */
public class ViewPagerFragment extends Fragment {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton mFabSettings;
    private StyleTab mUtils;
    private ViewPager.PageTransformer mPageTransformer;
    private CoordinatorLayout mClParent;

    private String[] mUrls = {
            "http://www.desktop-background.com/download/480x800/2015/12/08/1054402_download-wallpapers-gerrard-456t-dikirim-oleh-namik9-bola-net_1680x1050_h.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/f1/12/8d/f1128dc83ba00c9a01f1ea5ce42d2485.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/92/7b/da/927bda71d6d87be6c3b58f9b4162ef6d.jpg"
    };

    private int[] mDrawables = {
            R.drawable.bg_love,
            R.drawable.bg_important,
            R.drawable.bg_star
    };

    private int[] mDrawableLines = {
            R.drawable.bg_love_custom,
            R.drawable.bg_important_custom,
            R.drawable.bg_star_custom
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_layout, container, false);

        mFabSettings = (FloatingActionButton) v.findViewById(R.id.fabSettings);
        mViewPager = (ViewPager) v.findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) v.findViewById(tabLayout);
        mClParent = (CoordinatorLayout) v.findViewById(R.id.clParent);

        // Chang style tabs here
        setStyleTabs(StyleTab.MY_TAB_CUSTOM);

        // Change slider mode here
        setSlideMode(SliderMode.CUBE_PAGE_TRANSFORMER);

        mViewPager.setAdapter(new ViewPagerAdapter(getActivity().getSupportFragmentManager(), mUrls));
        mTabLayout.setupWithViewPager(mViewPager);
        setTitle();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (getActivity() instanceof ViewPagerActivity) {
                    if (position == 1) {
                        mClParent.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                // It crazy i cant disable swiping , but it look so fun =))
                                return true;
                            }
                        });
                        ((ViewPagerActivity) getActivity()).setFloatActionButton(View.GONE);
                    } else {
                        ((ViewPagerActivity) getActivity()).setFloatActionButton(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mFabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogChoice();
            }
        });

        return v;
    }

    private void setTitle() {
        int m = mUrls.length;
        if (mUtils == StyleTab.TEXT_ONLY) {
            for (int i = 0; i < m; i++) {
                setTabOnlyText(i);
            }
        } else if (mUtils == StyleTab.TEXT_AND_ICON) {
            for (int i = 0; i < m; i++) {
                setTabTextAndIcon(i);
            }
        } else if (mUtils == StyleTab.ICON_ONLY) {
            for (int i = 0; i < m; i++) {
                setTabOnlyIcon(i);
            }
        } else if (mUtils == StyleTab.MY_TAB_CUSTOM) {
            for (int i = 0; i < m; i++) {
                setMyTabCustom(i);
            }
        }
    }

    private void setTabOnlyText(int position) {
        TextView tab = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.item_tab_layout_custom, null);
        String s = "Tab " + position;
        tab.setText(s);
    }

    private void setTabTextAndIcon(int position) {
        TextView tab = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.item_tab_layout_custom, null);
        String s = "Tab " + position;
        tab.setText(s);
        tab.setCompoundDrawablesWithIntrinsicBounds(0, mDrawables[position], 0, 0);
        TabLayout.Tab tabSave = mTabLayout.getTabAt(position);
        if (tabSave != null) {
            tabSave.setCustomView(tab);
        }
    }

    private void setTabOnlyIcon(int position) {
        TabLayout.Tab tabSave = mTabLayout.getTabAt(position);
        if (tabSave != null) {
            tabSave.setIcon(mDrawables[position]);
        }
    }

    private void setMyTabCustom(int position) {
        View tab = LayoutInflater.from(getActivity()).inflate(R.layout.item_tab_layout_custom_second, null);
        ((ImageView) tab.findViewById(R.id.imgIcon)).setImageResource(mDrawables[position]);
        (tab.findViewById(R.id.view)).setBackgroundResource(mDrawableLines[position]);
        mTabLayout.setSelectedTabIndicatorHeight(0);
        TabLayout.Tab tabSave = mTabLayout.getTabAt(position);
        if (tabSave != null) {
            tabSave.setCustomView(tab);
        }
    }

    private void setStyleTabs(StyleTab utils) {
        mUtils = utils;
    }

    private void setSlideMode(SliderMode slideStyle) {
        if (slideStyle == SliderMode.CUBE_PAGE_TRANSFORMER) {
            mPageTransformer = new CubePageTransformer();
        } else if (slideStyle == SliderMode.ZOOM_OUT_PAGE_TRANSFORMER) {
            mPageTransformer = new ZoomOutPageTransformer();
        } else if (slideStyle == SliderMode.DEPTH_PAGE_TRANSFORMER) {
            mPageTransformer = new DepthPageTransformer();
        } else if (slideStyle == SliderMode.DRAW_FROM_BACK_TRANSFORMER) {
            mPageTransformer = new DrawFromBackTransformer();
        }
        mViewPager.setPageTransformer(true, mPageTransformer);
    }

    private void showDialogChoice() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_choice_tab_style);
        dialog.setTitle(R.string.dialog_text_choice_select);

        Spinner spnChoiceSlide = (Spinner) dialog.findViewById(R.id.spnChoiceSlide);
        Spinner spnChoiceTabStyle = (Spinner) dialog.findViewById(R.id.spnChoiceTabStyle);

        ArrayAdapter<String> adapterSlier = new ArrayAdapter<>(dialog.getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Sliders));
        ArrayAdapter<String> adapterTabStyle = new ArrayAdapter<>(dialog.getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.TabStyle));
        spnChoiceSlide.setAdapter(adapterSlier);
        spnChoiceTabStyle.setAdapter(adapterTabStyle);

        // TODO it have some bug I'll fix it
        // Edit in java code to change transform
        spnChoiceSlide.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    setSlideMode(SliderMode.CUBE_PAGE_TRANSFORMER);
                } else if (position == 1) {
                    setSlideMode(SliderMode.ZOOM_OUT_PAGE_TRANSFORMER);
                } else if (position == 2) {
                    setSlideMode(SliderMode.DEPTH_PAGE_TRANSFORMER);
                } else if (position == 3) {
                    setSlideMode(SliderMode.DRAW_FROM_BACK_TRANSFORMER);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        dialog.show();
    }

    public void setFloatActionButton(int visible) {
        mFabSettings.setVisibility(visible);
    }
}
