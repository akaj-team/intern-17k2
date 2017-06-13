package vn.asiantech.internship.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Created by Administrator on 6/13/2017.
 */
public class SecondFragment extends Fragment {
    private Button mBtnInFrag;
    private TextView mTvDetail;
    private OnClickButton mOnClickButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container);
        mBtnInFrag = (Button) view.findViewById(R.id.btnFragment);
        mTvDetail = (TextView) view.findViewById(R.id.tvDetail);
        mBtnInFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickButton = (OnClickButton) getActivity();
                mOnClickButton.setClick();
            }
        });
        return view;
    }

    public interface OnClickButton {
        void setClick();
    }

    public void setData(String input) {
        mTvDetail.setText(input);
    }
}
