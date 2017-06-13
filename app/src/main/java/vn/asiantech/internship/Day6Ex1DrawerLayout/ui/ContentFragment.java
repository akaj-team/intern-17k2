package vn.asiantech.internship.Day6Ex1DrawerLayout.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * create fragment to display content
 * Created by at-hoavo on 12/06/2017.
 */
public class ContentFragment extends Fragment {
    private TextView mTvContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_content, container, false);
        mTvContent = (TextView) v.findViewById(R.id.tvContent);
        return v;
    }

    public void showContent(String content) {
        mTvContent.setText(content);
    }

}
