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
    private static final String ARG_PARAM2 = "param2";

    private ImageView mImgView;

    private String mUrl;
    private int mPosition;


    public static ViewPagerTabChildSecondFragment newInstance(String url, int position) {
        ViewPagerTabChildSecondFragment fragment = new ViewPagerTabChildSecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, url);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(ARG_PARAM1);
            mPosition = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_pager_tab_child_second, container, false);
        mImgView = (ImageView) v.findViewById(R.id.imgView);
        if (mPosition == 0) {
            Picasso.with(getContext()).load(mUrl).placeholder(R.drawable.vector_refresh).into(mImgView);
        }
        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getContext() != null) {
            Picasso.with(getContext()).load(mUrl).placeholder(R.drawable.vector_refresh).into(mImgView);
        }
    }
}
