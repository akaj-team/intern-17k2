package vn.asiantech.internship.ui.questions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Question;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionShowFragment extends Fragment {

    private static final String KEY_QUESTION = "KEY_QUESTION";
    private static final String KEY_POS = "KEY_POS";

    private RadioButton mRbA;
    private RadioButton mRbB;
    private RadioButton mRbC;
    private RadioButton mRbD;

    private Question mQuestion;
    private int mCurrentPosition;

    public static QuestionShowFragment newInstance(Question questions, int position) {
        QuestionShowFragment fragment = new QuestionShowFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_QUESTION, questions);
        args.putSerializable(KEY_POS, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestion = getArguments().getParcelable(KEY_QUESTION);
        mCurrentPosition = getArguments().getInt(KEY_POS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_question_show, container, false);
        TextView tvQuestion = (TextView) v.findViewById(R.id.tvQuestion);

        mRbA = (RadioButton) v.findViewById(R.id.rbA);
        mRbB = (RadioButton) v.findViewById(R.id.rbB);
        mRbC = (RadioButton) v.findViewById(R.id.rbC);
        mRbD = (RadioButton) v.findViewById(R.id.rbD);

        tvQuestion.setText(mQuestion.getQuestion());
        mRbA.setText(mQuestion.getAnswerA());
        mRbB.setText(mQuestion.getAnswerB());
        mRbC.setText(mQuestion.getAnswerC());
        mRbD.setText(mQuestion.getAnswerD());

        mRbA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sendToMain(mRbA.getText().toString(), mCurrentPosition);
                }
            }
        });
        mRbB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sendToMain(mRbB.getText().toString(), mCurrentPosition);
                }
            }
        });
        mRbC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sendToMain(mRbC.getText().toString(), mCurrentPosition);
                }
            }
        });
        mRbD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sendToMain(mRbD.getText().toString(), mCurrentPosition);
                }
            }
        });
        return v;
    }

    private void sendToMain(String s, int mCurrentPosition) {
        ((QuestionActivity) getActivity()).sendToMain(s, mCurrentPosition);
    }

    /**
     * OnCheckAnswerListener
     */
    public interface OnCheckAnswerListener {
        void onClickAnswer(int question, String answer);
    }
}
