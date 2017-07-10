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
 * TabChild of viewpager
 */
public class ViewPagerTabChildFragment extends Fragment {

    private static final String ARG_PARAM = "my_param";

    private ImageView mImgView;

    private String mUrlImg;
    private boolean mIsLoadData;
    private boolean mVisible;
    private View mView;

    /**
     * @param urlImg is a url of image loading
     * @return TabChild Fragment
     */
    public static ViewPagerTabChildFragment newInstance(String urlImg) {
        ViewPagerTabChildFragment fragment = new ViewPagerTabChildFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, urlImg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrlImg = getArguments().getString(ARG_PARAM);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        mVisible = isVisibleToUser;
        if (isVisibleToUser && getContext() != null) {
            if (!mIsLoadData && mView != null) {
                mIsLoadData = true;
                Picasso.with(getContext()).load(mUrlImg).placeholder(R.drawable.vector_refresh).into(mImgView);
            }
        }
    }

    @Override
    public boolean getUserVisibleHint() {
        return mVisible;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_tab_child, container, false);
        mImgView = (ImageView) mView.findViewById(R.id.imgView);

        // Default load in the open first time
        if (!mIsLoadData && getUserVisibleHint()) {
            Picasso.with(getContext()).load(mUrlImg).placeholder(R.drawable.vector_refresh).into(mImgView);
        }

        return mView;
    }
}
