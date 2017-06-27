package vn.asiantech.internship.ui.viewpager;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.asiantech.internship.R;

public class ViewPagerFragment extends Fragment {
    private View mView;
    private int mImageResource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_view_pager, container, false);
        return mView;
    }

    public void setImageResource(int imageResource) {
        mImageResource = imageResource;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mView != null) {
            ImageView imageView = (ImageView) mView.findViewById(R.id.imgView);
            imageView.setImageResource(mImageResource);
        }
    }
}
