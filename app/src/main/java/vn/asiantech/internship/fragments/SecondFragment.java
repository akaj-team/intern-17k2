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
 * fragment content button and text view show text in FirstFragment
 */
public class SecondFragment extends Fragment {
    private TextView mTvDetail;
    private OnClickButton mOnClickButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);
        Button btnInFrag = (Button) view.findViewById(R.id.btnFragment);
        mTvDetail = (TextView) view.findViewById(R.id.tvDetail);
        btnInFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickButton = (OnClickButton) getActivity();
                if (mOnClickButton != null) {
                    mOnClickButton.setClick();
                }
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
