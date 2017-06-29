package vn.asiantech.internship.day16.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.asiantech.internship.R;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 26/06/2017.
 */
public class TabFragment extends Fragment {
    private static final String ID_IMAGE = "image";
    private int mIdImage;
    private ImageView mImgText;

    public static TabFragment init(int image) {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ID_IMAGE, image);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIdImage = getArguments() != null ? getArguments().getInt(ID_IMAGE) : 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab, container, false);
        mImgText = (ImageView) v.findViewById(R.id.imgTab);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setMenuVisibility(isVisibleToUser);
        if (isVisibleToUser) {
            if (mImgText != null) {
                mImgText.setImageResource(mIdImage);
            }
        }
    }
}
