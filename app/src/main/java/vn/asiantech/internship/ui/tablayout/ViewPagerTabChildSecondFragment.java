package vn.asiantech.internship.ui.tablayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import vn.asiantech.internship.R;

/**
 * ViewPagerTabChildSecondFragment
 */
public class ViewPagerTabChildSecondFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private ImageView mImgView;
    private View mView;

    private String mUrl;
    private boolean mIsLoadData;
    private boolean mVisible;

    /**
     * ViewPagerTabChildSecondFragment will be called in adapter when user scroll to position tab
     * @param url is a url of image sever will load
     * @return ViewPagerTabChildSecondFragment to showing
     */
    public static ViewPagerTabChildSecondFragment newInstance(String url) {
        ViewPagerTabChildSecondFragment fragment = new ViewPagerTabChildSecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getArguments().getString(ARG_PARAM1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_view_pager_tab_child_second, container, false);
        mImgView = (ImageView) mView.findViewById(R.id.imgView);

        // Default load in the open first time
        if (!mIsLoadData && getUserVisibleHint()) {
            Picasso.with(getContext()).load(mUrl).placeholder(R.drawable.vector_refresh).into(mImgView);
        }
        return mView;
    }

    @Override
    public boolean getUserVisibleHint() {
        return mVisible;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        mVisible = isVisibleToUser;
        if (isVisibleToUser && getContext() != null) {
            if (!mIsLoadData && mView != null) {
                mIsLoadData = true;
                Picasso.with(getContext()).load(mUrl).placeholder(R.drawable.vector_refresh).into(mImgView);
            }
        }
    }

    @Override
    public void onDestroyView() {
        mVisible = false;
        super.onDestroyView();
    }
}
