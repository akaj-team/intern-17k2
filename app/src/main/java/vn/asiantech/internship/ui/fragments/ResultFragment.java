package vn.asiantech.internship.ui.fragments;

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
 *
 * Created by Hai on 6/13/2017.
 */
public class ResultFragment extends Fragment {
    private TextView mTvResult;
    private OnListener mOnListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        mTvResult = (TextView) view.findViewById(R.id.tvResult);
        Button btnSendData = (Button) view.findViewById(R.id.btnSendData_2);
        btnSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnListener != null) {
                    mOnListener.onSendData();
                }
            }
        });
        return view;
    }

    public ResultFragment(OnListener onListener) {
        mOnListener = onListener;
    }

    public void setText(String s) {
        mTvResult.setText(s);
    }

    /**
     * interface using in SendDataActivity handle event Click send data
     */
    public interface OnListener {
        void onSendData();
    }
}
