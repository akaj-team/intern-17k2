package vn.asiantech.internship.fragment;

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
 * Used to get data from first fragment
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-13
 */
public class SecondFragment extends Fragment {
    private TextView mTvResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        Button btnSend1 = (Button) view.findViewById(R.id.btnSend1);
        mTvResult = (TextView) view.findViewById(R.id.tvResult);
        btnSend1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof SendActivity) {
                    ((SendActivity) getActivity()).onClick();
                }
            }
        });
        return view;
    }

    public void setResult(String result) {
        mTvResult.setText(result);
    }
}
