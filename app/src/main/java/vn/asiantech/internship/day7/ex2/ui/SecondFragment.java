package vn.asiantech.internship.day7.ex2.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Created by rimoka on 13/06/2017.
 */
public class SecondFragment extends Fragment {
    private MainActivity mMainActivity;
    private TextView mTvResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_2, container, false);
        mMainActivity = (MainActivity) getActivity();
        Button btnGo = (Button) v.findViewById(R.id.btnGo);
        mTvResult = (TextView) v.findViewById(R.id.tvResult);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvResult.setText(mMainActivity.onClick());
            }
        });
        return v;
    }

    public void setOutput(String s) {
        mTvResult.setText(s);
    }
}
