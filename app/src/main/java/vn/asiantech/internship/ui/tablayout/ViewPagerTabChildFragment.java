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

    private String mUrlImg;

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
        if (getArguments() != null) {
            mUrlImg = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab_child, container, false);
        ImageView imgView = (ImageView) v.findViewById(R.id.imgView);
        Picasso.with(getActivity()).load(mUrlImg).placeholder(R.drawable.ic_avatar).into(imgView);
        return v;
    }
}
