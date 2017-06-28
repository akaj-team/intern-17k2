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
    private static final String ARG_PARAM2 = "my_param2";

    private ImageView mImgView;

    private String mUrlImg;
    private int mPosition;

    /**
     * @param urlImg is a url of image loading
     * @return TabChild Fragment
     */
    public static ViewPagerTabChildFragment newInstance(String urlImg, int position) {
        ViewPagerTabChildFragment fragment = new ViewPagerTabChildFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, urlImg);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrlImg = getArguments().getString(ARG_PARAM);
        mPosition = getArguments().getInt(ARG_PARAM2);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getContext() != null) {
            Picasso.with(getContext()).load(mUrlImg).placeholder(R.drawable.vector_refresh).into(mImgView);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_child, container, false);
        mImgView = (ImageView) v.findViewById(R.id.imgView);

        if (mPosition == 0) {
            Picasso.with(getContext()).load(mUrlImg).placeholder(R.drawable.vector_refresh).into(mImgView);
        }
        return v;
    }
}
