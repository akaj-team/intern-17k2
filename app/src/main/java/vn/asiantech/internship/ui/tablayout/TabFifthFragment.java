package vn.asiantech.internship.ui.tablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.asiantech.internship.R;

/**
 * Fragment fifth.
 * Created by AnhHuy on 27-Jun-17.
 */
public class TabFifthFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ImageView imgTab5 = (ImageView) inflater.inflate(R.layout.fragment_tab_single, container, false);
        imgTab5.setImageResource(R.drawable.img_tab5);
        return imgTab5;
    }
}
