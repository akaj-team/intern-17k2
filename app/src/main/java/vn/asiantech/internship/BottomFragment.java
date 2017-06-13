package vn.asiantech.internship;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 *
 * Created by datbu on 14-06-2017.
 */

public class BottomFragment extends Fragment{
    private TextView mTvOutput;
    private MainActivity.OnClick mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListener = ((SendData) getArguments().getSerializable("Data")).getListener();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);
        mTvOutput = (TextView) view.findViewById(R.id.tvOutput);
        Button btnGetText = (Button) view.findViewById(R.id.btnGetText);
        btnGetText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(mTvOutput);
            }
        });
        return view;
    }

    public void setText(String str) {
        mTvOutput.setText(str);
    }
}
