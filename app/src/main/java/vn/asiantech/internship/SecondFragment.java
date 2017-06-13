package vn.asiantech.internship;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SecondFragment extends Fragment {
    private Button mBtnSecondSend;
    private TextView mTvResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, null);
        mBtnSecondSend = (Button) view.findViewById(R.id.btnSecondSend);
        mTvResult = (TextView) view.findViewById(R.id.tvResult);
        return view;
    }

    public  interface OnClickListenner {
          void onClick();
    }
}
