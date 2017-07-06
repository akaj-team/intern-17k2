package vn.asiantech.internship.test;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Used to display question in viewPager.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-23
 */
public class TestFragment extends Fragment {
    private RadioButton mRbA;
    private RadioButton mRbB;
    private RadioButton mRbC;
    private RadioButton mRbD;
    private String mAnswer;
    private Test mTest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rgChoose);
        mRbA = (RadioButton) view.findViewById(R.id.rbAnswerA);
        mRbB = (RadioButton) view.findViewById(R.id.rbAnswerB);
        mRbC = (RadioButton) view.findViewById(R.id.rbAnswerC);
        mRbD = (RadioButton) view.findViewById(R.id.rbAnswerD);
        TextView tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);
        tvQuestion.setText(mTest.getQuestion());
        mRbA.setText(mTest.getAnswerA());
        mRbB.setText(mTest.getAnswerB());
        mRbC.setText(mTest.getAnswerC());
        mRbD.setText(mTest.getAnswerD());

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                mTest.setState();
                switch (i) {
                    case R.id.rbAnswerA:
                        mAnswer = mRbA.getText().toString();
                        break;
                    case R.id.rbAnswerB:
                        mAnswer = mRbB.getText().toString();
                        break;
                    case R.id.rbAnswerC:
                        mAnswer = mRbC.getText().toString();
                        break;
                    case R.id.rbAnswerD:
                        mAnswer = mRbD.getText().toString();
                        break;
                }
                if (TextUtils.equals(mTest.getRightAnswer(), mAnswer)) {
                    mTest.setTrue(true);
                } else {
                    mTest.setTrue(false);
                }
            }
        });
        return view;
    }

    public void setData(Test test) {
        mTest = test;
    }
}
