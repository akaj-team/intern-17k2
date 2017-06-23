package vn.asiantech.internship.ui;

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
 * A simple {@link Fragment} subclass.
 */
public class ActionFragment extends Fragment {

    private TextView mTvContent;
    private Button mBtnGet;

    private OnSendDataListener mOnSendDataListener;

    /**
     * callback senddata from activity.
     */
    interface OnSendDataListener {
        void onSendData();
    }

    public void setOnSendDataListener(OnSendDataListener onSendDataListener) {
        mOnSendDataListener = onSendDataListener;
    }

    public ActionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_action, container, false);
        mTvContent = (TextView) layout.findViewById(R.id.tvContent);
        mBtnGet = (Button) layout.findViewById(R.id.btnGetContent);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBtnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnSendDataListener.onSendData();
            }
        });
    }

    public void setText(String content) {
        mTvContent.setText(content);
    }
}
