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
 * Fragment first.
 * Created by AnhHuy on 26-Jun-17.
 */
public class TabFirstFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ImageView imgTab1 = (ImageView) inflater.inflate(R.layout.item_list_image, container, false);
        imgTab1.setImageResource(R.drawable.img_tab1);
        return imgTab1;
    }
}
